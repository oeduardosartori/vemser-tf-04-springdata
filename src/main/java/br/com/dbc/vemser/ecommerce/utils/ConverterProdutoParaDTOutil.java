package br.com.dbc.vemser.ecommerce.utils;


import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.produto.ProdutoDTO;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConverterProdutoParaDTOutil {

    @Autowired
    private ObjectMapper objectMapper;

    public ProdutoDTO converteProdutoParaDTO(ProdutoEntity produtoEntityUpdate) {
        ProdutoDTO produtoUpdateDTO = new ProdutoDTO();


        produtoUpdateDTO.setIdProduto(produtoEntityUpdate.getIdProduto());
        produtoUpdateDTO.setCor(produtoEntityUpdate.getCor());
        produtoUpdateDTO.setModelo(produtoEntityUpdate.getModelo());
        produtoUpdateDTO.setTamanho(produtoEntityUpdate.getTamanho());
        produtoUpdateDTO.setIdProduto(produtoEntityUpdate.getIdProduto());
        produtoUpdateDTO.setSetor(produtoEntityUpdate.getSetor());
        produtoUpdateDTO.setValor(produtoEntityUpdate.getValor());


        return produtoUpdateDTO;
    }

    public ProdutoEntity converteDTOparaProduto(ProdutoCreateDTO produtoCreateDTO) {
        ProdutoEntity produtoEntityConvertido = objectMapper.convertValue(produtoCreateDTO, ProdutoEntity.class);

        produtoEntityConvertido.setCor(produtoCreateDTO.getCor());
        produtoEntityConvertido.setModelo(produtoCreateDTO.getModelo());
        produtoEntityConvertido.setTamanho(produtoCreateDTO.getTamanho());
        produtoEntityConvertido.setSetor(produtoCreateDTO.getSetor());
        produtoEntityConvertido.setValor(produtoCreateDTO.getValor());

        return produtoEntityConvertido;
    }
}
