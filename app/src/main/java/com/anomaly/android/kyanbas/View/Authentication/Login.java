package com.anomaly.android.kyanbas.View.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    private static final String TAG = "SignInActivity";
    TextView textView_signup;
    EditText editText_email, editText_password;
    Button button_login;
    LoginButton button_fbLogin;
    ProgressDialog progressDialog;
    CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 9001;

    private static String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();


        editText_email = findViewById(R.id.editText_login_Email);
        editText_password = findViewById(R.id.editText_login_password);
        progressDialog = new ProgressDialog(this);


        //login--------------------------------------------------------------------

        button_login =

                findViewById(R.id.button_login_Login);


        button_login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textView_signup =

                findViewById(R.id.textView_signup_login);
        textView_signup.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });


        //fb login==============================================
        button_fbLogin = (LoginButton) findViewById(R.id.button_login_Facebook);

        button_fbLogin.setReadPermissions(Arrays.asList(

                "public_profile", "email", "user_birthday", "user_friends"

        ));


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        // App code
                        Toast.makeText(Login.this, loginResult.toString(), Toast.LENGTH_LONG).show();

                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject fbobject, GraphResponse response) {

                                        Log.v("LoginActivity", response.toString());

                                        Toast.makeText(Login.this, "fbobject\n" + fbobject.toString()+"\n\n response\n"+response.toString()+"\n\nlogin result\n"+loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();

                                        response.getJSONObject();



                                        // Application code

                                        Sociallogin(loginResult.getAccessToken().getToken(), "facebook");


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        //google login----------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        final SignInButton googlesignInButton = findViewById(R.id.button_login_google);
        googlesignInButton.setSize(SignInButton.SIZE_STANDARD);


        googlesignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

    }



//MEthods Starts here===============================================================================================================



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Toast.makeText(Login.this,"Log in Successful !",Toast.LENGTH_LONG).show();

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]







    private void login() {
        final String email=editText_email.getText().toString().trim();
        final String password =editText_password.getText().toString().trim();

        progressDialog.setMessage("Logging in......");
        progressDialog.show();

        StringRequest loginRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        //Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                                if(!jsonObject.getBoolean("success"))
                                {
                                    Toast.makeText(Login.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                                }
                                else {
                                    JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(dataJsonObject.getString(SharedPrefManager.KEY_ACCESS_TOKEN));
                                    SharedPrefManager.getInstance(getApplicationContext()).tokenType(dataJsonObject.getString(SharedPrefManager.KEY_ACCESS_TOKEN_TYPE));

                                    StyleableToast.makeText(getApplicationContext(),"Login Successful !", R.style.Success).show();
                                    finish();
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
                        //Toast.makeText(Login.this,"Invalid Credentials !",Toast.LENGTH_LONG).show();
                        if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {

                            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailure Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnection Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Timeout Error", Toast.LENGTH_SHORT).show();

                        }

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

        loginRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(loginRequest);

    }



    private void Sociallogin(final String Accesstoken, final String platform)
        {
            StringRequest stringRequest=new StringRequest(
                    Request.Method.POST,
                    Constants.URL_LOGIN_social,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(!jsonObject.getBoolean("success"))
                                {
                                    Toast.makeText(Login.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                                }
                                else {
                                    JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(dataJsonObject.getString(SharedPrefManager.KEY_ACCESS_TOKEN));
                                    finish();
                                }



                            } catch (JSONException e) {
                                Toast.makeText(Login.this,"exception error",Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<>();
                    param.put("provider",platform);
                    param.put("token",Accesstoken);


                    return param;
                }

            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }






    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }



    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {

            Toast.makeText(this,account.getClass().toString(),Toast.LENGTH_LONG).show();


            //Sociallogin(account.getClass().getIdToken().toString(),"google");

            //findViewById(R.id.button_login_google).setVisibility(View.GONE);

        } else {
            findViewById(R.id.button_login_google).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplication());
        if(sharedPrefManager.isLoggedIn()) {

            Toast.makeText(Login.this, "Already Logged In !", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(Login.this, "Not Logged In !", Toast.LENGTH_LONG).show();
        }

    }

}
