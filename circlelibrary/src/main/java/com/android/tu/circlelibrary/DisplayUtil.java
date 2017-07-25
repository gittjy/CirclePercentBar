package com.android.tu.circlelibrary;

import android.content.Context;

/**
 * Created by tjy on 2017/7/25.
 */
public class DisplayUtil {


    public static int px2dp(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static int dp2px(Context context,float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

}
