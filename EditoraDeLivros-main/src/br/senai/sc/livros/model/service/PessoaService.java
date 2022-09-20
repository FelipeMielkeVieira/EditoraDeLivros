package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Pessoa;

import java.sql.SQLException;

public class PessoaService {

    PessoaDAO dao = new PessoaDAO();

    public void inserir(Pessoa pessoa) {
        dao.inserir(pessoa);
    }

    public Pessoa selecionarPorEmail(String email) throws SQLException {
        return dao.selecionarPorEmail(email);
    }

}
