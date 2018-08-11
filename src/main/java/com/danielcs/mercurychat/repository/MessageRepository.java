package com.danielcs.mercurychat.repository;

import com.danielcs.mercurychat.models.Message;
import com.danielcs.mercurychat.repository.utils.ModelAssembler;
import com.danielcs.mercurychat.repository.utils.SQLUtils;
import com.danielcs.webserver.core.annotations.Dependency;
import com.danielcs.webserver.core.annotations.InjectionPoint;

import java.sql.Timestamp;
import java.util.List;

@Dependency
public class MessageRepository implements MessageDAO {

    private SQLUtils db;

    @InjectionPoint
    public void setDb(SQLUtils db) {
        this.db = db;
    }

    private final ModelAssembler<Message> assembler = rs -> new Message(
            rs.getLong("id"),
            rs.getString("content"),
            rs.getString("from_user"),
            rs.getString("to_user"),
            rs.getLong("createdAt")
    );

    @Override
    public List<Message> getMessagesBetween(String from, String to) {
        return db.fetchAll(
                "SELECT * FROM messages " +
                            "WHERE (from_user = ? AND to_user = ?) OR (from_user = ? AND to_user = ?) " +
                              "ORDER BY createdAt ASC;",
                assembler, from, to, to, from
        );
    }

    @Override
    public Message createMessage(Message message) {
        final long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        return db.fetchOne(
                "INSERT INTO messages (content, from_user, to_user, createdAt) " +
                            "VALUES (?, ?, ?, ?) RETURNING *;",
                assembler, message.getContent(), message.getFrom(), message.getTo(), currentTime
        );
    }
}
