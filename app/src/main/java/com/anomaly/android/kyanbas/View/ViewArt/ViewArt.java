package com.anomaly.android.kyanbas.View.ViewArt;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.ImageViews.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ViewArt extends AppCompatActivity {

    private View emptyview;
    private TextView artName,artPrice,artDesc,userName,userEmail,
            toolbarText, artMesurements,artWeight,artSpecs,
            artSpecDesc,artAvailable,artDeliveryType;
    private ImageView artImage,userProfileImage;
    private LinearLayout linearLayout,viewArtSpecs;
    private android.support.v7.widget.Toolbar toolbar;
    private Button addToCart,addToWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_art);

        toolbar= findViewById(R.id.activityToolbar);
        toolbarText=findViewById(R.id.textViewToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        //BindView
        linearLayout = findViewById(R.id.linearLayoutViewArt);
        viewArtSpecs=findViewById(R.id.linearLayoutViewArtSpecs);
        emptyview = findViewById(R.id.emptyViewLayout);
        artName = findViewById(R.id.textViewArtName);
        artPrice = findViewById(R.id.textViewArtPrice);
        artDesc = findViewById(R.id.textViewArtDesc);
        userName = findViewById(R.id.textViewArtUserName);
        userEmail = findViewById(R.id.textViewArtUserEmail);
        artMesurements = findViewById(R.id.textViewArtMesurements);
        artWeight = findViewById(R.id.textViewArtWeight);
        artSpecs = findViewById(R.id.textViewArtSpecs);
        artSpecDesc= findViewById(R.id.textViewArtSpecsDesc);
        artAvailable=findViewById(R.id.textViewArtAvailable);
        artDeliveryType=findViewById(R.id.textViewArtDeliveryType);
        artImage = findViewById(R.id.imageViewArt);
        userProfileImage = findViewById(R.id.imageViewArtUserProfile);
        addToCart=findViewById(R.id.buttonAddtoCart);
        addToWishlist=findViewById(R.id.buttonWishlist);


        Bundle bundle=getIntent().getExtras();
        if(bundle.getString("nicename")!=null)
        {

            loadArtDetails(bundle.getString("nicename"));

        }
        else
        {
            /*emptyview.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);*/
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void loadArtDetails(String nicename)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.URL_VIEW_ART+nicename, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



               /* emptyview.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);*/

                try {


                    JSONObject jsonData = new JSONObject(response).getJSONObject("data");
                    JSONObject productJson=jsonData.getJSONObject("product");

                    JSONObject categoryJson=productJson.getJSONObject("category");
                    JSONObject userJson=productJson.getJSONObject("user");

                    toolbarText.setText(categoryJson.getString("name"));
                    artName.setText(productJson.getString("name"));
                    artPrice.setText("\u20B9 "+String.valueOf(productJson.getInt("price")));
                    artDesc.setText(productJson.getString("description"));

                    artAvailable.setText("Currently  "+productJson.getString("available"));
                    artDeliveryType.setText("Delivery type  :  "+Constants.firstLetterCaps(productJson.getString("delivery_type")));

                    if (productJson.getString("measurements")!="null")
                    {
                        artMesurements.setText("Measurements  :  "+productJson.getString("measurements"));
                    }
                    else {
                        artMesurements.setText("Measurements  :  N/A");
                    }
                    if (productJson.getString("weight")!="null")
                    {
                        artWeight.setText("Weight  :  "+productJson.getString("weight"));
                    }
                    else {
                        artWeight.setText("Weight  :  N/A");
                    }


                    userName.setText(userJson.getString("first_name")+" "+userJson.getString("last_name"));
                    userEmail.setText(userJson.getString("email"));



                    if(productJson.getInt("has_specification")==1)
                    {
                        if(!productJson.isNull("specifications"))
                        {
                            viewArtSpecs.setVisibility(View.VISIBLE);
                            JSONObject specsJson=productJson.getJSONObject("specifications");
                            artSpecs.setText(Constants.firstLetterCaps(specsJson.getString("name"))+" : "+Constants.firstLetterCaps(specsJson.getString("value")));
                            artSpecDesc.setText(specsJson.getString("description"));
                        }
                        else {
                            viewArtSpecs.setVisibility(View.GONE);
                        }

                    }
                    else
                    {
                        viewArtSpecs.setVisibility(View.GONE);
                    }



                    String image_url=productJson.getString("thumbnail_picture").replace("thumbnail","original");

                    artImage.setPadding(10,10,10,10);
                    Picasso.get()
                            .load(Constants.URL_THUMBNAIL_IMAGE+image_url)
                            .fit()
                            .placeholder(R.drawable.ic_art_vector_placeholder)
                            .into(artImage);

                    Picasso.get()
                            .load(Constants.URL_THUMBNAIL_IMAGE+userJson.getString("profile_picture"))
                            .fit()
                            .transform(new PicassoCircleTransformation())
                            .placeholder(R.drawable.ic_art_vector_placeholder)
                            .into(userProfileImage);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    /*emptyview.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);*/
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
                /*emptyview.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);*/
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
}
