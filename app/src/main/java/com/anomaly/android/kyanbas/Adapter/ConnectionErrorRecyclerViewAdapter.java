package com.anomaly.android.kyanbas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anomaly.android.kyanbas.R;


public class ConnectionErrorRecyclerViewAdapter extends RecyclerView.Adapter<ConnectionErrorRecyclerViewAdapter.ViewHolder> {

    private String mMessage;

    public ConnectionErrorRecyclerViewAdapter(){}

    public ConnectionErrorRecyclerViewAdapter(String message){
        mMessage = message;
    }

    @Override
    public ConnectionErrorRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conn_error_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        if(mMessage != null){
            viewHolder.mMessageView.setText(mMessage);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConnectionErrorRecyclerViewAdapter.ViewHolder holder, int position) {}

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
            mMessageView = (TextView) view.findViewById(R.id.connErrorText);
        }
    }
}