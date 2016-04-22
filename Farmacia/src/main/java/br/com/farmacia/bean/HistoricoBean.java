package br.com.farmacia.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.ProdutoDAO;
import br.com.farmacia.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable{
	private Produto produto;
	private boolean exibirResultado;

	@PostConstruct
	public void novo(){
		produto = new Produto();
		exibirResultado = false;
	}
	
	public void buscar(){
		try{
			ProdutoDAO dao = new ProdutoDAO();
			Produto resultado = dao.findById(produto.getCodigo());
			if(resultado != null){
				exibirResultado = true;
				produto = resultado;
			}else{
				exibirResultado = false;
				Messages.addGlobalInfo("Esse produto não existe");
			}
		}catch(RuntimeException e){
			Messages.addGlobalError("Erro ao buscar o produto! ");
			e.printStackTrace();
		}
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean isExibirResultado() {
		return exibirResultado;
	}

	public void setExibirResultado(boolean exibirResultado) {
		this.exibirResultado = exibirResultado;
	}
	
	
}
