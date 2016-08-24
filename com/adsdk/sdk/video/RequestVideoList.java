package com.adsdk.sdk.video;

import com.adsdk.sdk.AdRequest;
import com.adsdk.sdk.Const;
import com.adsdk.sdk.RequestException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class RequestVideoList {
    public HashMap<String, Long> sendRequest(AdRequest request) throws RequestException {
        String url = request.toString() + "&listads=1";
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

    private HashMap<String, Long> parse(InputStream inputStream) throws RequestException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ResponseHandler myHandler = new ResponseHandler();
            xr.setContentHandler(myHandler);
            InputSource src = new InputSource(inputStream);
            src.setEncoding(CharEncoding.ISO_8859_1);
            xr.parse(src);
            return myHandler.videoList;
        } catch (Exception e) {
            throw new RequestException("Cannot parse Response:" + e.getMessage(), e);
        }
    }
}
