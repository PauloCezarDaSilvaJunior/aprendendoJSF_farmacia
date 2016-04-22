package br.com.farmacia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Estado extends GenericDomain {
	@Column(length=2, nullable=false)//varchar(2)NOT NULL
	private String sigla;
	@Column(length=50, nullable=false)//varchar(50) NOT NULL
	private String nome;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
