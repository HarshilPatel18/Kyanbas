package com.anomaly.android.kyanbas.Network;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Authentication.Login;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    public static final String URL_TEST_TOKEN=API_ROOT_URL+"auth/refresh";


    //user
    public static final String URL_USER_INFO=API_ROOT_URL+"users/me";


    //art
    public static final String URL_ART_LIST=API_ROOT_URL+"art/list";

    public static final String URL_ARTBY_CATEGORY=API_ROOT_URL+"art/category/list/";

    public static final String URL_PRODUCT_BY_CATEGORY=API_ROOT_URL+"product/category/list/";

    public static final String URL_THUMBNAIL_IMAGE="http://staging.rentedcanvas.com/";

    public static final String URL_ARTBY_NICENAME=API_ROOT_URL+"product/view/";

    public static final String URL_VIEW_ART=API_ROOT_URL+"art/view/";





    public static String firstLetterCaps(String data)
    {
        String firstLetter = data.substring(0,1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    public static void testToken(final Context mContext)
    {
        StringRequest refreshRequest=new StringRequest(
                Request.Method.GET,
                Constants.URL_TEST_TOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("success"))
                            {
                                StyleableToast.makeText(mContext,"Session Time Out",R.style.Error).show();
                                SharedPrefManager.getInstance(mContext).logout();

                            }
                            else {
                                JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                SharedPrefManager.getInstance(mContext).userLogin(dataJsonObject.getString(SharedPrefManager.KEY_ACCESS_TOKEN));
                                SharedPrefManager.getInstance(mContext).tokenType(dataJsonObject.getString(SharedPrefManager.KEY_ACCESS_TOKEN_TYPE));
                                StyleableToast.makeText(mContext,"Authorized Token",R.style.Success).show();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(mContext,"Error : "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                String bearer = SharedPrefManager.getInstance(mContext).getTokenType()+SharedPrefManager.getInstance(mContext).getAccessToken().trim();
                Map headers = new HashMap();
                headers.put("Authorization",bearer);
                return headers;
            }
        };

        refreshRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(mContext).addToRequestQueue(refreshRequest);
    }


}
