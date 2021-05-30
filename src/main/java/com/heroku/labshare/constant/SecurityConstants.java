package com.heroku.labshare.constant;

public final class SecurityConstants {

    public static final String SECRET = "e4rtb436h6e44n34neyne54ny4ny5";
    public static final long EXPIRATION_TIME = 604_800_000; // 7 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = "/api/auth/register";
    public static final String SIGN_IN_URL = "/api/auth/login";
    public static final String SIGN_OUT_URL = "/api/auth/logout";
    public static final String FETCH_USER_URL = "/api/auth/fetchUser";

    public static final String SAVE_TASK_URL = "/api/data/save";
    public static final String FACULTY_URL = "/api/data/faculty";
    public static final String SPECIALTY_URL = "/api/data/specialty";
    public static final String SUBJECT_URL = "/api/data/subject";
    public static final String DOWNLOAD_LINK_URL = "/api/data/downloadLink";
    public static final String SEARCH_URL = "/api/data/search";
    public static final String LIKE_URL = "/api/data/like";
    public static final String FETCH_TASK_URL = "/api/auth/fetchTask";
    public static final String ADVANCED_SEARCH_URL = "/api/data/advanced/search";
    public static final String CRAWLER_URL = "/api/data/advanced/crawler";

    private SecurityConstants() {
    }
}
