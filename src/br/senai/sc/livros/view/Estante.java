package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.EditoraController;
import br.senai.sc.livros.controller.LivroController;
import br.senai.sc.livros.model.entities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Estante extends JFrame {
    private JPanel estante;
    private JTable tabelaLivros;
    private JButton voltarButton;
    private JButton editarButton;
    private JLabel semLivros;
    private static int lista;

    public Estante(int botao) {
        lista = botao;
        criarComponentes();
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu menu = new Menu(Menu.getUsuario());
                menu.setVisible(true);
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pessoa usuario = Menu.getUsuario();
                if(tabelaLivros.getSelectedRow() > -1) {

                    String titulo = (String) tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 1);
                    int isbn = (int) tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 0);
                    int qtdPaginas = (int) tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 2);
                    Autor autor = (Autor) tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 3);
                    Status status = (Status) tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 5);
                    Livro livroAtual = new Livro(titulo, isbn, qtdPaginas, autor, status);

                    if(usuario instanceof Autor) {
                        dispose();
                        CadastroLivro cadastroLivro = new CadastroLivro(usuario, livroAtual);
                        cadastroLivro.setVisible(true);
                    } else if (usuario instanceof Revisor) {
                        String linhas = JOptionPane.showInputDialog(null, "Páginas lidas na revisão:");
                        LivroController controller = new LivroController();
                        try{
                            if(Integer.parseInt(linhas) >= livroAtual.getQtdPaginas()) {
                                dispose();
                                PainelLivro painelLivro = new PainelLivro(livroAtual, botao);
                                painelLivro.setVisible(true);
                            } else {
                                controller.editarLivro(livroAtual);
                            }
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Problema na Edição do Livro!");
                            Estante estante = new Estante(botao);
                            estante.setVisible(true);
                        }
                    } else {
                        Object[] opcoes = {"Reprovar", "Devolver", "Publicar"};
                        int opcao = JOptionPane.showOptionDialog(null, "Selecione a ação desejada", "Editar livro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, opcoes, opcoes[0]);
                        LivroController controller = new LivroController();

                        try {
                            if(opcao == 2) {
                                String editora = JOptionPane.showInputDialog(null, "Nome da Editora da Publicação:");
                                EditoraController editoraController = new EditoraController();
                                if(editoraController.buscar(editora) != null) {
                                    livroAtual.setEditora(editoraController.buscar(editora));
                                    controller.aprovacao(livroAtual, opcao);
                                    JOptionPane.showMessageDialog(null, "Livro Editado com Sucesso!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Editora Inválida!");
                                }
                            } else {
                                controller.aprovacao(livroAtual, opcao);
                                JOptionPane.showMessageDialog(null, "Livro Editado com Sucesso!");
                            }
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Editora Inválida!");
                        }
                    }
                }
            }
        });
    }

    private void criarComponentes() {
        LivroController controller = new LivroController();
        try {
            tabelaLivros.setModel(new DefaultTableModelCollection(controller.buscarLista(lista)));
            semLivros.setVisible(false);
        } catch (RuntimeException exception) {
            tabelaLivros.setVisible(false);
            semLivros.setVisible(true);
        }
        if(lista == 1 && !(Menu.getUsuario() instanceof Revisor)) {
            editarButton.setVisible(false);
        }
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setContentPane(estante);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
}
