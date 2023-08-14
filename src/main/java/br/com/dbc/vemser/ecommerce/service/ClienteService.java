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

<<<<<<< HEAD
    private final NotificacaoByEmail notificacaoByEmail;

    private final ObjectMapper objectMapper;

    public List<ClienteDTO> list() throws BancoDeDadosException {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteDTOS.add(converterByClienteDTO(cliente));
        }

        return clienteDTOS;
    }

    public ClienteDTO getClienteById(Integer idCliente) throws Exception {

        Cliente cliente = clienteRepository.findById(idCliente).get();

        if (cliente == null) throw new RegraDeNegocioException("Cliente não cadastrado!");

        return converterByClienteDTO(cliente);
    }

    public ClienteDTO create(ClienteCreateDTO clienteCreateDTO) throws Exception {
        Cliente entity = converterByCliente(clienteCreateDTO);
        Cliente cliente = clienteRepository.save(entity);
        ClienteDTO clienteDTO = converterByClienteDTO(cliente);
        notificacaoByEmail.notificarByEmailCliente(clienteDTO, "criado");

        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws Exception {

        getClienteById(idCliente);

        Cliente entity = converterByCliente(clienteCreateDTO);
        Cliente cliente = clienteRepository.save(entity);
        ClienteDTO clienteDTO = converterByClienteDTO(cliente);
        notificacaoByEmail.notificarByEmailCliente(clienteDTO, "atualizado");

        return clienteDTO;
    }

    public void delete(Integer idCliente) throws Exception {
        ClienteDTO clienteDTO = getClienteById(idCliente);
        notificacaoByEmail.notificarByEmailCliente(clienteDTO, "deletado");


        clienteRepository.delete(converterByCliente(clienteDTO));
=======
    public ClienteDTO save(ClienteCreateDTO clienteCreateDTO){
        return convertToDto(clienteRepository.save(convertToEntity(clienteCreateDTO)));
    }

    public List<ClienteDTO> findAll(){
        return clienteRepository.findAll().stream().map(clienteEntity -> convertToDto(clienteEntity)).collect(Collectors.toList());
    }

    public ClienteDTO getByid(Integer idCliente)throws RegraDeNegocioException{
        ClienteDTO clienteDTO = convertToDto(findById(idCliente));
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException{
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
>>>>>>> developer
    }



    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException{
        return clienteRepository.findById(idcliente).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteDTO convertToDto(ClienteEntity clienteEntity){
        return objectMapper.convertValue(clienteEntity,ClienteDTO.class);
    }
    public ClienteEntity convertToEntity(ClienteCreateDTO clienteCreateDTO){
        return objectMapper.convertValue(clienteCreateDTO,ClienteEntity.class);
    }
}