package projeto.model;

import java.io.Serializable;

public class ModelLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String login;
	private String senha;
	private String email;
	private String nome;

	public ModelLogin() {
	}

	public ModelLogin(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public ModelLogin(Long id, String login, String senha, String email, String nome) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
