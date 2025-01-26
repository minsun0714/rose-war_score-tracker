package com.rosewar.scoretracker.listener;

import com.rosewar.scoretracker.event.UserCreatedEvent;
import com.rosewar.scoretracker.service.StatService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StatEventListener {
    private final StatService statService;

    public StatEventListener(StatService statService) {
        this.statService = statService;
    }

    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        statService.createStat(event.getUserId());
    }
}
