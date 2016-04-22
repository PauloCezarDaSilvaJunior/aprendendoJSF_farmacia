package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.ClienteDAO;
import br.com.farmacia.dao.PessoaDAO;
import br.com.farmacia.domain.Cliente;
import br.com.farmacia.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private List<Cliente> clientes; 
	private List<Pessoa> pessoas; 
	private Cliente cliente;
	
	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar clientes! ");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar carregar pessoas! ");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			clientes = clienteDAO.listar();
			novo();

			Messages.addGlobalInfo("Salvo com sucesso! ");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar cliente! ");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento){
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.delete(cliente);
			
			clientes = clienteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar excluir clientes! ");
			e.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar editar clientes! ");
			e.printStackTrace();
		}
		
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	

}
