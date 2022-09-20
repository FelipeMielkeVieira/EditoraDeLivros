package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.Genero;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.service.PessoaService;

import java.sql.SQLException;

public class PessoaController {

    Pessoa model;

    public Pessoa validaLogin(String email, String senha) throws SQLException {
        PessoaService service = new PessoaService();
        model = service.selecionarPorEmail(email);
        return model.validaLogin(senha);
    }

    public void cadastrar(String nome, String sobrenome, String email, String senha, String cpf, Object genero, String confirmarSenha) throws SQLException {
        Pessoa pessoa = Pessoa.cadastrar(nome, sobrenome, email, senha, cpf, (Genero) genero, confirmarSenha);
        PessoaService service = new PessoaService();
        service.inserir(pessoa);
    }
}
