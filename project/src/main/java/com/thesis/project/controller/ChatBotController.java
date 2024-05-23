package com.thesis.project.controller;

import com.thesis.project.helper.PromptBuilder;
import com.thesis.project.model.ChatBotRequest;
import com.thesis.project.model.ChatBotResponse;
import com.thesis.project.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


@RestController
public class ChatBotController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.chatgtp.model}")
    private String model;

    @Value("${openai.chatgtp.temperature}")
    private double temperature;

    @Value("${openai.chatgtp.max_tokens}")
    private int maxTokens;

    @Value("${openai.chatgtp.api.url}")
    private String apiUrl;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam("prompt") String prompt) {
        try {
            ChatBotRequest request = new ChatBotRequest(model,
                    List.of(new Message("user", PromptBuilder.buildPrompt(prompt))),
                    temperature,
                    maxTokens);

            ChatBotResponse response = restTemplate.postForObject(apiUrl, request, ChatBotResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No response was generated");
            }

            return ResponseEntity.ok(response.getChoices().get(0).getMessage().getContent());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while building prompt: " + e.getMessage());
        }
    }
}
