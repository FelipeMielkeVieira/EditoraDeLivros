package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaDAO {

    public void inserir(Pessoa pessoa) throws SQLException {
        String sql = "insert into pessoa (cpf, nome, sobrenome, email, senha, genero, funcao) values " +
                "(?,?,?,?,?,?,?)";
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pessoa.getCpf());
        statement.setString(2, pessoa.getNome());
        statement.setString(3, pessoa.getSobrenome());
        statement.setString(4, pessoa.getEmail());
        statement.setString(5, pessoa.getSenha());
        statement.setString(6, pessoa.getGenero().toString());

        if(pessoa instanceof Autor) {
            statement.setString(7, "AUTOR");
        } else if (pessoa instanceof Revisor) {
            statement.setString(7, "REVISOR");
        } else {
            statement.setString(7, "DIRETOR");
        }
        statement.execute();
        connection.close();
    }

    public Pessoa selecionarPorEmail(String email) throws SQLException {
        String sql = "select * from pessoa where email = ?";
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet != null && resultSet.next()) {
            Pessoa pessoa;
            if(resultSet.getString("funcao").equals("AUTOR")) {
                pessoa = new Autor(resultSet.getString("nome"), resultSet.getString("sobrenome"),
                        resultSet.getString("email"), resultSet.getString("senha"),
                        resultSet.getString("cpf"), Genero.valueOf(resultSet.getString("genero")));
            } else if (resultSet.getString("funcao").equals("REVISOR")) {
                pessoa = new Revisor(resultSet.getString("nome"), resultSet.getString("sobrenome"),
                        resultSet.getString("email"), resultSet.getString("senha"),
                        resultSet.getString("cpf"), Genero.valueOf(resultSet.getString("genero")));
            } else {
                pessoa = new Diretor(resultSet.getString("nome"), resultSet.getString("sobrenome"),
                        resultSet.getString("email"), resultSet.getString("senha"),
                        resultSet.getString("cpf"), Genero.valueOf(resultSet.getString("genero")));
            }
            connection.close();
            return pessoa;
        }
        connection.close();
        throw new RuntimeException("E-mail não encontrado!");
    }

}
