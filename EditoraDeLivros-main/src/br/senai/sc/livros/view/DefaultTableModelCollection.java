package br.senai.sc.livros.view;

import br.senai.sc.livros.model.entities.Livro;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultTableModelCollection extends AbstractTableModel {

    List<Livro> dados;
    String[] colunas;

    public DefaultTableModelCollection(Collection<Livro> lista) {
        this.dados = new ArrayList<>(lista);
        this.colunas = new String[]{"ISBN", "Título", "Qtd Páginas", "Autor", "Editora", "Status"};
    }

    @Override
    public int getRowCount() {
        try {
            return dados.size();
        } catch (Exception exception) {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Livro livro = dados.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return livro.getIsbn();
            }
            case 1 -> {
                return livro.getTitulo();
            }
            case 2 -> {
                return livro.getQtdPaginas();
            }
            case 3 -> {
                return livro.getAutor();
            }
            case 4 -> {
                if(livro.getEditora() == null) {
                    return "Sem Editora";
                } else {
                    return livro.getEditora().getNome();
                }
            }
            case 5 -> {
                return livro.getStatus();
            }
            default -> {
                return livro;
            }
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }
}
