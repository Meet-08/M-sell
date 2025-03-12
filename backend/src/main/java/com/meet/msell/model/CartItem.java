package com.meet.msell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    private Product product;

    private String size;

    private int quantity;

    private Integer mrpPrice;

    private Integer sellingPrice;

    private Long userId;

}
