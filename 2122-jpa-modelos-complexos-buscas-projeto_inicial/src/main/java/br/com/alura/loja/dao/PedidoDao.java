package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public BigDecimal valorTotalVendido(){
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}

	public List<RelatorioDeVendasVo> relatorioDeVendas(){
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo(" +
				" produto.nome, " +
				"SUM(item.quantidade), " +
				"MAX(pedido.data)) " +
				"FROM Pedido pedido " +
				"JOIN pedido.produtosPedido item " +
				"JOIN item.produto produto " +
				"GROUP BY produto.nome " +
				"ORDER BY SUM(item.quantidade) DESC";


		return em.createQuery(jpql,RelatorioDeVendasVo.class)
		.getResultList();

	}


	public Pedido buscarPedidoComCliente(long l) {
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
				.setParameter("id",l)
				.getSingleResult();

	}


}
