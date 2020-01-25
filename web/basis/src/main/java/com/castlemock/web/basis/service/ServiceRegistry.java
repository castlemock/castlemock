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

package com.castlemock.web.basis.service;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The service registry is a registry for all the services. The class provides the functionality to
 * map an input message to a service class that is responsible for processing the incoming message.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Input
 * @see Output
 * @see Service
 */
@Component
public class ServiceRegistry<I extends Input, O extends Output> {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * The collection contains all the services and their identifiers (The input message class)
     */
    private Map<Class<I>, Service<I,O>> services = new HashMap<Class<I>, Service<I,O>>();

    /**
     * The method provides the functionality to retrieve a specific service that is identified
     * with the provided input parameter
     * @param input The input message. The message is used to identify the service class responsible
     *              for processing the incoming input message and generating an output message
     * @return The service class that is identified with the input message
     */
    public Service<I,O> getService(final I input){
        return services.get(input.getClass());
    }

    /**
     * The initialize method is responsible for initializing the service registry.
     * The initialize method will search for all the classes with the @service annotation and
     * store all the classes that are an instance of the Service class.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void initialize(){
        final Map<String, Object> components = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Service.class);
        for(Map.Entry<String, Object> entry : components.entrySet()){
            final Object value = entry.getValue();
            if(value instanceof Service){
                final Service service = (Service) value;
                final Class<?>[] processorInputOutputClasses = GenericTypeResolver.resolveTypeArguments(service.getClass(), Service.class);
                if(processorInputOutputClasses != null && processorInputOutputClasses.length > 0){
                    final Class<I> processorInputClass = (Class<I>) processorInputOutputClasses[0];
                    services.put(processorInputClass, service);
                }
            }
        }
    }

}
