package br.com.farmacia.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.farmacia.domain.Cidade;
import br.com.farmacia.domain.Estado;

public class CidadeDAOTest {
	@Test
	@Ignore
	public void save() {
		Cidade cidade = new Cidade();
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.findById(1L);

		cidade.setEstado(estado);
		cidade.setNome("Jundiai");

		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.save(cidade);
	}

	@Test
	@Ignore
	public void listar() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> resultado = cidadeDAO.listar();
		for (Cidade cidade : resultado) {
			System.out.println(cidade.getNome() + " - " + cidade.getEstado().getNome());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.findById(1L);

		System.out.println(cidade.getNome() + " - " + cidade.getEstado().getNome());
	}

	@Test
	@Ignore
	public void excluir() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.findById(2L);
		if (cidade != null)
			cidadeDAO.delete(cidade);
	}

	@Test
	@Ignore
	public void editar() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.findById(4L);
		cidade.setNome("Itu");
		if (cidade != null)
			cidadeDAO.update(cidade);
	}
	
	@Test
	public void buscarPorEstado() {
		Long estadoCod = 1L;
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> resultado = cidadeDAO.buscarPorEstado(estadoCod);
		for (Cidade cidade : resultado) {
			System.out.println(cidade.getNome() + " - " + cidade.getEstado().getNome());
		}
	}
}
