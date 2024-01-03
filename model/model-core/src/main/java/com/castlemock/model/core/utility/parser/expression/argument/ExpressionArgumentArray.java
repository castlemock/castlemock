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

package com.castlemock.model.core.utility.parser.expression.argument;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@link ExpressionArgumentArray} is an array argument for an
 * {@link com.castlemock.model.core.utility.parser.expression.Expression} and
 * can either be empty or contain multiple values.
 * @author Karl Dahlgren
 * @since 1.14
 */
public class ExpressionArgumentArray extends ExpressionArgument<List<ExpressionArgument<?>>> implements Iterable<ExpressionArgument<?>> {

    /**
     * Constructor for {@link ExpressionArgumentArray}.
     */
    public ExpressionArgumentArray() {
        super(new ArrayList<>());
    }

    /**
     * Add a new {@link ExpressionArgument} to the array.
     * @param argument The {@link ExpressionArgument} that will be added to the array.
     */
    public void addArgument(final ExpressionArgument<?> argument){
        super.value.add(argument);
    }

    /**
     * Returns an {@link ExpressionArgument} on the provided <code>index</code>.
     * @param index The index of the {@link ExpressionArgument}.
     * @return The {@link ExpressionArgument} on the provided <code>index</code>.
     * @throws IndexOutOfBoundsException: If index is either equal or bigger than the array size.
     */
    public ExpressionArgument<?> getArgument(final int index){
        if(index >= this.value.size()){
            throw new IndexOutOfBoundsException(
                    String.format("Invalid index for argument array (Index: %s}, Size: %s)",
                            index, this.value.size()));
        }
        return this.value.get(index);
    }

    /**
     * Get the size of the argument array.
     * @return The size of the argument array.
     */
    public int getArgumentSize(){
        return this.value.size();
    }

    @Override
    @NonNull
    public Iterator<ExpressionArgument<?>> iterator() {
        return super.value.iterator();
    }
}
