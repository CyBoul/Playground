package com.cyboul.demo.web;

import com.cyboul.demo.model.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The well known starting point, say hi!
 */
@Controller
public class GreetingsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/greeting")
    public HttpEntity<Greeting> greet(
            @RequestParam(value = "name", required=false, defaultValue = "World") String name
    ){
        Greeting greetings = new Greeting(counter.get(), String.format(template,name));
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @GetMapping("/gugu")
    @ResponseBody
    public Map<String, Object> gugu(){
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

}