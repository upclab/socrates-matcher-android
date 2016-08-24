package pe.edu.upc.mobile.RESTClient;

import com.google.common.net.HttpHeaders;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class RESTClient {
    private static int TIMEOUT_MILLISEC;

    static {
        TIMEOUT_MILLISEC = NetworkClient.DEFAULT_SOCKET_TIMEOUT_MILLIS;
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                is.close();
                return sb.toString();
            }
            sb.append(new StringBuilder(String.valueOf(line)).append("\n").toString());
        }
    }

    public static String connectAndReturnResponse(String url, StringEntity params) throws SocketTimeoutException, ConnectTimeoutException, Exception {
        String result = StringUtils.EMPTY;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient httpclient = new DefaultHttpClient(httpParams);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(params);
        httppost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httppost.setHeader("content-type", "application/json");
        HttpEntity entity = httpclient.execute(httppost).getEntity();
        if (entity == null) {
            return result;
        }
        InputStream instream = entity.getContent();
        result = convertStreamToString(instream);
        instream.close();
        return result;
    }

    public static String connectAndReturnResponse(String url) throws Exception {
        String result = StringUtils.EMPTY;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpEntity entity = new DefaultHttpClient(httpParams).execute(new HttpGet(url)).getEntity();
        if (entity == null) {
            return result;
        }
        InputStream instream = entity.getContent();
        result = convertStreamToString(instream);
        instream.close();
        return result;
    }

    public static InputStream connectAndReturnInputStream(String url) throws Exception {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpEntity entity = new DefaultHttpClient(httpParams).execute(new HttpGet(url)).getEntity();
        if (entity != null) {
            return entity.getContent();
        }
        return null;
    }
}
