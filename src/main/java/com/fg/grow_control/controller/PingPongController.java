package com.fg.grow_control.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
public class PingPongController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong!");
    }

    @GetMapping("/ping-secured")
    @PreAuthorize("denyAll()")
    public String pingSecured(){
        return "Pong! Unauthorized";
    }

}
