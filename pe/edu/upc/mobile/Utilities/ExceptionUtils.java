package pe.edu.upc.mobile.Utilities;

import android.os.AsyncTask;
import android.text.format.Time;
import java.net.URLEncoder;
import org.apache.commons.lang3.StringUtils;
import pe.edu.upc.mobile.Entities.Session;
import pe.edu.upc.mobile.RESTClient.RESTClient;

public class ExceptionUtils {

    private static class RESTTask extends AsyncTask<String, Void, String> {
        private RESTTask() {
        }

        protected String doInBackground(String... urls) {
            String response = StringUtils.EMPTY;
            for (String url : urls) {
                try {
                    response = RESTClient.connectAndReturnResponse(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public static void handleException(Exception exception, Session session) {
        try {
            String sStacktrace = StringUtils.EMPTY;
            String sMensaje = StringUtils.EMPTY;
            if (exception != null) {
                sStacktrace = URLEncoder.encode(exception.getStackTrace().toString(), "utf-8");
                sMensaje = URLEncoder.encode(exception.getMessage(), "utf-8");
            }
            Time now = new Time();
            now.setToNow();
            String sFecha = new StringBuilder(String.valueOf(String.valueOf(now.year))).append(String.valueOf(now.month)).append(String.valueOf(now.monthDay)).toString();
            String url = "https://upcmovil.upc.edu.pe/UPCMobile.svc/Excepcion/?CodAlumno=" + session.getCodAlumno() + "&Token=" + session.getToken() + "&Mensaje=" + sMensaje + "&Stactrace=" + sStacktrace + "&Fecha=" + sFecha + "&Hora=" + now.format("%H%M%S") + "&Plataforma=" + "1";
            new RESTTask().execute(new String[]{url});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
