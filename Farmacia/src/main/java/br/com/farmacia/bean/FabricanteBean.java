package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.FabricanteDAO;
import br.com.farmacia.domain.Fabricante;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class FabricanteBean implements Serializable {

	private Fabricante fabricante;
	private List<Fabricante> fabricantes;
	
	@PostConstruct
	public void listar(){
		try{
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os fabricantes");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.merge(fabricante);
			// atualizar
			novo();
			fabricantes = fabricanteDAO.listar();
			
			Messages.addGlobalInfo("Salvo com Sucesso!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar Salvar fabricante");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento){
		try{
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.delete(fabricante);
			Messages.addGlobalInfo("fabricante removido com sucesso! ");
			
			fabricantes = fabricanteDAO.listar();
			
		}catch(RuntimeException e){
			Messages.addGlobalError("Erro ao tentar Excluir fabricante");
			e.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
	}
	
	public void novo() {
		fabricante = new Fabricante();
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> facricantes) {
		this.fabricantes = facricantes;
	}
	

}
