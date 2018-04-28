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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Repository
public abstract class AbstractLegacyRepositoryImpl<T extends Saveable<I>, I extends Serializable> implements LegacyRepository<T, I> {

    @Autowired
    protected DozerBeanMapper mapper;
    @Autowired
    protected FileRepositorySupport fileRepositorySupport;

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

}
