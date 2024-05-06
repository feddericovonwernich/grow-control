package com.fg.grow_control.service.assistant.telegram;

import com.fg.grow_control.service.assistant.AssistantEnabledCondition;
import com.fg.grow_control.service.assistant.AssistantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@Conditional(AssistantEnabledCondition.class)
public class TelegramBotConfiguration {

    @Value("${telegram.bot.key}")
    private String botKey;

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botKey);
    }

    @Bean
    public TelegramBotController telegramBotController(TelegramClient telegramClient, AssistantService assistantService, AssistantThreadService assistantThreadService) throws TelegramApiException {
        TelegramBotController telegramBotController = new TelegramBotController(telegramClient, assistantService, assistantThreadService);
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(botKey, telegramBotController);
        return telegramBotController;
    }

}
