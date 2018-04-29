/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.basis.model;

import com.castlemock.core.basis.model.LegacyRepository;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.web.basis.support.FileRepositorySupport;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Repository
public abstract class AbstractLegacyRepositoryImpl<T extends Saveable<I>, D, I extends Serializable> implements LegacyRepository<T, D, I> {

    @Autowired
    protected DozerBeanMapper mapper;
    @Autowired
    protected FileRepositorySupport fileRepositorySupport;

    private Class<T> entityClass;

    private Class<D> dtoClass;

    /**
     * The default constructor for the AbstractRepositoryImpl class. The constructor will extract class instances of the
     * generic types (TYPE and ID). These instances could later be used to identify the types for when interacting
     * with the file system.
     */
    public AbstractLegacyRepositoryImpl() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
    }

    /**
     * The method provides the functionality to convert a Collection of TYPE instances into a list of DTO instances
     * @param types The collection that will be converted into a list of DTO
     * @param clazz CLass of the DTO type (D)
     * @param <T> The type that the operation is managing
     * @param <D> The DTO (Data transfer object) version of the type (TYPE)
     * @return The provided collection but converted into the DTO class
     */
    protected <T, D> List toDtoList(final Collection<T> types, Class<D> clazz) {
        final List<D> dtos = new ArrayList<D>();
        for (T type : types) {
            dtos.add(mapper.map(type, clazz));
        }
        return dtos;
    }


    /**
     * The method provides the functionality to import a entity as a String
     * @param raw The entity as a String
     */
    @Override
    public D importOne(final String raw){

        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (raw.getBytes());
            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final T type = (T) jaxbUnmarshaller.unmarshal(byteArrayInputStream);
            return save(type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save an instance.
     * @param type The instance that will be saved.
     */
    protected abstract D save(T type);


}
