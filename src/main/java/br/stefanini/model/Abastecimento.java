package br.stefanini.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Abastecimento implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idAbastecimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataAbastecimento;

	@Column(length = 50)
	private int quilometragem;

	@Column(precision = 10000, scale = 2)
	private double litroAbastecido;

	@Column(length = 50)
	private String tipoCombustivel;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private byte[] comprovante;

	@OneToOne
	@JoinColumn(name = "idPosto", referencedColumnName = "idPosto")
	private PostoCredenciado postoCredenciado;

	@ManyToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "idVeiculo", referencedColumnName = "idVeiculo")
	private Veiculo veiculo;

	public Abastecimento() {
		// TODO Auto-generated constructor stub
	}

	public long getIdAbastecimento() {
		return idAbastecimento;
	}

	public void setIdAbastecimento(long idAbastecimento) {
		this.idAbastecimento = idAbastecimento;
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

	public PostoCredenciado getPostoCredenciado() {
		return postoCredenciado;
	}

	public void setPostoCredenciado(PostoCredenciado postoCredenciado) {
		this.postoCredenciado = postoCredenciado;
	}

	public Date getDataAbastecimento() {
		return dataAbastecimento;
	}

	public void setDataAbastecimento(Date dataAbastecimento) {
		this.dataAbastecimento = dataAbastecimento;
	}

	public int getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(int quilometragem) {
		this.quilometragem = quilometragem;
	}

	public double getLitroAbastecido() {
		return litroAbastecido;
	}

	public void setLitroAbastecido(double litroAbastecido) {
		this.litroAbastecido = litroAbastecido;
	}

	public String getTipoCombustivel() {
		return tipoCombustivel;
	}

	public void setTipoCombustivel(String tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}

	public byte[] getComprovante() {
		return comprovante;
	}

	public void setComprovante(byte[] comprovante) {
		this.comprovante = comprovante;
	}

}
