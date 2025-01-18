package com.teste.itau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.teste.itau.entity.Produtos;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

    @Query("SELECT p FROM Produtos p WHERE p.categoria = :categoria")
    List<Produtos> findByCategoria(@Param("categoria") String categoria);

    @Query("SELECT p FROM Produtos p WHERE p.preco BETWEEN :precoMin AND :precoMax")
    List<Produtos> findByPrecoBetween(@Param("precoMin") Double precoMin, @Param("precoMax") Double precoMax);

    @Query("SELECT p FROM Produtos p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produtos> findByNomeContainingIgnoreCase(@Param("nome") String nome);

}
