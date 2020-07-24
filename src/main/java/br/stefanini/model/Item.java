package br.stefanini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long idItem;

	@ManyToOne
	@JoinColumn(name = "idManutencao", referencedColumnName = "idManutencao")
	private Manutencao manutencao;

	@Column(length = 200)
	private String descricao;

	@Column(length = 10)
	private int qtd;

	@Column(precision = 10000, scale = 2)
	private Double valorUnitario;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public Manutencao getManutencao() {
		return this.manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQtd() {
		return this.qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}
