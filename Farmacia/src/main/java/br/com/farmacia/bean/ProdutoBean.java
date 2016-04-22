package br.com.farmacia.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.farmacia.dao.FabricanteDAO;
import br.com.farmacia.dao.ProdutoDAO;
import br.com.farmacia.domain.Fabricante;
import br.com.farmacia.domain.Produto;
import br.com.farmacia.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private List<Produto> produtos;
	private Produto produto;
	private List<Fabricante> fabricantes;

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO dao = new ProdutoDAO();
			produtos = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os produtos!");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			produto = new Produto();

			FabricanteDAO dao = new FabricanteDAO();
			fabricantes = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os fabricantes!");
			e.printStackTrace();
		}

	}

	public void salvar() {
		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.merge(produto);
			Messages.addGlobalInfo("Salvo com sucesso !");

			// criar novo produto
			novo();
			// atualizar a lista
			produtos = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar o produto!");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			ProdutoDAO dao = new ProdutoDAO();
			dao.delete(produto);
			Messages.addGlobalInfo("Removido com sucesso !");

			// atualizar a lista
			produtos = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar excluir o produto!");
			e.printStackTrace();
		}

	}
	
	public void editar(ActionEvent evento){
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			FabricanteDAO dao = new FabricanteDAO();
			fabricantes = dao.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os fabricantes!");
			e.printStackTrace();
		}
	}
	
	public void imprimir(){
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();
			String pdescricao = (String) filtros.get("descricao");
			String fdescricao = (String) filtros.get("fabricante.descricao");
			
			String caminho = Faces.getRealPath("/reports/produtos.jasper");
			
			Map<String, Object> parametros = new HashMap<>();
			if(pdescricao == null){
				parametros.put("produto_descricao", "%%");
			}else{
				parametros.put("produto_descricao", "%"+pdescricao+"%");
			}
			if(fdescricao == null){
				parametros.put("fabricante_descricao", "%%");
			}else{
				parametros.put("fabricante_descricao", "%"+fdescricao+"%");
			}
			
			Connection conn = HibernateUtil.getConexao();
			JasperPrint jp = JasperFillManager.fillReport(caminho, parametros, conn);
			
			JasperPrintManager.printReport(jp, true);
		} catch (JRException e) {
			Messages.addGlobalError("Erro ao tentar gerar relatorio de produtos!");
			e.printStackTrace();
		}
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

}
