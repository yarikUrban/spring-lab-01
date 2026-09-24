package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("telegram")
@Order(3)
public class TelegramNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(TelegramNotifier.class);

    @PostConstruct
    public void init() {
        log.info("TelegramNotifier bean initialized via @PostConstruct");
    }

    @Override
    public String send(String message) {
        log.info("TELEGRAM >> {}", message);
        return "telegram: " + message;
    }

    @Override
    public String channel() {
        return "telegram";
    }
}