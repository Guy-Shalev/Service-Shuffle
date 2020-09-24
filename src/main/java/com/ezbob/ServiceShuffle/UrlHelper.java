package com.ezbob.ServiceShuffle;

import javax.inject.Singleton;

@Singleton
public class UrlHelper {

    private static final String HTTP = "http://";

    public static String getUrlFromServiceName(String serviceName, String urlPath) {
        return HTTP + serviceName + urlPath;
    }
}
