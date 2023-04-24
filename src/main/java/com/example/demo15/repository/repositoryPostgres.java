package com.example.demo15.repository;

import com.example.demo15.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface repositoryPostgres extends JpaRepository<Order, Long> {
    @Query(value = "select orders.* from orders  where users.creationDate = :cr",
            nativeQuery = true)
    List<Order> findAllByCreationDate(String cr);
}
