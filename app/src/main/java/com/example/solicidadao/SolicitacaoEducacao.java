package com.example.solicidadao;

import java.io.Serializable;

public class SolicitacaoEducacao implements Serializable {
    int id;
    private ProfileUser nome;
    private String cpf;
    private String rg;
    private String data_criacao;
    private String comentario;

    public SolicitacaoEducacao(){

    }

    public SolicitacaoEducacao(int id, ProfileUser nome, String cpf, String rg, String data_criacao, String comentario) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.data_criacao = data_criacao;
        this.comentario = comentario;
    }

    public ProfileUser getNome() {
        return nome;
    }

    public void setNome(ProfileUser nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
