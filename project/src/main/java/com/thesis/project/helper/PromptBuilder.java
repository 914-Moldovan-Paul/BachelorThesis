package com.thesis.project.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PromptBuilder {

    private final static String PREDEFINED_PROMPT_FOR_SEARCH_FILE_PATH = "predefined-prompt-for-search.txt";
    private final static String PREDEFINED_PROMPT_FOR_ADD_PATH = "predefined-prompt-for-add.txt";

    public static String buildPrompt(String userPrompt, Map<String, Integer> carCount) throws IOException {
        return new String(Files.readAllBytes(Paths.get(PREDEFINED_PROMPT_FOR_SEARCH_FILE_PATH))) + carCount.toString() + "\n"
                + "Here is the user prompt\n" + userPrompt + "\nLet's think step by step. Only return the sql query, no triple quotes.";
    }

    public static String buildPrompt(String userPrompt) throws IOException {
        return new String(Files.readAllBytes(Paths.get(PREDEFINED_PROMPT_FOR_ADD_PATH))) + "\nHere is the user prompt\n"
                + userPrompt + "\nLet's think step by step. Only return the json object as a string, no triple quotes.";
    }
}
