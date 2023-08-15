package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("Select c From CLIENTE c where (:idCliente is null or c.idCliente = :idCliente)")
    List<ClienteEntity> buscarTodosOptionalId(Integer idCliente);

}
