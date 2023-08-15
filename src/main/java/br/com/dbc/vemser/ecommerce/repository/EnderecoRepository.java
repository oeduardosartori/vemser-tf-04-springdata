package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer>{

    ClienteEntity findClienteById(Integer idCliente);

    List<EnderecoEntity> findByClienteId(Integer idCliente);
}
