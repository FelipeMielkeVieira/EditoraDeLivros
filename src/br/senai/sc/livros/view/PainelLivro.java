package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.LivroController;
import br.senai.sc.livros.model.entities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelLivro extends JFrame {
    private JPanel painelLivro;
    private JLabel tituloLivro;
    private JButton reprovarButton;
    private JButton devolverButton;
    private JButton aprovarButton;
    private JLabel isbnLivro;
    private JLabel paginasLivro;

    public PainelLivro(Livro livro, int opcaoEstante) {
        criarComponentes(livro);
        LivroController controller = new LivroController();
        reprovarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aprovacao(livro, 1);
                JOptionPane.showMessageDialog(null, "Livro Reprovado com Sucesso!");
                dispose();
                Estante estante = new Estante(opcaoEstante);
                estante.setVisible(true);
            }
        });
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aprovacao(livro, 2);
                JOptionPane.showMessageDialog(null, "Livro Devolvido para Edição com Sucesso!");
                dispose();
                Estante estante = new Estante(opcaoEstante);
                estante.setVisible(true);
            }
        });
        aprovarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aprovacao(livro, 3);
                JOptionPane.showMessageDialog(null, "Livro Aprovado com Sucesso!");
                dispose();
                Estante estante = new Estante(opcaoEstante);
                estante.setVisible(true);
            }
        });
    }

    private void criarComponentes(Livro livro) {
        setContentPane(painelLivro);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        tituloLivro.setText(livro.getTitulo());
        isbnLivro.setText(String.valueOf(livro.getIsbn()));
        paginasLivro.setText(String.valueOf(livro.getQtdPaginas()));
    }
}
