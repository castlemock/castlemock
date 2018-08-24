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

package com.castlemock.core.basis.utility.compare;


import org.junit.Assert;
import org.junit.Test;


/**
 * @since 1.8
 * @author Karl Dahlgren
 */
public class UrlUtilityTest {

    @Test
    public void compareTest1(){
        Assert.assertTrue(UrlUtility.compareUri("/user", "/user"));
    }

    @Test
    public void compareTest2(){
        Assert.assertTrue(UrlUtility.compareUri("/user/id", "/user/id"));
    }


    @Test
    public void compareTest3(){
        Assert.assertTrue(UrlUtility.compareUri("/user/id.json", "/user/id.json"));
    }

    @Test
    public void compareTest4(){
        Assert.assertTrue(UrlUtility.compareUri("/user/{id}", "/user/1"));
    }

    @Test
    public void compareTest5(){
        Assert.assertTrue(UrlUtility.compareUri("/user/{id}.json", "/user/1.json"));
    }

    @Test
    public void compareTest6(){
        Assert.assertTrue(UrlUtility.compareUri("/user/1.{format}", "/user/1.json"));
        Assert.assertTrue(UrlUtility.compareUri("/user/1.{format}", "/user/1.xml"));
        Assert.assertFalse(UrlUtility.compareUri("/user/1.{format}", "/user/2.xml"));
    }

    @Test
    public void compareTest7(){
        Assert.assertTrue(UrlUtility.compareUri("/user/{id}.{format}", "/user/1.json"));
        Assert.assertTrue(UrlUtility.compareUri("/user/{id}.{format}", "/user/2.xml"));
    }

    @Test
    public void compareTest8(){
        Assert.assertTrue(UrlUtility.compareUri("/company/{company}/user/{id}.{format}", "/company/Castle Mock/user/1.json"));
    }

    @Test
    public void compareTest9(){
        Assert.assertFalse(UrlUtility.compareUri("/user/{id", "/user/1"));
    }

    @Test
    public void testGetPath(){
        Assert.assertEquals("http://castlemock.com/wsdl2.wsdl",
                UrlUtility.getPath("http://castlemock.com/wsdl1.wsdl", "./wsdl2.wsdl"));
    }

    @Test
    public void testGetPathFull(){
        Assert.assertEquals("http://castlemock.com/wsdl2.wsdl",
                UrlUtility.getPath("http://castlemock.com/wsdl1.wsdl", "http://castlemock.com/wsdl2.wsdl"));
    }
}
