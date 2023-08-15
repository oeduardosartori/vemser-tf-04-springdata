package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.EnderecoEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    // private final NotificacaoByEmail notificacaoByEmail;
    public List<EnderecoDTO> listarEnderecos() throws Exception {
        List<EnderecoEntity> enderecos = enderecoRepository.findAll();
        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();

        for (EnderecoEntity endereco : enderecos) {
            enderecoDTOS.add(converterByEnderecoDTO(endereco));
        }
        return enderecoDTOS;
    }

    public EnderecoDTO getEnderecoById(Integer idEndereco) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(Math.toIntExact(idEndereco));
        if (enderecoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        return converterByEnderecoDTO(enderecoOpt.get());
    }

    public List<EnderecoDTO> listarEnderecoByIdCliente(Integer idCliente) throws Exception {
        List<EnderecoEntity> enderecos = enderecoRepository.findByClienteId(idCliente);
        if (enderecos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum endereço encontrado para o cliente");
        }

        return enderecos.stream()
                .map(this::converterByEnderecoDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO create(Integer idCliente, EnderecoCreateDTO enderecoCreateDTO) throws Exception {
        ClienteEntity clienteEntity = enderecoRepository.findClienteById(idCliente);
        if (clienteEntity == null) {
            throw new RegraDeNegocioException("Cliente não encontrado");
        }

        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
        entity.setCliente(clienteEntity);

        EnderecoEntity enderecoCreated = enderecoRepository.save(entity);

        return converterByEnderecoDTO(enderecoCreated);
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(idEndereco);
        if (enderecoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        EnderecoEntity endereco = enderecoOpt.get();

        enderecoCreateDTO.setIdCliente(endereco.getCliente().getIdCliente());
        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
        entity.setIdEndereco(idEndereco);

        EnderecoEntity enderecoUpdated = enderecoRepository.save(entity);

        EnderecoDTO enderecoDTO = converterByEnderecoDTO(enderecoUpdated);

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws Exception {
        Optional<EnderecoEntity> enderecoOpt = enderecoRepository.findById(idEndereco);
        if (enderecoOpt.isPresent()) {
            EnderecoEntity endereco = enderecoOpt.get();
//             ClienteDTO clienteDTO = clienteService.getByid(endereco.getCliente().getId());
            enderecoRepository.delete(endereco);
//            notificacaoByEmail.notificarByEmailEndereco(clienteDTO, "deletado");
        }
    }

    public EnderecoDTO converterByEnderecoDTO(EnderecoEntity endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdCliente(endereco.getCliente().getIdCliente());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setBairro(endereco.getBairro());
        return enderecoDTO;
    }

    public EnderecoEntity converterByEndereco(EnderecoCreateDTO enderecoCreateDTO) {
        EnderecoEntity entity = new EnderecoEntity();
        entity.setNumero(enderecoCreateDTO.getNumero());
        entity.setLogradouro(enderecoCreateDTO.getLogradouro());
        entity.setComplemento(enderecoCreateDTO.getComplemento());
        entity.setCep(enderecoCreateDTO.getCep());
        entity.setCidade(enderecoCreateDTO.getCidade());
        entity.setEstado(enderecoCreateDTO.getEstado());
        entity.setBairro(enderecoCreateDTO.getBairro());
        return entity;
    }

    //    CÓDIGO ANTERIOR PARA BACKUP

//    ===========================

//    private final EnderecoRepository enderecoRepository;
//    private final ClienteService clienteService;
//    private final NotificacaoByEmail notificacaoByEmail;
//    private final ObjectMapper objectMapper;
//
//    public List<EnderecoDTO> listarEnderecos() throws Exception {
//        List<EnderecoEntity> enderecos = enderecoRepository.listarEnderecos();
//        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
//
//        for (EnderecoEntity endereco : enderecos) {
//            enderecoDTOS.add(converterByEnderecoDTO(endereco));
//        }
//        return enderecoDTOS;
//
//    }
//
//    public EnderecoDTO getEnderecoById(Integer idEndereco) throws Exception {
//        EnderecoEntity endereco = enderecoRepository.getEnderecoById(idEndereco);
//        if(endereco == null) {
//            throw new RegraDeNegocioException("Endereço não encontrado");
//        }
//        return converterByEnderecoDTO(endereco);
//    }
//    public List<EnderecoDTO> listarEnderecoByIdCliente(Integer idCliente) throws Exception {
//        ClienteDTO clienteDTO = clienteService.getClienteById(idCliente);
//        if(clienteDTO == null) {
//            throw new RegraDeNegocioException("Cliente não encontrado");
//        }
//        List<EnderecoDTO> enderecoDTOList = enderecoRepository.listarEnderecoByIdCliente(idCliente)
//                .stream().map(this::converterByEnderecoDTO).collect(Collectors.toList());
//
//        return enderecoDTOList;
//    }
//
//    public EnderecoDTO create(Integer idCliente, EnderecoCreateDTO enderecoCreateDTO) throws Exception {
//        ClienteDTO clienteDTO = clienteService.getClienteById(idCliente);
//        if(clienteDTO == null) {
//            throw new RegraDeNegocioException("Cliente não encontrado");
//        }
//        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
//
//        EnderecoEntity enderecoCreated = enderecoRepository.create(idCliente, entity);
//
//        EnderecoDTO enderecoDTO = converterByEnderecoDTO(enderecoCreated);
//        notificacaoByEmail.notificarByEmailEndereco(clienteDTO, "criado");
//        return enderecoDTO;
//    }
//
//    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws Exception {
//        EnderecoEntity endereco = enderecoRepository.getEnderecoById(idEndereco);
//        if(endereco == null) {
//            throw new RegraDeNegocioException("Endereço não encontrado");
//        }
//
//        enderecoCreateDTO.setIdCliente(endereco.getIdCliente());
//        EnderecoEntity entity = converterByEndereco(enderecoCreateDTO);
//        entity.setIdEndereco(idEndereco);
//
//        EnderecoEntity enderecoUpdated = enderecoRepository.update(idEndereco, entity);
//        ClienteDTO clienteDTO = clienteService.getClienteById(enderecoUpdated.getIdCliente());
//
//        EnderecoDTO enderecoDTO = converterByEnderecoDTO(enderecoUpdated);
//        notificacaoByEmail.notificarByEmailEndereco(clienteDTO, "atualizado");
//
//        return enderecoDTO;
//
//    }
//
//    public void delete(Integer idEndereco) throws Exception {
//        EnderecoEntity endereco = enderecoRepository.getEnderecoById(idEndereco);
//        if(endereco != null) {
//            ClienteDTO clienteDTO = clienteService.getClienteById(endereco.getIdCliente());
//            enderecoRepository.delete(idEndereco);
//            notificacaoByEmail.notificarByEmailEndereco(clienteDTO, "deletado");
//        }
//    }
//
//    public EnderecoDTO converterByEnderecoDTO(EnderecoEntity endereco) {
//        EnderecoDTO enderecoDTO = new EnderecoDTO();
//        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
//        enderecoDTO.setIdCliente(endereco.getIdCliente());
//        enderecoDTO.setNumero(endereco.getNumero());
//        enderecoDTO.setLogradouro(endereco.getLogradouro());
//        enderecoDTO.setComplemento(endereco.getComplemento());
//        enderecoDTO.setCep(endereco.getCep());
//        enderecoDTO.setCidade(endereco.getCidade());
//        enderecoDTO.setEstado(endereco.getEstado());
//        enderecoDTO.setBairro(endereco.getBairro());
//
//        return enderecoDTO;
//    }
//
//    public EnderecoEntity converterByEndereco(EnderecoCreateDTO enderecoCreateDTO) {
//        EnderecoEntity entity = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
//        entity.setNumero(enderecoCreateDTO.getNumero());
//        entity.setLogradouro(enderecoCreateDTO.getLogradouro());
//        entity.setComplemento(enderecoCreateDTO.getComplemento());
//        entity.setCep(enderecoCreateDTO.getCep());
//        entity.setCidade(enderecoCreateDTO.getCidade());
//        entity.setEstado(enderecoCreateDTO.getEstado());
//        entity.setIdCliente(enderecoCreateDTO.getIdCliente());
//        entity.setBairro(enderecoCreateDTO.getBairro());
//
//        return entity;
//    }
}
