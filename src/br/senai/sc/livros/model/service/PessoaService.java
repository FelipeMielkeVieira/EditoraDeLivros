package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Pessoa;

public class PessoaService {

    public void inserir(Pessoa pessoa) {
        new PessoaDAO().inserir(pessoa);
    }

    public Pessoa selecionarPorEmail(String email) {
        return new PessoaDAO().selecionarPorEmail(email);
    }

}
