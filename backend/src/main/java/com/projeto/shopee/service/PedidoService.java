package com.projeto.shopee.service;

import com.projeto.shopee.entities.Pedido;
import com.projeto.shopee.entities.PedidoItens;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.entities.StatusPedido;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.PedidoRepository;
import com.projeto.shopee.repository.PedidoItensRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.repository.StatusPedidoRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoItensRepository pedidoItensRepository;

    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    public void finalizarCompra(List<Map<String, Object>> produtos, Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);

        pedido.setDataPedido(LocalDate.now());

        List<PedidoItens> itens = produtos.stream().map(produtoMap -> {
            Long produtoId = Long.valueOf(produtoMap.get("id").toString());
            Integer quantidade = Integer.valueOf(produtoMap.get("quantidade").toString());

            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            produtoService.reduzirEstoque(produtoId, quantidade);

            PedidoItens pedidoItem = new PedidoItens();
            pedidoItem.setNomeItem(produto.getNome());
            pedidoItem.setValor(produto.getPreco());
            pedidoItem.setQuantidade(quantidade);
            pedidoItem.setValorTotal(produto.getPreco() * quantidade);
            pedidoItem.setPedido(pedido);
            pedidoItem.setProdutoId(produtoId);
            pedidoItem.setLojaId(produto.getLoja().getId());
            pedidoItem.setStatusPedido(new StatusPedido(1L, "Aguardando pagamento"));

            return pedidoItem;
        }).collect(Collectors.toList());

        pedido.setPedidoItens(itens);
        pedido.setValorTotal(itens.stream().mapToDouble(PedidoItens::getValorTotal).sum());

        pedidoRepository.save(pedido);
    }

    public List<Pedido> getPedidosByUserId(Long userId) {
        return pedidoRepository.findByUsuarioId(userId);
    }

    public List<PedidoItens> getItensVendidosPorLoja(Long lojaId) {
        return pedidoItensRepository.findByLojaId(lojaId);
    }

    public void updateItemStatus(Long itemId, Long statusId) throws Exception {
        Optional<PedidoItens> pedidoItensOptional = pedidoItensRepository.findById(itemId);
        if (pedidoItensOptional.isPresent()) {
            PedidoItens pedidoItens = pedidoItensOptional.get();
            Optional<StatusPedido> statusPedidoOptional = statusPedidoRepository.findById(statusId);
            if (statusPedidoOptional.isPresent()) {
                pedidoItens.setStatusPedido(statusPedidoOptional.get());
                pedidoItensRepository.save(pedidoItens);
            } else {
                throw new Exception("Status não encontrado");
            }
        } else {
            throw new Exception("Item do pedido não encontrado");
        }
    }
} 