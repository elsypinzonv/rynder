package com.elsy.rynder.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;


public class ResourceCompatMethod {

    Context context;

    public ResourceCompatMethod(Context context) {
        this.context=context;
    }

    public Drawable getDrawableCompat(int id){
        Drawable draw;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            draw=context.getDrawable(id);
        }else{
            draw=context.getResources().getDrawable(id);
        }
        return draw;
    }

    public int getColorCompat(int id){
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color=context.getColor(id);
        }else{
            color=context.getResources().getColor(id);
        }
        return color;
    }


}
