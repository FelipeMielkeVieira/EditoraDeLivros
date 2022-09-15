package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditoraDAO {

    public void inserir(Editora editora) throws SQLException {
        if(selecionar(editora.getNome()) == null) {
            Conexao conexao = new Conexao();
            Connection connection = conexao.conectaBD();
            PreparedStatement statement = connection.prepareStatement("insert into editora (nome) values (?)");
            statement.setString(1, editora.getNome());
            statement.execute();
        } else {
            throw new RuntimeException();
        }
    }

    public Editora selecionar(String nome) throws SQLException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.conectaBD();
        PreparedStatement statement = connection.prepareStatement("select * from editora where nome = ?");
        statement.setString(1, nome);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            Editora editora = new Editora(resultSet.getString("nome"));
            connection.close();
            return editora;
        }
        connection.close();
        return null;
    }
}
