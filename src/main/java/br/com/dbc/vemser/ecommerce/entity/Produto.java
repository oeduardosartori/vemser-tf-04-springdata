package br.com.dbc.vemser.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Enumerated(EnumType.STRING)
    private String setor;


    @Column(name = "VALOR")
    private Double valor;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "produtos", cascade = CascadeType.ALL)
//    private Set<Pedido> pedidos = new HashSet<>();

}


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity(name = "PEDIDO")
//public class Pedido {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
//    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
//    @Column(name = "ID_PEDIDO")
//    private Integer idPedido;
//    private Integer idCliente;
//    private Double valor;
//    private String statusPedido;
//
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "Pedido_X_Produto",
//            joinColumns = @JoinColumn(name = "id_pedido"),
//            inverseJoinColumns = @JoinColumn(name = "id_produto")
//    )
//    private Set<Produto> produtos = new HashSet<>();
