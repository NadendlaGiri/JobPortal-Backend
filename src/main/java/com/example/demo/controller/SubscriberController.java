package com.example.demo.controller;

import com.example.demo.model.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
@CrossOrigin(origins = "*")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepo;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody Subscriber subscriber) {
        try {
            // Check if already subscribed
            if (subscriberRepo.existsByEmail(subscriber.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Email already subscribed");
            }

            subscriberRepo.save(subscriber);
            return ResponseEntity.ok("Subscription successful");

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error processing subscription");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSubscribers() {
        return ResponseEntity.ok(subscriberRepo.findAll());
    }
}
