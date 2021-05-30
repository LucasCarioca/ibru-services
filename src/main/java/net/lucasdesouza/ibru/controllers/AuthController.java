package net.lucasdesouza.ibru.controllers;

import net.lucasdesouza.ibru.models.RegisterUserRequest;
import net.lucasdesouza.ibru.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> login(@RequestHeader("Authorization") String auth) {
        try {
            String base64Credentials = auth.split(" ")[1];
            String[] decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials)).split(":");
            String username = decodedCredentials[0];
            String password = decodedCredentials[1];
            Map<String, String> response = authService.login(username, password);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterUserRequest request) {
        String id = authService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "new user  "+ request.getEmail()+" created!");
        response.put("userId", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
