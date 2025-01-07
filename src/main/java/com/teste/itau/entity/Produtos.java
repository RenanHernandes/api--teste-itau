package com.teste.itau.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
