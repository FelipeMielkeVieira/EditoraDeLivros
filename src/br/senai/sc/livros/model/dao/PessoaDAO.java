package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

import java.util.HashSet;
import java.util.Set;

public class PessoaDAO {

    private static Set<Pessoa> listaPessoas = new HashSet<>();

    static {
        Autor autor = new Autor("Felipe", "Vieira", "felipe@gmail.com", "123", "123", Genero.MASCULINO);
        listaPessoas.add(autor);
        Revisor revisor = new Revisor("Kenzo", "Sato", "kenzo@gmail.com", "123", "123", Genero.FEMININO);
        listaPessoas.add(revisor);
        Diretor diretor = new Diretor("Matheus", "Hohmann", "matheus@gmail.com", "123", "123", Genero.OUTRO);
        listaPessoas.add(diretor);
    }

    public void inserir(Pessoa pessoa) {
        listaPessoas.add(pessoa);
    }

    public Pessoa selecionarPorEmail(String email) {
        for(Pessoa pessoa: listaPessoas) {
            if(pessoa.getEmail().equals(email)) {
                return pessoa;
            }
        }
        throw new RuntimeException("E-mail não encontrado!");
    }

}
