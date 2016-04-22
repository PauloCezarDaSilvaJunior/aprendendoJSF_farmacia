package br.com.farmacia.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.farmacia.domain.Estado;

public class EstadoDAOTeste {

	@Test
	@Ignore
	public void salvar() {
		Estado estado = new Estado();
		estado.setNome("Minas Gerais");
		estado.setSigla("MG");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.save(estado);
	}

	@Test
	@Ignore
	public void listar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listar();

		for (Estado estado : resultado) {
			System.out.println(estado.getNome() + " - " + estado.getSigla());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.findById(1L);
		if (estado != null)
			System.out.println(estado.getNome() + " - " + estado.getSigla());
		else
			System.out.println("Registro não encontrado!");

	}

	@Test
	@Ignore
	public void excluir() {
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.findById(3L);
		if (estado != null)
			estadoDAO.delete(estado);
	}
	
	@Test
	public void update(){
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.findById(2L);
		estado.setNome("Rio de Janeiro");
		estado.setSigla("RJ");
		if (estado != null)
			estadoDAO.update(estado);
	}
}
