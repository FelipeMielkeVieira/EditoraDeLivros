package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.LivroDAO;
import br.senai.sc.livros.model.entities.Livro;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Status;
import java.util.Collection;

public class LivroService {

    LivroDAO acesso = new LivroDAO();

    public void inserir(Livro livro) {
        acesso.inserir(livro);
    }

    public void atualizar(int isbn, Livro livroAtualizado) {
        acesso.atualizar(isbn, livroAtualizado);
    }

    public Collection<Livro> selecionarTodos() {
        return acesso.selecionarTodos();
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) {
        return acesso.selecionarPorAutor(pessoa);
    }

    public Collection<Livro> selecionarPorStatus(Status status) {
        return acesso.selecionarPorStatus(status);
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        return acesso.selecionarAtividadesAutor(pessoa);
    }
}
