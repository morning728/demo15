package com.example.demo15.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Orders", schema = "mirea")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "creation_date", nullable = true)
    private String creationDate;

    @OneToMany(mappedBy = "order")
    private List<Item> items;

    public Order() {
    }

    public Order(String test4) {
        creationDate = test4;
    }
}
