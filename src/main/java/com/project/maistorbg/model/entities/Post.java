package com.project.maistorbg.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column
    private String description;
    @Column
    private LocalDate publication_date;
    @ManyToOne
    @JoinColumn(name = "repair_categories_id")
    private RepairCategory repairCategory;
    private String city;
}
