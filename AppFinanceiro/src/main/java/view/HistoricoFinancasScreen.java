package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoFinancasScreen extends JFrame {
    private JTextField txtDataInicial;
    private JTextField txtDataFinal;
    private JTextField txtBuscaDescricao;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboTipo;
    private JList<String> lista;
    private DefaultListModel<String> listModel;
    private JButton btnFiltrar;
    private JPopupMenu menuPopup;
    private JMenuItem itemEditar;
    private JMenuItem itemExcluir;
    private List<Color> coresCategorias;

    public HistoricoFinancasScreen() {

        setTitle("Histórico de Finanças");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // IMAGEM DA APLICAÇÃO: Tratamento de erros caso não consiga carregar a imagem que colocamos
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

        // Painel do Filtro
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 7));

        // Campo de busca por data
        txtDataInicial = new JTextField(6);
        txtDataFinal = new JTextField(6);
        painelFiltro.add(new JLabel("Data Inicial (Dia/Mês/Ano):"));
        painelFiltro.add(txtDataInicial);
        painelFiltro.add(new JLabel("Data Final (Dia/Mês/Ano):"));
        painelFiltro.add(txtDataFinal);

        // Campo de busca por descrição
        txtBuscaDescricao = new JTextField(6);
        painelFiltro.add(new JLabel("Descrição:"));
        painelFiltro.add(txtBuscaDescricao);

        // Combo de categoria
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Todas as Categorias");
        painelFiltro.add(new JLabel("Categoria:"));
        painelFiltro.add(comboCategoria);

        // Combo de tipo
        comboTipo = new JComboBox<>();
        comboTipo.addItem("Todos os Tipos");
        comboTipo.addItem("Receita");
        comboTipo.addItem("Despesa");
        painelFiltro.add(new JLabel("Tipo:"));
        painelFiltro.add(comboTipo);

        // Botão de Filtrar
        btnFiltrar = new JButton("Filtrar");
        painelFiltro.add(btnFiltrar);

        add(painelFiltro, BorderLayout.NORTH);

        // Preencher a lista com as finanças iniciais
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formatação da data

        // Criando um menu Popup
        menuPopup = new JPopupMenu();
        itemEditar = new JMenuItem("Editar");
        itemExcluir = new JMenuItem("Remover");

        menuPopup.add(itemEditar);
        menuPopup.add(itemExcluir);

        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(lista);
        add(scrollPane, BorderLayout.CENTER);

        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (coresCategorias != null && index >= 0 && index < coresCategorias.size()) {
                    Color cor = coresCategorias.get(index);
                    label.setBackground(cor); // Cor de fundo (como na imagem)
                }

                // Mantém o fundo de seleção visível
                label.setOpaque(true);
                return label;
            }
        });

        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // Clique do botão direito
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = lista.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        lista.setSelectedIndex(index);
                        menuPopup.show(lista, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public void setListaFinancas(List<String> linhas) {
        listModel.clear();
        for (String linha : linhas) {
            listModel.addElement(linha);
        }
    }

    public void setComboCategorias(List<String> categorias) {
        comboCategoria.removeAllItems();
        for (String c : categorias) {
            comboCategoria.addItem(c);
        }
    }

    public void setComboTipos(List<String> tipos) {
        comboTipo.removeAllItems();
        for (String t : tipos) {
            comboTipo.addItem(t);
        }
    }

    public void setCoresCategorias(List<Color> cores) {
        this.coresCategorias = cores;
    }

    public String getDescricaoFiltro() {
        return txtBuscaDescricao.getText().trim();
    }

    public String getDataInicialFiltro() {
        return txtDataInicial.getText().trim();
    }

    public String getDataFinalFiltro() {
        return txtDataFinal.getText().trim();
    }

    public String getCategoriaSelecionada() {
        return (String) comboCategoria.getSelectedItem();
    }

    public String getTipoSelecionado() {
        return (String) comboTipo.getSelectedItem();
    }

    public int getItemSelecionadoIndex() {
        return lista.getSelectedIndex();
    }

    public void addFiltrarListener(ActionListener listener) {
        btnFiltrar.addActionListener(listener);
    }

    public void addEditarListener(ActionListener listener) {
        itemEditar.addActionListener(listener);
    }

    public void addExcluirListener(ActionListener listener) {
        itemExcluir.addActionListener(listener);
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public JFrame getFrame() {
        return this;
    }
}
