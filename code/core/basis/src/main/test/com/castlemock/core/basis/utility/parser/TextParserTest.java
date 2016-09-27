/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.core.basis.utility.parser;

import org.junit.Assert;
import org.junit.Test;


/**
 * @since 1.6
 * @author Karl Dahlgren
 */
public class TextParserTest {

    @Test
    public void testParseRandomInteger(){
        String input = "Hello this is a {RANDOM_INTEGER}.";
        String output = TextParser.parse(input);
        Assert.assertNotEquals(input, output);
        Assert.assertTrue(output.matches("Hello this is a (.*?)."));
    }

    @Test
    public void testParseRandomDouble(){
        String input = "Hello this is a {RANDOM_DOUBLE}.";
        String output = TextParser.parse(input);
        Assert.assertNotEquals(input, output);
        Assert.assertTrue(output.matches("Hello this is a (.*?)."));
    }

    @Test
    public void testParseRandomLong(){
        String input = "Hello this is a {RANDOM_LONG}.";
        String output = TextParser.parse(input);
        Assert.assertNotEquals(input, output);
        Assert.assertTrue(output.matches("Hello this is a (.*?)."));
    }

    @Test
    public void testParseRandomFloat(){
        String input = "Hello this is a {RANDOM_FLOAT}.";
        String output = TextParser.parse(input);
        Assert.assertNotEquals(input, output);
        Assert.assertTrue(output.matches("Hello this is a (.*?)."));
    }

}
