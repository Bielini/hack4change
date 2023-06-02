package com.alibou.security.ai;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("api/ai")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OpenAIController {

    OpenAIService openAIService;

    @PostMapping("/hobbies")
    public List<Hobby> generateImages(@RequestBody GenerateHobbiesRequest request)
    {
        return openAIService.getSuggestedHobbies(request);
    }

}
