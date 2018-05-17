/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.core.basis.utility.parser.expression.argument;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@link ExpressionArgumentMap} is an String argument for an
 * {@link com.castlemock.core.basis.utility.parser.expression.Expression}.
 * @author Karl Dahlgren
 * @since 1.14
 */
public class ExpressionArgumentMap extends ExpressionArgument<Map<Object,ExpressionArgument<?>>> {

    /**
     * Constructor for {@link ExpressionArgumentMap}.
     */
    public ExpressionArgumentMap() {
        super(new HashMap<>());
    }

    /**
     * Add a new {@link ExpressionArgument} to the map
     * @param key The key that is used to identify the argument.
     * @param argument The {@link ExpressionArgument} that will be added to the array.
     */
    public void addArgument(final Object key, final ExpressionArgument<?> argument){
        super.value.put(key, argument);
    }

    /**
     * Returns an {@link ExpressionArgument} on the provided <code>key</code>.
     * @param key The key used to identify the {@link ExpressionArgument}.
     * @return The {@link ExpressionArgument} identified the provided <code>key</code>.
     */
    public ExpressionArgument<?> getArgument(final Object key){
        return super.value.get(key);
    }

    /**
     * Get the size of the argument map.
     * @return The size of the argument map.
     */
    public int getArgumentSize(){
        return this.value.size();
    }

}
