package fr.ig2i.pocketbudget;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.net.CookieHandler;
import java.net.CookieManager;

import fr.ig2i.pocketbudget.service.BalanceService;
import fr.ig2i.pocketbudget.service.CategoryService;
import fr.ig2i.pocketbudget.service.EarningService;
import fr.ig2i.pocketbudget.service.SpendingService;

/**
 * Created by kenzakhamaily on 04/03/2016.
 */
public class GlobalState extends Application{

    private static final String TAG = "GlobalState";
    private static Context context;
    private SharedPreferences prefs;
    private int[] darkTemplate;
    private int[] niceTemplate;
    private int[] joyfulTemplate;
    private int[] colorfulTemplate;
    private int[] paleTemplate;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalState.context = getApplicationContext();
        Resources resources = context.getResources();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        darkTemplate = new int[]{
                resources.getColor(R.color.spanishCrimson),
                resources.getColor(R.color.seaGreen),
                resources.getColor(R.color.oceanBoatBlue),
                resources.getColor(R.color.deepCarrotOrange),
                resources.getColor(R.color.canaryYellow),
                resources.getColor(R.color.magentaHaze)};

        niceTemplate = new int[]{
                resources.getColor(R.color.darkPink),
                resources.getColor(R.color.lightSeaGreen),
                resources.getColor(R.color.dodgerBlue),
                resources.getColor(R.color.giantsOrange),
                resources.getColor(R.color.amber),
                resources.getColor(R.color.pearlyPurple)};

        joyfulTemplate = new int[]{
                resources.getColor(R.color.frenchFuchsia),
                resources.getColor(R.color.ufoGreen),
                resources.getColor(R.color.cyan),
                resources.getColor(R.color.darkOrange),
                resources.getColor(R.color.neonCarrot),
                resources.getColor(R.color.pearlyPurple)};

        colorfulTemplate = new int[]{
                resources.getColor(R.color.ruddy),
                resources.getColor(R.color.eucalyptus),
                resources.getColor(R.color.spiroDiscoBall),
                resources.getColor(R.color.salmon2),
                resources.getColor(R.color.lemon),
                resources.getColor(R.color.mulberry)};

        paleTemplate = new int[]{
                resources.getColor(R.color.blush),
                resources.getColor(R.color.findTheCouponGreen),
                resources.getColor(R.color.columbiaBlue),
                resources.getColor(R.color.lemon),
                resources.getColor(R.color.googleYellow),
                resources.getColor(R.color.violetRed),};

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

    public EarningService getEarningService() {
        return new EarningService(GlobalState.context);
    }

    public CategoryService getCategoryService() {
        return new CategoryService(GlobalState.context);
    }

    public SpendingService getSpendingService() {
        return new SpendingService(GlobalState.context);
    }

    public BalanceService getBalanceService() {
        return new BalanceService(GlobalState.context);
    }
    public SharedPreferences getPrefs() {
        return prefs;
    }

    public int[] getDarkTemplate() {
        return darkTemplate;
    }

    public int[] getNiceTemplate() {
        return niceTemplate;
    }

    public int[] getJoyfulTemplate() {
        return joyfulTemplate;
    }

    public int[] getColorfulTemplate() {
        return colorfulTemplate;
    }

    public int[] getPaleTemplate() {
        return paleTemplate;
    }
}
