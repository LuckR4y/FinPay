package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.awt.event.ActionListener;

public class RegistroScreen extends JFrame {

    private JTextField txtNome;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnRegistrar;
    private JButton btnVoltar;

    public RegistroScreen() {
        setTitle("Registrar Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        // Layout do painel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espaçamento entre os componentes
        gbc.gridx = 0; // Define a coluna sempre como 0
        gbc.anchor = GridBagConstraints.WEST; // Alinha os textos à esquerda

        // Espaço para "Nome"
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(15);
        gbc.gridy = 0;
        gbc.gridx = 0;
        panel.add(lblNome, gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        // Espaço para "Nome de Usuário"
        JLabel lblUsuario = new JLabel("Nome de Usuário:");
        txtUsuario = new JTextField(15);
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Espaço para "Senha"
        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(15);
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(lblSenha, gbc);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        // Botão Cadastrar
        btnRegistrar = new JButton("Cadastrar");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnRegistrar, gbc);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        gbc.gridy = 4;
        panel.add(btnVoltar, gbc);

        add(panel);
        setVisible(true);
    }

    public String getNome(){
        return txtNome.getText();
    }

    public String getUsuario(){
        return txtUsuario.getText();
    }

    public String getSenha(){
        return new String(txtSenha.getPassword());
    }

    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    public void addVoltarListener(ActionListener listener) {
        btnVoltar.addActionListener(listener);
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
