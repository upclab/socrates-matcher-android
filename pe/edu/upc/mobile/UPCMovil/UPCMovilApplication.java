package pe.edu.upc.mobile.UPCMovil;

import android.app.Application;
import pe.edu.upc.mobile.Utilities.JdkBasedTimeZoneProvider;

public class UPCMovilApplication extends Application {
    public void onCreate() {
        super.onCreate();
        System.setProperty("org.joda.time.DateTimeZone.Provider", JdkBasedTimeZoneProvider.class.getCanonicalName());
    }
}
