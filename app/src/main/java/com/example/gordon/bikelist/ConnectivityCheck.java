package com.example.gordon.bikelist;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityCheck {
    Context context;

    ConnectivityCheck(Context context) {
        this.context = context;
    }

    public boolean isNetworkReachable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = manager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }

    public boolean isWifiReachable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = manager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.getType() == ConnectivityManager.TYPE_WIFI);
    }
}

