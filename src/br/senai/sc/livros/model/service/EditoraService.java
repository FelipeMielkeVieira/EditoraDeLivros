package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.EditoraDAO;
import br.senai.sc.livros.model.entities.Editora;

public class EditoraService {

    EditoraDAO acesso = new EditoraDAO();

    public void inserir(Editora editora) {
        try {
            acesso.inserir(editora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Editora selecionar(String nome) {
        try {
            return acesso.selecionar(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
