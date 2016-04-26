package br.com.farmacia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.farmacia.domain.Cidade;
import br.com.farmacia.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade>{
	@SuppressWarnings("unchecked")
	public List<Cidade> buscarPorEstado(Long estadoCod){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCod));
			consulta.addOrder(Order.asc("nome"));
			List<Cidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}
}
