package com.danielcs.mercurychat;

import com.danielcs.webserver.Server;
import com.danielcs.webserver.socket.SocketServer;

public class Main {

    private static int PORT = Integer.valueOf(System.getenv("PORT"));

    public static void main(String[] args) {
        Server server = new SocketServer(PORT, "com.danielcs.mercurychat");
        server.start();
    }
}