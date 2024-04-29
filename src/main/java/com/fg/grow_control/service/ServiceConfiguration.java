package com.fg.grow_control.service;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ServiceConfiguration {

    @Value("${openia.apikey}")
    private String openIaApiKey;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(openIaApiKey);
    }

}
