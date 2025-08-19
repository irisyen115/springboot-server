package com.example.proxy;

public class UpstreamConfig {
    private static final String BACKEND_URL = "http://httpbin.org";

    public static String getBackendUrl() {
        return BACKEND_URL;
    }
}
