package br.com.dbc.vemser.ecommerce.controller;

import br.com.dbc.vemser.ecommerce.doc.ClienteControllerDoc;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.service.ClienteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements ClienteControllerDoc {

    private final ClienteService clienteService;

    @Override
    public ResponseEntity<List<ClienteDTO>> findAll(@RequestParam(required = false) Integer idCliente){
        return new ResponseEntity<List<ClienteDTO>>(clienteService.findAll(idCliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteDTO> getById(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<ClienteDTO>(clienteService.getByid(idCliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteCreateDTO cliente){
        return new ResponseEntity<ClienteDTO>(clienteService.save(cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer idCliente, @RequestBody ClienteCreateDTO cliente) throws RegraDeNegocioException{
        return new ResponseEntity<ClienteDTO>(clienteService.update(idCliente, cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer idCliente){
        clienteService.delete(idCliente);
        return ResponseEntity.ok().build();
    }
}