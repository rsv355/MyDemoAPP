package krishna.mydemo.Helpers;

/**
 * Created by krishnakumar on 15-04-2016.
 */

import android.content.Context;

public class PrefUtils {


    public static void setLogin(Context ctx, boolean val){
        Prefs.with(ctx).save("isLogin",val);
    }
    public static boolean getLogin(Context ctx){
        boolean val = Prefs.with(ctx).getBoolean("isLogin", false);
        return val;
    }


}
