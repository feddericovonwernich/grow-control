package com.fg.grow_control.service.assistant.telegram;

import com.fg.grow_control.service.assistant.AssistantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;

import static java.sql.Timestamp.valueOf;

public class TelegramBotController implements LongPollingSingleThreadUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(TelegramBotController.class);

    private final TelegramClient telegramClient;

    private final AssistantService assistantService;

    private final AssistantThreadService assistantThreadService;

    public TelegramBotController(TelegramClient telegramClient, AssistantService assistantService, AssistantThreadService assistantThreadService) {
        this.assistantThreadService = assistantThreadService;
        log.info("Initializing Telegram bot.");
        this.telegramClient = telegramClient;
        this.assistantService = assistantService;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String chatText = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            String userId = String.valueOf(update.getMessage().getChat().getId());

            String assistantResponse = "";

            // TODO Should print usage instructions on /start command.

            if (chatText.equals("/newThread")) {
                // start a new thread with the assistant.
                assistantResponse = assistantService.processRequest("Hey assistant.");
                saveThreadIdForUser(userId);
            } else {
                // look for existing thread.
                AssistantThread assistantThread = assistantThreadService.getLatestThreadForUser(userId);
                if (assistantThread == null) {
                    assistantResponse = assistantService.processRequest(chatText);
                    saveThreadIdForUser(userId);
                } else {
                    assistantResponse = assistantService.processRequest(chatText, assistantThread.getThreadId());
                }
            }

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

    private void saveThreadIdForUser(String userId) {
        try {
            AssistantThread assistantThread;
            String threadId = MDC.get("threadId");
            if (threadId != null) {
                assistantThread = AssistantThread.builder()
                        .userId(userId)
                        .threadId(threadId)
                        .creationDate(valueOf(LocalDateTime.now()))
                        .build();
                assistantThreadService.createOrUpdate(assistantThread);
            }
        } finally {
            MDC.clear();
        }
    }

}
