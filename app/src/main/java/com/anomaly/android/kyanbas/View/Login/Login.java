package com.anomaly.android.kyanbas.View.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Signup.Signup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    TextView textView_signup;
    EditText editText_email,editText_password;
    Button button_login;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email=findViewById(R.id.editText_login_Email);
        editText_password=findViewById(R.id.editText_login_password);
        progressDialog = new ProgressDialog(this);


        button_login = findViewById(R.id.button_login_Login);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });













        textView_signup = findViewById(R.id.textView_signup_login);
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
    }

    private void login() {
        final String email=editText_email.getText().toString().trim();
        final String password =editText_password.getText().toString().trim();

        progressDialog.setMessage("Registering......");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);



                            if(!jsonObject.getBoolean("error"))
                                {

                                }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("password",password);
                return param;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
