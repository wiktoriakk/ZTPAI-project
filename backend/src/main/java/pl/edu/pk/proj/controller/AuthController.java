package pl.edu.pk.proj.controller;

import jakarta.validation.Valid;
import pl.edu.pk.proj.dto.AuthResponse;
import pl.edu.pk.proj.dto.LoginRequest;
import pl.edu.pk.proj.dto.RegisterRequest;
import pl.edu.pk.proj.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
