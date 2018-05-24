/*
 * Copyright (C) 2013 The Flogger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.flogger;

import com.google.common.flogger.parser.DefaultPrintfMessageParser;
import com.google.common.flogger.parser.MessageParser;
import com.google.common.flogger.util.Checks;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.logging.Level;
import javax.annotation.Nullable;

/**
 * Implementation of any Google specific extensions to the default fluent logging API. This could
 * be implemented purely as an inner class inside GoogleLogger, but by making it abstract and top
 * level, it allows other teams to subclass it to add additional functionality.
 *
 * @param <LOGGER> The logger implementation from which this context is produced.
 * @param <API> The logging api supported by this context.
 */
@CheckReturnValue
public abstract class GoogleLogContext<
        LOGGER extends AbstractLogger<API>, API extends GoogleLoggingApi<API>>
    extends LogContext<LOGGER, API> implements GoogleLoggingApi<API> {

  /**
   * Creates a logging context for the GoogleLoggerApi.
   *
   * @param level the log level of this log statement.
   * @param isForced whether the log statement should be forced.
   */
  protected GoogleLogContext(Level level, boolean isForced) {
    super(level, isForced);
  }

  // This is made final to prevent teams within Google switching parsers without contacting the
  // Java core libraries team first. In general we expect all instances of GoogleLogger inside
  // Google to use the same syntax for place-holders to avoid potential confusion and bugs.
  @Override
  protected final MessageParser getMessageParser() {
    return DefaultPrintfMessageParser.getInstance();
  }

  @Override
  public final <T> API with(MetadataKey<T> key, @Nullable T value) {
    // Null keys are always bad (even if the value is also null). This is one of the few places
    // where the logger API will throw a runtime exception (and as such it's important to ensure
    // the NoOp implementation also does the check). The reasoning for this is that the metadata
    // key is never expected to be passed user data, and should always be a static constant.
    // Because of this it's always going to be an obvious code error if we get a null here.
    Checks.checkNotNull(key, "metadata key");
    if (value != null) {
      addMetadata(key, value);
    }
    return api();
  }
}
