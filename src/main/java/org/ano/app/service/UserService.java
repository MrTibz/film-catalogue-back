package org.ano.app.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.ano.app.dto.SignupRequest;
import org.ano.app.model.User;
import org.ano.app.model.repository.UserRepository;
import org.ano.app.security.AuthUtils;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepo;

    @Inject
    AuthUtils authUtils;

    @Transactional
    public void register(SignupRequest req) {
        if (userRepo.findByEmail(req.email).isPresent()) {
            throw new WebApplicationException("Email déjà utilisé", 409);
        }

        String salt = authUtils.generateSalt();
        String hashedPassword = authUtils.hashPassword(req.password, salt);

        User user = new User();
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);
        user.setEmail(req.email);
        user.setPasswordHash(hashedPassword);
        user.setSalt(salt);

        userRepo.persist(user);
    }

    public User authenticate(String email, String password) {
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException("Invalid credentials", 401));

        boolean valid = authUtils.verifyPassword(
                password,     // mot de passe brut
                user.getSalt(),            // sel enregistré
                user.getPasswordHash()     // hash enregistré
        );


        if (!valid) {
            throw new WebApplicationException("Invalid credentials", 401);
        }
        return user;
    }

    public User findByEmail(String email){
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException("Invalid email", 401));
        return user;
    }
}
