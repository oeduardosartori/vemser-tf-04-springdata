package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;

import br.com.dbc.vemser.ecommerce.entity.Produto;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterProdutoParaDTOutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ConverterProdutoParaDTOutil converterProdutoParaDTOutil;


    public List<ProdutoDTO> listar() throws Exception {

        return  produtoRepository.findAll().stream()
                .map(converterProdutoParaDTOutil::converteProdutoParaDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProduto(Integer idProduto) throws Exception {

        Produto produto = produtoRepository.findByIdProduto(idProduto);

        if (produto == null) {
            throw new RegraDeNegocioException("Produto não cadastrado.");
        }

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produto);

    }


    public ProdutoDTO salvar(ProdutoCreateDTO produtoCreateDTO) throws Exception {

        Produto produto = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        Produto produtoBuscado = produtoRepository.save(produto);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoBuscado);
    }

    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws Exception {

        Produto buscarProduto = produtoRepository.findByIdProduto(idProduto);
        if (buscarProduto == null) {
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }
        Produto produto = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoAtualizado);
    }

    public void deletar(Integer idProduto) throws Exception {

        Produto buscarProduto = produtoRepository.findByIdProduto(idProduto);

        produtoRepository.delete(buscarProduto);

    }
}

