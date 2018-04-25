package com.anomaly.android.kyanbas.View.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Login.Login;

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
        getuserinfo();
    }











//Methods Here========================================================================================

    private void getuserinfo() {

        imageViewUserDp = findViewById(R.id.imageview_Profiledp);
        textViewusername = findViewById(R.id.textview_ProfileName);
        textViewuseremail = findViewById(R.id.textview_ProfileEmail);


        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("success"))
                            {
                                Toast.makeText(Profile.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                            }
                            else {

                            }
                        } catch (JSONException e) {
                            Toast.makeText(Profile.this,"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {



                HashMap<String,String> headers  = new HashMap<>();
                headers.put("Authorization","bearer "+SharedPrefManager.KEY_ACCESS_TOKEN);
                return headers;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);





    }
}
