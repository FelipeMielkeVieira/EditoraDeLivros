package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.PessoaFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PessoaDAO {

    private final Connection conn;

    public PessoaDAO() {
        this.conn = new ConexaoFactory().conectaBD();
    }

    public void inserir(Pessoa pessoa) {
        String sql = "insert into pessoa (cpf, nome, sobrenome, email, senha, genero, funcao) values " +
                "(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getSobrenome());
            stmt.setString(4, pessoa.getEmail());
            stmt.setString(5, pessoa.getSenha());
            stmt.setString(6, pessoa.getGenero().toString());
            stmt.setString(7, (
                    (pessoa instanceof Autor) ? "AUTOR" :
                            (pessoa instanceof Revisor) ? "REVISOR" : "DIRETOR"
            ));

            try {
                stmt.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }

        } catch (Exception exception) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    public Pessoa selecionarPorEmail(String email) {
        String sql = "select * from pessoa where email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if(resultSet != null && resultSet.next()) {
                    return extrairObjeto(resultSet);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }

        throw new RuntimeException("E-mail não encontrado!");
    }

    private Pessoa extrairObjeto(ResultSet resultSet) {
        try {
            return new PessoaFactory().getPessoa(resultSet.getString("nome"),
                    resultSet.getString("sobrenome"),
                    resultSet.getString("email"),
                    resultSet.getString("senha"),
                    resultSet.getString("cpf"),
                    resultSet.getString("genero"),
                    resultSet.getString("funcao"));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair o objeto!");
        }
    }

}