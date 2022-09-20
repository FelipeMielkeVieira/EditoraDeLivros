package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        if (selecionar(livro.getIsbn()) == null) {

            String sql = "insert into livro (isbn, titulo, qtd_paginas, status, pessoa_cpf) " +
                    "values (?,?,?,?,?)";

            ConexaoFactory conexaoFactory = new ConexaoFactory();
            Connection connection = conexaoFactory.conectaBD();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, livro.getIsbn());
            statement.setString(2, livro.getTitulo());
            statement.setInt(3, livro.getQtdPaginas());
            statement.setString(4, livro.getStatus().toString());
            statement.setString(5, livro.getAutor().getCpf());

            statement.execute();
            connection.close();
        } else {
            throw new RuntimeException("Livro já cadastrado!");
        }
    }

    public Livro selecionar(int isbn) throws SQLException {
        String sql = "select * from livro where isbn = ?";
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, isbn);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {

            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));
            Editora editora;
            if(resultSet.getInt("editora_id") == 0) {
                editora = null;
            } else {
                editora = buscarEditora(resultSet.getInt("editora_id"));
            }

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), autor, Status.valueOf(resultSet.getString("status")), editora);
            connection.close();
            return livro;
        } else {
            connection.close();
            return null;
        }
    }

    public void atualizar(int isbn, Livro livroAtualizado) throws SQLException {

        String sql = "update livro set titulo = ?, qtd_paginas = ?, status = ?" +
                "where isbn = ?";
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, livroAtualizado.getTitulo());
        statement.setInt(2, livroAtualizado.getQtdPaginas());
        statement.setString(3, livroAtualizado.getStatus().toString());
        statement.setInt(4, isbn);

        statement.execute();
        connection.close();
    }

    public Collection<Livro> selecionarTodos() throws SQLException {

        String sql = "select * from livro";
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Collection<Livro> livros = new ArrayList<>();
        while (resultSet != null && resultSet.next()) {

            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));
            Editora editora;
            if(resultSet.getInt("editora_id") == 0) {
                editora = null;
            } else {
                editora = buscarEditora(resultSet.getInt("editora_id"));
            }

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), autor, Status.valueOf(resultSet.getString("status")), editora);
            livros.add(livro);
        }
        connection.close();
        return livros;
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) throws SQLException {

        String sql = "select * from livro where pessoa_cpf = ?";
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pessoa.getCpf());

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Livro> livros = new ArrayList<>();
        while (resultSet != null && resultSet.next()) {
            Editora editora;
            if(resultSet.getInt("editora_id") == 0) {
                editora = null;
            } else {
                editora = buscarEditora(resultSet.getInt("editora_id"));
            }

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), (Autor) pessoa,
                    Status.valueOf(resultSet.getString("status")), editora);
            livros.add(livro);
        }
        return livros;
    }

    public Collection<Livro> selecionarPorStatus(Status status) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from livro where status = ?");
        statement.setString(1, status.toString());

        ResultSet resultSet = statement.executeQuery();
        Collection<Livro> livrosStatus = new ArrayList<>();

        while (resultSet != null && resultSet.next()) {
            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));

            Editora editora;
            if(resultSet.getInt("editora_id") == 0) {
                editora = null;
            } else {
                editora = buscarEditora(resultSet.getInt("editora_id"));
            }

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), autor,
                    Status.valueOf(resultSet.getString("status")), editora);
            livrosStatus.add(livro);
        }
        connection.close();
        return livrosStatus;
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from livro where pessoa_cpf = ? and status = ?");
        statement.setString(1, pessoa.getCpf());
        statement.setString(2, Status.AGUARDANDO_EDICAO.toString());

        ResultSet resultSet = statement.executeQuery();
        Collection<Livro> livrosAutor = new ArrayList<>();
        while (resultSet != null && resultSet.next()) {
            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));
            Editora editora;
            if(resultSet.getInt("editora_id") == 0) {
                editora = null;
            } else {
                editora = buscarEditora(resultSet.getInt("editora_id"));
            }

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), autor,
                    Status.valueOf(resultSet.getString("status")), editora);
            livrosAutor.add(livro);
        }
        connection.close();
        return livrosAutor;
    }

    public Autor buscarAutor(String cpf) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from pessoa where cpf = ?");
        statement.setString(1, cpf);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Autor autor = new Autor(resultSet.getString("nome"), resultSet.getString("sobrenome"),
                resultSet.getString("email"), resultSet.getString("senha"),
                resultSet.getString("cpf"), Genero.valueOf(resultSet.getString("genero")));
        connection.close();
        return autor;
    }

    public Editora buscarEditora(Integer id) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from editora where id = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        try {
            Editora editora = new Editora(resultSet.getString("nome"));
            connection.close();
            return editora;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer buscarIDEditora(String nome) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from editora where nome = ?");
        statement.setString(1, nome);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Integer id = resultSet.getInt("id");
        connection.close();
        return id;
    }

    public void adicionarEditora(Editora editora, Livro livro) throws SQLException {
        ConexaoFactory conexaoFactory = new ConexaoFactory();
        Connection connection = conexaoFactory.conectaBD();
        PreparedStatement statement = connection.prepareStatement("update livro set editora_id = ? where isbn = ?");
        statement.setInt(1, buscarIDEditora(editora.getNome()));
        statement.setInt(2, livro.getIsbn());

        statement.execute();
    }
}