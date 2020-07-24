package br.stefanini.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Veiculo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idVeiculo;

	@Column(length = 100)
	private String marca;

	@Column(length = 100)
	private String modelo;

	@Column(length = 100)
	private String cor;

	@Column(length = 100)
	private String chassi;

	@Column(length = 100)
	private String numtombo;

	@Column(length = 100)
	private String renavam;

	@Column(length = 10)
	private String placa;

	@Column(length = 4)
	private Integer anoFabricacao;

	@Column(length = 50)
	private String tpcombustivel;

	@Column(precision = 10000, scale = 2)
	private Double valoraquis;

	@Column
	private boolean segurado;

	@Column
	private boolean ativo;

	@Column(columnDefinition = "TEXT")
	private String observacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtcadastro;

	@OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
	private List<Logposicional> logposicional;

	public Veiculo() {
		// TODO Auto-generated constructor stub
	}

	public long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumtombo() {
		return numtombo;
	}

	public void setNumtombo(String numtombo) {
		this.numtombo = numtombo;
	}

	public String getCor() {
		return this.cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getChassi() {
		return this.chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getRenavam() {
		return this.renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public boolean isSegurado() {
		return segurado;
	}

	public void setSegurado(boolean segurado) {
		this.segurado = segurado;
	}

	public String getTpcombustivel() {
		return this.tpcombustivel;
	}

	public void setTpcombustivel(String tpcombustivel) {
		this.tpcombustivel = tpcombustivel;
	}

	public Double getValoraquis() {
		return this.valoraquis;
	}

	public void setValoraquis(Double valoraquis) {
		this.valoraquis = valoraquis;
	}

	public Date getDtcadastro() {
		return this.dtcadastro;
	}

	public void setDtcadastro(Date dtcadastro) {
		this.dtcadastro = dtcadastro;
	}

	public List<Logposicional> getLogposicional() {
		return logposicional;
	}

	public void setLogposicional(List<Logposicional> logposicional) {
		this.logposicional = logposicional;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
