package com.anomaly.android.kyanbas.View.Main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;



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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setHasFixedSize(true);

        setupAdapter();
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
            String response="{\"data\":[{\"id\":1,\"product\":{\"id\":149,\"category\":{\"id\":19,\"name\":\"Tyrell Stravenue\",\"parent_id\":18,\"description\":\"Architecto numquam beatae ex et.\",\"nicename\":\"719Lake\"},\"name\":\"Juliana Underpass\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/8016287556db59594926c7f4e0f0688e.jpg\",\"description\":\"Corporis rem placeat sed ut debitis ut. Dicta quia aut dolorum.\",\"price\":6734,\"user\":{\"id\":3,\"first_name\":\"Gonzalo\",\"last_name\":\"Fisher\",\"contact_number\":86338,\"email\":\"towne.ethel@example.com\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"820Stephanie\"},\"delivery_type\":\"shippable\"}},{\"id\":2,\"product\":{\"id\":86,\"category\":{\"id\":3,\"name\":\"Torphy Light\",\"parent_id\":2,\"description\":\"Autem quam doloribus pariatur quia atque.\",\"nicename\":\"512West\"},\"name\":\"Dicki Crest\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/30985239ebfc0e85e984989c78ef60c4.jpg\",\"description\":\"Ratione aliquid commodi id fugiat sed aliquid amet. Nihil laboriosam autem molestiae. Voluptates quam tempora id. Voluptatem in aliquam nostrum.\",\"price\":6160,\"user\":{\"id\":9,\"first_name\":\"Marquise\",\"last_name\":\"Dare\",\"contact_number\":97332,\"email\":\"laron41@example.com\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"112Zakary\"},\"delivery_type\":\"digital\"}},{\"id\":3,\"product\":{\"id\":64,\"category\":{\"id\":20,\"name\":\"Selena Mill\",\"parent_id\":12,\"description\":\"Necessitatibus quidem delectus ut omnis.\",\"nicename\":\"713East\"},\"name\":\"Lind Summit\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/ea24019a3ebdadde44b05b9963ffc00c.jpg\",\"description\":\"Recusandae nihil similique culpa corrupti accusantium. Et assumenda veniam et et est sed natus. Ex sequi dignissimos beatae labore eius.\",\"price\":2737,\"user\":{\"id\":10,\"first_name\":\"Jerald\",\"last_name\":\"Lang\",\"contact_number\":99441,\"email\":\"keaton20@example.org\",\"address\":{\"id\":1,\"user_id\":2,\"address_line1\":\"77969 Floy Mountain Suite 390\\nEast Demetris, MT 45093-4259\",\"address_line2\":\"8040 Ryley Run Apt. 482\",\"city\":\"Beckerburgh\",\"state\":\"Delaware\",\"country\":\"Guatemala\",\"landmark\":\"Apt. 712\",\"pincode\":896384,\"nicename\":\"514Daugherty Cliff\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"617Anibal\"},\"delivery_type\":\"shippable\"}},{\"id\":4,\"product\":{\"id\":173,\"category\":{\"id\":20,\"name\":\"Selena Mill\",\"parent_id\":12,\"description\":\"Necessitatibus quidem delectus ut omnis.\",\"nicename\":\"713East\"},\"name\":\"Schamberger Mission\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/739e4b082c404338c6e82a6084bc038a.jpg\",\"description\":\"Et hic tempore cum autem unde ut aliquid odio. Dolor necessitatibus illum veritatis hic iure impedit rem. Eum esse esse et vero necessitatibus.\",\"price\":8498,\"user\":{\"id\":10,\"first_name\":\"Jerald\",\"last_name\":\"Lang\",\"contact_number\":99441,\"email\":\"keaton20@example.org\",\"address\":{\"id\":1,\"user_id\":2,\"address_line1\":\"77969 Floy Mountain Suite 390\\nEast Demetris, MT 45093-4259\",\"address_line2\":\"8040 Ryley Run Apt. 482\",\"city\":\"Beckerburgh\",\"state\":\"Delaware\",\"country\":\"Guatemala\",\"landmark\":\"Apt. 712\",\"pincode\":896384,\"nicename\":\"514Daugherty Cliff\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"617Anibal\"},\"delivery_type\":\"digital\"}},{\"id\":5,\"product\":{\"id\":160,\"category\":{\"id\":5,\"name\":\"Oberbrunner Union\",\"parent_id\":1,\"description\":\"Recusandae libero debitis earum.\",\"nicename\":\"018East\"},\"name\":\"Renner Valley\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/86d7e017d998b57688c709bda44a7141.jpg\",\"description\":\"Quo voluptatum consequuntur atque. Assumenda rerum iure provident id nam hic vitae. Sunt sint saepe enim recusandae velit.\",\"price\":5556,\"user\":{\"id\":5,\"first_name\":\"Ted\",\"last_name\":\"Wunsch\",\"contact_number\":92334,\"email\":\"pwalker@example.net\",\"address\":{\"id\":2,\"user_id\":9,\"address_line1\":\"9050 Malvina Locks\\nLysanneville, OK 55973\",\"address_line2\":\"51232 Huels Way Suite 635\",\"city\":\"South Kitty\",\"state\":\"North Dakota\",\"country\":\"Falkland Islands (Malvinas)\",\"landmark\":\"Apt. 348\",\"pincode\":751508,\"nicename\":\"516Zieme Stravenue\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"615Johnnie\"},\"delivery_type\":\"shippable\"}},{\"id\":6,\"product\":{\"id\":242,\"category\":{\"id\":23,\"name\":\"Pottery\",\"parent_id\":0,\"description\":\"Officia eos est et dolor tenetur.\",\"nicename\":\"23Pottery\"},\"name\":\"Hudson Lodge\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/0d5ee7940d6e8b2317787b79bfb0e395.jpg\",\"description\":\"Harum voluptate ratione blanditiis reprehenderit nostrum. Et expedita possimus quo quod rem. Ipsum amet sit ut.\",\"price\":8955,\"user\":{\"id\":5,\"first_name\":\"Ted\",\"last_name\":\"Wunsch\",\"contact_number\":92334,\"email\":\"pwalker@example.net\",\"address\":{\"id\":2,\"user_id\":9,\"address_line1\":\"9050 Malvina Locks\\nLysanneville, OK 55973\",\"address_line2\":\"51232 Huels Way Suite 635\",\"city\":\"South Kitty\",\"state\":\"North Dakota\",\"country\":\"Falkland Islands (Malvinas)\",\"landmark\":\"Apt. 348\",\"pincode\":751508,\"nicename\":\"516Zieme Stravenue\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"615Johnnie\"},\"delivery_type\":\"digital\"}},{\"id\":7,\"product\":{\"id\":100,\"category\":{\"id\":11,\"name\":\"Dach Isle\",\"parent_id\":2,\"description\":\"Ullam ea occaecati quaerat nisi reprehenderit.\",\"nicename\":\"1017North\"},\"name\":\"Berge Road\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/d87e51ba08e127d68135169c159a9207.jpg\",\"description\":\"Et soluta molestiae impedit voluptatum facere saepe non. Sed at voluptatem in nostrum. Quod magnam expedita at quo qui. Est sit est est deserunt.\",\"price\":1556,\"user\":{\"id\":4,\"first_name\":\"Kaya\",\"last_name\":\"Rolfson\",\"contact_number\":49819,\"email\":\"dfritsch@example.org\",\"address\":{\"id\":5,\"user_id\":9,\"address_line1\":\"703 Candelario Divide\\nLake Selmer, OK 53045\",\"address_line2\":\"7769 Ritchie Summit\",\"city\":\"Lake Kelvinview\",\"state\":\"Arizona\",\"country\":\"Norway\",\"landmark\":\"Suite 899\",\"pincode\":459423,\"nicename\":\"719Beahan Stravenue\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"1014Vinnie\"},\"delivery_type\":\"shippable\"}},{\"id\":8,\"product\":{\"id\":119,\"category\":{\"id\":15,\"name\":\"Ivah Harbor\",\"parent_id\":11,\"description\":\"Aut dicta est voluptatem.\",\"nicename\":\"220East\"},\"name\":\"Bechtelar Run\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/f6e8dfd8698879588db4dcfdbe9e55ff.jpg\",\"description\":\"Animi non porro nihil qui. Est possimus quod eum inventore. In aliquam ratione nostrum architecto est quo.\",\"price\":6525,\"user\":{\"id\":9,\"first_name\":\"Marquise\",\"last_name\":\"Dare\",\"contact_number\":97332,\"email\":\"laron41@example.com\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"112Zakary\"},\"delivery_type\":\"digital\"}},{\"id\":9,\"product\":{\"id\":35,\"category\":{\"id\":19,\"name\":\"Tyrell Stravenue\",\"parent_id\":18,\"description\":\"Architecto numquam beatae ex et.\",\"nicename\":\"719Lake\"},\"name\":\"Laurel Manor\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/1edc5f938d647b23500885ae0085c0b7.jpg\",\"description\":\"Velit pariatur minima ut porro exercitationem quo dolorem. Quod suscipit quia ut at aliquam. Recusandae autem tempora voluptas ipsam ea at quod.\",\"price\":1675,\"user\":{\"id\":9,\"first_name\":\"Marquise\",\"last_name\":\"Dare\",\"contact_number\":97332,\"email\":\"laron41@example.com\",\"address\":null,\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"112Zakary\"},\"delivery_type\":\"shippable\"}},{\"id\":10,\"product\":{\"id\":176,\"category\":{\"id\":16,\"name\":\"Curtis Crescent\",\"parent_id\":10,\"description\":\"Sed omnis ea non quis itaque ut.\",\"nicename\":\"913South\"},\"name\":\"Albina Spurs\",\"thumbnail_picture\":\"images\\/product\\/thumbnail\\/90899f34b164573a84060efce92da272.jpg\",\"description\":\"Molestiae et facilis quia hic repellendus ut corrupti. Atque sint nisi velit blanditiis et quia. Possimus sint dignissimos repellat beatae facere.\",\"price\":9342,\"user\":{\"id\":5,\"first_name\":\"Ted\",\"last_name\":\"Wunsch\",\"contact_number\":92334,\"email\":\"pwalker@example.net\",\"address\":{\"id\":2,\"user_id\":9,\"address_line1\":\"9050 Malvina Locks\\nLysanneville, OK 55973\",\"address_line2\":\"51232 Huels Way Suite 635\",\"city\":\"South Kitty\",\"state\":\"North Dakota\",\"country\":\"Falkland Islands (Malvinas)\",\"landmark\":\"Apt. 348\",\"pincode\":751508,\"nicename\":\"516Zieme Stravenue\"},\"is_verified\":1,\"profile_picture\":\"images\\/user\\/original\\/default-profile-picture.jpg\",\"thumbnail_picture\":\"images\\/user\\/thumbnail\\/default-profile-picture.jpg\",\"nicename\":\"615Johnnie\"},\"delivery_type\":\"shippable\"}}]}";
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
        e.printStackTrace();
        Toast.makeText(getContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
    }




        //Featured Art
        /*StringRequest featuredArtRequest= new StringRequest(Request.Method.GET, Constants.URL_ART_LIST, new Response.Listener<String>() {
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



                    snapAdapter.addSnap(new Snap(Gravity.START, "Featured Art", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Painting", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Sculpture", arts),getContext());
                    snapAdapter.addSnap(new Snap(Gravity.START, "Digital Art", arts),getContext());
                    mRecyclerView.setAdapter(snapAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
