package br.com.farmacia.dao;

import java.util.List;

import org.junit.Test;

public class ItemVendaDAOTeste {
	@Test
	public void produtosVendidos() {

		ItemVendaDAO dao = new ItemVendaDAO();
		List<Object[]> lista = dao.quantidadeProdutosVendidos(1);

		for (Object[] linha : lista) {
			Long quantidade = Long.parseLong(linha[1].toString());
			
			System.out.println(linha[0].toString());
			System.out.println(quantidade);
		}
	}
}
