package com.anomaly.android.kyanbas.View.ViewArt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.SharedPrefManager;
import com.anomaly.android.kyanbas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewArt extends AppCompatActivity {

    ImageView ImageviewArtImage;
    TextView artname,artprice,artdesc,artby,artcategory,artweight,artmeasurements,artAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_art);

        ImageviewArtImage=findViewById(R.id.imageview_art_image);
        artname=findViewById(R.id.textView_Art_Name);
        artprice=findViewById(R.id.textView_Art_price);
        artdesc=findViewById(R.id.textView_Art_desc);
        artby=findViewById(R.id.textView_Art_by);
        artcategory=findViewById(R.id.textView_CategoryName);
        artweight=findViewById(R.id.textView_weight);
        artmeasurements=findViewById(R.id.textView_measurements);
        artAvailable=findViewById(R.id.textView_available);


         PhotoViewAttacher photoAttacher;
         photoAttacher= new PhotoViewAttacher(ImageviewArtImage);
         photoAttacher.update();

        setArt();
    }

    void setArt(){
        String url= Constants.URL_ARTBY_NICENAME.concat("319Neoma");
        StringRequest stringRequest=new StringRequest(


                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("success"))
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                            }
                            else {

                                //code here================================================================

                                JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                                JSONObject userJsonObject=dataJsonObject.getJSONObject("user");
                                JSONObject categoryJsonObject=dataJsonObject.getJSONObject("category");
                                Toast.makeText(getApplicationContext(),dataJsonObject.toString(),Toast.LENGTH_LONG).show();



                                Picasso.get()
                                        .load(Constants.URL_THUMBNAIL_IMAGE+dataJsonObject.get("product"))
                                        .fit()
                                        .placeholder(R.drawable.ic_art_image_placeholder)
                                        .into(ImageviewArtImage);

                                artname.setText(dataJsonObject.get("name").toString());
                                artdesc.setText(dataJsonObject.get("description").toString());
                                artprice.setText("\u20B9 "+dataJsonObject.get("price").toString());
                                artAvailable.setText(dataJsonObject.get("available").toString());
                                artby.setText("By "+userJsonObject.get("first_name")+" "+userJsonObject.get("last_name"));
                                artcategory.setText("Category : "+categoryJsonObject.get("name").toString());
                                artmeasurements.setText("Measurements : "+dataJsonObject.get("measurements").toString());
                                artweight.setText("Weight : "+dataJsonObject.get("weight").toString());







                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"exception error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();


                        error.printStackTrace();

                    }
                }
        ){

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

}
