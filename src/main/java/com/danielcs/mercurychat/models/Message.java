package com.danielcs.mercurychat.models;

public class Message {

    private long id;
    private String content;
    private String from;
    private String to;
    private long createdAt;

    public Message() {
    }

    public Message(long id, String content, String from, String to, long createdAt) {
        this.id = id;
        this.content = content;
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
