package com.prime.auth.security.jwt;

public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "P_" + this.name();
    }
}
