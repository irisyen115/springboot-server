package com.example.proxy;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class ProxyServer {
    private final int port;

    public ProxyServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new ProxyHandler());
            server.setExecutor(null);
            System.out.println("Reverse Proxy running on port " + port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
