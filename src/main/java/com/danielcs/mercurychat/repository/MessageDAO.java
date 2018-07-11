package com.danielcs.mercurychat.repository;

import com.danielcs.mercurychat.models.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> getMessagesBetween(String from, String to);
    Message createMessage(Message message);
}
