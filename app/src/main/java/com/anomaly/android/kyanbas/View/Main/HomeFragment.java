package com.anomaly.android.kyanbas.View.Main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.ConnectionErrorRecyclerViewAdapter;
import com.anomaly.android.kyanbas.Adapter.SnapAdapter;
import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Modal.Snap;
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


public class HomeFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false); inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Homeswipe_refresh_layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setHasFixedSize(true);
        setupAdapter();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupAdapter();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        setupAdapter();
                                    }
                                }
        );


    }

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ResponseKeys.CATEGORY_NICENAME, param1);
        fragment.setArguments(args);
        return fragment;
    }



    private void setupAdapter() {
        final List<Art> arts = new ArrayList<>();
        final SnapAdapter snapAdapter = new SnapAdapter();

        try
        {
            String response="{\"data\":[{\"id\":1,\"product\":{\"id\":22,\"category\":{\"id\":24,\"name\":\"Darrion Route\",\"parent_id\":3,\"description\":\"Ut possimus vel aut.\",\"nicename\":\"1019North\"},\"name\":\"Alden Gardens\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/2567843ad4fe01a933f1cf0965d996e3.jpg\",\"description\":\"Voluptas doloremque nisi sint incidunt fugiat cum. Minima omnis quaerat ex et cum. Et aperiam eligendi placeat dolores.\",\"price\":4812,\"user\":{\"id\":1,\"first_name\":\"Bobby\",\"last_name\":\"Mitchell\",\"contact_number\":86148,\"email\":\"declan29@example.org\",\"address\":{\"id\":10,\"user_id\":3,\"address_line1\":\"604 Leannon Extensions\\nLake Louveniabury, DC 35453\",\"address_line2\":\"780 Langworth Ways Suite 619\",\"city\":\"Port Jailyn\",\"state\":\"South Dakota\",\"country\":\"Palau\",\"landmark\":\"Apt. 733\",\"pincode\":110239,\"nicename\":\"018Teagan Station\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"411Alize\"},\"delivery_type\":\"shippable\"}},{\"id\":2,\"product\":{\"id\":6,\"category\":{\"id\":21,\"name\":\"Gilberto Mews\",\"parent_id\":11,\"description\":\"Consequatur necessitatibus ut maxime.\",\"nicename\":\"420Lake\"},\"name\":\"Bernhard Mountain\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\"description\":\"Recusandae magnam labore dicta voluptates et autem ab. Est soluta ab ducimus et recusandae et mollitia.\",\"price\":5013,\"user\":{\"id\":6,\"first_name\":\"Blanche\",\"last_name\":\"Hills\",\"contact_number\":65718,\"email\":\"aileen.dickinson@example.org\",\"address\":{\"id\":5,\"user_id\":3,\"address_line1\":\"577 Koepp Harbors Apt. 666\\nJovanyfurt, HI 87391\",\"address_line2\":\"85120 Chadrick Mission\",\"city\":\"Geoffreyside\",\"state\":\"New York\",\"country\":\"Mexico\",\"landmark\":\"Suite 738\",\"pincode\":359309,\"nicename\":\"911Hyatt Cove\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"414Angel\"},\"delivery_type\":\"shippable\"}},{\"id\":3,\"product\":{\"id\":27,\"category\":{\"id\":20,\"name\":\"Reagan Spur\",\"parent_id\":0,\"description\":\"Maiores impedit dolore consequatur dolorum et.\",\"nicename\":\"511Lake\"},\"name\":\"Norberto Mills\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/ba18a27fcb5f1fc95948a5c1a8b51f81.jpg\",\"description\":\"Earum sed sit est qui. Sed facilis eligendi sed dolore tenetur. Voluptas nulla quia minima debitis. Vitae labore tenetur aperiam possimus magni.\",\"price\":8590,\"user\":{\"id\":6,\"first_name\":\"Blanche\",\"last_name\":\"Hills\",\"contact_number\":65718,\"email\":\"aileen.dickinson@example.org\",\"address\":{\"id\":5,\"user_id\":3,\"address_line1\":\"577 Koepp Harbors Apt. 666\\nJovanyfurt, HI 87391\",\"address_line2\":\"85120 Chadrick Mission\",\"city\":\"Geoffreyside\",\"state\":\"New York\",\"country\":\"Mexico\",\"landmark\":\"Suite 738\",\"pincode\":359309,\"nicename\":\"911Hyatt Cove\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"414Angel\"},\"delivery_type\":\"shippable\"}},{\"id\":4,\"product\":{\"id\":14,\"category\":{\"id\":25,\"name\":\"Lea Meadows\",\"parent_id\":9,\"description\":\"Porro quaerat et ad corrupti sunt aliquid.\",\"nicename\":\"520East\"},\"name\":\"Lowe Spurs\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/0ac80ee663c7046e42c8444b10888765.jpg\",\"description\":\"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\"price\":5438,\"user\":{\"id\":8,\"first_name\":\"Erna\",\"last_name\":\"Lehner\",\"contact_number\":80066,\"email\":\"mfahey@example.net\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"317Lauryn\"},\"delivery_type\":\"shippable\"}},{\"id\":5,\"product\":{\"id\":8,\"category\":{\"id\":15,\"name\":\"Beau Tunnel\",\"parent_id\":13,\"description\":\"At consequuntur expedita suscipit itaque nihil.\",\"nicename\":\"411South\"},\"name\":\"Oswald Gardens\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/fc743596c4c47ca82b5b6f4d398cf46a.jpg\",\"description\":\"Hic qui excepturi itaque quaerat deserunt rerum nesciunt excepturi. Voluptas sint incidunt provident.\",\"price\":1170,\"user\":{\"id\":5,\"first_name\":\"Laisha\",\"last_name\":\"Schoen\",\"contact_number\":20608,\"email\":\"harmony02@example.net\",\"address\":{\"id\":9,\"user_id\":4,\"address_line1\":\"2943 Bogisich Park Suite 453\\nRichmondstad, NM 46841\",\"address_line2\":\"7539 Lester Ville Apt. 780\",\"city\":\"Hodkiewiczchester\",\"state\":\"Rhode Island\",\"country\":\"United Arab Emirates\",\"landmark\":\"Apt. 822\",\"pincode\":847183,\"nicename\":\"1014Gerda Common\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"215Ivy\"},\"delivery_type\":\"shippable\"}},{\"id\":6,\"product\":{\"id\":26,\"category\":{\"id\":6,\"name\":\"Cristal Cliff\",\"parent_id\":4,\"description\":\"Ullam et iusto tenetur.\",\"nicename\":\"812North\"},\"name\":\"Braeden Mission\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/a4abf606e119ecea24733cffbf0606b7.jpg\",\"description\":\"Atque repellat quis aut sit ut. Unde repudiandae porro ut ea quo tempore ut.\",\"price\":7434,\"user\":{\"id\":7,\"first_name\":\"Johnathon\",\"last_name\":\"Luettgen\",\"contact_number\":71388,\"email\":\"grover77@example.net\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"111Gladys\"},\"delivery_type\":\"digital\"}},{\"id\":7,\"product\":{\"id\":7,\"category\":{\"id\":9,\"name\":\"Blick Skyway\",\"parent_id\":4,\"description\":\"Ratione sunt aspernatur id earum.\",\"nicename\":\"914New\"},\"name\":\"Zelda Flat\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/37ce08207e3a0d0eb06d2ddc08061e50.jpg\",\"description\":\"Porro adipisci deserunt recusandae. Consectetur ratione qui soluta impedit expedita. Sequi sed cum dolorum cupiditate et autem.\",\"price\":4829,\"user\":{\"id\":5,\"first_name\":\"Laisha\",\"last_name\":\"Schoen\",\"contact_number\":20608,\"email\":\"harmony02@example.net\",\"address\":{\"id\":9,\"user_id\":4,\"address_line1\":\"2943 Bogisich Park Suite 453\\nRichmondstad, NM 46841\",\"address_line2\":\"7539 Lester Ville Apt. 780\",\"city\":\"Hodkiewiczchester\",\"state\":\"Rhode Island\",\"country\":\"United Arab Emirates\",\"landmark\":\"Apt. 822\",\"pincode\":847183,\"nicename\":\"1014Gerda Common\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"215Ivy\"},\"delivery_type\":\"digital\"}},{\"id\":8,\"product\":{\"id\":4,\"category\":{\"id\":18,\"name\":\"Cartwright Drive\",\"parent_id\":9,\"description\":\"Sed voluptatem voluptatem at.\",\"nicename\":\"518East\"},\"name\":\"Jerde Harbors\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/b770a96ffa3302756a65851b50c1b782.jpg\",\"description\":\"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\"price\":9879,\"user\":{\"id\":10,\"first_name\":\"Raymundo\",\"last_name\":\"Ritchie\",\"contact_number\":91062,\"email\":\"satterfield.connor@example.com\",\"address\":{\"id\":2,\"user_id\":3,\"address_line1\":\"5813 Wiegand Wells\\nNikolasborough, CT 74404-8733\",\"address_line2\":\"63258 O'Conner Terrace Apt. 070\",\"city\":\"South Anitastad\",\"state\":\"North Dakota\",\"country\":\"Finland\",\"landmark\":\"Suite 822\",\"pincode\":740104,\"nicename\":\"619Ondricka Path\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"912Assunta\"},\"delivery_type\":\"digital\"}},{\"id\":9,\"product\":{\"id\":14,\"category\":{\"id\":25,\"name\":\"Lea Meadows\",\"parent_id\":9,\"description\":\"Porro quaerat et ad corrupti sunt aliquid.\",\"nicename\":\"520East\"},\"name\":\"Lowe Spurs\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/0ac80ee663c7046e42c8444b10888765.jpg\",\"description\":\"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\"price\":5438,\"user\":{\"id\":8,\"first_name\":\"Erna\",\"last_name\":\"Lehner\",\"contact_number\":80066,\"email\":\"mfahey@example.net\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"317Lauryn\"},\"delivery_type\":\"shippable\"}},{\"id\":10,\"product\":{\"id\":4,\"category\":{\"id\":18,\"name\":\"Cartwright Drive\",\"parent_id\":9,\"description\":\"Sed voluptatem voluptatem at.\",\"nicename\":\"518East\"},\"name\":\"Jerde Harbors\",\"thumbnail_picture\":\"storage\\/images\\/product\\/thumbnail\\/b770a96ffa3302756a65851b50c1b782.jpg\",\"description\":\"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\"price\":9879,\"user\":{\"id\":10,\"first_name\":\"Raymundo\",\"last_name\":\"Ritchie\",\"contact_number\":91062,\"email\":\"satterfield.connor@example.com\",\"address\":{\"id\":2,\"user_id\":3,\"address_line1\":\"5813 Wiegand Wells\\nNikolasborough, CT 74404-8733\",\"address_line2\":\"63258 O'Conner Terrace Apt. 070\",\"city\":\"South Anitastad\",\"state\":\"North Dakota\",\"country\":\"Finland\",\"landmark\":\"Suite 822\",\"pincode\":740104,\"nicename\":\"619Ondricka Path\"},\"is_verified\":1,\"profile_picture\":\"storage\\/images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"storage\\/images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"912Assunta\"},\"delivery_type\":\"digital\"}}]}";
            //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(ResponseKeys.JSON_DATA_WRAPPER);

            swipeRefreshLayout.setRefreshing(true);
            int count=0;
            for (int i = 0; i < jsonArray.length(); i++) {

                if(count>4)
                {
                    break;
                }
                else {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    JSONObject artJson=jo.getJSONObject("product");
                    JSONObject userJson=artJson.getJSONObject("user");

                    User user=new User(userJson.getInt("id"),userJson.getString("first_name"),userJson.getString("last_name"),userJson.getString("profile_picture"),userJson.getString("thumbnail_picture"),userJson.getString("nicename"));
                    Art art=new Art(artJson.getInt("id"),artJson.getString("name"),artJson.getString("thumbnail_picture"),artJson.getString("description"),artJson.getInt("price"),user);
                    Toast.makeText(getContext(),user.getFirstName(),Toast.LENGTH_SHORT);
                    arts.add(art);
                }
                count++;
        }

        swipeRefreshLayout.setRefreshing(false);

        if(arts==null || arts.size()==0)
        {
            mRecyclerView.setAdapter(new ConnectionErrorRecyclerViewAdapter());
        }
        else
        {
            snapAdapter.addSnap(new Snap(Gravity.START, "Featured Art", arts),getContext());
            snapAdapter.addSnap(new Snap(Gravity.START, "Painting", arts),getContext());
            snapAdapter.addSnap(new Snap(Gravity.START, "Sculpture", arts),getContext());
            snapAdapter.addSnap(new Snap(Gravity.START, "Digital Art", arts),getContext());
            mRecyclerView.setAdapter(snapAdapter);
        }




    } catch (JSONException e) {
            swipeRefreshLayout.setRefreshing(false);
        e.printStackTrace();
        Toast.makeText(getContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
    }




        //Featured Art
        /*swipeRefreshLayout.setRefreshing(true);
        StringRequest featuredArtRequest= new StringRequest(Request.Method.GET, Constants.URL_ART_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(ResponseKeys.JSON_DATA_WRAPPER);

                    int count=0;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        if(count>4)
                        {
                            break;
                        }
                        else {
                            JSONObject jo = jsonArray.getJSONObject(i);

                            JSONObject artJson=jo.getJSONObject("product");
                            JSONObject userJson=artJson.getJSONObject("user");

                            User user=new User(userJson.getInt("id"),userJson.getString("first_name"),userJson.getString("last_name"),userJson.getString("profile_picture"),userJson.getString("thumbnail_picture"),userJson.getString("nicename"));
                            Art art=new Art(artJson.getInt("id"),artJson.getString("name"),artJson.getString("thumbnail_picture"),artJson.getString("description"),artJson.getInt("price"),user);
                            Toast.makeText(getContext(),user.getFirstName(),Toast.LENGTH_SHORT);
                            arts.add(art);
                        }
                        count++;
                    }

                    swipeRefreshLayout.setRefreshing(false);

                    snapAdapter.addSnap(new Snap(Gravity.START, "Featured Art", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Painting", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Sculpture", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Digital Art", arts),getContext());
                    mRecyclerView.setAdapter(snapAdapter);



                } catch (JSONException e) {
                swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                //Toast.makeText(getContext(),"Network issue : "+error.getMessage(),Toast.LENGTH_SHORT).show();

                Log.d("VOLLEY", "Failed with error msg:\t" + error.getMessage());
                Log.d("VOLLEY", "Error StackTrace: \t" + error.getStackTrace());
                // edited here
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e("VOLLEY", new String(htmlBodyBytes), error);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (error.getMessage() == null){
                    Toast.makeText(getContext(),"Network issue : "+error.getMessage(),Toast.LENGTH_SHORT).show();

                }
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

        RequestHandler.getInstance(getContext()).addToRequestQueue(featuredArtRequest);*/


    }



}
