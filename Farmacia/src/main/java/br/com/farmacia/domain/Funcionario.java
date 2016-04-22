package br.com.farmacia.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Funcionario extends GenericDomain {
	@Column(length = 20, nullable = false)
	private String CarteiraTrabalho;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date DataAdmissao;

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	public String getCarteiraTrabalho() {
		return CarteiraTrabalho;
	}

	public void setCarteiraTrabalho(String carteiraTrabalho) {
		CarteiraTrabalho = carteiraTrabalho;
	}

	public Date getDataAdmissao() {
		return DataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		DataAdmissao = dataAdmissao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
