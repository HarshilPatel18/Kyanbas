package com.anomaly.android.kyanbas.View.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    ImageView imageViewUserDp;
    TextView textViewusername,textViewuseremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewUserDp = findViewById(R.id.imageview_Profiledp);
        textViewusername = findViewById(R.id.textview_ProfileName);
        textViewuseremail = findViewById(R.id.textview_ProfileEmail);

        getUserInfo();
    }











//Methods Here========================================================================================

    private void getUserInfo(){



        StringRequest stringRequest=new StringRequest(
                Request.Method.GET,
                Constants.URL_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("success"))
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                            }
                            else {

                                //code here================================================================

                                JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                Toast.makeText(getApplicationContext(),dataJsonObject.toString(),Toast.LENGTH_LONG).show();


                                String name = dataJsonObject.get("first_name")+" "+dataJsonObject.get("last_name");
                                textViewusername.setText(name);
                                textViewuseremail.setText(dataJsonObject.get("email").toString());


                                Picasso.get()
                                        .load(Constants.URL_THUMBNAIL_IMAGE+dataJsonObject.get("profile_picture"))
                                        .fit()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_art_vector_placeholder)
                                        .into(imageViewUserDp);







                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();


                        error.printStackTrace();

                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            /** Passing some request headers* */
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                String bearer = "Bearer ".concat(SharedPrefManager.getInstance(getApplicationContext()).getAccessToken().trim());
                Map headers = new HashMap();
                headers.put("Authorization",bearer);
                return headers;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }






}
