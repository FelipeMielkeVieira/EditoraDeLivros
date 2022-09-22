package br.senai.sc.livros.model.entities;

import br.senai.sc.livros.model.factory.LivroFactory;

public class Livro {

    private Autor autor;
    private Editora editora;
    private String titulo;
    private Integer isbn, qtdPaginas;
    private Status status;

    public Livro(String titulo, Integer isbn, Integer qtdPaginas, Autor autor,  Status status) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.qtdPaginas = qtdPaginas;
        this.autor = autor;
        this.status = status;
    }

    public Livro(String titulo, Integer isbn, Integer qtdPaginas, Autor autor,  Status status, Editora editora) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.qtdPaginas = qtdPaginas;
        this.autor = autor;
        this.status = status;
        this.editora = editora;
    }

    public Livro() {

    }

    public Autor getAutor() {
        return autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public Integer getQtdPaginas() {
        return qtdPaginas;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public static Livro cadastrar(String titulo, int isbn, int qtdPaginas, Autor autor) {
        return new LivroFactory().getLivro(titulo, isbn, qtdPaginas, autor, null,null);
    }

    @Override
    public boolean equals(Object o) {
        Livro livro = (Livro) o;
        return this.isbn == livro.isbn;
    }

    @Override
    public int hashCode() {
        return isbn;
    }
}
