package com.anomaly.android.kyanbas.View.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.RecyclerGridViewAdapter;
import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Modal.User;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private String category;
    private List<Art> listArt;
    private RecyclerView recyclerGrid;
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);
        if(getArguments()!=null)
        {
            category= String.valueOf(getArguments().getInt(ResponseKeys.CATEGORY_PARENT_ID));

        }

        mContext=container.getContext();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerGrid = (RecyclerView) view.findViewById(R.id.gridRecycler);
        emptyview=view.findViewById(R.id.emptyViewLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Cate_swipe_refresh_layout);


        displayArtbyCategory();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayArtbyCategory();
            }
        });

    }

    public static CategoryFragment newInstance(Integer param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ResponseKeys.CATEGORY_PARENT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void displayArtbyCategory()
    {
        listArt = new ArrayList<>();
        emptyview.setVisibility(View.GONE);
        //Toast.makeText(mContext,category,Toast.LENGTH_SHORT).show();

        swipeRefreshLayout.setRefreshing(true);
        StringRequest categoryRequest= new StringRequest(Request.Method.GET, Constants.URL_ARTBY_CATEGORY+category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("success")==true)
                    {

                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        JSONArray jsonArray=jsonData.getJSONArray("object");
                        JSONArray jsonArrayObject= (JSONArray) jsonArray.get(0);


                        for (int i = 1; i < jsonArrayObject.length(); i++) {


                            JSONArray jsonArrayData = jsonArrayObject.getJSONArray(i);

                            for (int j = 0; j < jsonArrayData.length(); j++) {

                                JSONObject producJson=jsonArrayData.getJSONObject(j);


                                User user=new User(producJson.getInt("user_id"));
                                Art art=new Art(producJson.getInt("id"),producJson.getString("name"),producJson.getString("thumbnail_picture"),producJson.getString("description"),producJson.getInt("price"),producJson.getString("nicename"),user);
                                //Toast.makeText(getContext(),user.getFirstName(),Toast.LENGTH_SHORT);
                                listArt.add(art);


                            }

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        if(listArt.isEmpty())
                        {
                            emptyview.setVisibility(View.VISIBLE);
                            recyclerGrid.setVisibility(View.GONE);
                        }
                        else {
                            emptyview.setVisibility(View.GONE);
                            recyclerGrid.setVisibility(View.VISIBLE);
                            RecyclerGridViewAdapter myAdapter = new RecyclerGridViewAdapter(getContext(),listArt);
                            recyclerGrid.setLayoutManager(new GridLayoutManager(getContext(),2));
                            recyclerGrid.setAdapter(myAdapter);
                        }

                    }





                } catch (JSONException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                    Toast.makeText(mContext,"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext,"Network issue : "+error.getMessage(),Toast.LENGTH_SHORT).show();
                if( error instanceof NetworkError) {
                    Toast.makeText(mContext,"Network Error",Toast.LENGTH_SHORT).show();
                } else if( error instanceof ServerError) {

                    Toast.makeText(mContext,"Server Error",Toast.LENGTH_SHORT).show();
                } else if( error instanceof AuthFailureError) {
                    Toast.makeText(mContext,"AuthFailure Error",Toast.LENGTH_SHORT).show();
                } else if( error instanceof ParseError) {
                    Toast.makeText(mContext,"Parse Error",Toast.LENGTH_SHORT).show();
                } else if( error instanceof NoConnectionError) {
                    Toast.makeText(mContext,"NoConnection Error",Toast.LENGTH_SHORT).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(mContext,"Timeout Error",Toast.LENGTH_SHORT).show();
                }
            }
        }){



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

        categoryRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(getContext()).addToRequestQueue(categoryRequest);

        if(listArt.isEmpty())
        {
            emptyview.setVisibility(View.VISIBLE);
            recyclerGrid.setVisibility(View.GONE);
        }
        else {
            emptyview.setVisibility(View.GONE);
            recyclerGrid.setVisibility(View.VISIBLE);

        }

    }



}
