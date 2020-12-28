/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.model.core;

/**
 * The Input message class contains all arguments and information required for a service to process a
 * its functionality. All validation annotations in the input message will be controlled and their roles
 * will be enforced.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Service
 */
public interface Input extends Message {
}
