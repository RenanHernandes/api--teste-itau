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
    private String nome;

    @Column(name = "CATEGORY", nullable = false)
    private String categoria;

    @Column(name = "PRICE", nullable = false)
    private Double preco;

    @Column(name = "DESCRIPTION", nullable = false)
    private String descricao;
}
