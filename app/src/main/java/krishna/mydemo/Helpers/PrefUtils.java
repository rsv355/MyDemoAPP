package krishna.mydemo.Helpers;

/**
 * Created by krishnakumar on 15-04-2016.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class PrefUtils {


    public static void setLogin(Context ctx, boolean val){
        Prefs.with(ctx).save("isLogin",val);
    }
    public static boolean getLogin(Context ctx){
        boolean val = Prefs.with(ctx).getBoolean("isLogin", false);
        return val;
    }

    public static boolean isInternetConnected(Context _ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) _ctx.getSystemService(_ctx.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) /*||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState() == NetworkInfo.State.CONNECTED) */ {
            return true;
        } else {
            return false;
        }
    }

    public static String deviceID(Context _ctx) {
      String deviceID= Settings.Secure.getString(_ctx.getContentResolver(),
              Settings.Secure.ANDROID_ID);

        return deviceID;
    }
}
