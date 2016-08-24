package pe.edu.upc.mobile.RESTClient;

public final class Environment {
    public static String BASE_URL;
    public static Boolean DEBUGGING;

    static {
        DEBUGGING = Boolean.valueOf(true);
        BASE_URL = "https://upcmovil.upc.edu.pe/upcmovil1/UPCMobile.svc";
    }
}
