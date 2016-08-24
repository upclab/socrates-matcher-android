package com.adsdk.sdk.video;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.plus.PlusShare;
import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Vector;
import org.joda.time.MutableDateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ResponseHandler extends DefaultHandler {
    private CharArrayWriter contents;
    private long currentExpiration;
    private TrackerData currentTracker;
    private boolean insideInterstitial;
    private boolean insideMarkup;
    private boolean insideVideo;
    private boolean insideVideoList;
    private RichMediaAd richMediaAd;
    HashMap<String, Long> videoList;

    public ResponseHandler() {
        this.richMediaAd = null;
        this.videoList = null;
        this.contents = new CharArrayWriter();
        this.currentTracker = new TrackerData();
        this.insideMarkup = false;
        this.insideVideo = false;
        this.insideInterstitial = false;
        this.insideVideoList = false;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        this.contents.write(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("creative")) {
            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                throw new SAXException("Creative tag found outside video node");
            }
            getRichMediaAd().getVideo().videoUrl = this.contents.toString().trim();
        } else if (localName.equals("duration")) {
            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                throw new SAXException("Duration tag found outside video node");
            }
            getRichMediaAd().getVideo().duration = getInteger(this.contents.toString().trim());
        } else if (localName.equals("tracker")) {
            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                throw new SAXException("Tracker tag found outside video node");
            }
            VideoData video = getRichMediaAd().getVideo();
            this.currentTracker.url = this.contents.toString().trim();
            Vector<String> trackers = null;
            switch (this.currentTracker.type) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    trackers = video.getStartEvents();
                    break;
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    trackers = video.getCompleteEvents();
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    trackers = (Vector) video.timeTrackingEvents.get(Integer.valueOf(this.currentTracker.time));
                    if (trackers == null) {
                        trackers = new Vector();
                        video.timeTrackingEvents.put(Integer.valueOf(this.currentTracker.time), trackers);
                        break;
                    }
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    trackers = video.pauseEvents;
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    trackers = video.unpauseEvents;
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    trackers = video.muteEvents;
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    trackers = video.unmuteEvents;
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    trackers = video.skipEvents;
                    break;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    trackers = video.replayEvents;
                    break;
            }
            if (trackers != null) {
                trackers.add(this.currentTracker.url);
            }
        } else if (localName.equals("htmloverlay")) {
            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                throw new SAXException("htmloverlay tag found outside video node");
            }
            getRichMediaAd().getVideo().htmlOverlayMarkup = this.contents.toString().trim();
            this.insideMarkup = false;
        } else if (localName.equals("video")) {
            if (this.insideVideoList) {
                this.videoList.put(this.contents.toString().trim(), Long.valueOf(this.currentExpiration));
            }
            this.insideVideo = false;
        } else if (localName.equals("interstitial")) {
            this.insideInterstitial = false;
        } else if (localName.equals("markup")) {
            if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                throw new SAXException("markup tag found outside interstitial node");
            }
            this.insideMarkup = false;
            getRichMediaAd().getInterstitial().interstitialMarkup = this.contents.toString().trim();
        } else if (localName.equals("error")) {
            getRichMediaAd().setType(2);
        }
    }

    public void startDocument() throws SAXException {
        setRichMediaAd(new RichMediaAd());
        this.insideVideoList = false;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!this.insideMarkup) {
            this.contents.reset();
            if (localName.equals("activevideolist")) {
                this.videoList = new HashMap();
                this.insideVideoList = true;
                return;
            }
            String type;
            if (localName.equals("ad")) {
                type = attributes.getValue("type");
                if ("video-to-interstitial".equalsIgnoreCase(type)) {
                    getRichMediaAd().setType(3);
                } else if ("interstitial-to-video".equalsIgnoreCase(type)) {
                    getRichMediaAd().setType(4);
                } else if ("video".equalsIgnoreCase(type)) {
                    getRichMediaAd().setType(5);
                } else if ("interstitial".equalsIgnoreCase(type)) {
                    getRichMediaAd().setType(6);
                } else if ("noAd".equalsIgnoreCase(type)) {
                    getRichMediaAd().setType(2);
                } else {
                    throw new SAXException("Unknown response type " + type);
                }
                String animation = attributes.getValue("animation");
                if ("fade-in".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(1);
                    return;
                } else if ("slide-in-top".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(2);
                    return;
                } else if ("slide-in-bottom".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(3);
                    return;
                } else if ("slide-in-left".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(4);
                    return;
                } else if ("slide-in-right".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(5);
                    return;
                } else if ("flip-in".equalsIgnoreCase(animation)) {
                    getRichMediaAd().setAnimation(6);
                    return;
                } else {
                    getRichMediaAd().setAnimation(0);
                    return;
                }
            }
            String orientation;
            VideoData video;
            if (!localName.equals("video")) {
                InterstitialData inter;
                String url;
                if (localName.equals("interstitial")) {
                    this.insideInterstitial = true;
                    inter = new InterstitialData();
                    inter.autoclose = getInteger(attributes.getValue("autoclose"));
                    type = attributes.getValue("type");
                    if (PlusShare.KEY_CALL_TO_ACTION_URL.equalsIgnoreCase(type)) {
                        inter.interstitialType = 0;
                        url = attributes.getValue(PlusShare.KEY_CALL_TO_ACTION_URL);
                        if (url == null || url.length() == 0) {
                            throw new SAXException("Empty url for interstitial type " + type);
                        }
                        inter.interstitialUrl = url;
                    } else if ("markup".equalsIgnoreCase(type)) {
                        inter.interstitialType = 1;
                        this.insideMarkup = true;
                    } else {
                        inter.interstitialType = 0;
                        url = attributes.getValue(PlusShare.KEY_CALL_TO_ACTION_URL);
                        if (url == null || url.length() == 0) {
                            throw new SAXException("Empty url for interstitial type " + type);
                        }
                        inter.interstitialUrl = url;
                    }
                    orientation = attributes.getValue("orientation");
                    if ("landscape".equalsIgnoreCase(orientation)) {
                        inter.orientation = 0;
                    } else if ("portrait".equalsIgnoreCase(orientation)) {
                        inter.orientation = 1;
                    } else {
                        inter.orientation = 0;
                    }
                    if (getRichMediaAd() == null) {
                        throw new SAXException("Interstitial tag found outside document root");
                    } else if (getRichMediaAd().getType() != 5 || getRichMediaAd().getType() == 4 || getRichMediaAd().getType() == 3) {
                        getRichMediaAd().setInterstitial(inter);
                        return;
                    } else {
                        throw new SAXException("Found Interstitial tag in a video ad:" + getRichMediaAd().getType());
                    }
                }
                if (!localName.equals("creative")) {
                    if (!localName.equals("skipbutton")) {
                        if (!localName.equals("navigation")) {
                            if (!localName.equals("topbar")) {
                                if (!localName.equals("bottombar")) {
                                    if (!localName.equals("navicon")) {
                                        if (!localName.equals("tracker")) {
                                            if (!localName.equals("htmloverlay") || !this.insideVideo) {
                                                return;
                                            }
                                            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                                throw new SAXException("htmloverlay tag found inside wrong video node");
                                            }
                                            video = getRichMediaAd().getVideo();
                                            this.insideMarkup = true;
                                            type = attributes.getValue("type");
                                            if (PlusShare.KEY_CALL_TO_ACTION_URL.equalsIgnoreCase(type)) {
                                                video.htmlOverlayType = 0;
                                                url = attributes.getValue(PlusShare.KEY_CALL_TO_ACTION_URL);
                                                if (url == null || url.length() == 0) {
                                                    throw new SAXException("Empty url for overlay type " + type);
                                                }
                                                video.htmlOverlayUrl = url;
                                            } else if ("markup".equalsIgnoreCase(type)) {
                                                video.htmlOverlayType = 1;
                                                this.insideMarkup = true;
                                            } else {
                                                video.htmlOverlayType = 0;
                                                url = attributes.getValue(PlusShare.KEY_CALL_TO_ACTION_URL);
                                                if (url == null || url.length() == 0) {
                                                    throw new SAXException("Empty url for overlay type " + type);
                                                }
                                                video.htmlOverlayUrl = url;
                                            }
                                            video.showHtmlOverlayAfter = getInteger(attributes.getValue("showafter"));
                                            video.showHtmlOverlay = getBoolean(attributes.getValue("show"));
                                        } else if (!this.insideVideo) {
                                        } else {
                                            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                                throw new SAXException("tracker tag found inside wrong video node");
                                            }
                                            video = getRichMediaAd().getVideo();
                                            this.currentTracker.reset();
                                            type = attributes.getValue("type");
                                            if ("start".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 0;
                                            } else if ("complete".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 1;
                                            } else if ("midpoint".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 2;
                                                this.currentTracker.time = video.duration / 2;
                                            } else if ("firstquartile".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 3;
                                                this.currentTracker.time = video.duration / 4;
                                            } else if ("thirdquartile".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 4;
                                                this.currentTracker.time = (video.duration * 3) / 4;
                                            } else if ("pause".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 6;
                                            } else if ("unpause".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 7;
                                            } else if ("mute".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 8;
                                            } else if ("unmute".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 9;
                                            } else if ("replay".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 11;
                                            } else if ("skip".equalsIgnoreCase(type)) {
                                                this.currentTracker.type = 10;
                                            } else if (type != null && type.startsWith("sec:")) {
                                                this.currentTracker.type = 5;
                                                this.currentTracker.time = getInteger(type.substring(4));
                                            }
                                        }
                                    } else if (this.insideVideo) {
                                        if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                            throw new SAXException("navicon tag found inside wrong video node");
                                        }
                                        video = getRichMediaAd().getVideo();
                                        icon = new NavIconData();
                                        icon.title = attributes.getValue(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
                                        icon.clickUrl = attributes.getValue("clickurl");
                                        icon.iconUrl = attributes.getValue("iconurl");
                                        if ("inapp".equalsIgnoreCase(attributes.getValue("opentype"))) {
                                            icon.openType = 0;
                                        } else {
                                            icon.openType = 1;
                                        }
                                        video.icons.add(icon);
                                    } else if (!this.insideInterstitial) {
                                    } else {
                                        if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                                            throw new SAXException("navicon tag found inside wrong interstitial node");
                                        }
                                        inter = getRichMediaAd().getInterstitial();
                                        icon = new NavIconData();
                                        icon.title = attributes.getValue(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
                                        icon.clickUrl = attributes.getValue("clickurl");
                                        icon.iconUrl = attributes.getValue("iconurl");
                                        if ("inapp".equalsIgnoreCase(attributes.getValue("opentype"))) {
                                            icon.openType = 0;
                                        } else {
                                            icon.openType = 1;
                                        }
                                        inter.icons.add(icon);
                                    }
                                } else if (this.insideVideo) {
                                    if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                        throw new SAXException("bottombar tag found inside wrong video node");
                                    }
                                    video = getRichMediaAd().getVideo();
                                    video.showBottomNavigationBar = getBoolean(attributes.getValue("show"));
                                    video.bottomNavigationBarBackground = attributes.getValue("custombackgroundurl");
                                    video.showPauseButton = getBoolean(attributes.getValue("pausebutton"));
                                    video.showReplayButton = getBoolean(attributes.getValue("replaybutton"));
                                    video.showTimer = getBoolean(attributes.getValue("timer"));
                                    video.pauseButtonImage = attributes.getValue("pausebuttonurl");
                                    video.playButtonImage = attributes.getValue("playbuttonurl");
                                    video.replayButtonImage = attributes.getValue("replaybuttonurl");
                                } else if (!this.insideInterstitial) {
                                } else {
                                    if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                                        throw new SAXException("bottombar tag found inside wrong interstitial node");
                                    }
                                    inter = getRichMediaAd().getInterstitial();
                                    inter.showBottomNavigationBar = getBoolean(attributes.getValue("show"));
                                    inter.bottomNavigationBarBackground = attributes.getValue("custombackgroundurl");
                                    inter.showBackButton = getBoolean(attributes.getValue("backbutton"));
                                    inter.showForwardButton = getBoolean(attributes.getValue("forwardbutton"));
                                    inter.showReloadButton = getBoolean(attributes.getValue("reloadbutton"));
                                    inter.showExternalButton = getBoolean(attributes.getValue("externalbutton"));
                                    inter.showTimer = getBoolean(attributes.getValue("timer"));
                                    inter.backButtonImage = attributes.getValue("backbuttonurl");
                                    inter.forwardButtonImage = attributes.getValue("forwardbuttonurl");
                                    inter.reloadButtonImage = attributes.getValue("reloadbuttonurl");
                                    inter.externalButtonImage = attributes.getValue("externalbuttonurl");
                                }
                            } else if (this.insideVideo) {
                                if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                    throw new SAXException("topbar tag found inside wrong video node");
                                }
                                video = getRichMediaAd().getVideo();
                                video.showTopNavigationBar = getBoolean(attributes.getValue("show"));
                                video.topNavigationBarBackground = attributes.getValue("custombackgroundurl");
                            } else if (!this.insideInterstitial) {
                            } else {
                                if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                                    throw new SAXException("topbar tag found inside wrong interstitial node");
                                }
                                inter = getRichMediaAd().getInterstitial();
                                inter.showTopNavigationBar = getBoolean(attributes.getValue("show"));
                                inter.topNavigationBarBackground = attributes.getValue("custombackgroundurl");
                                String titleType = attributes.getValue(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
                                if ("fixed".equalsIgnoreCase(titleType)) {
                                    inter.topNavigationBarTitleType = 0;
                                    inter.topNavigationBarTitle = attributes.getValue("titlecontent");
                                } else if ("variable".equalsIgnoreCase(titleType)) {
                                    inter.topNavigationBarTitleType = 1;
                                } else {
                                    inter.topNavigationBarTitleType = 2;
                                }
                            }
                        } else if (this.insideVideo) {
                            if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                                throw new SAXException("navigation tag found inside wrong video node");
                            }
                            video = getRichMediaAd().getVideo();
                            video.showNavigationBars = getBoolean(attributes.getValue("show"));
                            video.allowTapNavigationBars = getBoolean(attributes.getValue("allowtap"));
                        } else if (!this.insideInterstitial) {
                        } else {
                            if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                                throw new SAXException("navigation tag found inside wrong interstitial node");
                            }
                            inter = getRichMediaAd().getInterstitial();
                            inter.showNavigationBars = getBoolean(attributes.getValue("show"));
                            inter.allowTapNavigationBars = getBoolean(attributes.getValue("allowtap"));
                        }
                    } else if (this.insideVideo) {
                        if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                            throw new SAXException("skipbutton tag found inside wrong video node");
                        }
                        video = getRichMediaAd().getVideo();
                        video.showSkipButton = getBoolean(attributes.getValue("show"));
                        video.showSkipButtonAfter = getInteger(attributes.getValue("showafter"));
                        video.skipButtonImage = attributes.getValue("graphic");
                    } else if (!this.insideInterstitial) {
                    } else {
                        if (getRichMediaAd() == null || getRichMediaAd().getInterstitial() == null) {
                            throw new SAXException("skipbutton tag found inside wrong interstitial node");
                        }
                        inter = getRichMediaAd().getInterstitial();
                        inter.showSkipButton = getBoolean(attributes.getValue("show"));
                        inter.showSkipButtonAfter = getInteger(attributes.getValue("showafter"));
                        inter.skipButtonImage = attributes.getValue("graphic");
                    }
                } else if (getRichMediaAd() == null || getRichMediaAd().getVideo() == null) {
                    throw new SAXException("Creative tag found outside video node");
                } else {
                    video = getRichMediaAd().getVideo();
                    String delivery = attributes.getValue("delivery");
                    if ("progressive".equalsIgnoreCase(delivery)) {
                        video.delivery = 0;
                    } else if ("streaming".equalsIgnoreCase(delivery)) {
                        video.delivery = 1;
                    } else {
                        video.delivery = 1;
                    }
                    type = attributes.getValue("type");
                    if (type == null || type.length() == 0) {
                        type = "application/mp4";
                    }
                    String display = attributes.getValue("display");
                    if ("fullscreen".equalsIgnoreCase(display)) {
                        video.display = 0;
                    } else if ("normal".equalsIgnoreCase(display)) {
                        video.display = 0;
                    } else {
                        video.display = 0;
                    }
                    video.type = type;
                    video.width = getInteger(attributes.getValue("width"));
                    video.height = getInteger(attributes.getValue("height"));
                    video.bitrate = getInteger(attributes.getValue("bitrate"));
                }
            } else if (this.insideVideoList) {
                this.currentExpiration = getLong(attributes.getValue("expiration")) * 1000;
            } else {
                this.insideVideo = true;
                video = new VideoData();
                orientation = attributes.getValue("orientation");
                if ("landscape".equalsIgnoreCase(orientation)) {
                    video.orientation = 0;
                } else if ("portrait".equalsIgnoreCase(orientation)) {
                    video.orientation = 1;
                } else {
                    video.orientation = 0;
                }
                if (getRichMediaAd() == null) {
                    throw new SAXException("Video tag found outside document root");
                } else if (getRichMediaAd().getType() != 6 || getRichMediaAd().getType() == 4 || getRichMediaAd().getType() == 3) {
                    getRichMediaAd().setVideo(video);
                } else {
                    throw new SAXException("Found Video tag in an interstitial ad:" + getRichMediaAd().getType());
                }
            }
        }
    }

    private int getInteger(String text) {
        int i = -1;
        if (text != null) {
            try {
                i = Integer.parseInt(text);
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    private long getLong(String text) {
        long j = -1;
        if (text != null) {
            try {
                j = Long.parseLong(text);
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    private boolean getBoolean(String text) {
        if (text == null) {
            return false;
        }
        try {
            if (Integer.parseInt(text) > 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public RichMediaAd getRichMediaAd() {
        return this.richMediaAd;
    }

    public void setRichMediaAd(RichMediaAd richMediaAd) {
        this.richMediaAd = richMediaAd;
    }
}
