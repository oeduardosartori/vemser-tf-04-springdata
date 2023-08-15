package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer>{
    List<EnderecoEntity> findEnderecoEntityByCliente_IdCliente(Integer idCliente);

    @Query("SELECT e FROM EnderecoEntity e JOIN e.cliente c WHERE e.cidade = :cidade")
    Page<EnderecoEntity> listarEnderecosPaginadosPorCidade(@Param("cidade") String cidade, Pageable pageable);

}
