package br.com.farmacia.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.farmacia.dao.UsuarioDAO;
import br.com.farmacia.domain.Pessoa;
import br.com.farmacia.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	private Usuario usuario;
	private Usuario usuarioLogado;
	private boolean usuarioEstaLogado;

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void autenticar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuarioLogado = dao.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			usuarioEstaLogado = true;
			
			if (usuarioLogado == null) {
				Messages.addGlobalInfo("CPF ou Senha incorretos!");
				return;
			}
			Faces.redirect("./pages/principal.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError("Não foi possivel redirecionar!");
			e.printStackTrace();
		} catch (RuntimeException re) {
			Messages.addGlobalError("Ocorreu um erro ao tentar autenticar!");
			re.printStackTrace();
		}
	}

	public boolean TemPermissao(List<String> permissoes) {
		if (usuarioLogado != null) {
			Iterator<String> it = permissoes.iterator();
			while (it.hasNext()) {
				String permissao = it.next();
				if (usuarioLogado.getTipo() == permissao.charAt(0)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void sair(){
		usuarioEstaLogado = false;
		usuarioLogado = null;
		Faces.navigate("/pages/login.xhtml");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public boolean isUsuarioEstaLogado() {
		return usuarioEstaLogado;
	}

	public void setUsuarioEstaLogado(boolean usuarioEstaLogado) {
		this.usuarioEstaLogado = usuarioEstaLogado;
	}
	

}
