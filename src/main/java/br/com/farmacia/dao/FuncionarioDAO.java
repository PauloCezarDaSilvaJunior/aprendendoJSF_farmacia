package br.com.farmacia.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.farmacia.domain.Funcionario;
import br.com.farmacia.util.HibernateUtil;

public class FuncionarioDAO extends GenericDAO<Funcionario> {
	// retorna uma lista na posicao [0] = nome do funcionario e na [1] = quantidade de vendas
	public List<Object[]> quantidadeProdutosVendidos(int produto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String sql = "select p.nome, count(v.funcionario_codigo)from venda v "
					+ "right join funcionario f on f.codigo = v.funcionario_codigo "
					+ "join pessoa p on f.pessoa_codigo = p.codigo "
					+ "group by v.funcionario_codigo;";
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
