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
public class Oficina implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idOficina;

	@Column(length = 150)
	private String nome;

	@Column(length = 300)
	private String endereco;

	@Column(length = 18)
	private String cnpj;

	@Column(length = 15)
	private String telefone;

	@OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL)
	private List<Manutencao> manutencaos;

	@Column
	private boolean ativo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataRegistro;

	public Oficina() {
		// TODO Auto-generated constructor stub
	}

	public long getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(long idOficina) {
		this.idOficina = idOficina;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Manutencao> getManutencaos() {
		return manutencaos;
	}

	public void setManutencaos(List<Manutencao> manutencaos) {
		this.manutencaos = manutencaos;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
