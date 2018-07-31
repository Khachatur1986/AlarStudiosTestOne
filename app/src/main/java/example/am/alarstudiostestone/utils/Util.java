package example.am.alarstudiostestone.utils;

import android.util.Pair;

/**
 * Created by Khach on 31-Jul-18.
 */

public final class Util {
    public static String buildUrl(String apiUrl, Pair<String, String>[] pairs) {
        StringBuilder builder = new StringBuilder();
        builder.append(apiUrl + "?");
        boolean first = true;

        for (int i = 0; i < pairs.length; i++) {
            if (first) {
                builder.append(pairs[i].first + "=" + pairs[i].second);
                first = false;
            } else {
                builder.append("&" + pairs[i].first + "=" + pairs[i].second);
            }
        }
        return builder.toString();
    }
}
