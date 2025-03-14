package com.meet.msell.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meet.msell.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private double rating;

    @ElementCollection
    private List<String> productImages;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
