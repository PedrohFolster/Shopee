package com.projeto.shopee.dto;

import java.util.Date;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Date dataNascimento;
    private EnderecoDTO enderecoDTO;  
    private UsuarioAutenticarDTO usuarioAutenticarDTO; 

    // Construtores
    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String email, String telefone, Date dataNascimento,
                      EnderecoDTO enderecoDTO, UsuarioAutenticarDTO usuarioAutenticarDTO) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.enderecoDTO = enderecoDTO;
        this.usuarioAutenticarDTO = usuarioAutenticarDTO;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    public UsuarioAutenticarDTO getUsuarioAutenticarDTO() {
        return usuarioAutenticarDTO;
    }

    public void setUsuarioAutenticarDTO(UsuarioAutenticarDTO usuarioAutenticarDTO) {
        this.usuarioAutenticarDTO = usuarioAutenticarDTO;
    }
}
