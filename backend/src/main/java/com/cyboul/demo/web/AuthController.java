package com.cyboul.demo.web;

import com.cyboul.demo.logic.service.UserService;
import com.cyboul.demo.model.dto.AuthRequest;
import com.cyboul.demo.tools.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

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
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(), request.password()));
        } catch (BadCredentialsException e){
            log.error("Login attempt failed for '{}' : {}", request.email(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        UserDetails userDetails = userService.loadUserByEmail(request.email());
        String jwt = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }

}
