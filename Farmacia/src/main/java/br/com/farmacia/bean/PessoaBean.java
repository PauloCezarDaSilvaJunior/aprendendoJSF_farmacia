package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.CidadeDAO;
import br.com.farmacia.dao.EstadoDAO;
import br.com.farmacia.dao.PessoaDAO;
import br.com.farmacia.domain.Cidade;
import br.com.farmacia.domain.Estado;
import br.com.farmacia.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {
	private List<Pessoa> pessoas;
	private Pessoa pessoa;
	private Estado estado;
	private List<Estado> estados;
	private List<Cidade> cidades;

	@PostConstruct
	public void listar() {
		try {
			PessoaDAO dao = new PessoaDAO();
			pessoas = dao.listar("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar pessoas!");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new Pessoa();
			EstadoDAO dao = new EstadoDAO();
			estados = dao.listar();
			estado = new Estado();
			cidades = new ArrayList<Cidade>();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar pessoas!");
			e.printStackTrace();
		}

	}

	public void salvar() {
		try {
			PessoaDAO dao = new PessoaDAO();
			dao.merge(pessoa);

			pessoas = dao.listar("nome");
			novo();
			Messages.addGlobalInfo("Salvo com sucesso!");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao salvar pessoa!");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			
			EstadoDAO EstadadoDao = new EstadoDAO();
			estados = EstadadoDao.listar();
			estado = pessoa.getCidade().getEstado();
			popular();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar filtrar o estado ou a cidade para editar!");
			e.printStackTrace();
		}

	}
	
	public void excluir(ActionEvent evento){
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.delete(pessoa);
			
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar filtrar o estado ou a cidade para editar!");
			e.printStackTrace();
		}
	}

	// popular cidade com base no estado
	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO dao = new CidadeDAO();
				cidades = dao.buscarPorEstado(estado.getCodigo());
			} else {
				cidades = new ArrayList<Cidade>();
			}
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar filtrar as cidades!");
			e.printStackTrace();
		}
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidade) {
		this.cidades = cidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
