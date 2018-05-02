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
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.ViewPagerAdapter;
import com.anomaly.android.kyanbas.Modal.Category;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.Internet.ConnectivityReceiver;
import com.anomaly.android.kyanbas.Network.Internet.MyApplication;
import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Login.Login;
import com.anomaly.android.kyanbas.View.Profile.Profile;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    NavigationView navigationView;
    TabLayout tabLayout;

    View headerView;
    TextView navHeaderUsername;
    ImageView navHeaderUserdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tabs
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.INVISIBLE);

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
                StyleableToast.makeText(getApplicationContext(),"Clicked on Cart",R.style.Success).show();
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        headerView=navigationView.getHeaderView(0);
        navHeaderUsername=(TextView)headerView.findViewById(R.id.textviewUsernameNavDrawer);
        navHeaderUserdp = headerView.findViewById(R.id.imageviewProfileNavDrawer);

        //onclick here===================================================

        navHeaderUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    startActivity(new Intent(MainActivity.this,Profile.class));

                }
                else{
                    startActivity(new Intent(MainActivity.this,Login.class));
                }

            }
        });












    }

    private void setCategoriesTabs() {


           tabLayout.setVisibility(View.VISIBLE);
           final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);



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

            setCategoriesTabs();
        }
        else {

            StyleableToast.makeText(getApplicationContext(),"Not connected to internet !",R.style.Error).show();

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();



        if(id==R.id.nav_logout)
        {

            SharedPrefManager.getInstance(this).logout();

            this.mDrawerLayout.closeDrawer(GravityCompat.START);

            Toast.makeText(this,"You Are Succesfully Logged Out",Toast.LENGTH_SHORT).show();
            onStart();
        }

        else if(id==R.id.nav_cart){
            this.mDrawerLayout.closeDrawer(GravityCompat.START);

            //startActivity(new Intent(MainActivity.this, ViewArt.class));
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


    //header==================================================================================================================================

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
                                Toast.makeText(MainActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                            }
                            else {

                                //code here================================================================

                                JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                Toast.makeText(MainActivity.this,dataJsonObject.toString(),Toast.LENGTH_LONG).show();


                                String name = dataJsonObject.get("first_name")+" "+dataJsonObject.get("last_name");



                                navHeaderUsername.setText(name);

                                Picasso.get()
                                        .load(Constants.URL_THUMBNAIL_IMAGE+dataJsonObject.get("profile_picture"))
                                        .fit()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_art_vector_placeholder)
                                        .into(navHeaderUserdp);


                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();


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
                String bearer = "Bearer ".concat(SharedPrefManager.getInstance(getApplicationContext()).GetAccessToken().trim());
                Map headers = new HashMap();
                headers.put("Authorization",bearer);
                return headers;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //changing navigation drawer value here==================================


        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

            getUserInfo();

        }
        else{
            //Toast.makeText(MainActivity.this, "Not Logged In !", Toast.LENGTH_LONG).show();
        }

    }
}
