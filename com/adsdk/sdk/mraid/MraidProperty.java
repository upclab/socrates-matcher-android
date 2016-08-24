package com.adsdk.sdk.mraid;

import org.apache.commons.lang3.StringUtils;

abstract class MraidProperty {
    public abstract String toJsonPair();

    MraidProperty() {
    }

    private String sanitize(String str) {
        return str != null ? str.replaceAll("[^a-zA-Z0-9_,:\\s\\{\\}\\'\\\"]", StringUtils.EMPTY) : StringUtils.EMPTY;
    }

    public String toString() {
        return sanitize(toJsonPair());
    }
}
