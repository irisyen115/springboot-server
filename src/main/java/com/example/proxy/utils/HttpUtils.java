package com.example.proxy.utils;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtils {

    public static void copyHeaders(HttpExchange exchange, HttpRequest.Builder builder) {
        exchange.getRequestHeaders().forEach((k, v) -> {
            String key = k.toLowerCase();
            if (!key.equals("connection") && !key.equals("host") && !key.equals("content-length")) {
                builder.header(k, String.join(",", v));
            }
        });
    }

    public static void copyResponse(HttpExchange exchange, HttpResponse<InputStream> resp) throws IOException {
        resp.headers().map().forEach((k, v) -> exchange.getResponseHeaders().put(k, v));
        long contentLength = resp.body().available();
        exchange.sendResponseHeaders(resp.statusCode(), contentLength);
        resp.body().transferTo(exchange.getResponseBody());
        exchange.close();
    }
}
