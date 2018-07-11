package com.danielcs.mercurychat.controllers;

import com.danielcs.mercurychat.models.Message;
import com.danielcs.mercurychat.repository.MessageDAO;
import com.danielcs.webserver.socket.SocketContext;
import com.danielcs.webserver.socket.annotations.OnMessage;
import com.danielcs.webserver.socket.annotations.SocketController;

import java.util.List;

@SocketController
public class MessageController {

    private MessageDAO messageRepository;

    public MessageController(MessageDAO messageRepository) {
        this.messageRepository = messageRepository;
    }

    @OnMessage(route = "private/send", type = Message.class)
    public void onNewMessage(SocketContext ctx, Message message) {
        message.setFrom(ctx.getProperty("userId").toString());
        Message resp = messageRepository.createMessage(message);
        ctx.reply(resp);
        ctx.sendToUser("userId", message.getTo(), "private/receive", resp);
    }

    @OnMessage(route = "private/history")
    public void sendChatHistory(SocketContext ctx, String to) {
        List<Message> messages = messageRepository.getMessagesBetween(ctx.getProperty("userId").toString(), to);
        ctx.reply(messages);
    }

}
