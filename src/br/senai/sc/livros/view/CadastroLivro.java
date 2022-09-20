package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.LivroController;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Livro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroLivro extends JFrame {
    private JButton voltarButton;
    private JButton cadastrarButton;
    private JTextField tituloInput;
    private JTextField isbnInput;
    private JTextField paginasInput;
    private JPanel cadastroLivro;
    private JButton editarButton;

    public CadastroLivro(Pessoa pessoa, Livro livro) {
        criarComponentes(livro);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu(Menu.getUsuario());
                menu.setVisible(true);
            }
        });
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tituloInput.getText().isEmpty() || isbnInput.getText().isEmpty() || paginasInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Há campos vazios!\n" +
                            "Preencha e tente novamente!");
                } else {
                    LivroController controller = new LivroController();
                    try {
                        controller.cadastrar(tituloInput.getText(), isbnInput.getText(), paginasInput.getText(), pessoa);
                        JOptionPane.showMessageDialog(null, "Livro cadastrado com Sucesso!");
                        dispose();
                        Menu menu = new Menu(Menu.getUsuario());
                        menu.setVisible(true);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tituloInput.getText().isEmpty() || isbnInput.getText().isEmpty() || paginasInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Há campos vazios!\n" +
                            "Preencha e tente novamente!");
                } else {
                    LivroController controller = new LivroController();
                    try {
                        livro.setTitulo(tituloInput.getText());
                        livro.setQtdPaginas(Integer.parseInt(paginasInput.getText()));
                        controller.editarLivro(livro);
                        JOptionPane.showMessageDialog(null, "Livro editado com Sucesso!");
                        dispose();
                        Menu menu = new Menu(Menu.getUsuario());
                        menu.setVisible(true);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
            }
        });
    }

    private void criarComponentes(Livro livro) {
        setContentPane(cadastroLivro);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        if (livro == null) {
            editarButton.setVisible(false);
        } else {
            cadastrarButton.setVisible(false);
            tituloInput.setText(livro.getTitulo());
            isbnInput.setText(String.valueOf(livro.getIsbn()));
            isbnInput.setEditable(false);
            paginasInput.setText(String.valueOf(livro.getQtdPaginas()));
        }
    }

}
