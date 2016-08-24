package com.adsdk.sdk;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

public abstract class RequestAd<T> {
    InputStream is;

    abstract T parse(InputStream inputStream) throws RequestException;

    abstract T parseTestString() throws RequestException;

    public T sendRequest(AdRequest request) throws RequestException {
        if (this.is != null) {
            return parseTestString();
        }
        String url = request.toString();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(client.getParams(), Const.SOCKET_TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(client.getParams(), Const.SOCKET_TIMEOUT);
        HttpProtocolParams.setUserAgent(client.getParams(), request.getUserAgent());
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                return parse(response.getEntity().getContent());
            }
            throw new RequestException("Server Error. Response code:" + responseCode);
        } catch (RequestException e) {
            throw e;
        } catch (ClientProtocolException e2) {
            throw new RequestException("Error in HTTP request", e2);
        } catch (IOException e3) {
            throw new RequestException("Error in HTTP request", e3);
        } catch (Throwable t) {
            RequestException requestException = new RequestException("Error in HTTP request", t);
        }
    }
}
