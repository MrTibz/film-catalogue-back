package org.ano.app.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionService {

    private final Map<String, Long> tokenUserMap = new ConcurrentHashMap<>();

    public void store(String token, Long userId) {
        tokenUserMap.put(token, userId);
        System.out.println("Token stocké : " + token + " pour userId : " + userId);
    }

    public Long getUserIdFromToken(String token) {
        return tokenUserMap.get(token);
    }

    public void invalidate(String token) {
        tokenUserMap.remove(token);
    }
}
