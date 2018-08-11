package com.danielcs.mercurychat.controllers;

import com.danielcs.mercurychat.models.Message;
import com.danielcs.mercurychat.repository.MessageDAO;
import com.danielcs.webserver.core.annotations.Weave;
import com.danielcs.webserver.socket.SocketContext;
import com.danielcs.webserver.socket.annotations.*;

import java.util.List;

@SocketController
public class MessageController {

    private final MessageDAO messageRepository;

    public MessageController(MessageDAO messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Weave(aspect = "cerberus")
    @OnMessage(route = "private/send", type = Message.class)
    public void onNewMessage(SocketContext ctx, Message message) {
        message.setFrom(ctx.getProperty("userId").toString());
        Message resp = messageRepository.createMessage(message);
        ctx.reply(resp);
        ctx.sendToUser("userId", message.getTo(), "private/receive", resp);
    }

    @Weave(aspect = "cerberus")
    @OnMessage(route = "private/history")
    public void sendChatHistory(SocketContext ctx, String to) {
        List<Message> messages = messageRepository.getMessagesBetween(ctx.getProperty("userId").toString(), to);
        ctx.reply(messages);
    }

}
