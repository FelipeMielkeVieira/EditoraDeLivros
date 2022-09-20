package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.service.EditoraService;

public class EditoraController {

    public void cadastrar(String nome) {
        Editora editora = Editora.cadastrar(nome);
        EditoraService service = new EditoraService();
        service.inserir(editora);
    }

    public Editora buscar(String nome) {
        EditoraService service = new EditoraService();
        return  service.selecionar(nome);
    }
}
