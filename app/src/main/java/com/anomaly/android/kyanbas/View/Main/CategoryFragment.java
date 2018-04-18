package com.anomaly.android.kyanbas.View.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.R;

public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);
        if(getArguments()!=null)
        {
            TextView textView = fragmentView.findViewById(R.id.textViewCategory);
            textView.setText(getArguments().getString(ResponseKeys.CATEGORY_NICENAME));
        }

        return fragmentView;
    }

    public static CategoryFragment newInstance(String param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ResponseKeys.CATEGORY_NICENAME, param1);
        fragment.setArguments(args);
        return fragment;
    }


}
