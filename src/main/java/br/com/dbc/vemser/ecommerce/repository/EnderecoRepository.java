package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {


    @Query("select e from ENDERECO e")
   List<Endereco> listarEnderecoByIdCliente(Integer idCliente);


    List<Endereco> findAllByClienteIdCliente(Integer idCliente);
}
