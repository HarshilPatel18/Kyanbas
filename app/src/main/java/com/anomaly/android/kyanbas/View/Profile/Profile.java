package com.anomaly.android.kyanbas.View.Profile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Authentication.Login;
import com.anomaly.android.kyanbas.View.ImageViews.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    ImageView imageViewUserProfilePic;
    TextView textViewUserName,textViewUserEmail;
    CardView cardAddart,cardUploads,cardMyCart,cardMyWishlist,
            cardMyOrders,cardMyAddress,cardEditProfile,cardLogout;
    LinearLayout linearLayoutProfileContent,linearLayoutProfileBottom;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar= findViewById(R.id.includeToolbarUserProfile);
        TextView toolbarText=findViewById(R.id.textViewToolbar);
        toolbarText.setText("My Account");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        linearLayoutProfileContent=findViewById(R.id.linearLayoutProfileContent);
        linearLayoutProfileBottom=findViewById(R.id.linearLayoutProfileBottomContent);
        progressBar=findViewById(R.id.progressBar);

        imageViewUserProfilePic=findViewById(R.id.imageViewProfile);
        textViewUserName=findViewById(R.id.textViewProfileName);
        textViewUserEmail=findViewById(R.id.textViewProfileEmail);
        cardAddart=findViewById(R.id.cardAddArt);
        cardUploads=findViewById(R.id.cardMyUploads);
        cardMyCart=findViewById(R.id.cardMyCart);
        cardMyWishlist=findViewById(R.id.cardMyWishlist);
        cardMyOrders=findViewById(R.id.cardMyOrders);
        cardMyAddress=findViewById(R.id.cardMyAddress);
        cardEditProfile=findViewById(R.id.cardEditProfile);
        cardLogout=findViewById(R.id.cardLogut);

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            getUserInfo();
        }
        else {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }







//Methods Here========================================================================================

    private void getUserInfo(){

        progressBar.setVisibility(View.VISIBLE);

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
                                //Toast.makeText(getApplicationContext(),dataJsonObject.toString(),Toast.LENGTH_LONG).show();

                                String fullname = Constants.firstLetterCaps(dataJsonObject.getString("first_name")) +" "+Constants.firstLetterCaps(dataJsonObject.getString("last_name"));

                                textViewUserName.setText(fullname);
                                textViewUserEmail.setText(dataJsonObject.getString("email").toString());


                                Picasso.get()
                                        .load(Constants.URL_THUMBNAIL_IMAGE+dataJsonObject.get("profile_picture"))
                                        .transform(new PicassoCircleTransformation())
                                        .fit()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_art_vector_placeholder)
                                        .into(imageViewUserProfilePic);

                                progressBar.setVisibility(View.GONE);
                                linearLayoutProfileContent.setVisibility(View.VISIBLE);
                                linearLayoutProfileBottom.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
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
                Map headers = new HashMap();
                if(SharedPrefManager.getInstance(getApplicationContext()).getAccessToken()!=null)
                {
                    String bearer = "bearer "+SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
                    headers.put("Authorization",bearer);
                    return headers;
                }
                else{
                    String bearer = "bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vc3RhZ2luZy5yZW50ZWRjYW52YXMuY29tL2FwaS9hdXRoL2xjU4OTY0OTUsIm5iZiI6MTUyNTg5Mjg5N";
                    headers.put("Authorization",bearer);

                }
                return headers;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }






}
