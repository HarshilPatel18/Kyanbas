package com.anomaly.android.kyanbas.View.Main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.ViewPagerAdapter;
import com.anomaly.android.kyanbas.Modal.Category;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=findViewById(R.id.drawerLayout);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));

        mToolbar=findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);



        //Adding Tab Fragments

        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.URL_CATEGORY_PARENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
                    adapter.addFragment(CategoryFragment.getInstance("Home"),"Home");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(ResponseKeys.JSON_DATA_WRAPPER);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jo = jsonArray.getJSONObject(i);
                        Category category=new Category(jo.getInt(ResponseKeys.CATEGORY_ID),jo.getString(ResponseKeys.CATEGORY_NAME),jo.getInt(ResponseKeys.CATEGORY_PARENT_ID),jo.getString(ResponseKeys.CATEGORY_DESC),jo.getString(ResponseKeys.CATEGORY_NICENAME));
                        adapter.addFragment(CategoryFragment.getInstance(category.getNicename()),category.getName());
                    }
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        /*adapter.addFragment(CategoryFragment.getInstance(getResources().getString(R.string.tab_home)),getResources().getString(R.string.tab_home));
        adapter.addFragment(CategoryFragment.getInstance(getResources().getString(R.string.tab_painting)),getResources().getString(R.string.tab_painting));
        adapter.addFragment(CategoryFragment.getInstance(getResources().getString(R.string.tab_photography)),getResources().getString(R.string.tab_photography));
        adapter.addFragment(CategoryFragment.getInstance(getResources().getString(R.string.tab_sculpture)),getResources().getString(R.string.tab_sculpture));
*/
        //Adpter Setup
       /* viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
