package br.stefanini.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Logacesso implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idLogAcesso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Usuario usuario;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(length = 1)
	private String tipoOperacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataOperacao;

	public Logacesso() {
		// TODO Auto-generated constructor stub
	}

	public long getIdLogAcesso() {
		return idLogAcesso;
	}

	public void setIdLogAcesso(long idLogAcesso) {
		this.idLogAcesso = idLogAcesso;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

}
