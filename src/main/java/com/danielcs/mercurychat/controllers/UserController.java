package com.danielcs.mercurychat.controllers;

import com.danielcs.mercurychat.services.UserService;
import com.danielcs.webserver.socket.SocketContext;
import com.danielcs.webserver.socket.annotations.OnMessage;
import com.danielcs.webserver.socket.annotations.SocketController;

@SocketController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @OnMessage(route = "connect")
    public void userJoined(SocketContext ctx) {
        userService.loginUser(ctx.getProperty("userId").toString());
        ctx.emit("users", userService.getUsers());
    }

    @OnMessage(route = "disconnect")
    public void userLeft(SocketContext ctx) {
        userService.logoutUser(ctx.getProperty("userId").toString());
        ctx.emit("users", userService.getUsers());
    }
}
