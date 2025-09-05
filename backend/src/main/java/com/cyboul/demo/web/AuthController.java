package com.cyboul.demo.web;

import com.cyboul.demo.service.UserService;
import com.cyboul.demo.tools.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(
            AuthenticationManager authManager,
            JwtUtils jwtUtils,
            UserService userService
    ){
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody AuthRequest request){

        UserDetails userDetails = userService.loadUserByUsername(request.email());
        String jwt = jwtUtils.generateToken(userDetails.getUsername());

        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(), request.password()));

        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }

    public record AuthRequest(String email, String password){}
}
