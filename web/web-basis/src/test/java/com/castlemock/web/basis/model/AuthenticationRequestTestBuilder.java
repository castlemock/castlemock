package com.castlemock.web.basis.model;

import com.castlemock.web.basis.model.authentication.AuthenticationRequest;

public class AuthenticationRequestTestBuilder {

    private AuthenticationRequestTestBuilder(){

    }

    public static AuthenticationRequest.Builder builder(){
        return AuthenticationRequest.builder()
            .username("username")
            .password("password");
    }


}
