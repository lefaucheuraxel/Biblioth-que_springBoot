package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    BookEntity book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Column(nullable = false)
    LocalDateTime borrowDate;

    LocalDateTime returnDate;

}
