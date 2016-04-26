package br.com.farmacia.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.farmacia.domain.Usuario;
import br.com.farmacia.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario>{
	public Usuario autenticar(String cpf, String senha){
		org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			Criteria consulta = session.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.cpf", cpf));
			
			SimpleHash hash = new SimpleHash("md5",senha);
			consulta.add(Restrictions.eq("senha",hash.toHex()));
			
			Usuario resultado = (Usuario) consulta.uniqueResult();
			
			return resultado;
		}catch(RuntimeException e){
			throw e;
		}finally {
			session.close();
		}
	}
}
