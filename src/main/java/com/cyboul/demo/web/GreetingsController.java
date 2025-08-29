package com.cyboul.demo.web;

import java.util.concurrent.atomic.AtomicLong;

import com.cyboul.demo.model.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class GreetingsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greet(
            @RequestParam(value = "name", defaultValue = "World") String name) {

        Greeting greeting = new Greeting(44L, String.format(template,name));

        // HATEAOS
        greeting.add(linkTo(methodOn(GreetingsController.class)
                .greet(name))
                .withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}