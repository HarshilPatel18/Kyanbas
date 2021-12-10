package com.anomaly.android.kyanbas.View.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.RecyclerviewBaseAdapter;
import com.anomaly.android.kyanbas.Adapter.SnapAdapter;
import com.anomaly.android.kyanbas.Adapter.WishListSnapCardAdapter;
import com.anomaly.android.kyanbas.Adapter.WishlistSnapAdapter;
import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Modal.Snap;
import com.anomaly.android.kyanbas.Modal.User;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.Authentication.Login;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistActivity extends AppCompatActivity {
    private ArrayList<Art> mArts;
    private WishlistSnapAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView wishlistError,toolbarText;
    private ProgressBar progressBar;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        toolbar= findViewById(R.id.includeToolbarWishlist);
        toolbarText=findViewById(R.id.textViewToolbar);
        toolbarText.setText("My Wishlist");
        RelativeLayout relativeLayout=toolbar.findViewById(R.id.relativeLayoutGenericCart);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));
            }
        });

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        wishlistError=findViewById(R.id.textViewWishlistError);

        recyclerView=findViewById(R.id.recyclerWishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        progressBar=findViewById(R.id.progressBar);


        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            loadWishlist();

        }
        else {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){

                    //removeFromCart(adapter.getArt(position),position);
                } else {
                    //adapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#4CAF50"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_wishlist);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                        c.drawText("Move to Wishlist",width,height,p);
                    } else {
                        p.setColor(Color.parseColor("#ff5252"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void loadWishlist()
    {
        List<Art> arts = new ArrayList<>();
        adapter = new WishlistSnapAdapter();


        StringRequest wishlistRequest=new StringRequest(Request.Method.GET, Constants.URL_WISHLIST_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);


                    if(jsonObject.has("success"))
                    {
                        if(!jsonObject.getBoolean("success"))
                        {
                            progressBar.setVisibility(View.GONE);
                            wishlistError.setVisibility(View.VISIBLE);
                            wishlistError.setText(jsonObject.getString("error"));
                        }
                    }
                    else
                    {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {


                            JSONObject json = jsonArray.getJSONObject(i);
                            StyleableToast.makeText(getApplicationContext(),json.getString("name"),R.style.Success).show();
                            adapter.addSnap(new Snap(Gravity.START, json.getString("name"), loadWishlistItems(json.getString("name"))),getApplicationContext());


                        }
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {

                    progressBar.setVisibility(View.GONE);
                    wishlistError.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                wishlistError.setVisibility(View.VISIBLE);
                wishlistError.setText(error.getMessage());

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

        wishlistRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy
                        .DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(wishlistRequest);

    }


    public List<Art> loadWishlistItems(String wishlistname) {

        List<Art> arts = new ArrayList<>();

            StringRequest wishlistRequest = new StringRequest(Request.Method.GET,
                    Constants.URL_WISHLIST_ITEMS + wishlistname.trim(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {


                        JSONObject jsonObject = new JSONObject(response);


                        if (jsonObject.has("success")) {
                            if (!jsonObject.getBoolean("success")) {
                                /*progressBar.setVisibility(View.GONE);
                                wishlistError.setVisibility(View.VISIBLE);
                                wishlistError.setText(jsonObject.getString("error"));*/
                            }
                        } else {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                StyleableToast.makeText(getApplicationContext(),response,R.style.Success).show();

                                JSONObject artJson = jsonArray.getJSONObject(i);
                                JSONObject productJson=artJson.getJSONObject("product");
                                JSONObject userJson=productJson.getJSONObject("user");


                                User user=new User(userJson.getInt("id"),userJson.getString("first_name"),userJson.getString("last_name"),userJson.getString("profile_picture"),userJson.getString("thumbnail_picture"),userJson.getString("nicename"));


                                Art art=new Art(productJson.getInt("id"),productJson.getString("name"),productJson.getString("thumbnail_picture"),productJson.getString("description"),productJson.getInt("price"),productJson.getString("nicename"),user);
                                mArts.add(art);


                            }

                            if(!mArts.isEmpty())
                            {
                                initSwipe();
                            }

                        }

                    } catch (JSONException e) {

                        /*progressBar.setVisibility(View.GONE);
                        wishlistError.setVisibility(View.VISIBLE);*/
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Response Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    /*progressBar.setVisibility(View.GONE);
                    wishlistError.setVisibility(View.VISIBLE);
                    wishlistError.setText(error.getMessage());*/

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map headers = new HashMap();
                    if (SharedPrefManager.getInstance(getApplicationContext()).getAccessToken() != null) {
                        String bearer = "bearer " + SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
                        headers.put("Authorization", bearer);
                        return headers;
                    } else {
                        String bearer = "bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vc3RhZ2luZy5yZW50ZWRjYW52YXMuY29tL2FwaS9hdXRoL2xjU4OTY0OTUsIm5iZiI6MTUyNTg5Mjg5N";
                        headers.put("Authorization", bearer);

                    }
                    return headers;
                }

            };

            wishlistRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy
                            .DEFAULT_BACKOFF_MULT));
            RequestHandler.getInstance(this).addToRequestQueue(wishlistRequest);

            return mArts;
    }



}
