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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column
    private int age;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "profile_photo_url")
    private String profilePhoto;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "registration_date")
    private LocalDate registrationDate;

// Doesn't compile because of missing classes
//    @ManyToMany
//    @JoinTable(
//            name = "workman_categories",
//            joinColumns = @JoinColumn(name = "workman_id"),
//            inverseJoinColumns = @JoinColumn(name = "workman_repair_category_id")
//    )
//    private Set<Category> categories = new HashSet<>();
//    @OneToMany
//    @JoinColumn(name = "owner_id")
//    private Set<Post> posts = new HashSet<>();

}