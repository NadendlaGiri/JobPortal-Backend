package com.example.demo.controller;

import com.example.demo.model.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @PostMapping
    public ResponseEntity<?> addSubscriber(@RequestBody Subscriber subscriber) {
        if (subscriber.getEmail() == null || subscriber.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (subscriberRepository.existsByEmail(subscriber.getEmail())) {
            return ResponseEntity.status(409).body("You are already subscribed.");
        }

        Subscriber saved = subscriberRepository.save(subscriber);
        return ResponseEntity.ok(saved);
    }
}
