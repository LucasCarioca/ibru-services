package net.lucasdesouza.ibru.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import net.lucasdesouza.ibru.models.RegisterUserRequest;
import net.lucasdesouza.ibru.models.User;
import net.lucasdesouza.ibru.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UpdatableBCryptSerivce updatableBCryptSerivce;
    private final Algorithm algorithm;
    private final String issuer;

    @Autowired
    AuthService(
            UserRepository userRepository,
            UpdatableBCryptSerivce updatableBCryptSerivce,
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.issuer}") String issuer
    ) {
        this.userRepository = userRepository;
        this.updatableBCryptSerivce = updatableBCryptSerivce;
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
    }

    public String register(RegisterUserRequest request) {
        String hash = updatableBCryptSerivce.hash(request.getPassword());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hash);

        userRepository.save(user);
        return user.getId();
    }

    public Optional<User> getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    public Map<String, String> login(String username, String password) throws Exception {
        Optional<User> user = getUser(username);
        if (user.isPresent()) {
            boolean isAuthenticated = updatableBCryptSerivce.verifyHash(password, user.get().getPassword());
            if (isAuthenticated) {
                Map<String, String> payload = new HashMap<>();
                payload.put("email", username);
                String token = JWT.create()
                        .withPayload(payload)
                        .withIssuer(issuer)
                        .sign(algorithm);
                Map<String, String> identity = new HashMap<>();
                identity.put("token", token);
                identity.put("id", user.get().getId());
                identity.put("email", user.get().getEmail());
                return identity;
            }
        }
        throw new Exception("Failed to authenticate user");
    }
}
