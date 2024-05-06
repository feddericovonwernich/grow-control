package com.fg.grow_control.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.key}")
    private String botKey;

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botKey);
    }

}
