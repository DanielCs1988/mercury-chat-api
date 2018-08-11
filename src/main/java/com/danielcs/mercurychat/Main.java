package com.danielcs.mercurychat;

import com.danielcs.mercurychat.repository.utils.SQLUtils;
import com.danielcs.webserver.core.Application;
import com.danielcs.webserver.core.annotations.Dependency;
import com.danielcs.webserver.socket.SocketServer;
import org.jose4j.jwk.HttpsJwks;

public class Main {

    private static final String CLASSPATH = "com.danielcs.mercurychat";
    private static final int PORT = Integer.valueOf(System.getenv("PORT"));
    private static final int POOL_SIZE = 100;

    public static void main(String[] args) {
        Application application = new Application(SocketServer.class, CLASSPATH, PORT, POOL_SIZE);
        application.start();
    }

    @Dependency
    public SQLUtils getDataManager() {
        return new SQLUtils(
                System.getenv("DB_PATH"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD")
        );
    }

    @Dependency
    public HttpsJwks getJwks() {
        return new HttpsJwks(System.getenv("JWKS_URI"));
    }
}