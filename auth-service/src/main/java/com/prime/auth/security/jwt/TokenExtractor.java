package com.prime.auth.security.jwt;

public interface TokenExtractor {

    String extract(String payload);

}
