package br.com.dbc.vemser.ecommerce.service;




import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterProdutoParaDTOutil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ConverterProdutoParaDTOutil converterProdutoParaDTOutil;


    public List<ProdutoDTO> listar(Integer idProduto) throws Exception {

        return  produtoRepository.buscarTodosOptionalId(idProduto).stream()
                .map(converterProdutoParaDTOutil::converteProdutoParaDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProduto(Integer idProduto) throws Exception {

        ProdutoEntity produtoEntity = produtoRepository.findByIdProduto(idProduto);

        if (produtoEntity == null) {
            throw new RegraDeNegocioException("Produto não cadastrado.");
        }

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntity);

    }


    public ProdutoDTO salvar(ProdutoCreateDTO produtoCreateDTO) throws Exception {

        ProdutoEntity produtoEntity = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        ProdutoEntity produtoEntityBuscado = produtoRepository.save(produtoEntity);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntityBuscado);
    }

    public ProdutoDTO atualizar(Integer idProduto, ProdutoCreateDTO produtoCreateDTO) throws Exception {

        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);
        if (buscarProdutoEntity == null) {
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }
        ProdutoEntity produtoEntity = converterProdutoParaDTOutil.converteDTOparaProduto(produtoCreateDTO);

        ProdutoEntity produtoEntityAtualizado = produtoRepository.save(produtoEntity);

        return converterProdutoParaDTOutil.converteProdutoParaDTO(produtoEntityAtualizado);
    }

    public void deletar(Integer idProduto) throws Exception {

        ProdutoEntity buscarProdutoEntity = produtoRepository.findByIdProduto(idProduto);

        produtoRepository.delete(buscarProdutoEntity);

    }
}
