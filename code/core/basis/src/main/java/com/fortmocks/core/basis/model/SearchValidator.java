package com.fortmocks.core.basis.model;

/**
 * The search validator is used to validate provide input towards a provided query
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchValidator {


    public static final boolean validate(final String input, final String query){
        return input.toLowerCase().contains(query.toLowerCase());
    }

}
