package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.LivroDAO;
import br.senai.sc.livros.model.entities.Livro;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Status;

import java.sql.SQLException;
import java.util.Collection;

public class LivroService {

    LivroDAO acesso = new LivroDAO();

    public void inserir(Livro livro) throws SQLException {
        acesso.inserir(livro);
    }

    public void atualizar(int isbn, Livro livroAtualizado) {
        try {
            acesso.atualizar(isbn, livroAtualizado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Livro> selecionarTodos() {
        try {
            return acesso.selecionarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) {
        try {
            return acesso.selecionarPorAutor(pessoa);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Livro> selecionarPorStatus(Status status) {
        return acesso.selecionarPorStatus(status);
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        return acesso.selecionarAtividadesAutor(pessoa);
    }
}
