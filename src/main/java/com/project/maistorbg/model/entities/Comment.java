package com.project.maistorbg.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity (name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String text;
    @Column
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne
    @JoinColumn (name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn (name = "receiver_id")
    private User receiver;

}
