package com.example.demo15.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Items", schema = "mirea")
@NoArgsConstructor
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(columnDefinition = "varchar(200) ", insertable = true, length = 200, name = "createDate", nullable = true, unique = false, updatable = true)
    private String createDate;
    @Column(columnDefinition = "varchar(200) ", insertable = true, length = 200, name = "price", nullable = true, unique = false, updatable = true)
    private String price;

    @ManyToOne
    @JsonIgnore
    private Order order;



}
