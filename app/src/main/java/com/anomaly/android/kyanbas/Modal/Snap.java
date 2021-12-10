package com.anomaly.android.kyanbas.Modal;


import java.util.List;

public class Snap {

    private int mGravity;
    private String mTextHead;
    private List<Art> mArtList;

    public Snap(int gravity, String text, List<Art> apps) {
        mGravity = gravity;
        mTextHead = text;
        mArtList = apps;
    }

    public String getText(){
        return mTextHead;
    }

    public int getGravity(){
        return mGravity;
    }

    public List<Art> getArts(){
        return mArtList;
    }

}
