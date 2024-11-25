package com.projeto.shopee.util;

import com.projeto.shopee.dto.PedidoDTO;
import com.projeto.shopee.dto.PedidoItensDTO;
import com.projeto.shopee.entities.Pedido;
import com.projeto.shopee.entities.PedidoItens;
import com.projeto.shopee.entities.StatusPedido;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Pedido toEntity(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoDTO.getIdPedido());

        Usuario usuario = usuarioRepository.findById(pedidoDTO.getIdUsuario()).orElse(null);
        pedido.setUsuario(usuario);

        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedido.setDataPedido(pedidoDTO.getDataPedido());

        if (pedidoDTO.getPedidoItens() != null) {
            List<PedidoItens> itens = pedidoDTO.getPedidoItens().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
            pedido.setPedidoItens(itens);
        }

        return pedido;
    }

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(pedido.getIdPedido());
        pedidoDTO.setIdUsuario(pedido.getUsuario().getId());
        pedidoDTO.setValorTotal(pedido.getValorTotal());
        pedidoDTO.setDataPedido(pedido.getDataPedido());

        if (pedido.getPedidoItens() != null) {
            List<PedidoItensDTO> itensDTO = pedido.getPedidoItens().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
            pedidoDTO.setPedidoItens(itensDTO);
        }

        return pedidoDTO;
    }

    private PedidoItens toEntity(PedidoItensDTO pedidoItensDTO) {
        PedidoItens pedidoItens = new PedidoItens();
        pedidoItens.setId(pedidoItensDTO.getId());
        pedidoItens.setNomeItem(pedidoItensDTO.getNomeItem());
        pedidoItens.setValor(pedidoItensDTO.getValor());
        pedidoItens.setQuantidade(pedidoItensDTO.getQuantidade());
        pedidoItens.setValorTotal(pedidoItensDTO.getValorTotal());
        pedidoItens.setProdutoId(pedidoItensDTO.getProdutoId());
        pedidoItens.setLojaId(pedidoItensDTO.getLojaId());
        pedidoItens.setStatusPedido(new StatusPedido(1L, "Aguardando pagamento"));
        return pedidoItens;
    }

    public PedidoItensDTO toDTO(PedidoItens pedidoItens) {
        PedidoItensDTO pedidoItensDTO = new PedidoItensDTO();
        pedidoItensDTO.setId(pedidoItens.getId());
        pedidoItensDTO.setNomeItem(pedidoItens.getNomeItem());
        pedidoItensDTO.setValor(pedidoItens.getValor());
        pedidoItensDTO.setQuantidade(pedidoItens.getQuantidade());
        pedidoItensDTO.setValorTotal(pedidoItens.getValorTotal());
        pedidoItensDTO.setProdutoId(pedidoItens.getProdutoId());
        pedidoItensDTO.setLojaId(pedidoItens.getLojaId());
        pedidoItensDTO.setStatus(pedidoItens.getStatusPedido().getNomeStatus());
        return pedidoItensDTO;
    }
}