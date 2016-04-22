package br.com.farmacia.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.farmacia.domain.Fabricante;

public class FabricanteDAOTeste {

	@Test
	public void salvar() {
		Fabricante fabricante = new Fabricante();
		fabricante.setDescricao("Neo Quimica");

		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.save(fabricante);
	}

	@Test
	public void listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> resultado = fabricanteDAO.listar();

		for (Fabricante fabricante : resultado) {
			System.out.println(fabricante.getDescricao());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.findById(1L);
		if (fabricante != null)
			System.out.println(fabricante.getDescricao());
		else
			System.out.println("Registro não encontrado!");

	}

	@Test
	public void excluir() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.findById(2L);
		if (fabricante != null)
			fabricanteDAO.delete(fabricante);
	}

	@Test
	public void update() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.findById(1L);
		fabricante.setDescricao("QumicaXLN");
		if (fabricante != null)
			fabricanteDAO.update(fabricante);
	}
}
