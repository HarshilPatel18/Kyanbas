package com.anomaly.android.kyanbas.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SnapCardAdapter extends RecyclerView.Adapter<SnapCardAdapter.ViewHolder> {

    private List<Art> mArts;
    private Context mContext;


    public SnapCardAdapter(List<Art> apps, Context context) {

        mArts = apps;
        mContext=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.snap_art_cardview, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Art art = mArts.get(position);


        holder.nameTextView.setText(art.getName());
        holder.artUserTextView.setText("By "+art.getUser().getFirstName()+" "+art.getUser().getLastName());
        holder.priceTextView.setText("\u20B9 "+art.getPrice().toString());

        Picasso.with(mContext)
                .load(Constants.URL_THUMBNAIL_IMAGE+art.getThumbnailPicture())
                .fit()
                .placeholder(R.drawable.defaultart_image_2)
                .into(holder.imageView);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mArts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView artUserTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.artImageCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.ArtNameCardview);
            artUserTextView= (TextView) itemView.findViewById(R.id.AuthorCardview);
            priceTextView= (TextView) itemView.findViewById(R.id.artPriceCardview);


        }


    }

}

