package com.cyboul.demo.web;

import com.cyboul.demo.model.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The well known starting point, say hi!
 */
@RestController
public class GreetingsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/greeting")
    public HttpEntity<Greeting> greet(
            @RequestParam(value = "name", required=false, defaultValue = "World") String name
    ){
        Greeting greeting = new Greeting(counter.get(), String.format(template,name));
        greeting.add(linkTo(methodOn(GreetingsController.class)
                .greet(name))
                .withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}