package com.daka.sdk;

/**
 * Created by Dana on 22-Sep-17.
 */

public class Config {
    private static String url = "http://daka-demo-api.azurewebsites.net/api/";
            //"http://daka-demo-api.azurewebsites.net/swagger/docs/v1/api/";
    private boolean logEnabled;

    public Config() {
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
}