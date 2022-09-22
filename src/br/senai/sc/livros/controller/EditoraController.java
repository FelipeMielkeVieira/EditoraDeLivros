package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.service.EditoraService;

import java.util.ArrayList;
import java.util.Collection;

public class EditoraController {

    public void cadastrar(String nome) {
        Editora editora = Editora.cadastrar(nome);
        EditoraService service = new EditoraService();
        service.inserir(editora);
    }

    public Editora buscar(String nome) {
        EditoraService service = new EditoraService();
        return service.selecionar(nome);
    }

    public String listarEditoras() {
        EditoraService service = new EditoraService();
        ArrayList<Editora> listaEditoras = (ArrayList<Editora>) service.listar();

        String texto = "Lista de Editoras: \n";
        for (Integer i = 0; i < listaEditoras.size(); i++) {
            texto += i + " - " + listaEditoras.get(i).getNome() + "\n";
        }

        if(texto == "") {
            texto = "Nenhuma editora cadastrada";
        }

        return texto;
    }
}
