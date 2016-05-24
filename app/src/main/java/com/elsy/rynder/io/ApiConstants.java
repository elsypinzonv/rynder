package com.elsy.rynder.io;


public class ApiConstants {

    public static final String BASE_URL = "http://www.hungrr.com.mx";
    public static final String API_PATH = "/api";
    public static final String VERSION_PATH = "/v1";
    public static final String BASE_API_URL = BASE_URL + API_PATH + VERSION_PATH;

    //Login
    public static final String LOGIN_PATH = "/login";
    public static final String RESTAURANTS_PATH = "/restaurants";
    public static final String LOGIN_URL = BASE_API_URL + LOGIN_PATH;

    //Restaurants

    public static final String KEY_LAT_PATH = "lat";
    public static final String KEY_LNG_PATH = "lng";
    public static final String KEY_BUDGET_MIN_PATH = "budgetMin";
    public static final String KEY_BUDGET_MAX_PATH = "budgetMax";

    //Relative fragments
    public static final String LATITUDE_RELATIVE_PATH = "/{"+ KEY_LAT_PATH +"}";
    public static final String LONGITUDE_RELATIVE_PATH = "/{"+ KEY_LNG_PATH +"}";
    public static final String BUDGET_MIN_RELATIVE_PATH = "/{"+ KEY_BUDGET_MIN_PATH +"}";
    public static final String BUDGET_MAX_RELATIVE_PATH = "/{"+ KEY_BUDGET_MAX_PATH +"}";

    public static final String RESTAURANTS_LEVEL_2_URL = BASE_API_URL + RESTAURANTS_PATH +
            LATITUDE_RELATIVE_PATH + LONGITUDE_RELATIVE_PATH +
            BUDGET_MIN_RELATIVE_PATH + BUDGET_MAX_RELATIVE_PATH;

    //headers
    public static final String HEADER_RESPONSE_TOKEN = "token";
    public static final String HEADER_REQUEST_TOKEN = "Authorization";
}
