package com.anomaly.android.kyanbas.View.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;


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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation Drawer
        mDrawerLayout=findViewById(R.id.drawerLayout);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));

        mToolbar=findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setCategoriesTabs() {
           final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
                           adapter.addFragment(CategoryFragment.newInstance(category.getNicename()),category.getName());
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
            Toast.makeText(this,"Not connected to internet !",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_login)
        {
            startActivity(new Intent(this, Login.class));
        }

        else if(id==R.id.nav_logout)
        {

            SharedPrefManager.getInstance(this).logout();

            this.mDrawerLayout.closeDrawer(GravityCompat.START);

            Toast.makeText(this,"You Are Succesfully Logged Out",Toast.LENGTH_SHORT).show();
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



}
