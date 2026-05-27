package pl.edu.pk.proj.service;

import pl.edu.pk.proj.dto.LoginRequest;
import pl.edu.pk.proj.dto.RegisterRequest;
import pl.edu.pk.proj.model.Role;
import pl.edu.pk.proj.model.User;
import pl.edu.pk.proj.repository.UserRepository;
import pl.edu.pk.proj.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public String login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        User user = userRepository.findByUsername(req.getUsername()).orElseThrow();
        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
}
