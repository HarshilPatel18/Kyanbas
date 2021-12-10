package com.anomaly.android.kyanbas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerviewBaseAdapter extends RecyclerView.Adapter<RecyclerviewBaseAdapter.ViewHolder> {
    private ArrayList<Art> artList;

    public RecyclerviewBaseAdapter(ArrayList<Art> artList) {
        this.artList =artList ;
    }

    @Override
    public RecyclerviewBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_cardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Art art = artList.get(position);


        holder.artName.setText(art.getName());
        holder.artUsername.setText("By "+art.getUser().getFirstName()+" "+art.getUser().getLastName());
        holder.artPrice.setText("\u20B9 "+art.getPrice().toString());

        String image_url=art.getThumbnailPicture().replace("thumbnail","original");


        Picasso.get()
                .load(Constants.URL_THUMBNAIL_IMAGE+image_url)
                .fit()
                .placeholder(R.drawable.ic_art_vector_placeholder)
                .into(holder.artImage);
    }

    @Override
    public int getItemCount() {
        return artList.size();
    }



    public void removeItem(int position) {
        artList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, artList.size());
    }

    public Art getArt(int position){
        return artList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView artName,artUsername,artPrice;
        ImageView artImage;

        public ViewHolder(View view) {
            super(view);

            artName = (TextView)view.findViewById(R.id.textViewItemArtName);
            artUsername = (TextView)view.findViewById(R.id.textViewItemArtUserName);
            artPrice = (TextView)view.findViewById(R.id.textViewItemArtPrice);
            artImage = view.findViewById(R.id.imageViewItemArtImage);
        }
    }
}