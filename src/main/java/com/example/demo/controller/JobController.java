package com.example.demo.controller;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

@CrossOrigin(origins = "*") // allows your React frontend to access this API
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Get all jobs (sorted by latest postedDate)
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll(Sort.by(Sort.Direction.DESC, "postedDate"));
    }

    // Add a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobRepository.save(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
 // Delete job by ID
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }

}
