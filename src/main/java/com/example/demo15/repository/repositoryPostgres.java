package com.example.demo15.repository;

import com.example.demo15.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface repositoryPostgres extends JpaRepository<Order, Long> {
}
