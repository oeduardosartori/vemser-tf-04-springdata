package br.com.dbc.vemser.ecommerce.repository;


import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {

}