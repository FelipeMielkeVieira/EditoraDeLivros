package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.Autor;
import br.senai.sc.livros.model.entities.Editora;
import br.senai.sc.livros.model.entities.Livro;
import br.senai.sc.livros.model.entities.Status;

public class LivroFactory {

    public Livro getLivro(String titulo, Integer isbn, Integer qtdPaginas, Autor autor, Status status, Editora editora) {
        if(editora != null) {
            if(status != null) {
                return new Livro(titulo, isbn, qtdPaginas, autor, status, editora);
            } else {
                return new Livro(titulo, isbn, qtdPaginas, autor, Status.AGUARDANDO_REVISAO, editora);
            }
        } else {
            if(status != null) {
                return new Livro(titulo, isbn, qtdPaginas, autor, status);
            } else {
                return new Livro(titulo, isbn, qtdPaginas, autor, Status.AGUARDANDO_REVISAO);
            }
        }
    }
}
