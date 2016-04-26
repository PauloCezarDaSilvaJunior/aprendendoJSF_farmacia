package br.com.farmacia.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.farmacia.bean.LoginBean;
import br.com.farmacia.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String pagina = Faces.getViewId();
		boolean PaginaAutenticacao = pagina.contains("login.xhtml");
		if(!PaginaAutenticacao){
			LoginBean login = Faces.getSessionAttribute("loginBean");
			if(login == null){
				Faces.navigate("/pages/login.xhtml");
				return;
			}
			Usuario usuario = login.getUsuarioLogado();
			if(usuario == null){
				Faces.navigate("/pages/login.xhtml");
				return;
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
