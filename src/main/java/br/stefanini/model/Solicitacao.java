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
import javax.persistence.Transient;

@Entity
public class Solicitacao implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idSolicitacao;
	
	@Column(length = 150)
	private String solicitante;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtsaida;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtretorno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataRegistro;

	@Column(length = 300)
	private String destino;

	@Column(length = 50)
	private int kmsaida;

	@Column(length = 50)
	private int kmretorno;

	@Column
	private boolean autorizado;

	@Column(columnDefinition = "TEXT")
	private String observacao;

	@Column
	private boolean ativo;

	@ManyToOne
	@JoinColumn(name = "idResponsavel", referencedColumnName = "idUsuario")
	private Usuario responsavel;

	@ManyToOne
	@JoinColumn(name = "idVeiculo", referencedColumnName = "idVeiculo")
	private Veiculo veiculo;

	@Transient
	private String data1;

	@Transient
	private String data2;

	public Solicitacao() {
		// TODO Auto-generated constructor stub
	}

	public long getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Date getDtsaida() {
		return this.dtsaida;
	}

	public void setDtsaida(Date dtsaida) {
		this.dtsaida = dtsaida;
	}

	public Date getDtretorno() {
		return this.dtretorno;
	}

	public void setDtretorno(Date dtretorno) {
		this.dtretorno = dtretorno;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getKmsaida() {
		return this.kmsaida;
	}

	public void setKmsaida(int kmsaida) {
		this.kmsaida = kmsaida;
	}

	public int getKmretorno() {
		return this.kmretorno;
	}

	public void setKmretorno(int kmretorno) {
		this.kmretorno = kmretorno;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

}
