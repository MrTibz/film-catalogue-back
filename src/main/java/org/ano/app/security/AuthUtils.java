package org.ano.app.security;

import jakarta.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class AuthUtils {

    private static final SecureRandom secureRandom = new SecureRandom();

    // Génère un salt sécurisé
    public String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hache un mot de passe avec SHA-256 et salt
    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }


    // Vérifie si le mot de passe fourni correspond au mot de passe haché stocké
    public boolean verifyPassword(String rawPassword, String salt, String hashedPassword) {
        String computedHash = hashPassword(rawPassword, salt);
        return computedHash.equals(hashedPassword);
    }

    public String generateSessionToken(String seed) {
        long timestamp = System.currentTimeMillis();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String raw = seed + ":" + timestamp + ":" + Base64.getEncoder().encodeToString(randomBytes);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest); // URL-safe
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors de la génération du token", e);
        }
    }

}