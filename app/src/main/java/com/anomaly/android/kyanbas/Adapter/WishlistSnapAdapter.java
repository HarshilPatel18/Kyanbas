package com.anomaly.android.kyanbas.Adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anomaly.android.kyanbas.Modal.Snap;
import com.anomaly.android.kyanbas.R;
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;

public class WishlistSnapAdapter extends RecyclerView.Adapter<WishlistSnapAdapter.ViewHolder> implements GravitySnapHelper.SnapListener {



    private ArrayList<Snap> mSnaps;
    private Context mContext;

    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    public WishlistSnapAdapter() {
        mSnaps = new ArrayList<>();
    }

    public void addSnap(Snap snap,Context context) {
        mSnaps.add(snap);
        mContext=context;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);
        view.findViewById(R.id.recyclerView).setNestedScrollingEnabled(false);

        /*Toast.makeText(parent.getContext(),"Ye Call hua",Toast.LENGTH_SHORT).show();*/
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Snap snap = mSnaps.get(position);
        holder.snapTextView.setText(snap.getText());

        if (snap.getGravity() == Gravity.START || snap.getGravity() == Gravity.END) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravitySnapHelper(snap.getGravity(), false, this).attachToRecyclerView(holder.recyclerView);
        } else if (snap.getGravity() == Gravity.CENTER_HORIZONTAL ||
                snap.getGravity() == Gravity.CENTER_VERTICAL) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), snap.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        } else if (snap.getGravity() == Gravity.CENTER) { // Pager snap
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(holder.recyclerView);
        } else { // Top / Bottom
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext()));
            new GravitySnapHelper(snap.getGravity()).attachToRecyclerView(holder.recyclerView);
        }


        holder.recyclerView.setAdapter(new WishListSnapCardAdapter(snap.getArts(),mContext));
    }

    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    @Override
    public void onSnap(int position) {
        Log.d("Snapped: ", position + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView snapTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
        }

    }
}

