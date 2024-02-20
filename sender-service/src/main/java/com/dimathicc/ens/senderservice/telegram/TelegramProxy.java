package com.dimathicc.ens.senderservice.telegram;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TelegramApi", url = "https://api.telegram.org/")
public interface TelegramProxy {
    @GetMapping(value = "/bot{token}/sendMessage?chat_id={id}&text={message}")
    StatusResponse sendMessage(
            @PathVariable String token,
            @PathVariable String id,
            @PathVariable String message
    );
}
