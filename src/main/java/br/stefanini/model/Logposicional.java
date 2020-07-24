package br.stefanini.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Logposicional implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idLogPosicional;

	@ManyToOne
	@JoinColumn(name = "idVeiculo", referencedColumnName = "idVeiculo")
	private Veiculo veiculo;

	@Column(nullable = false, length = 200)
	private String coordenada;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtocorrencia;

	public Logposicional() {
		// TODO Auto-generated constructor stub
	}

	public long getIdLogPosicional() {
		return idLogPosicional;
	}

	public void setIdLogPosicional(long idLogPosicional) {
		this.idLogPosicional = idLogPosicional;
	}

	public Veiculo getVeiculo() {
		return this.veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public String getCoordenada() {
		return this.coordenada;
	}

	public void setCoordenada(String coordenada) {
		this.coordenada = coordenada;
	}

	public Date getDtocorrencia() {
		return this.dtocorrencia;
	}

	public void setDtocorrencia(Date dtocorrencia) {
		this.dtocorrencia = dtocorrencia;
	}

}
