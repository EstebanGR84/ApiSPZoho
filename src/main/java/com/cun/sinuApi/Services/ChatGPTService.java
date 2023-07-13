package com.cun.sinuApi.Services;

import com.cun.sinuApi.Models.MessageChatGpt;
import com.cun.sinuApi.Models.RequestChatGPT;
import com.cun.sinuApi.Models.ResponseChatGPT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {
    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ChatGPTService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    public String sendMessageChatGPT(String ciudad) {
        try{
            String url = this.BASE_URL;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(this.API_KEY);
            // Crear una lista de mensajes
            String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"puedes de este texto escribirme solo la ciudad que corresponde sin ningun tipo de descripcion y sin ningun otra palabra "+ciudad+"\"}]}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            String body = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(body);
            ArrayNode choises = (ArrayNode) jsonNode.get("choices");
            JsonNode message = choises.get(0);
            JsonNode responseChatGpt = message.get("message").get("content");
            return responseChatGpt.asText();
        }
        catch (Exception e){
            return "";
        }

    }

}
