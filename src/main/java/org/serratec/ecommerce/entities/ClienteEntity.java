package org.serratec.ecommerce.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "CLIENTE")
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(unique=true)
	private String email;
	
	@NotNull
	@Column(unique=true)
	private String userName;
	
	@NotNull
	private String senha;
	
	@NotNull
	private String nome;
	
	@CPF
	@NotNull
	@Column(unique=true)
	private String cpf;
	
	@NotNull
	private String telefone;
	
	@Past
	private LocalDate dataNascimento;
	
	private String token;
	
	@OneToMany(mappedBy = "cliente")
	private List<EnderecoEntity> endereco = new ArrayList<>();
	
	@OneToMany(mappedBy = "cliente")
	private List<PedidoEntity> pedidos = new ArrayList<>();
	
	private boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<EnderecoEntity> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<EnderecoEntity> endereco) {
		this.endereco = endereco;
	}

	public List<PedidoEntity> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoEntity> pedidos) {
		this.pedidos = pedidos;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
