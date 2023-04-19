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
    @Column(name= "days_needed")
    private int daysNeeded;
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "application_workman_id")
    private User user;
    private int status;

}