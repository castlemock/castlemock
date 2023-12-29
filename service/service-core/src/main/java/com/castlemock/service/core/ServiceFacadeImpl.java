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

package com.castlemock.service.core;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceAdapter;
import com.castlemock.model.core.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The facade class is responsible for gather all services for a specific type and collaborate with all services
 * in order to provide unified functionality. This enables the using classes to operate all service of a specific type
 * from one single point.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Service
 */
@org.springframework.stereotype.Service
public abstract class ServiceFacadeImpl<D, I extends Serializable, SA extends ServiceAdapter<D,D,I>> implements ServiceFacade<D,I> {

    @Autowired
    private ApplicationContext applicationContext;
    protected final Set<SA> services = new HashSet<>();

    /**
     * The initialize method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @param clazz The class of the {@link ServiceAdapter} that the facade is managing
     * @see Service
     */
    @SuppressWarnings("unchecked")
    protected void initiate(final Class<?> clazz){
        final Map<String, Object> foundServices = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Service.class);

        for(Map.Entry<String, Object> entry : foundServices.entrySet()){
            final Object value = entry.getValue();
            if(clazz.isInstance(value)){
                final SA serviceAdapter = (SA) value;
                services.add(serviceAdapter);
            }
        }
    }

}
