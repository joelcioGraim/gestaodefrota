package br.stefanini.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Manutencao implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idManutencao;

	@Column(length = 150)
	private String nomemecanico;

	@Column(length = 50)
	private int kmsaida;

	@Column(length = 50)
	private int kmretorno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtinicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtfim;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataRegistro;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private byte[] comprovante;

	@OneToMany(mappedBy = "manutencao", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Item> itens;

	@Column
	private boolean autorizado;

	@Column
	private boolean ativo;

	@ManyToOne
	@JoinColumn(name = "idOficina", referencedColumnName = "idOficina")
	private Oficina oficina;

	@ManyToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "idVeiculo", referencedColumnName = "idVeiculo")
	private Veiculo veiculo;

	public Manutencao() {
		// TODO Auto-generated constructor stub
	}

	public long getIdManutencao() {
		return idManutencao;
	}

	public void setIdManutencao(long idManutencao) {
		this.idManutencao = idManutencao;
	}

	public Oficina getOficina() {
		return this.oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public String getNomemecanico() {
		return this.nomemecanico;
	}

	public void setNomemecanico(String nomemecanico) {
		this.nomemecanico = nomemecanico;
	}

	public Date getDtinicio() {
		return this.dtinicio;
	}

	public void setDtinicio(Date dtinicio) {
		this.dtinicio = dtinicio;
	}

	public Date getDtfim() {
		return this.dtfim;
	}

	public void setDtfim(Date dtfim) {
		this.dtfim = dtfim;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getComprovante() {
		return comprovante;
	}

	public void setComprovante(byte[] comprovante) {
		this.comprovante = comprovante;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getKmsaida() {
		return kmsaida;
	}

	public void setKmsaida(int kmsaida) {
		this.kmsaida = kmsaida;
	}

	public int getKmretorno() {
		return kmretorno;
	}

	public void setKmretorno(int kmretorno) {
		this.kmretorno = kmretorno;
	}

}
