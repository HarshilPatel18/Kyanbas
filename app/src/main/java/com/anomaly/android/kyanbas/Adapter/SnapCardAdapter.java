package com.anomaly.android.kyanbas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.anomaly.android.kyanbas.View.ImageViews.ImagePreviewer;
import com.anomaly.android.kyanbas.View.ViewArt.ViewArt;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Art art = mArts.get(position);


        holder.nameTextView.setText(art.getName());
        holder.artUserTextView.setText("By "+art.getUser().getFirstName()+" "+art.getUser().getLastName());
        holder.priceTextView.setText("\u20B9 "+art.getPrice().toString());


        final String image_url=art.getThumbnailPicture().replace("thumbnail","original");

        Picasso.get()
                .load(Constants.URL_THUMBNAIL_IMAGE+image_url)
                .fit()
                .placeholder(R.drawable.ic_art_vector_placeholder)
                .into(holder.imageView);




        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onLongClick(View view) {
                new ImagePreviewer().show(view.getContext(),holder.imageView,art.getName(),art.getUser().getFirstName()+" "+art.getUser().getLastName(),art.getPrice().toString(),image_url);
                return false;
            }
        });

        final Intent intentViewArt=new Intent(mContext, ViewArt.class);
        intentViewArt.putExtra("nicename",art.getNicename());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(intentViewArt);
            }
        });

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(intentViewArt);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(intentViewArt);
            }
        });

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
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.snapCardview);
            imageView = (ImageView) itemView.findViewById(R.id.artImageCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.ArtNameCardview);
            artUserTextView= (TextView) itemView.findViewById(R.id.AuthorCardview);
            priceTextView= (TextView) itemView.findViewById(R.id.artPriceCardview);


        }


    }

}

