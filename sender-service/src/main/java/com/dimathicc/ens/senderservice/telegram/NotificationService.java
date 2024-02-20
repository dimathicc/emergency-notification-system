package com.dimathicc.ens.senderservice.telegram;

import feign.FeignException.BadRequest;
import feign.FeignException.Forbidden;
import feign.FeignException.TooManyRequests;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Value("${telegram.bot.token}")
    private String token;

    private final TelegramProxy telegramProxy;

    public NotificationService(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;
    }

    public boolean sendMessage(String chatId, String message) {
        try {
            StatusResponse response = telegramProxy.sendMessage(token, chatId, message);
            return response.ok();
        } catch (BadRequest | Forbidden e) {
            return false;
        } catch (TooManyRequests | RetryableException e) {
            log.warn("Too many requests to Telegram");
            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
        return sendMessage(chatId, message);
    }
}
