package com.daka.servicedesk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.daka.servicedesk.app.ContextProvider;

/**
 * Created by Dana on 22-Sep-17.
 */

public class NetworkUtil {

    public static boolean hasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) ContextProvider.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean hasUnmeteredNetwork() {
        ConnectivityManager cm = (ConnectivityManager) ContextProvider.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            return false;
        }
        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
            return true;
        }
        return false;
    }
}
