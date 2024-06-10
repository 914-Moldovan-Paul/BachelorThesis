package com.thesis.project.controller;

import com.thesis.project.dto.UserPromptDto;
import com.thesis.project.helper.PromptBuilder;
import com.thesis.project.model.ChatBotRequest;
import com.thesis.project.model.ChatBotResponse;
import com.thesis.project.model.Message;
import com.thesis.project.service.CarListingService;
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
    private CarListingService carListingService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.chatgtp.model}")
    private String model;

    @Value("${openai.chatgtp.temperature}")
    private double temperature;

    @Value("${openai.chatgtp.max_tokens}")
    private int max_tokens;

    @Value("${openai.chatgtp.api.url}")
    private String apiUrl;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody UserPromptDto userPrompt) {
        try {
            ChatBotRequest request = switch (userPrompt.getRequestType()) {
                case 1 -> new ChatBotRequest(model,
                        List.of(new Message("user", PromptBuilder.buildPrompt(userPrompt.getUserPrompt(), carListingService.getCarCount()))),
                        temperature,
                        max_tokens);
                case 2 -> new ChatBotRequest(model,
                        List.of(new Message("user", PromptBuilder.buildPrompt(userPrompt.getUserPrompt()))),
                        temperature,
                        max_tokens);
                default ->
                        throw new IllegalArgumentException("Invalid request type: " + userPrompt.getRequestType());
            };


            ChatBotResponse response = restTemplate.postForObject(apiUrl, request, ChatBotResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No response was generated");
            }

            return ResponseEntity.ok(response.getChoices().get(0).getMessage().getContent());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating request: " + e.getMessage());
        }
    }
}
