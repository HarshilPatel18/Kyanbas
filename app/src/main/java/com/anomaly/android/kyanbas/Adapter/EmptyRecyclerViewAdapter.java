package com.anomaly.android.kyanbas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.anomaly.android.kyanbas.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<EmptyRecyclerViewAdapter.ViewHolder> {

    private String mMessage;

    public EmptyRecyclerViewAdapter(){}

    public EmptyRecyclerViewAdapter(String message){
        mMessage = message;
    }

    @Override
    public EmptyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.art_empty_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        if(mMessage != null){
            viewHolder.mMessageView.setText(mMessage);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmptyRecyclerViewAdapter.ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return 1;//must return one otherwise none item is shown
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMessageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMessageView = (TextView) view.findViewById(R.id.emptyViewText);
        }
    }
}