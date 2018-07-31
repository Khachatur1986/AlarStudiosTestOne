package example.am.alarstudiostestone.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Khach on 31-Jul-18.
 */

public final class NetworkUtil {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
