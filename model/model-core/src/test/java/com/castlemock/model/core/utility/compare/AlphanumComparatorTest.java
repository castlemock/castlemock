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

package com.castlemock.model.core.utility.compare;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AlphanumComparatorTest {

    @Test
    public void compareTest1(){
        final AlphanumComparator comparator = new AlphanumComparator();
        final List<String> collection = Arrays.asList("a100", "a1", "a10", "a11", "a101");
        collection.sort(comparator);

        Assert.assertEquals("a1", collection.getFirst());
        Assert.assertEquals("a10", collection.get(1));
        Assert.assertEquals("a11", collection.get(2));
        Assert.assertEquals("a100", collection.get(3));
        Assert.assertEquals("a101", collection.get(4));
    }

}
