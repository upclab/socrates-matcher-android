package com.adsdk.sdk;

import com.adsdk.sdk.video.ResponseHandler;
import com.adsdk.sdk.video.RichMediaAd;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class RequestRichMediaAd extends RequestAd<RichMediaAd> {
    public RequestRichMediaAd() {
        this.is = null;
    }

    public RequestRichMediaAd(InputStream xmlArg) {
        this.is = xmlArg;
    }

    RichMediaAd parseTestString() throws RequestException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ResponseHandler myHandler = new ResponseHandler();
            xr.setContentHandler(myHandler);
            xr.parse(new InputSource(new InputStreamReader(this.is, CharEncoding.UTF_8)));
            return myHandler.getRichMediaAd();
        } catch (Exception e) {
            throw new RequestException("Cannot parse Response:" + e.getMessage(), e);
        }
    }

    RichMediaAd parse(InputStream inputStream) throws RequestException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ResponseHandler myHandler = new ResponseHandler();
            xr.setContentHandler(myHandler);
            InputSource src = new InputSource(inputStream);
            src.setEncoding(CharEncoding.ISO_8859_1);
            xr.parse(src);
            return myHandler.getRichMediaAd();
        } catch (Exception e) {
            throw new RequestException("Cannot parse Response:" + e.getMessage(), e);
        }
    }

    private String convertStreamToString(InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (NoSuchElementException e) {
            return StringUtils.EMPTY;
        }
    }
}
