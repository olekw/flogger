/*
 * Copyright (C) 2018 The Flogger Authors.
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

package com.google.common.flogger.backend;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Callback interface to handle additional contextual key/value pairs with log statements. Used
 * for both {@code Tags} and {@code Metadata}.
 */
public interface KeyValueHandler {
  /**
   * Handle a single key value pair of contextual metadata for a log statement. Note that it is
   * permitted for the value to be null if a tag was added by name only.
   */
  KeyValueHandler handle(String key, @Nullable Object value);
}
