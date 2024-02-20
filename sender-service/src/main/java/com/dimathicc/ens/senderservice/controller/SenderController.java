package com.dimathicc.ens.senderservice.controller;

import com.dimathicc.ens.senderservice.telegram.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sender")
public class SenderController {
    private final NotificationService notificationService;

    @GetMapping("/send_message")
    private ResponseEntity<Boolean> sendMessage(
            @RequestParam String  chatId,
            @RequestParam String message) {
        return ResponseEntity.ok(notificationService.sendMessage(chatId, message));
    }
}
