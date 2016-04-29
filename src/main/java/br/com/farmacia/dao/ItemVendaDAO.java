package br.com.farmacia.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.farmacia.domain.ItemVenda;
import br.com.farmacia.util.HibernateUtil;

public class ItemVendaDAO extends GenericDAO<ItemVenda> {

	// retorna uma lista na posicao [0] = nome do produto e na [1] = quantidade
	public List<Object[]> quantidadeProdutosVendidos(int produto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String sql = "select p.descricao, sum(it.quantidade) from itemvenda it"
					+ " join produto p on it.produto_codigo = p.codigo" + " group by produto_codigo";
			@SuppressWarnings("unchecked")
			List<Object[]> lista = session.createSQLQuery(sql).list();
			return lista;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}
}
