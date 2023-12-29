package com.castlemock.model.core.utility;

import org.apache.commons.lang3.RandomStringUtils;

public final class IdUtility {

    private IdUtility() {

    }

    /**
     * The method is responsible for generating new ID for the entity. The
     * ID will be six character and contain both characters and numbers.
     * @return A generated ID
     */
    public static String generateId(){
        return RandomStringUtils.random(6, true, true);
    }

}
