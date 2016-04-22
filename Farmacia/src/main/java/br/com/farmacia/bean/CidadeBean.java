package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.CidadeDAO;
import br.com.farmacia.dao.EstadoDAO;
import br.com.farmacia.domain.Cidade;
import br.com.farmacia.domain.Estado;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class CidadeBean implements Serializable {
	private List<Cidade> cidades;
	private Cidade cidade;
	private List<Estado> estados;

	@PostConstruct
	public void listar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar! ");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			cidade = new Cidade();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os estados! ");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);

			novo();
			// atualizar
			cidades = cidadeDAO.listar();

			Messages.addGlobalInfo("Salvo com sucesso!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar a cidade! ");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.delete(cidade);
			
			cidades = cidadeDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar excluir a cidade! ");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento){
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os estados! ");
			e.printStackTrace();
		}
		
	}
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
