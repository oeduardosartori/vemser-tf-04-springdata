package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Produto findByIdProduto(Integer idProduto);


    @Query("Select p From PRODUTO p where (:idProduto is null or p.idProduto = :idProduto)")
    List<Produto> buscarTodosOptionalId(Integer idProduto);
}
