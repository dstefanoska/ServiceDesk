package com.daka.sdk;

/**
 * Created by Dana on 22-Sep-17.
 */

public class Config {

    private String url;
    private String authority;
    private boolean logEnabled;

    public Config() {
    }

    public Config setUrl(String url) {
        this.url = url;
        return this;
    }

    public Config setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
        return this;
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}