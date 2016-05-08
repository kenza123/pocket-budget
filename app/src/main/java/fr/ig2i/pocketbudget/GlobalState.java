package fr.ig2i.pocketbudget;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kenzakhamaily on 04/03/2016.
 */
public class GlobalState extends Application{

    private static final String TAG = "GlobalState";
    private static Context context;
    SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalState.context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    public void alerter(String s ) {
        Log.i(TAG, "alerter: " + s);
        Toast t = Toast.makeText(this,s,Toast.LENGTH_LONG);
        t.show();
    }

    public static Context getAppContext() {
        return GlobalState.context;
    }

}
