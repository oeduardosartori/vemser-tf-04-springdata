package br.com.dbc.vemser.ecommerce.dto.produto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoEntityDTO {
    private Integer idProduto;
    private String modelo;
    private String tamanho;
    private String cor;
    private String setor;
    private Double valor;


}
