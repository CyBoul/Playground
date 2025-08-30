package com.cyboul.demo.data;

import com.cyboul.demo.web.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * Consume an external and public API
 * https://jsonplaceholder.typicode.com/users
 *
 */
@Component
public class RestClientRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RestClientRunner.class);
    private final UserRestClient client;

    public RestClientRunner(UserRestClient userRestClient) {
        this.client = userRestClient;
    }

    @Override
    public void run(String... args) {
        client.findAll()
              .stream()
              .limit(2)
              .forEach(u -> {
                  log.info(u.toString());
              });
    }
}
