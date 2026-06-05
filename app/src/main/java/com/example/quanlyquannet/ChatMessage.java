package com.example.quanlyquannet;

public class ChatMessage {
    public String sender, text, time;
    public ChatMessage() {}
    public ChatMessage(String sender, String text, String time) {
        this.sender = sender;
        this.text   = text;
        this.time   = time;
    }
}