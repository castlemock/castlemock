/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.core.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class JWTEncoderDecoder {

    private static final String ALGORITHM_NAME = "RSA";
    private static final int KEY_SIZE = 2 * 1024;
    private static final String ISSUER = "castlemock";
    private static final long TOKEN_MAX_AGE_MILLI_SECONDS = 7 * 24 * 60 * 60 * 1000;

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTEncoderDecoder() {
        this.algorithm = getAlgorithm();
        this.verifier = JWT.require(algorithm).build();
    }

    private Algorithm getAlgorithm() {
        try {
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM_NAME);
            kpg.initialize(KEY_SIZE);
            final KeyPair keyPair = kpg.generateKeyPair();

            final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            final RSAPrivateKey privateKey =(RSAPrivateKey) keyPair.getPrivate();

            return Algorithm.RSA256(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    public String createToken(Map<String, String> claims) {
        final Date expiresAt = getExpirationDate();
        final JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(expiresAt);

        claims.forEach(builder::withClaim);
        return builder.sign(algorithm);
    }

    private Date getExpirationDate() {
        final Date expiresAt = new Date();
        expiresAt.setTime(expiresAt.getTime() + TOKEN_MAX_AGE_MILLI_SECONDS);
        return expiresAt;
    }

    public Map<String, Claim> verify(String token) throws IllegalArgumentException {
        try {
            final DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }
}
