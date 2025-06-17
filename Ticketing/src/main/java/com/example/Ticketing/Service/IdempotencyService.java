package com.example.Ticketing.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class IdempotencyService {
    private final Set<String> usedKeys = ConcurrentHashMap.newKeySet();

    public boolean isKeyUsed(String key) {
        return usedKeys.contains(key);
    }

    public void markKeyUsed(String key) {
        usedKeys.add(key);
    }

    @Scheduled(fixedRate = 3600000) // Limpar cache a cada hora
    public void cleanCache() {
        usedKeys.clear();
    }
}