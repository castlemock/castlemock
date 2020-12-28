package com.castlemock.model.core.model;

import org.junit.Assert;
import org.junit.Test;

public class SearchValidatorTest {

    @Test
    public void testValidateValid(){
        Assert.assertTrue(SearchValidator.validate("AbCdEf", "aBcDeF"));
    }

    @Test
    public void testValidateInvalid(){
        Assert.assertFalse(SearchValidator.validate("Abc", "Def"));
    }

}
