package br.com.farmacia.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*Classe para fazer o carregamento do contexto do hibernate junto com o tom cat ao invez de carregar quando abrir o site*/

public class HibernateContext implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//fechar a fabrica de sessoes quando o tomcat for desligado
		HibernateUtil.getSessionFactory().close();

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//abrir uma sessao do hibernate na hora da inicialização do tomcat
		HibernateUtil.getSessionFactory();

	}

}
