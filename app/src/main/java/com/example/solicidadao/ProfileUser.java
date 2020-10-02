package com.example.solicidadao;


public class ProfileUser {
    private int id;
    private String username;
    private String nome;
    private String password;
    private String password2;
    private String email;
    private String token;

    public ProfileUser(String username,
                       String password,
                       String password2,
                       String nome,
                       String email) {

        this.username = username;
        this.nome = nome;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }

    public int getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getToken(){
        return token;
    }
}
