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

package com.fortmocks.web.core.model;

import com.fortmocks.core.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The facade class is responsible for gather all services for a specific type and collaborate with all services
 * in order to provide unified functionality. This enables the using classes to operate all service of a specific type
 * from one single point.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The type that the service facade is managing
 * @param <D> The DTO (Data transfer object) version of the type (TYPE)
 * @param <I> The ID type that is used to identify the type (TYPE)
 * @param <S> The service which provides the basic functionality for the TYPE
 * @see Service
 */
@org.springframework.stereotype.Service
public abstract class ServiceFacadeImpl<T extends Saveable<I>, D extends TypeIdentifiable, I extends Serializable, S extends TypeIdentifiable & TypeConverter<D, D> & Service<T,D,I>> implements ServiceFacade<D, I> {

    @Autowired
    private ApplicationContext applicationContext;
    protected final Map<String, S> services = new HashMap<String, S>();

    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see Service
     * @see TypeIdentifier
     * @see TypeIdentifiable
     */
    protected void initiate(final Class<?> clazz){
        final Map<String, Object> foundServices = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Service.class);

        for(Map.Entry<String, Object> entry : foundServices.entrySet()){
            final Object value = entry.getValue();
            if(clazz.isInstance(value)){
                final S service = (S) value;
                final String type = service.getTypeIdentifier().getType().toUpperCase();
                services.put(type, service);
            }
        }
    }

    /**
     * The method provides the functionality to create and store a DTO instance to a specific service.
     * The service is identified with the provided type value.
     * @param type The type of the instance that will be created
     * @param dto The instance that will be created
     * @return The saved instance
     */
    @Override
    public D save(final String type, final D dto){
        final S service = findByType(type);
        return service.save(service.convertType(dto));
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be deleted
     */
    @Override
    public void delete(final String typeUrl, final I id){
        final S service = findByTypeUrl(typeUrl);
        service.delete(id);
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided dto instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be updated
     * @param dto The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public D update(final String typeUrl, final I id, final D dto){
        final S service = findByTypeUrl(typeUrl);
        return service.update(id, service.convertType(dto));
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<D> findAll(){
        final List<D> dtoList = new LinkedList<D>();
        for(Map.Entry<String, S> entry : services.entrySet()){
            S service = entry.getValue();

            for(D dto : service.findAll()){
                dto.setTypeIdentifier(service.getTypeIdentifier());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    @Override
    public D findOne(String typeUrl, final I id){
        final S service = findByTypeUrl(typeUrl);
        final D dto = service.findOne(id);
        final TypeIdentifier typeIdentifier = service.getTypeIdentifier();
        dto.setTypeIdentifier(typeIdentifier);
        return dto;
    }

    /**
     * The method retrieves all service types
     * @return A list with all the service types
     */
    @Override
    public List<String> getTypes(){
        return new LinkedList<String>(services.keySet());
    }

    /**
     * The method is responsible for finding a service class that has the same type url as the
     * one provided.
     * @param typeUrl The type url which the service class must match
     * @return A service that has the same type url as the one provided
     * @throws IllegalArgumentException A IllegalArgumentException will be thrown
     * if no service matches the provided type
     */
    protected S findByTypeUrl(final String typeUrl){
        S service = null;
        for(S tmpService : services.values()){
            if(tmpService.getTypeIdentifier().getTypeUrl().equals(typeUrl)){
                service = tmpService;
                break;
            }
        }

        if(service == null){
            throw new IllegalArgumentException("Invalid type");
        }

        return service;
    }

    /**
     * The method provides the functionality to find a service class that has the same type
     * as the one provided.
     * @param type The type that the service has to match
     * @return A service that has the same type as the one provided
     * @throws IllegalArgumentException A IllegalArgumentException will be thrown
     * If no service matches the provided type
     */
    protected S findByType(final String type){
        final S service = services.get(type);

        if(service == null){
            throw new IllegalArgumentException("Invalid type");
        }

        return service;
    }

    /**
     * Returns the type URL for a specific type
     * @param type The type that will be used to retrieve the type URL
     * @return The matching type URL
     * @throws IllegalArgumentException A IllegalArgumentException will be thrown
     * If no service matches the provided type
     */
    @Override
    public String getTypeUrl(final String type){
        final S service = findByType(type);
        return service.getTypeIdentifier().getTypeUrl();
    }
}
