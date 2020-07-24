package br.stefanini.dto;

import br.stefanini.model.Perfil;

public class AutenticacaoDto {

	private String token;
	private long num;
	private String nome;
	private String email;
	private Perfil perfil;

	public AutenticacaoDto() {
		// TODO Auto-generated constructor stub
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
