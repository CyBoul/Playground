package com.cyboul.demo.web;

import com.cyboul.demo.logic.service.UserService;
import com.cyboul.demo.tools.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        log.info("Request received: " + request.email);

        // Validate credentials
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch(Exception e){
            log.error(e.getMessage());
        }

        // Load user & create token
        UserDetails userDetails = userService.loadUserByUsername(request.email());

        if(userDetails == null){
            log.error("NO USERDETAILS MATCHES");
            return null;
        }

        log.info("UserDetails: " + userDetails.getUsername());

        String jwt = jwtUtils.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }

}
