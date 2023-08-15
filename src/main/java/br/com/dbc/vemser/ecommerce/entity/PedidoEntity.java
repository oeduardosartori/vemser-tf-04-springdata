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
@Entity(name = "PEDIDO")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity cliente;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "pago")
    private String statusPedido;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Pedido_X_Produto")
    private Set<Produto> produtos = new HashSet<>();

    public void addProduto(Produto produto) {
        produto.addPedido(this);
        produtos.add(produto);
    }
}

