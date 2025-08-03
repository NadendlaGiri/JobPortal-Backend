package com.example.demo.service;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Job addJob(Job job) {
        Job savedJob = jobRepository.save(job);
        notifySubscribers(savedJob.getTitle());
        return savedJob;
    }

    private void notifySubscribers(String jobTitle) {
        String emailServiceUrl = "https://mailer-backend-mgx9.onrender.com/send-alerts"; // Replace with your deployed mailer URL

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("title", jobTitle);  // as your mailer expects "title" field

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(emailServiceUrl, request, String.class);
        } catch (Exception e) {
            System.err.println("Failed to notify subscribers: " + e.getMessage());
        }
    }
}
