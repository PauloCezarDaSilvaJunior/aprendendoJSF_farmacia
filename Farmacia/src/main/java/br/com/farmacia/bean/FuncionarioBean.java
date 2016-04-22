package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.FuncionarioDAO;
import br.com.farmacia.dao.PessoaDAO;
import br.com.farmacia.domain.Funcionario;
import br.com.farmacia.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

	private List<Funcionario> funcionarios; 
	private List<Pessoa> pessoas; 
	private Funcionario funcionario;
	
	@PostConstruct
	public void listar() {
		try {
			FuncionarioDAO dao = new FuncionarioDAO();
			funcionarios = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar funcionarios! ");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			funcionario = new Funcionario();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar carregar funcionarios! ");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FuncionarioDAO dao = new FuncionarioDAO();
			dao.merge(funcionario);
			funcionarios = dao.listar();
			novo();

			Messages.addGlobalInfo("Salvo com sucesso! ");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar cliente! ");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento){
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			
			FuncionarioDAO dao = new FuncionarioDAO();
			dao.delete(funcionario);
			
			funcionarios = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar excluir clientes! ");
			e.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar editar clientes! ");
			e.printStackTrace();
		}
		
	}



	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	

}
