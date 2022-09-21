package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.*;

public class PessoaFactory {

    public Pessoa getPessoa(String nome, String sobrenome, String email, String senha, String cpf, String genero, String funcao) {
        switch (funcao) {
            case "AUTOR" -> {
                return new Autor(nome, sobrenome, email, senha, cpf, Genero.valueOf(genero));
            }
            case "REVISOR" -> {
                return new Revisor(nome, sobrenome, email, senha, cpf, Genero.valueOf(genero));
            }
            default -> {
                return new Diretor(nome, sobrenome, email, senha, cpf, Genero.valueOf(genero));
            }
        }
    }
}
