package br.com.dbc.vemser.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUTO")
public class ProdutoEntity {

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
    @ManyToMany(mappedBy = "produtoEntities", cascade = CascadeType.ALL)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public void addPedido(PedidoEntity pedidoEntity) {

        pedidos.add(pedidoEntity);
    }

    public void removePedido(PedidoEntity pedidoEntity) {

        pedidos.remove(pedidoEntity);
    }

}