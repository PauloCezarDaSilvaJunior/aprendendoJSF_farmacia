package br.com.farmacia.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.farmacia.dao.PessoaDAO;
import br.com.farmacia.dao.UsuarioDAO;
import br.com.farmacia.domain.Pessoa;
import br.com.farmacia.domain.Usuario;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class UsuarioBean implements Serializable{
	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Pessoa> pessoas;
	private String senhaTemp;
	
	@PostConstruct
	public void listar(){
		try {
			UsuarioDAO usuarioDao = new UsuarioDAO();
			usuarios = usuarioDao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar usuarios! ");
			e.printStackTrace();
		}
	}
	public void novo(){
		try {
			usuario = new Usuario();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
			senhaTemp = "";
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar carregar pessoas! ");
			e.printStackTrace();
		}
	}
	public void salvar(){
		try {
			UsuarioDAO dao = new UsuarioDAO();
			
			//gerar senha criptografada
			SimpleHash hash = new SimpleHash("md5",senhaTemp);
			usuario.setSenha(hash.toHex());

			dao.merge(usuario);
			
			usuarios = dao.listar();
			novo();
			
			Messages.addGlobalInfo("Salvo com sucesso! ");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar usuarios! ");
			e.printStackTrace();
		}
	}
	public void editar(ActionEvent evento){
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar editar usuarios! ");
			e.printStackTrace();
		}
	}
	public void excluir(ActionEvent evento){
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.delete(usuario);
			
			usuarios = usuarioDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar editar usuarios! ");
			e.printStackTrace();
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public String getSenhaTemp() {
		return senhaTemp;
	}
	public void setSenhaTemp(String senhaTemp) {
		this.senhaTemp = senhaTemp;
	}
	
	
}
