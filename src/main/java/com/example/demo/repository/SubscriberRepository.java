// SubscriberRepository.java
package com.example.demo.repository;

import com.example.demo.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsByEmail(String email);
}
