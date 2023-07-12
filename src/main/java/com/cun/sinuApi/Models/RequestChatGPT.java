package com.cun.sinuApi.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestChatGPT {
        @JsonProperty("model")
        private String model;

        @JsonProperty("messages")
        private List<MessageChatGpt> messages;

        public RequestChatGPT(String model, List<MessageChatGpt> messages) {
                this.model = model;
                this.messages = messages;
        }
}

