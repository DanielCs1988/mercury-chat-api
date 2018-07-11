package com.danielcs.mercurychat.config;

import com.danielcs.mercurychat.repository.MessageDAO;
import com.danielcs.mercurychat.repository.MessageRepository;
import com.danielcs.mercurychat.repository.utils.SQLUtils;
import com.danielcs.mercurychat.services.UserService;
import com.danielcs.webserver.socket.annotations.Configuration;
import com.danielcs.webserver.socket.annotations.Dependency;
import org.jose4j.jwk.HttpsJwks;

@Configuration
public class AppConfig {

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

    @Dependency
    public MessageDAO getMessageRepository() {
        return new MessageRepository();
    }

    @Dependency
    public UserService getUserService() {
        return new UserService();
    }
}
