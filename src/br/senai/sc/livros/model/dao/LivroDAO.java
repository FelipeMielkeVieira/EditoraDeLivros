package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.LivroFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class LivroDAO {

    private final Connection conn;

    public LivroDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public void inserir(Livro livro) {
        if (selecionar(livro.getIsbn()) == null) {

            String sql = "insert into livro (isbn, titulo, qtd_paginas, status, pessoa_cpf) " +
                    "values (?,?,?,?,?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, livro.getIsbn());
                stmt.setString(2, livro.getTitulo());
                stmt.setInt(3, livro.getQtdPaginas());
                stmt.setString(4, livro.getStatus().toString());
                stmt.setString(5, livro.getAutor().getCpf());

                try {
                    stmt.execute();
                } catch (Exception e) {
                    throw new RuntimeException("Erro na execução do comando SQL");
                }

            } catch (Exception e) {
                throw new RuntimeException("Erro na preparação do comando SQL");
            }

        } else {
            throw new RuntimeException("Livro já cadastrado!");
        }
    }

    public Livro selecionar(int isbn) {
        String sql = "select * from livro where isbn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, isbn);

            try (ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet != null && resultSet.next()) {
                    return extrairObjeto(resultSet);
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public void atualizar(int isbn, Livro livroAtualizado) {

        String sql = "update livro set titulo = ?, qtd_paginas = ?, status = ?" +
                "where isbn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livroAtualizado.getTitulo());
            stmt.setInt(2, livroAtualizado.getQtdPaginas());
            stmt.setString(3, livroAtualizado.getStatus().toString());
            stmt.setInt(4, isbn);

            try {
                stmt.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public Collection<Livro> selecionarTodos() {

        String sql = "select * from livro";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = stmt.executeQuery()) {

                Collection<Livro> livros = new ArrayList<>();
                while (resultSet.next()) {
                    Livro livro = extrairObjeto(resultSet);
                    livros.add(livro);
                }
                return livros;

            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) {

        String sql = "select * from livro where pessoa_cpf = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getCpf());

            try (ResultSet resultSet = stmt.executeQuery()) {
                ArrayList<Livro> livros = new ArrayList<>();
                while (resultSet != null && resultSet.next()) {
                    Livro livro = extrairObjeto(resultSet);
                    livros.add(livro);
                }
                return livros;

            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    public Collection<Livro> selecionarPorStatus(Status status) {
        String sql = "select * from livro where status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.toString());

            try (ResultSet resultSet = stmt.executeQuery()) {
                Collection<Livro> livrosStatus = new ArrayList<>();

                while (resultSet != null && resultSet.next()) {
                    Livro livro = extrairObjeto(resultSet);
                    livrosStatus.add(livro);
                }
                return livrosStatus;

            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        String sql = "select * from livro where pessoa_cpf = ? and status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getCpf());
            stmt.setString(2, Status.AGUARDANDO_EDICAO.toString());

            try (ResultSet resultSet = stmt.executeQuery()) {

                Collection<Livro> livrosAutor = new ArrayList<>();
                while (resultSet != null && resultSet.next()) {
                    Livro livro = extrairObjeto(resultSet);
                    livrosAutor.add(livro);
                }
                return livrosAutor;

            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    public void adicionarEditora(Editora editora, Livro livro) {

        String sql = "update livro set editora_id = ? where isbn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, new EditoraDAO().buscarIDEditora(editora.getNome()));
            stmt.setInt(2, livro.getIsbn());

            try {
                stmt.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    private Livro extrairObjeto(ResultSet resultSet) {
        try {
            Autor autor = (Autor) new PessoaDAO().selecionarPorCPF(resultSet.getString("pessoa_cpf"));
            Editora editora = new EditoraDAO().buscarPorID(resultSet.getInt("editora_id"));

            return new LivroFactory().getLivro(resultSet.getString("titulo"),
                    resultSet.getInt("isbn"), resultSet.getInt("qtd_paginas"),
                    autor, Status.valueOf(resultSet.getString("status")), editora);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair o objeto!");
        }
    }
}
