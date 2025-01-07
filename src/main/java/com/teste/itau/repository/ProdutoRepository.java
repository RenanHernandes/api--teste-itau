package com.teste.itau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.itau.entity.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
}
