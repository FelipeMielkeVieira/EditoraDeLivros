package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.EditoraDAO;
import br.senai.sc.livros.model.entities.Editora;

public class EditoraService {

    EditoraDAO acesso = new EditoraDAO();

    public void inserir(Editora editora) {
        acesso.inserir(editora);
    }

    public Editora selecionar(String nome) {
        return acesso.selecionar(nome);
    }
}
