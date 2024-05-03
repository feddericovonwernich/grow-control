package com.fg.grow_control.service.assistant;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Value("${assistant.openia.apikey}")
    private String openIaApiKey;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(openIaApiKey);
    }

}
