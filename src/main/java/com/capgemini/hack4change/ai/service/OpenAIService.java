package com.capgemini.hack4change.ai.service;

import com.capgemini.hack4change.ai.model.GenerateHobbiesRequest;
import com.capgemini.hack4change.ai.model.HobbyDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpenAIService {

    final HobbyService hobbyService;
    @Value("${gpt.link}")
    String openaiUrl;

    @Value("${gpt.secret}")
    String apiKey;
    final RestTemplate restTemplate = new RestTemplate();

    public List<HobbyDto> getSuggestedHobbies(GenerateHobbiesRequest request){
        String output = generateHobbies(request);
        return hobbyService.convertOutputIntoHobbies(output);
    }

    private String generateHobbies(GenerateHobbiesRequest request)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, apiKey);

        String requestJson = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\": \"You are a sustainable mentor. Based on the user's day description, suggest " + request.getHobbies() + " sustainable hobbies with the impact on their routine (in scale from 1 to 3): " + request.getDifficulty() + ". Format the response as a JSON object: {'hobbies': [{'name': 'Hobby Name', 'description': 'Hobby Description', 'steps': ['Step 1', 'Step 2', 'Step 3']}, {'name': 'Hobby Name', 'description': 'Hobby Description', 'steps': ['Step 1', 'Step 2', 'Step 3']}]}\" },\n" +
                "    {\"role\": \"user\", \"content\": \"" + request.getInput() + "\"}\n" +
                "  ],\n" +
                "  \"temperature\": 0.4\n" +
                "}";

        HttpEntity<String> chat = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(openaiUrl, chat, String.class);
        return response.getBody();
    }
}
