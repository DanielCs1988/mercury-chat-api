package com.danielcs.mercurychat.services;

import com.danielcs.mercurychat.models.Message;
import com.danielcs.webserver.socket.SocketContext;
import com.danielcs.webserver.socket.annotations.Aspect;
import com.danielcs.webserver.socket.annotations.AspectType;

import java.util.*;

public class Aspects {

    private static final String USER_API_URL = "https://protected-island-21893.herokuapp.com/friendlist";

    private HttpRequest http;
    private Map<String, Set<String>> friendLists = new HashMap<>();

    public Aspects(HttpRequest http) {
        this.http = http;
    }

    @Aspect(type = AspectType.INTERCEPTOR)
    public boolean cerberus(Object... args) {
        SocketContext ctx = (SocketContext)args[1];
        String userId = ctx.getProperty("userId").toString();
        String token = ctx.getProperty("token").toString();
        String targetId = args[2] instanceof String ? args[2].toString() : ((Message)args[2]).getTo();
        if (!validateFriendship(userId, targetId, token)) {
            ctx.disconnect();
            return false;
        }
        return true;
    }

    @Aspect(type = AspectType.BEFORE)
    public void eyeOfMordor(Object... args) {
        String methodName = args[0].toString();
        SocketContext ctx = (SocketContext)args[1];
        String userId = ctx.getProperty("userId").toString();
        if (methodName.equals("userJoined")) {
            System.out.println("User " + userId + " has joined the server!");
        } else {
            System.out.println("User " + userId + " has left the server!");
        }
    }

    private boolean validateFriendship(String currentUser, String targetUser, String token) {
        if (currentUser.equals(targetUser)) {
            return true;
        }
        if (!friendLists.containsKey(currentUser)) {
            Set<String> friendList = http.getJsonWithBearer(USER_API_URL, token);
            friendLists.put(currentUser, friendList);
        }
        System.out.println(friendLists);
        return friendLists.get(currentUser).contains(targetUser);
    }

}
