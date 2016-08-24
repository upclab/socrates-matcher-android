package com.adsdk.sdk;

import com.adsdk.sdk.data.ClickType;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RequestBannerAd extends RequestAd<BannerAd> {
    public RequestBannerAd(InputStream xmlArg) {
        this.is = xmlArg;
    }

    private int getInteger(String text) {
        int i = 0;
        if (text != null) {
            try {
                i = Integer.parseInt(text);
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    private String getAttribute(Document document, String elementName, String attributeName) {
        Element element = (Element) document.getElementsByTagName(elementName).item(0);
        if (element != null) {
            String attribute = element.getAttribute(attributeName);
            if (attribute.length() != 0) {
                return attribute;
            }
        }
        return null;
    }

    private String getValue(Document document, String name) {
        Element element = (Element) document.getElementsByTagName(name).item(0);
        if (element != null) {
            NodeList nodeList = element.getChildNodes();
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getNodeValue();
            }
        }
        return null;
    }

    private boolean getValueAsBoolean(Document document, String name) {
        return "yes".equalsIgnoreCase(getValue(document, name));
    }

    private int getValueAsInt(Document document, String name) {
        return getInteger(getValue(document, name));
    }

    private String convertStreamToString(InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (NoSuchElementException e) {
            return StringUtils.EMPTY;
        }
    }

    BannerAd parse(InputStream inputStream) throws RequestException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        BannerAd response = new BannerAd();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource src = new InputSource(inputStream);
            src.setEncoding(CharEncoding.ISO_8859_1);
            Document doc = db.parse(src);
            Element element = doc.getDocumentElement();
            if (element == null) {
                throw new RequestException("Cannot parse Response, document is not an xml");
            }
            String errorValue = getValue(doc, "error");
            if (errorValue != null) {
                throw new RequestException("Error Response received: " + errorValue);
            }
            String type = element.getAttribute("type");
            element.normalize();
            if ("imageAd".equalsIgnoreCase(type)) {
                response.setType(0);
                response.setBannerWidth(getValueAsInt(doc, "bannerwidth"));
                response.setBannerHeight(getValueAsInt(doc, "bannerheight"));
                response.setClickType(ClickType.getValue(getValue(doc, "clicktype")));
                response.setClickUrl(getValue(doc, "clickurl"));
                response.setImageUrl(getValue(doc, "imageurl"));
                response.setRefresh(getValueAsInt(doc, "refresh"));
                response.setScale(getValueAsBoolean(doc, "scale"));
                response.setSkipPreflight(getValueAsBoolean(doc, "skippreflight"));
            } else if ("textAd".equalsIgnoreCase(type)) {
                response.setType(1);
                response.setText(getValue(doc, "htmlString"));
                skipOverlay = getAttribute(doc, "htmlString", "skipoverlaybutton");
                if (skipOverlay != null) {
                    response.setSkipOverlay(Integer.parseInt(skipOverlay));
                }
                response.setClickType(ClickType.getValue(getValue(doc, "clicktype")));
                response.setClickUrl(getValue(doc, "clickurl"));
                response.setRefresh(getValueAsInt(doc, "refresh"));
                response.setScale(getValueAsBoolean(doc, "scale"));
                response.setSkipPreflight(getValueAsBoolean(doc, "skippreflight"));
            } else if ("mraidAd".equalsIgnoreCase(type)) {
                response.setType(7);
                response.setText(getValue(doc, "htmlString"));
                skipOverlay = getAttribute(doc, "htmlString", "skipoverlaybutton");
                if (skipOverlay != null) {
                    response.setSkipOverlay(Integer.parseInt(skipOverlay));
                }
                response.setClickType(ClickType.getValue(getValue(doc, "clicktype")));
                response.setClickUrl(getValue(doc, "clickurl"));
                response.setUrlType(getValue(doc, "urltype"));
                response.setRefresh(0);
                response.setScale(getValueAsBoolean(doc, "scale"));
                response.setSkipPreflight(getValueAsBoolean(doc, "skippreflight"));
            } else if ("noAd".equalsIgnoreCase(type)) {
                response.setType(2);
            } else {
                throw new RequestException("Unknown response type " + type);
            }
            return response;
        } catch (ParserConfigurationException e) {
            throw new RequestException("Cannot parse Response", e);
        } catch (SAXException e2) {
            throw new RequestException("Cannot parse Response", e2);
        } catch (IOException e3) {
            throw new RequestException("Cannot read Response", e3);
        } catch (Throwable t) {
            RequestException requestException = new RequestException("Cannot read Response", t);
        }
    }

    BannerAd parseTestString() throws RequestException {
        return parse(this.is);
    }
}
