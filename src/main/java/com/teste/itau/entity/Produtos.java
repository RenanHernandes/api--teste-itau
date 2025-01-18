package com.teste.itau.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "CATEGORIA", nullable = false)
    private String categoria;

    @Column(name = "PRECO", nullable = false)
    private Double preco;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;
}
