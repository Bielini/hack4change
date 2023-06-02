package com.alibou.security.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class OpenAIService {
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    private final String apiKey = "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";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Hobby> getSuggestedHobbies(GenerateHobbiesRequest request){
        String output = generateHobbies(request);
        return convertOutputIntoHobbies(output);
    }

    private String generateHobbies(GenerateHobbiesRequest request)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String requestJson = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\": \"You are a sustainable mentor. Based on the user's day description, suggest 2 sustainable hobbies with minimal impact on their routine. Format the response as a JSON object: {'hobbies': [{'name': 'Hobby Name', 'description': 'Hobby Description', 'steps': ['Step 1', 'Step 2', 'Step 3']}, {'name': 'Hobby Name', 'description': 'Hobby Description', 'steps': ['Step 1', 'Step 2', 'Step 3']}]}\" },\n" +
                "    {\"role\": \"user\", \"content\": \"" + request.getInput() + "\"}\n" +
                "  ],\n" +
                "  \"temperature\": 0.4\n" +
                "}";

        HttpEntity<String> chat = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_URL, chat, String.class);
        return response.getBody();
    }

    private List<Hobby> convertOutputIntoHobbies(String output){
        output = output.substring(output.indexOf("\"content\":\"")+11, output.indexOf("\"},\"finis"));
        output = output.substring(output.indexOf("{'hobbies':")+12, output.lastIndexOf("']}]}")+4);
        output = output.replace("'", "\"");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(output, new TypeReference<List<Hobby>>() {});
        } catch (Exception e) {
            log.error("Error occurred during object mapping", e);
            return Collections.emptyList();
        }
    }
}
