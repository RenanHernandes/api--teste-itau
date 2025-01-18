package com.teste.itau.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;

@Schema(description = "Modelo de requisição para criar ou atualizar produtos")
public class RequisicaoProduto {

    @Schema(description = "Nome do produto", example = "Smartphone")
    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    private String nome;

    @Schema(description = "Categoria do produto", example = "Eletrônicos")
    @NotBlank(message = "A categoria do produto é obrigatória.")
    @Size(max = 50, message = "A categoria do produto deve ter no máximo 50 caracteres.")
    private String categoria;

    @Schema(description = "Descrição do produto", example = "Smartphone de última geração")
    @Size(max = 255, message = "A descrição do produto deve ter no máximo 255 caracteres.")
    private String descricao;

    @Schema(description = "Preço do produto", example = "1999.99")
    @NotNull(message = "O preço do produto é obrigatório.")
    @Min(value = 0, message = "O preço do produto deve ser maior ou igual a zero.")
    private Double preco;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    // Builder para facilitar a construção de objetos
    public static class Builder {
        private String nome;
        private String categoria;
        private String descricao;
        private Double preco;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder preco(Double preco) {
            this.preco = preco;
            return this;
        }

        public RequisicaoProduto build() {
            RequisicaoProduto requisicao = new RequisicaoProduto();
            requisicao.setNome(this.nome);
            requisicao.setCategoria(this.categoria);
            requisicao.setDescricao(this.descricao);
            requisicao.setPreco(this.preco);
            return requisicao;
        }
    }
}
