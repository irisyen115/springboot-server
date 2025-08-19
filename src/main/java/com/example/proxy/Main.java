package com.example.proxy;

public class Main {
    public static void main(String[] args) {
        int port = 8080;
        ProxyServer server = new ProxyServer(port);
        server.start();
    }
}
