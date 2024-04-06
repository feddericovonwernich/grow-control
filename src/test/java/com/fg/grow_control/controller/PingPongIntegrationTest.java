package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class PingPongIntegrationTest extends BasicApplicationintegrationTest {

    @Test
    void contextLoads() {
        // If context loads, it means it successfully started and connected to the database.
    }

    @Test
    void testPingPongEndpoint() {

        // Another http library could be used here, using what we were using in the dependencies in the meantime.

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/ping"),
                HttpMethod.GET,
                entity,
                String.class
        );

        String expected = "pong!";

        Assertions.assertEquals(expected, response.getBody());
    }

}
