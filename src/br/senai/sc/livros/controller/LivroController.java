package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.service.LivroService;
import br.senai.sc.livros.view.Menu;
import java.util.Collection;

public class LivroController {

    public void cadastrar(String titulo, String isbn, String qtdPaginas, Pessoa pessoa) {
        Livro livro = Livro.cadastrar(titulo, Integer.parseInt(isbn), Integer.parseInt(qtdPaginas), (Autor) (pessoa));
        LivroService service = new LivroService();
        service.inserir(livro);
    }

    public Collection<Livro> buscarLista(int lista) {
        LivroService service = new LivroService();
        Pessoa usuario = Menu.getUsuario();
        if (usuario instanceof Autor) {
            if (lista == 1) {
                return service.selecionarPorAutor(usuario);
            } else {
                return service.selecionarAtividadesAutor(usuario);
            }
        } else if (usuario instanceof Revisor) {
            if (lista == 2) {
                return service.selecionarPorStatus(Status.AGUARDANDO_REVISAO);
            } else {
                return service.selecionarPorStatus(Status.EM_REVISAO);
            }
        } else {
            if (lista == 1) {
                return service.selecionarTodos();
            } else {
                return service.selecionarPorStatus(Status.APROVADO);
            }
        }
    }

    public void editarLivro(Livro livro) {
        LivroService service = new LivroService();
        if(Menu.getUsuario() instanceof Autor) {
            livro.setStatus(Status.AGUARDANDO_REVISAO);
        } else
        if(Menu.getUsuario() instanceof Revisor) {
            livro.setStatus(Status.EM_REVISAO);
        }
        service.atualizar(livro.getIsbn(), livro);
    }

    public void aprovacao(Livro livro, int opcao) {
        LivroService service = new LivroService();
        if(Menu.getUsuario() instanceof Revisor) {
            if(opcao == 1) {
                livro.setStatus(Status.REPROVADO);
            } else if (opcao == 2) {
                livro.setStatus(Status.AGUARDANDO_EDICAO);
            } else if (opcao == 3) {
                livro.setStatus(Status.APROVADO);
            }
        } else if(Menu.getUsuario() instanceof Diretor) {
            if(opcao == 0) {
                livro.setStatus(Status.REPROVADO);
            } else if (opcao == 1) {
                livro.setStatus(Status.AGUARDANDO_REVISAO);
            } else if (opcao == 2) {
                livro.setStatus(Status.PUBLICADO);
            }
        }
        service.atualizar(livro.getIsbn(), livro);
    }
}
