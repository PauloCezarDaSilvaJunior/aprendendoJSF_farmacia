package br.com.farmacia.dao;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.farmacia.domain.Fabricante;
import br.com.farmacia.domain.Produto;

public class ProdutoDAOTest {

	@Test
	public void salvar(){
		Produto produto = new Produto();
		produto.setDescricao("Cataflans 50 mm com 20 comprimidos");
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.findById(1L);
		produto.setFabricante(fabricante);
		produto.setPreco(new BigDecimal("13.70"));
		produto.setQuantidade(new Short("40"));
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.save(produto);
	}
}
