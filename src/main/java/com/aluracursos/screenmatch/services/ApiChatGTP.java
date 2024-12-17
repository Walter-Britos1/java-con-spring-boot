package com.aluracursos.screenmatch.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ApiChatGTP {
    public static String transalte(String text) {
        final String API_KEY = "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";

        OpenAiService service = new OpenAiService(API_KEY);

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-4o-mini")
                .prompt("traduce a español el siguiente texto: " + text)
                .maxTokens(1000)
                .temperature(0.07)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
