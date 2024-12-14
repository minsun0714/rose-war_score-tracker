package com.rosewar.scoretracker.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCreatedEvent {
    private final String userId;
}
