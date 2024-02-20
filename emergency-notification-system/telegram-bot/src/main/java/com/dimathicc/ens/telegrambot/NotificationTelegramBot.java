package com.dimathicc.ens.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class NotificationTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botName;

    public NotificationTelegramBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().equals("/info")) {
            SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Your chat ID: " + chatId);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Error in telegram chat {}: {}", chatId, e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
