package com.example.proxy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.proxy.utils.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.*;

public class ProxyHandler implements HttpHandler {
    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String backendUrl = UpstreamConfig.getBackendUrl() + exchange.getRequestURI();
            HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(backendUrl))
                    .method(exchange.getRequestMethod(),
                            HttpRequest.BodyPublishers.ofInputStream(exchange::getRequestBody));

            HttpUtils.copyHeaders(exchange, reqBuilder);

            HttpResponse<InputStream> backendResp = client.send(reqBuilder.build(),
                    HttpResponse.BodyHandlers.ofInputStream());

            HttpUtils.copyResponse(exchange, backendResp);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.getResponseBody().write(("Error: " + e.getMessage()).getBytes());
            exchange.close();
        }
    }
}
