package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.Editora;

import java.util.ArrayList;

public class EditoraDAO {

    private static ArrayList<Editora> listaEditoras = new ArrayList<>();

    public void inserir(Editora editora) {
        if(selecionar(editora.getNome()) == null) {
            listaEditoras.add(editora);
        } else {
            throw new RuntimeException();
        }
    }

    public Editora selecionar(String nome) {
        for(Editora editora: listaEditoras) {
            if(editora.getNome().equals(nome)) {
                return editora;
            }
        }
        return null;
    }
}
