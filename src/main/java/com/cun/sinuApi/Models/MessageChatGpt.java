package com.cun.sinuApi.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageChatGpt {
    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;

    public MessageChatGpt(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
