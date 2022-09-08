package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class LivroDAO {

    private static Collection<Livro> listaLivros = new HashSet<>();

    public void inserir(Livro livro) {
        if(selecionar(livro.getIsbn()) == null) {
            listaLivros.add(livro);
        } else {
            throw new RuntimeException("Livro já cadastrado!");
        }
    }

    public Livro selecionar(int isbn) {
        for(Livro livro: listaLivros) {
            if(livro.getIsbn() == isbn) {
                return livro;
            }
        }
        return null;
    }

    public void atualizar(int isbn, Livro livroAtualizado) {
        for (Livro livro: listaLivros) {
            if (livro.getIsbn() == isbn) {
                listaLivros.remove(livro);
                listaLivros.add(livroAtualizado);
            }
        }
    }

    public Collection<Livro> selecionarTodos() {
        return Collections.unmodifiableCollection(listaLivros);
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) {
        Collection<Livro> livrosAutor = new ArrayList<>();
        for(Livro livro: listaLivros) {
            if(livro.getAutor().equals(pessoa)) {
                livrosAutor.add(livro);
            }
        }
        return livrosAutor;
    }

    public Collection<Livro> selecionarPorStatus(Status status) {
        Collection<Livro> livrosStatus = new ArrayList<>();
        for(Livro livro: listaLivros) {
            if(livro.getStatus().equals(status)) {
                livrosStatus.add(livro);
            }
        }
        return livrosStatus;
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        Collection<Livro> livrosAutor = new ArrayList<>();
        if(listaLivros.isEmpty()) {
            return null;
        }
        for(Livro livro: listaLivros) {
            if(livro.getAutor().equals(pessoa) && livro.getStatus().equals(Status.AGUARDANDO_EDICAO)) {
                livrosAutor.add(livro);
            }
        }
        return livrosAutor;
    }
}
