package br.senai.sc.livros.model.entities;

import br.senai.sc.livros.view.Menu;

public abstract class Pessoa {

    public Pessoa() {

    }

    public Pessoa(String nome, String sobrenome, String email, String senha, String cpf, Genero genero) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.genero = genero;
    }

    private String nome, sobrenome, email, senha, cpf;
    private Genero genero;

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pessoa validaLogin(String senha) {
        if (this.getSenha().equals(senha)) {
            return this;
        }
        throw new RuntimeException("Senha Incorreta!");
    }

    public static Pessoa cadastrar(String nome, String sobrenome, String email, String senha, String cpf,
                                   Genero genero, String confirmarSenha) {
        if(senha.equals(confirmarSenha)) {
            if(email.contains("@")) {
                if(Menu.getUsuario() instanceof Diretor) {
                    return new Revisor(nome, sobrenome, email, senha, cpf, genero);
                } else {
                    return new Autor(nome, sobrenome, email, senha, cpf, genero);
                }
            }
            throw new RuntimeException("Email Incorreto!");
        }
        throw new RuntimeException("As senhas não conferem!");
    }

    @Override
    public boolean equals(Object o) {
        Pessoa outraPessoa = (Pessoa) o;
        return cpf.equals(outraPessoa.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.charAt(0);
    }
}
