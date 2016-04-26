package br.com.farmacia.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.farmacia.dao.ClienteDAO;
import br.com.farmacia.dao.FuncionarioDAO;
import br.com.farmacia.dao.ProdutoDAO;
import br.com.farmacia.dao.VendaDAO;
import br.com.farmacia.domain.Cliente;
import br.com.farmacia.domain.Funcionario;
import br.com.farmacia.domain.ItemVenda;
import br.com.farmacia.domain.Produto;
import br.com.farmacia.domain.Venda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private Venda venda;

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal(0.00));

			ProdutoDAO dao = new ProdutoDAO();
			produtos = dao.listar("descricao");

			itensVenda = new ArrayList<>();

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os produtos!");
			e.printStackTrace();
		}
	}

	public void finalizar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
			venda.setHorario(new Date());
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar finalizar venda!");
			e.printStackTrace();
		}
	}

	public void adicionar(ActionEvent event) {
		Produto produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");

		boolean achou = false;
		Iterator<ItemVenda> itaretorItens = itensVenda.iterator();
		while (itaretorItens.hasNext()) {
			ItemVenda it = itaretorItens.next();
			if (it.getProduto().equals(produto)) {
				it.setQuantidade(new Short(it.getQuantidade() + 1 + ""));
				it.setValorParcial(produto.getPreco().multiply(new BigDecimal(it.getQuantidade())));
				achou = true;
				break;
			}
		}

		if (!achou) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));
			itemVenda.setValorParcial(produto.getPreco());
			itensVenda.add(itemVenda);
		}
		calcular();

	}

	public void remover(ActionEvent event) {
		ItemVenda itemVenta = (ItemVenda) event.getComponent().getAttributes().get("itemSelecionado");

		Iterator<ItemVenda> iteratorItens = itensVenda.iterator();
		while (iteratorItens.hasNext()) {
			ItemVenda it = iteratorItens.next();
			if (it.equals(itemVenta)) {
				itensVenda.remove(it);
				break;
			}
		}
		calcular();
	}

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal(0.00));

		Iterator<ItemVenda> iteratorItens = itensVenda.iterator();
		while (iteratorItens.hasNext()) {
			ItemVenda it = iteratorItens.next();
			venda.setPrecoTotal(venda.getPrecoTotal().add(it.getValorParcial()));
		}

	}

	public void salvar() {
		try {
			Produto produtoIdisponivel = null;
			if (venda.getPrecoTotal().signum() > 0) {
				boolean verifica = false;
				Iterator<ItemVenda> i = itensVenda.iterator();
				while (i.hasNext()) {
					ItemVenda it = i.next();
					if (it.getProduto().getQuantidade() > 0) {
						verifica = true;
					} else {
						produtoIdisponivel = it.getProduto();
						verifica = false;
						break;
					}
				}

				if (verifica == true) {
					VendaDAO dao = new VendaDAO();
					dao.save(venda, itensVenda);
					Messages.addFlashGlobalInfo("Salvo com Sucesso! ");
					novo();
				} else {
					Messages.addGlobalError("O produto "+produtoIdisponivel.getDescricao()+" esta indisponivel no estoque");
				}

			} else {
				Messages.addGlobalError("Compre pelo menos 1 item");
			}
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar Salvar Venda");
			e.printStackTrace();
		}
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
