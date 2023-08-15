package br.com.dbc.vemser.ecommerce.dto.cliente;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends ClienteCreateDTO{

    private Integer idCliente;
}