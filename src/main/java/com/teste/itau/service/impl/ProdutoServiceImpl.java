package com.teste.itau.service.impl;

import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.entity.Produtos;
import com.teste.itau.repository.ProdutoRepository;
import com.teste.itau.service.ProdutoService;
import com.teste.itau.service.messaging.PublicadorDeMensagemProduto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final PublicadorDeMensagemProduto publicadorDeMensagem;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, PublicadorDeMensagemProduto publicadorDeMensagem) {
        this.produtoRepository = produtoRepository;
        this.publicadorDeMensagem = publicadorDeMensagem;
    }

    @Override
    public List<Produtos> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produtos getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    @Override
    public Produtos createProduto(RequisicaoProduto requisicaoProduto) {
        Produtos produto = Produtos.builder()
                .nome(requisicaoProduto.getNome())
                .preco(requisicaoProduto.getPreco())
                .categoria(requisicaoProduto.getCategoria())
                .descricao(requisicaoProduto.getDescricao())
                .build();

        Produtos produtoSalvo = produtoRepository.save(produto);

        // Publica a mensagem após salvar
        publicadorDeMensagem.publicarMensagemProdutoCriado(produtoSalvo);

        return produtoSalvo;
    }

    @Override
    public Produtos updateProduto(Long id, RequisicaoProduto requisicaoProduto) {
        Produtos produtoExistente = getProdutoById(id);

        produtoExistente.setNome(requisicaoProduto.getNome());
        produtoExistente.setPreco(requisicaoProduto.getPreco());
        produtoExistente.setCategoria(requisicaoProduto.getCategoria());
        produtoExistente.setDescricao(requisicaoProduto.getDescricao());

        return produtoRepository.save(produtoExistente);
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
