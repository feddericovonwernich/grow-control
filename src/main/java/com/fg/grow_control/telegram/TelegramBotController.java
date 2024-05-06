package com.fg.grow_control.telegram;

import com.fg.grow_control.service.assistant.AssistantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Controller
public class TelegramBotController implements LongPollingSingleThreadUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(TelegramBotController.class);

    private final TelegramClient telegramClient;

    private final AssistantService assistantService;

    public TelegramBotController(TelegramClient telegramClient, AssistantService assistantService) {
        this.telegramClient = telegramClient;
        this.assistantService = assistantService;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String chatId = String.valueOf(update.getMessage().getChatId());

            String assistantResponse = assistantService.processRequest(update.getMessage().getText());

            // Create your send message object
            SendMessage sendMessage = new SendMessage(chatId, assistantResponse);
            try {
                // Execute it
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Error from telegram client.", e);
            }
        }
    }

}
