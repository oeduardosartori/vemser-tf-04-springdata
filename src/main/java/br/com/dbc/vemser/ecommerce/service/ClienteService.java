package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;


    public ClienteDTO save(ClienteCreateDTO clienteCreateDTO) {
        return convertToDto(clienteRepository.save(convertToEntity(clienteCreateDTO)));
    }

    public List<ClienteDTO> findAll(Integer idCliente) {
        return clienteRepository.buscarTodosOptionalId(idCliente)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ClienteDTO getByid(Integer idCliente) throws RegraDeNegocioException {
        ClienteDTO clienteDTO = convertToDto(findById(idCliente));
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        ClienteEntity findedClient = findById(idCliente);
        findedClient.setCpf(clienteCreateDTO.getCpf());
        findedClient.setNome(clienteCreateDTO.getNome());
        findedClient.setTelefone(clienteCreateDTO.getTelefone());
        findedClient.setEmail(clienteCreateDTO.getEmail());
        ClienteDTO updatedClient = convertToDto(clienteRepository.save(findedClient));
        return updatedClient;
    }

    public void delete(Integer idCliente) {
        ClienteEntity clienteEntity = clienteRepository.getById(idCliente);
        clienteRepository.delete(clienteEntity);

    }


    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException {
        return clienteRepository.findById(idcliente).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteDTO convertToDto(ClienteEntity clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }

    public ClienteEntity convertToEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }
}