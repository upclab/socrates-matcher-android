package com.adsdk.sdk.video;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class VideoData implements Serializable {
    public static final int DELIVERY_PROGRESSIVE = 0;
    public static final int DELIVERY_STREAMING = 1;
    public static final int DISPLAY_FULLSCREEN = 0;
    public static final int DISPLAY_NORMAL = 1;
    public static final int OVERLAY_MARKUP = 1;
    public static final int OVERLAY_URL = 0;
    private static final long serialVersionUID = 3402649540160829602L;
    boolean allowTapNavigationBars;
    int bitrate;
    String bottomNavigationBarBackground;
    Vector<String> completeEvents;
    int delivery;
    int display;
    int duration;
    int height;
    String htmlOverlayMarkup;
    int htmlOverlayType;
    String htmlOverlayUrl;
    Vector<NavIconData> icons;
    Vector<String> muteEvents;
    int orientation;
    String pauseButtonImage;
    Vector<String> pauseEvents;
    String playButtonImage;
    String replayButtonImage;
    Vector<String> replayEvents;
    boolean showBottomNavigationBar;
    boolean showHtmlOverlay;
    int showHtmlOverlayAfter;
    boolean showNavigationBars;
    boolean showPauseButton;
    boolean showReplayButton;
    boolean showSkipButton;
    int showSkipButtonAfter;
    boolean showTimer;
    boolean showTopNavigationBar;
    String skipButtonImage;
    Vector<String> skipEvents;
    Vector<String> startEvents;
    HashMap<Integer, Vector<String>> timeTrackingEvents;
    String topNavigationBarBackground;
    String type;
    Vector<String> unmuteEvents;
    Vector<String> unpauseEvents;
    String videoUrl;
    int width;

    public VideoData() {
        this.icons = new Vector();
        this.timeTrackingEvents = new HashMap();
        this.startEvents = new Vector();
        this.completeEvents = new Vector();
        this.muteEvents = new Vector();
        this.unmuteEvents = new Vector();
        this.pauseEvents = new Vector();
        this.unpauseEvents = new Vector();
        this.skipEvents = new Vector();
        this.replayEvents = new Vector();
        this.showHtmlOverlay = false;
    }

    public String toString() {
        return "VideoData \n[\norientation=" + this.orientation + ",\ndisplay=" + this.display + ",\ndelivery=" + this.delivery + ",\ntype=" + this.type + ",\nbitrate=" + this.bitrate + ",\nwidth=" + this.width + ",\nheight=" + this.height + ",\nvideoUrl=" + this.videoUrl + ",\nduration=" + this.duration + ",\nshowSkipButton=" + this.showSkipButton + ",\nshowSkipButtonAfter=" + this.showSkipButtonAfter + ",\nskipButtonImage=" + this.skipButtonImage + ",\nshowNavigationBars=" + this.showNavigationBars + ",\nallowTapNavigationBars=" + this.allowTapNavigationBars + ",\nshowTopNavigationBar=" + this.showTopNavigationBar + ",\ntopNavigationBarBackground=" + this.topNavigationBarBackground + ",\nshowBottomNavigationBar=" + this.showBottomNavigationBar + ",\nbottomNavigationBarBackground=" + this.bottomNavigationBarBackground + ",\nshowPauseButton=" + this.showPauseButton + ",\npauseButtonImage=" + this.pauseButtonImage + ",\nplayButtonImage=" + this.playButtonImage + ",\nshowReplayButton=" + this.showReplayButton + ",\nreplayButtonImage=" + this.replayButtonImage + ",\nshowTimer=" + this.showTimer + ",\nicons=" + this.icons + ",\ntimeTrackingEvents=" + this.timeTrackingEvents + ",\nstartEvents=" + getStartEvents() + ",\ncompleteEvents=" + getCompleteEvents() + ",\nmuteEvents=" + this.muteEvents + ",\nunmuteEvents=" + this.unmuteEvents + ",\npauseEvents=" + this.pauseEvents + ",\nunpauseEvents=" + this.unpauseEvents + ",\nskipEvents=" + this.skipEvents + ",\nreplayEvents=" + this.replayEvents + ",\nshowHtmlOverlay=" + this.showHtmlOverlay + ",\nshowHtmlOverlayAfter=" + this.showHtmlOverlayAfter + ",\nhtmlOverlayType=" + this.htmlOverlayType + ",\nhtmlOverlayUrl=" + this.htmlOverlayUrl + ",\nhtmlOverlayMarkup=" + this.htmlOverlayMarkup + "\n]";
    }

    public Vector<String> getCompleteEvents() {
        return this.completeEvents;
    }

    public void setCompleteEvents(Vector<String> completeEvents) {
        this.completeEvents = completeEvents;
    }

    public Vector<String> getStartEvents() {
        return this.startEvents;
    }

    public void setStartEvents(Vector<String> startEvents) {
        this.startEvents = startEvents;
    }
}
