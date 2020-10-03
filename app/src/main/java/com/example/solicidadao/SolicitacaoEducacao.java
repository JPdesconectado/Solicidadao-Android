package com.example.solicidadao;

import java.io.Serializable;

public class SolicitacaoEducacao implements Serializable {
    int pk;
    private int nome;
    private String cadastro_pf;
    private String rg;
    private String data_criacao;
    private String comentario;

    public SolicitacaoEducacao(){

    }

    public SolicitacaoEducacao(int pk, int nome, String cadastro_pf, String rg, String data_criacao, String comentario) {
        this.pk = pk;
        this.nome = nome;
        this.cadastro_pf = cadastro_pf;
        this.rg = rg;
        this.data_criacao = data_criacao;
        this.comentario = comentario;
    }

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cadastro_pf;
    }

    public void setCpf(String cadastro_pf) {
        this.cadastro_pf = cadastro_pf;
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
        return pk;
    }

    public void setId(int pk) {
        this.pk = pk;
    }
}
