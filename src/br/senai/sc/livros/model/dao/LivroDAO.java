package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

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

            Conexao conexao = new Conexao();
            Connection connection = conexao.conectaBD();
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
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, isbn);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {

            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));
            Editora editora = buscarEditora(resultSet.getInt("editora_id"));

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
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
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
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Collection<Livro> livros = new ArrayList<>();
        while (resultSet.next()) {

            Autor autor = buscarAutor(resultSet.getString("pessoa_cpf"));
            Editora editora = buscarEditora(resultSet.getInt("editora_id"));

            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), autor, Status.valueOf(resultSet.getString("status")), editora);
            livros.add(livro);
        }
        connection.close();
        return livros;
    }

    public Collection<Livro> selecionarPorAutor(Pessoa pessoa) throws SQLException {

        String sql = "select * from livro where pessoa_cpf = ?";
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pessoa.getCpf());

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Livro> livros = new ArrayList<>();
        while (resultSet.next()) {
            Livro livro = new Livro(resultSet.getString("titulo"), resultSet.getInt("isbn"),
                    resultSet.getInt("qtd_paginas"), (Autor) pessoa,
                    Status.valueOf(resultSet.getString("status")), buscarEditora(resultSet.getInt("editora_id")));
            livros.add(livro);
        }
        return livros;
    }

    public Collection<Livro> selecionarPorStatus(Status status) {
//        Collection<Livro> livrosStatus = new ArrayList<>();
//        for (Livro livro : listaLivros) {
//            if (livro.getStatus().equals(status)) {
//                livrosStatus.add(livro);
//            }
//        }
//        return livrosStatus;
        return null;
    }

    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
//        Collection<Livro> livrosAutor = new ArrayList<>();
//        if (listaLivros.isEmpty()) {
//            return null;
//        }
//        for (Livro livro : listaLivros) {
//            if (livro.getAutor().equals(pessoa) && livro.getStatus().equals(Status.AGUARDANDO_EDICAO)) {
//                livrosAutor.add(livro);
//            }
//        }
//        return livrosAutor;
        return null;
    }

    public Autor buscarAutor(String cpf) throws SQLException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from pessoa where cpf = ?");
        statement.setString(1, cpf);

        ResultSet resultSet = statement.executeQuery();
        Autor autor = new Autor(resultSet.getString("nome"), resultSet.getString("sobrenome"),
                resultSet.getString("email"), resultSet.getString("senha"),
                resultSet.getString("cpf"), Genero.valueOf(resultSet.getString("genero")));
        connection.close();
        return autor;
    }

    public Editora buscarEditora(Integer id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from editora where id = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        try {
            Editora editora = new Editora(resultSet.getString("nome"));
            connection.close();
            return editora;
        } catch (Exception e) {
            return null;
        }
    }
}
