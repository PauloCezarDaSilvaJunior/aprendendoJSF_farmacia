package br.com.farmacia.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.farmacia.domain.ItemVenda;
import br.com.farmacia.domain.Produto;
import br.com.farmacia.domain.Venda;
import br.com.farmacia.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {
	public void save(Venda venda, List<ItemVenda> itensVenda) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.save(venda);

			Iterator<ItemVenda> iterator = itensVenda.iterator();

			while (iterator.hasNext()) {
				ItemVenda itemVenda = iterator.next();

				itemVenda.setVenda(venda);
				session.save(itemVenda);
				
				Produto produto = itemVenda.getProduto();
				produto.setQuantidade(new Short(produto.getQuantidade() - itemVenda.getQuantidade() + ""));

				session.update(produto);

			}

			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}

	}
}
