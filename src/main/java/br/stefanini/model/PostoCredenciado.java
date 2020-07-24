package br.stefanini.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PostoCredenciado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idPosto;

	@Column(length = 150)
	private String nome;

	@Column(length = 300)
	private String endereco;

	@Column
	private boolean ativo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataRegistro;

	public PostoCredenciado() {
		// TODO Auto-generated constructor stub
	}

	public long getIdPosto() {
		return idPosto;
	}

	public void setIdPosto(long idPosto) {
		this.idPosto = idPosto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

}
