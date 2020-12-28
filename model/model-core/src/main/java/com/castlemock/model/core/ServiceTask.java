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
 * The ServiceTask represent a service task that can be processed by a service class.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Input
 */
public class ServiceTask<I extends Input> {

    private I input;
    private String serviceConsumer;

    /**
     * Default constructor for ServiceTask
     */
    public ServiceTask() {
    }

    /**
     * Constructor for ServiceTask
     * @param input The input that will be processed by the task
     */
    public ServiceTask(I input) {
        this.input = input;
    }

    /**
     * The input message for the service layer. The input is used to identify which service
     * class is responsible for processing the task.
     * @return The input value
     */
    public I getInput() {
        return input;
    }

    /**
     * Sets a new input value
     * @param input The new input value that will replace the old value.
     */
    public void setInput(final I input) {
        this.input = input;
    }

    /**
     * The serviceConsumer is used to identify the user that is executing the task
     * @return The service consumer value
     */
    public String getServiceConsumer() {
        return serviceConsumer;
    }

    /**
     * Sets a new serviceConsumer value
     * @param serviceConsumer The new execture value that will replace the old value.
     */
    public void setServiceConsumer(final String serviceConsumer) {
        this.serviceConsumer = serviceConsumer;
    }
}
