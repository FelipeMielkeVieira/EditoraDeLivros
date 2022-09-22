package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.Editora;
import br.senai.sc.livros.model.factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditoraDAO {

    private final Connection conn;

    public EditoraDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public void inserir(Editora editora) {

        if (selecionar(editora.getNome()) == null) {

            String sql = "insert into editora (nome) values (?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, editora.getNome());
                try {
                    stmt.execute();
                } catch (Exception e) {
                    throw new RuntimeException("Erro na execução do comando SQL");
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } else {
            throw new RuntimeException();
        }
    }

    public Editora selecionar(String nome) {

        String sql = "select * from editora where nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Editora(resultSet.getString("nome"));
                }
                return null;
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    public Editora buscarPorID(Integer id) {

        String sql = "select * from editora where id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                try {
                    return new Editora(resultSet.getString("nome"));
                } catch (Exception e) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    public Integer buscarIDEditora(String nome) {

        String sql = "select * from editora where nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);

            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("id");

            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }
}
