package com.heroku.labshare.constant;

public final class SecurityConstants {

    public static final String SECRET = "e4rtb436h6e44n34neyne54ny4ny5";
    public static final long EXPIRATION_TIME = 604_800_000; // 7 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/auth/register";
    public static final String SIGN_IN_URL = "/api/auth/login";
    public static final String SIGN_OUT_URL = "/api/auth/logout";
    public static final String FETCH_USER = "/api/auth/fetchUser";
    public static final String SAVE_TASK = "/api/data/save";
    public static final String FETCH_DATA = "/api/data/**";

    private SecurityConstants() {
    }
}
