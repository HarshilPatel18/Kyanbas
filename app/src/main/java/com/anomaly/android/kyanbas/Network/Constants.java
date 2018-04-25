package com.anomaly.android.kyanbas.Network;

/**
 * Created by Harshil on 21-01-2018.
 */

public class Constants {

    private static final String API_ROOT_URL = "http://staging.rentedcanvas.com/api/";
    //category
    public static final String URL_CATEGORY_PARENT= API_ROOT_URL+"category/list/0";
    //auth
    public static final String URL_LOGIN= API_ROOT_URL+"auth/login";
    public static final String URL_LOGIN_social= API_ROOT_URL+"auth/social";
    public static final String URL_REGISTER=API_ROOT_URL+"auth/register";
    //user
    public static final String URL_USER_INFO=API_ROOT_URL+"/api/users/me";
    //art
    public static final String URL_ART_LIST=API_ROOT_URL+"art/list";
    public static final String URL_ARTBY_CATEGORY=API_ROOT_URL+"art/category/list/";
    public static final String URL_PRODUCT_BY_CATEGORY=API_ROOT_URL+"product/category/list/";
    public static final String URL_THUMBNAIL_IMAGE="http://staging.rentedcanvas.com/";

}
