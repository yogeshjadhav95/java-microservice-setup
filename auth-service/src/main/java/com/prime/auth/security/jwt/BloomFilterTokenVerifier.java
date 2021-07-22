package com.prime.auth.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterTokenVerifier implements TokenVerifier {

    @Override
    public boolean verify(String jti) {
        return true;
    }
}
