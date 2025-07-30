package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    // No need to write anything, JpaRepository gives CRUD for free 💖
}
