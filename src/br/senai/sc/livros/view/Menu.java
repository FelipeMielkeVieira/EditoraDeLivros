package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.EditoraController;
import br.senai.sc.livros.model.entities.Autor;
import br.senai.sc.livros.model.entities.Diretor;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.entities.Revisor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    private JButton sairButton;
    private JPanel menu;
    private JButton cadastrarLivrosButton;
    private JButton listarLivrosButton;
    private JButton listarAtividadesButton;
    private JButton cadastrarRevisorButton;
    private JButton cadastrarEditoraButton;
    private JButton listarEditorasButton;
    private static Pessoa usuario;

    public static Pessoa getUsuario() {
        return usuario;
    }

    public Menu(Pessoa pessoa) {
        usuario = pessoa;
        criarComponentes();
    }

    private void criarComponentes() {
        setContentPane(menu);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        cadastrarLivrosButton.addActionListener(this);
        cadastrarLivrosButton.setActionCommand("cadastrarLivro");
        listarLivrosButton.addActionListener((this));
        listarLivrosButton.setActionCommand("listarLivros");
        listarAtividadesButton.addActionListener(this);
        listarAtividadesButton.setActionCommand("listarAtividades");
        cadastrarRevisorButton.addActionListener(this);
        cadastrarRevisorButton.setActionCommand("cadastrarRevisor");
        cadastrarEditoraButton.addActionListener(this);
        cadastrarEditoraButton.setActionCommand("cadastrarEditora");
        sairButton.addActionListener(this);
        sairButton.setActionCommand("sair");
        listarEditorasButton.addActionListener(this);
        listarEditorasButton.setActionCommand("listarEditoras");

        if (usuario instanceof Autor || usuario instanceof Revisor) {
            cadastrarRevisorButton.setVisible(false);
        }
        if (usuario instanceof Revisor || usuario instanceof Diretor) {
            cadastrarLivrosButton.setVisible(false);
        }
        if (usuario instanceof Autor || usuario instanceof Revisor) {
            cadastrarEditoraButton.setVisible(false);
            listarEditorasButton.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "cadastrarLivro":
                dispose();
                CadastroLivro cadastroLivro = new CadastroLivro(usuario, null);
                cadastroLivro.setVisible(true);
                break;
            case "listarLivros": {
                dispose();
                Estante estante = new Estante(1);
                estante.setVisible(true);
                break;
            }
            case "listarAtividades": {
                dispose();
                Estante estante = new Estante(2);
                estante.setVisible(true);
                break;
            }
            case "cadastrarRevisor":
                dispose();
                CadastroPessoa cadastroPessoa = new CadastroPessoa();
                cadastroPessoa.setVisible(true);
                break;
            case "sair":
                usuario = null;
                dispose();
                Login login = new Login();
                login.run();
                break;
            case "cadastrarEditora":
                try {
                    String editora = JOptionPane.showInputDialog(null, "Nome da Editora:");
                    if (editora != null) {
                        EditoraController controller = new EditoraController();
                        controller.cadastrar(editora);
                        JOptionPane.showMessageDialog(null, "Editora cadastrada com sucesso!");
                    }
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(null, "Deu Ruim");
                }
                break;
            case "listarEditoras":
                dispose();
                EditoraController controller = new EditoraController();
                JOptionPane.showMessageDialog(null, controller.listarEditoras());
                break;
        }
    }
}
