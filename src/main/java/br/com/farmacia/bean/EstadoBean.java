package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.EstadoDAO;
import br.com.farmacia.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {
	private Estado estado;
	private List<Estado> estados;

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			// atualizar o modelo
			estado = new Estado();
			Messages.addGlobalInfo("Salvo com sucesso!");

			// atualizar a lista
			estados = estadoDAO.listar();

		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado!");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o estado!");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.delete(estado);
			Messages.addGlobalInfo("Estado removido com sucesso! ");

			// atualizar a lista
			estados = estadoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o estado!");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		Messages.addGlobalInfo(estado.getNome() + " - " + estado.getSigla());
	}

	public void novo() {
		estado = new Estado();
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
