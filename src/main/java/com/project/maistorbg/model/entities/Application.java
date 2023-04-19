package com.project.maistorbg.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private double price_per_service;
    @Column
    private int daysNeeded;
    @ManyToOne(cascade ={ CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String status;

}