package com.thesis.project.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PromptBuilder {

    private static final String PREDEFINED_PROMPT_FILE_PATH = "predefined-prompt.txt";

    public static String buildPrompt(String userPrompt) throws IOException {
        return new String(Files.readAllBytes(Paths.get(PREDEFINED_PROMPT_FILE_PATH))) + " " + userPrompt;
    }
}
