package com.example.demo.controller;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // ✅ Updated: Return Map instead of JobPageResponse to avoid JSON deserialization issues
    @GetMapping
    public ResponseEntity<Map<String, Object>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postedDate"));
        Page<Job> jobPage = jobRepository.findAll(pageable);

        List<Job> jobs = jobPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("content", jobs);
        response.put("currentPage", jobPage.getNumber());
        response.put("totalItems", jobPage.getTotalElements());
        response.put("totalPages", jobPage.getTotalPages());
        response.put("isLast", jobPage.isLast());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Add a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobRepository.save(job);

        // Notify subscribers with job title and frontend link
        String jobLink = "https://jobportal-frontend.web.app";
        notifySubscribers(savedJob.getTitle(), jobLink);

        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    // Delete job by ID
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }

    // Notify email subscribers using the Node.js backend
    private void notifySubscribers(String title, String link) {
        String url = "https://mailer-backend-mgx9.onrender.com/send-alert";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("link", link);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Email alert sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send email alert: " + e.getMessage());
        }
    }
}
