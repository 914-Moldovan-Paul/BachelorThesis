package com.thesis.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotRequest {

    private String model;
    private List<Message> messages;
    private double temperature;
    private int max_tokens;

}