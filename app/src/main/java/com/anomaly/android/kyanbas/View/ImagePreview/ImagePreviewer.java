package com.anomaly.android.kyanbas.View.ImagePreview;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.R;
import com.squareup.picasso.Picasso;

public class ImagePreviewer {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    public void show(Context context, ImageView source, String artName, String artist, String artPrice,String imgurl) {
        BitmapDrawable background = ImagePreviewerUtils.getBlurredScreenDrawable(context, source.getRootView());

        View dialogView = LayoutInflater.from(context).inflate(R.layout.imageview_hold, null);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.imageViewArtPreview);
        TextView textArtName=dialogView.findViewById(R.id.textArtNamePreview);
        TextView textArtist=dialogView.findViewById(R.id.textArtistNamePreview);
        TextView textPrice=dialogView.findViewById(R.id.textPriceArtPreview);
        String imgURL=imgurl;

        Drawable copy = source.getDrawable().getConstantState().newDrawable();
        imageView.setImageDrawable(copy);

        /*Picasso.get()
                .load(Constants.URL_THUMBNAIL_IMAGE+imgURL)
                .fit()
                .placeholder(R.drawable.ic_art_vector_placeholder)
                .into(imageView);*/

        textArtName.setText(artName);
        textArtist.setText("By "+artist);
        textPrice.setText("\u20B9 "+artPrice);

        final Dialog dialog = new Dialog(context, -1);
        dialog.getWindow().setBackgroundDrawable(background);
        dialog.setContentView(dialogView);
        dialog.show();

        source.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dialog.isShowing()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    int action = event.getActionMasked();
                    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        dialog.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
