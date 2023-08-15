package br.com.dbc.vemser.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "SEQ_PRODUTO", allocationSize = 1)
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "MODELO")
    private String modelo;


    @Column(name = "TAMANHO")
    private String tamanho;


    @Column(name = "COR")
    private String cor;


    @Column(name = "SETOR")
    private String setor;


    @Column(name = "VALOR")
    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtos", cascade = CascadeType.ALL)
    private Set<PedidoEntity> pedidos = new HashSet<>();

    public void addPedido(PedidoEntity pedidoEntity) {

        pedidos.add(pedidoEntity);
    }

}