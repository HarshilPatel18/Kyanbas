package com.anomaly.android.kyanbas.View.Main;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.RecyclerviewBaseAdapter;
import com.anomaly.android.kyanbas.Adapter.ViewPagerAdapter;
import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Modal.Category;
import com.anomaly.android.kyanbas.Modal.User;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.Internet.ConnectivityReceiver;
import com.anomaly.android.kyanbas.Network.Internet.MyApplication;
import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Authentication.Login;
import com.anomaly.android.kyanbas.View.ImageViews.PicassoCircleTransformation;
import com.anomaly.android.kyanbas.View.Profile.CartActivity;
import com.anomaly.android.kyanbas.View.Profile.Profile;
import com.anomaly.android.kyanbas.View.Profile.WishlistActivity;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View headerView;
    private View connErrorview;
    TextView navHeaderUsername,cartItemCount;
    ImageView navHeaderUserProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View
        connErrorview=findViewById(R.id.connectionErrorLayout);
        connErrorview.setVisibility(View.VISIBLE);

        //Tabs
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);

        //Navigation Drawer
        mDrawerLayout=findViewById(R.id.drawerLayout);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));

        mToolbar=findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView toolbarCart=findViewById(R.id.imagviewCartToolbar);
        toolbarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
                {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                }
                else {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        headerView=navigationView.getHeaderView(0);
        navHeaderUsername=(TextView)headerView.findViewById(R.id.textviewUsernameNavDrawer);
        navHeaderUserProfilePic = headerView.findViewById(R.id.imageviewProfileNavDrawer);

        cartItemCount=findViewById(R.id.textViewCartItemCount);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        /*register connection status listener*/
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (isConnected) {

            connErrorview.setVisibility(View.GONE);
            setCategoriesTabs();
            onStart();
        }
        else {


            StyleableToast.makeText(getApplicationContext(),"Not connected to internet !",R.style.Error).show();

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_cart){
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        else if(id==R.id.nav_wishlist)
        {
            startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            //this.finishAffinity();
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(ConnectivityReceiver.isConnected())
        {
            if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                Constants.testToken(getApplicationContext());

                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                    getUserInfo();
                    setProfile();
                    cartCount();

                }
                else{

                    setLogin();
                }

            }
            else {
                setLogin();
            }


        }

    }


    //Requests==================================================================================================================================


    private void setCategoriesTabs() {

        connErrorview.setVisibility(View.GONE);

        //Adding Tab Fragments

        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.URL_CATEGORY_PARENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
                    adapter.addFragment(HomeFragment.newInstance("Home"),"Home");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(ResponseKeys.JSON_DATA_WRAPPER);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jo = jsonArray.getJSONObject(i);
                        Category category=new Category(jo.getInt(ResponseKeys.CATEGORY_ID),jo.getString(ResponseKeys.CATEGORY_NAME),jo.getInt(ResponseKeys.CATEGORY_PARENT_ID),jo.getString(ResponseKeys.CATEGORY_DESC),jo.getString(ResponseKeys.CATEGORY_NICENAME));
                        adapter.addFragment(CategoryFragment.newInstance(category.getId()),category.getName());
                    }
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Cache.Entry entry = this.getCacheEntry();
                    if(entry != null) {
                        Response<String> response = parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        deliverResponse(response.result);
                        return;
                    }
                }
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

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
                                StyleableToast.makeText(getApplicationContext(),"Session Time Out",R.style.Error).show();
                                SharedPrefManager.getInstance(getApplicationContext()).logout();
                            }
                            else {

                                //code here================================================================

                                JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                //Toast.makeText(MainActivity.this,dataJsonObject.toString(),Toast.LENGTH_LONG).show();


                                String name = dataJsonObject.get("first_name")+" "+dataJsonObject.get("last_name");

                                navHeaderUsername.setText(name);

                                Picasso.get()
                                        .load(Constants.URL_THUMBNAIL_IMAGE+dataJsonObject.get("profile_picture"))
                                        .transform(new PicassoCircleTransformation())
                                        .fit()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_art_vector_placeholder)
                                        .into(navHeaderUserProfilePic);


                            }
                        } catch (JSONException e) {
                            //SharedPrefManager.getInstance(getApplicationContext()).logout();
                            //Toast.makeText(MainActivity.this,"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //SharedPrefManager.getInstance(getApplicationContext()).logout();
                        //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
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


    //ChangeClicks on Profile

    private void setLogin()
    {
        navHeaderUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });


        navHeaderUserProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }

    private void setProfile()
    {

        navHeaderUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });


        navHeaderUserProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });
    }

    private void cartCount()
    {
        StringRequest cartRequest=new StringRequest(Request.Method.GET, Constants.URL_CART_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("success"))
                    {
                        if(!jsonObject.getBoolean("success"))
                        {
                            cartItemCount.setVisibility(View.GONE);
                            Log.e("CART","Empty");
                        }
                    }
                    else
                    {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        int count=0;
                        for (int i = 0; i < jsonArray.length(); i++) {


                            JSONObject artJson = jsonArray.getJSONObject(i);
                            count++;
                        }
                        cartItemCount.setVisibility(View.VISIBLE);
                        if(count>9)
                        {
                            cartItemCount.setText("9+");
                        }
                        else
                        {
                            cartItemCount.setText(String.valueOf(count));
                        }


                    }


                } catch (JSONException e) {


                    Log.e("CART",e.getLocalizedMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("CART",error.getLocalizedMessage());

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

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

        cartRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(cartRequest);
    }

}
