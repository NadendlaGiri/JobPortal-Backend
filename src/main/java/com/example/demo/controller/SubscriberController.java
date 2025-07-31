package com.example.demo.controller;

import com.example.demo.model.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
@CrossOrigin(origins = "https://jobportal-frontend.web.app")  // ✅ Allow only your deployed frontend
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @PostMapping
    public String subscribe(@RequestBody Subscriber subscriber) {
        if (subscriberRepository.existsByEmail(subscriber.getEmail())) {
            return "You are already subscribed!";
        }
        subscriberRepository.save(subscriber);
        return "Subscription successful!";
    }
}
