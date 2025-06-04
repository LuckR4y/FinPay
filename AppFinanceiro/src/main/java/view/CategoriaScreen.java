package view;

import model.Categoria;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.List;

public class CategoriaScreen extends JFrame {

    private JTextField campoNome = new JTextField(15);
    private JButton btnAdicionar = new JButton("Adicionar");
    private JButton btnEditar = new JButton("Editar");
    private JButton btnRemover = new JButton("Remover");
    private JButton btnCorSelect= new JButton("Cor");

    private Color corSelecionada = Color.WHITE;
    private DefaultListModel<Categoria> listModel = new DefaultListModel<>();
    private JList<Categoria> listaCategorias = new JList<>(listModel);

    public CategoriaScreen() {

        setTitle("Gerenciar Categorias");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            InputStream imgFinpay = getClass().getClassLoader().getResourceAsStream("Finpay.png");
            if (imgFinpay != null) {
                ImageIcon iconFinanceiro = new ImageIcon(ImageIO.read(imgFinpay));
                setIconImage(iconFinanceiro.getImage());
            } else {
                System.err.println("A imagem não foi encontrada no classpath!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Nome:"));
        panelTop.add(campoNome);
        panelTop.add(btnCorSelect);
        panelTop.add(btnAdicionar);

        listaCategorias.setCellRenderer(new CategoriaRenderer());
        JScrollPane scrollPane = new JScrollPane(listaCategorias);

        JPanel panelBottom = new JPanel();
        panelBottom.add(btnEditar);
        panelBottom.add(btnRemover);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnAdicionar);
    }

    // Métodos públicos para a controller
    public void addAdicionarListener(ActionListener al) {
        btnAdicionar.addActionListener(al);
    }

    public void addEditarListener(ActionListener al) {
        btnEditar.addActionListener(al);
    }

    public void addRemoverListener(ActionListener al) {
        btnRemover.addActionListener(al);
    }

    public void addSelecionarCorListener(ActionListener al) {
        btnCorSelect.addActionListener(al);
    }

    public String getNomeCategoria() {
        return campoNome.getText().trim();
    }

    public Color getCorSelecionada() {
        return corSelecionada;
    }

    public void setCorSelecionada(Color cor) {
        this.corSelecionada = cor;
    }

    public Categoria getCategoriaSelecionada() {
        return listaCategorias.getSelectedValue();
    }

    public int getCategoriaSelecionadaIndex() {
        return listaCategorias.getSelectedIndex();
    }

    public void atualizarListaCategorias(List<Categoria> categorias) {
        listModel.clear();
        for (Categoria c : categorias) {
            listModel.addElement(c);
        }
    }

    public void limparCampoNome() {
        campoNome.setText("");
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Renderer visual para exibir a cor da categoria
    private static class CategoriaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Categoria) {
                Categoria categoria = (Categoria) value;
                setText(categoria.getNome());
                setBackground(isSelected ? Color.LIGHT_GRAY : categoria.getColor());
            }
            return this;
        }
    }
}