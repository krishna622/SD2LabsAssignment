package com.krishna.sdlabs.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtility {
    public static boolean checkNetwork(Context mContext){
        if(mContext == null)
            return false;
        //Check whether network is available or not.
        return CommonUtility.isOnline(mContext);
    }

    public static boolean isOnline(Context context) {
        if(context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
