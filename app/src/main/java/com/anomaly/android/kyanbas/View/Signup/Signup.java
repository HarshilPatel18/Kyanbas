package com.anomaly.android.kyanbas.View.Signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Login.Login;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextView textView_login;
    EditText editText_fname,editText_lname,editText_email,editText_password,editText_confirm_password;
    Button signupbtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editText_fname = findViewById(R.id.editText_signup_fname);
        editText_lname = findViewById(R.id.editText_signup_lname);
        editText_email = findViewById(R.id.editText_Signup_Email);
        editText_password = findViewById(R.id.editText_signup_password);
        editText_confirm_password = findViewById(R.id.editText_signup_confirmPassword);


        progressDialog = new ProgressDialog(this);


        signupbtn = findViewById(R.id.button_signup_signup);



    //signing up
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }


        });







































        textView_login = findViewById(R.id.textView_signup_login);
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });



    }


//method Register
    private void Register() {

        final String fname = editText_fname.getText().toString().trim();
        final String lname = editText_lname.getText().toString().trim();
        final String email = editText_email.getText().toString().trim();
        final String password = editText_password.getText().toString().trim();
        final String confirmpassword = editText_confirm_password.getText().toString().trim();

        progressDialog.setMessage("Registering......");
        progressDialog.show();

        StringRequest stringRequest= new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this,response,Toast.LENGTH_SHORT).show();


//================Alert here

                        AlertDialog.Builder signupAlertBuilder = new AlertDialog.Builder(Signup.this)
                                .setMessage("Thanks for signing up! \nPlease check your email to complete your registration.\nGoto?")
                                .setCancelable(false)
                                .setPositiveButton("Mail", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("Log In", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        startActivity(new Intent(Signup.this, Login.class));

                                    }
                                });

                        AlertDialog SignupalertDialog = signupAlertBuilder.create();
                        SignupalertDialog.show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this,error.toString(),Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("first_name",fname);
                param.put("email",email);
                param.put("password",password);
                param.put("password_confirmation",confirmpassword);
                param.put("last_name",lname);

                return param;




            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
