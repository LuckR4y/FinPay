package view;

import model.Categoria;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class CadastrarFinancasScreen extends JFrame {
    private JTextField txtDescricao;
    private JTextField txtValor;
    private JSpinner spinnerData;
    private JComboBox<Categoria> comboCategorias;
    private JComboBox<String> tipoFinanca;
    private JButton btnCadastrar;

    public CadastrarFinancasScreen() {

        setTitle("Cadastrar Finança");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 0; // linha 0
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextField(15);
        add(txtDescricao, gbc);

        // Valor
        gbc.gridx = 0;
        gbc.gridy = 1; // linha 1
        add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(15);
        add(txtValor, gbc);

        // Tipo Finança
        gbc.gridx = 0;
        gbc.gridy = 2; // linha 2
        add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        tipoFinanca = new JComboBox<>(new String[] { "Receita", "Despesa" });
        add(tipoFinanca, gbc);

        // Data
        gbc.gridx = 0;
        gbc.gridy = 3; // linha 3
        add(new JLabel("Data:"), gbc);
        gbc.gridx = 1;

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        spinnerData = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);
        spinnerData.setPreferredSize(new Dimension(150, 25));
        add(spinnerData, gbc);

        // model.Categoria
        gbc.gridx = 0;
        gbc.gridy = 4; // linha 4
        add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        comboCategorias = new JComboBox<>();
        add(comboCategorias, gbc);

        // Botão de Cadastrar
        gbc.gridx = 0;
        gbc.gridy = 5; // linha 5
        gbc.gridwidth = 2;
        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar, gbc);

        // COMENTADO !!!!! setVisible(true);
        // atualizarCategorias();
    }

    public String getDescricao() {
        return txtDescricao.getText().trim();
    }

    public String getValor() {
        return txtValor.getText().trim();
    }

    public Date getData(){
        return (Date) spinnerData.getValue();
    }

    public String getTipo(){
        return (String) tipoFinanca.getSelectedItem();
    }

    public Categoria getCategoria(){
        return (Categoria) comboCategorias.getSelectedItem();
    }

    public void limparCampos() {
        txtDescricao.setText("");
        txtValor.setText("");
        tipoFinanca.setSelectedIndex(0);
        spinnerData.setValue(new Date());
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void atualizarCategorias(List<Categoria> categorias) {
        comboCategorias.removeAllItems();
        for (Categoria categoria : categorias) {
            comboCategorias.addItem(categoria);
        }
    }

    public Categoria getCategoriaSelecionada() {
        return (Categoria) comboCategorias.getSelectedItem();
    }

    public void addCadastrarListener(ActionListener listener) {
        btnCadastrar.addActionListener(listener);
    }
}
