package pe.edu.upc.mobile.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    public static boolean isConnected(Context context) throws Exception {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
