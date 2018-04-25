package com.anomaly.android.kyanbas.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.Modal.Art;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerGridViewAdapter extends RecyclerView.Adapter<RecyclerGridViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Art> mArtList ;


    public RecyclerGridViewAdapter(Context mContext, List<Art> mArtList) {
        this.mContext = mContext;
        this.mArtList = mArtList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.gridview_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Art art = mArtList.get(position);
        holder.nameTextView.setText(art.getName());
        holder.artUserTextView.setText("By "+art.getUser().getFirstName()+" "+art.getUser().getLastName());
        holder.priceTextView.setText("\u20B9 "+art.getPrice().toString());

        Picasso.get()
                .load(Constants.URL_THUMBNAIL_IMAGE+art.getThumbnailPicture())
                .fit()
                .placeholder(R.drawable.ic_art_image_placeholder)
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return mArtList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView artUserTextView;
        public TextView priceTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.GridViewImage);
            nameTextView = (TextView) itemView.findViewById(R.id.GridViewArtName);
            artUserTextView= (TextView) itemView.findViewById(R.id.GridViewAuthor);
            priceTextView= (TextView) itemView.findViewById(R.id.GridviewPrice);
        }
    }


}
