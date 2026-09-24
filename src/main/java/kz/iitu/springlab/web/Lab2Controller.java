package kz.iitu.springlab.web;

import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.notify.Notifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final Notifier customNotifier;

    public Lab2Controller(NotificationService notifications,
                          @Qualifier("telegram") Notifier customNotifier) {
        this.notifications = notifications;
        this.customNotifier = customNotifier;
    }

    @GetMapping("/custom")
    public Map<String, String> custom(@RequestParam String text) {
        return Map.of(
                "channel", customNotifier.channel(),
                "result", customNotifier.send(text)
        );
    }
}