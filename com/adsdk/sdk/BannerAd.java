package com.adsdk.sdk;

import com.adsdk.sdk.data.ClickType;

public class BannerAd implements Ad {
    public static final String OTHER = "other";
    public static final String WEB = "web";
    private static final long serialVersionUID = 3271938798582141269L;
    private int bannerHeight;
    private int bannerWidth;
    private ClickType clickType;
    private String clickUrl;
    private String imageUrl;
    private int refresh;
    private boolean scale;
    private int skipOverlay;
    private boolean skipPreflight;
    private String text;
    private int type;
    private String urlType;

    public BannerAd() {
        this.skipOverlay = 0;
    }

    public int getBannerHeight() {
        return this.bannerHeight;
    }

    public int getBannerWidth() {
        return this.bannerWidth;
    }

    public ClickType getClickType() {
        return this.clickType;
    }

    public String getClickUrl() {
        return this.clickUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public int getRefresh() {
        return this.refresh;
    }

    public String getText() {
        return this.text;
    }

    public int getType() {
        return this.type;
    }

    public String getUrlType() {
        return this.urlType;
    }

    public boolean isScale() {
        return this.scale;
    }

    public boolean isSkipPreflight() {
        return this.skipPreflight;
    }

    public void setBannerHeight(int bannerHeight) {
        this.bannerHeight = bannerHeight;
    }

    public void setBannerWidth(int bannerWidth) {
        this.bannerWidth = bannerWidth;
    }

    public void setClickType(ClickType clickType) {
        this.clickType = clickType;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    public void setSkipPreflight(boolean skipPreflight) {
        this.skipPreflight = skipPreflight;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int adType) {
        this.type = adType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String toString() {
        return "Response [refresh=" + this.refresh + ", type=" + this.type + ", bannerWidth=" + this.bannerWidth + ", bannerHeight=" + this.bannerHeight + ", text=" + this.text + ", imageUrl=" + this.imageUrl + ", clickType=" + this.clickType + ", clickUrl=" + this.clickUrl + ", urlType=" + this.urlType + ", scale=" + this.scale + ", skipPreflight=" + this.skipPreflight + "]";
    }

    public int getSkipOverlay() {
        return this.skipOverlay;
    }

    public void setSkipOverlay(int skipOverlay) {
        this.skipOverlay = skipOverlay;
    }
}
