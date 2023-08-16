package br.com.dbc.vemser.ecommerce.service;


import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.entity.PedidoEntity;
import br.com.dbc.vemser.ecommerce.entity.ProdutoEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.repository.PedidoRepository;
import br.com.dbc.vemser.ecommerce.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    //    private final NotificacaoByEmail notificacaoByEmail;
    private final ObjectMapper objectMapper;

    public PedidoDTO criarPedido(Integer idCliente, PedidoCreateDTO idProduto) throws Exception {

        ClienteEntity cliente = clienteService.findById(idCliente);
        ProdutoEntity produtoEntityBuscado = produtoRepository.findByIdProduto(idProduto.getIdProduto());

        PedidoEntity pedido = new PedidoEntity();
        pedido.setStatusPedido("N");
        pedido.addProduto(produtoEntityBuscado);
        pedido.setCliente(cliente);


        PedidoDTO pedidoOutputDTO = objectMapper.convertValue(pedidoRepository.save(
                        pedido)
                , PedidoDTO.class);


        return pedidoOutputDTO;
    }

    public List<PedidoDTO> listar() {

        return pedidoRepository.findAll().stream()
                .map(p -> converterPedidooParaDTO(p)).toList();

    }


    private PedidoDTO converterPedidooParaDTO(PedidoEntity pedido) {

        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
        pedidoDTO.setIdCliente(pedido.getCliente().getIdCliente());
        pedidoDTO.setProdutos(pedido.getProdutoEntities());

        return pedidoDTO;
    }

    public PedidoDTO buscarByIdPedido(Integer idPedido) throws RegraDeNegocioException {


        PedidoEntity pedidoEntity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido nao encontrado!"));


        return converterPedidooParaDTO(pedidoEntity);

    }


    public Void adicionarProdutoAoPedido(Integer idPedido, Integer idProduto) throws RegraDeNegocioException {

        PedidoEntity pedidoAchado = pedidoRepository.getById(idPedido);
        if (pedidoAchado == null) throw new RegraDeNegocioException("Pedido não encontrado!");

        validacaoPedidoFinalizado(pedidoAchado);

        ProdutoEntity produtoEntityBuscado = produtoRepository.findByIdProduto(idProduto);
        if (produtoEntityBuscado == null) throw new RegraDeNegocioException("Produto não encontrado!");

        pedidoAchado.addProduto(produtoEntityBuscado);

        pedidoRepository.save(pedidoAchado);

        return null;

    }

    private static void validacaoPedidoFinalizado(PedidoEntity pedidoAchado) throws RegraDeNegocioException {
        if (pedidoAchado.getStatusPedido().equalsIgnoreCase("S"))
            throw new RegraDeNegocioException("Pedido finalizado!");
    }

    public Void removerProdutoDoPedido(Integer idPedido, Integer idProduto) throws RegraDeNegocioException {

        PedidoEntity pedidoAchado = pedidoRepository.getById(idPedido);
        if (pedidoAchado == null) throw new RegraDeNegocioException("Pedido não encontrado!");

        validacaoPedidoFinalizado(pedidoAchado);

        ProdutoEntity produtoEntityBuscado = produtoRepository.findByIdProduto(idProduto);
        if (produtoEntityBuscado == null) throw new RegraDeNegocioException("Produto não encontrado!");

        pedidoAchado.removerProduto(produtoEntityBuscado);

        pedidoRepository.save(pedidoAchado);

        return null;

    }


    public void deletePedido(Integer idPedido) throws Exception {

        PedidoEntity pedidoEntity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado!"));

        pedidoEntity.getProdutoEntities().forEach(pedidoEntity::removerProduto);

        pedidoRepository.delete(pedidoEntity);


    }

    public PedidoDTO atualizarStatusPedido(Integer idPedido) throws Exception {

        PedidoEntity pedidoEntity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado!"));

        validacaoPedidoFinalizado(pedidoEntity);

        if (pedidoEntity.getStatusPedido().equalsIgnoreCase("N")) {
            pedidoEntity.setStatusPedido("S");
        }


        ClienteEntity byid = clienteRepository.getById(pedidoEntity.getCliente().getIdCliente());


        PedidoEntity save = pedidoRepository.save(pedidoEntity);

        PedidoDTO pedidoDTO = objectMapper.convertValue(save, PedidoDTO.class);


//            notificacaoByEmail
//                    .notificarByEmailPedidoCliente(byid,
//                            pedidoDTO);


        return pedidoDTO;

    }

}

