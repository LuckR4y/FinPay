package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.awt.event.KeyEvent;

public class LoginScreen extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnRegistrar;

    public LoginScreen() {

        setTitle("FinPay"); // Título da tela
        setSize(600, 450); // Tamanho da aplicação
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao sair
        setLocationRelativeTo(null); // Centraliza a janela na tela

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

        // Configurações da tela
        setLayout(new BorderLayout()); // Define o layout principal

        // Textos que serão exibidos na tela, sem as configurações de GridBagConstraints
        JLabel tituloLogin = new JLabel("Bem-vindo ao FinPay, seu aliado nas finanças!", SwingConstants.CENTER);
        tituloLogin.setFont(new Font("Arial", Font.BOLD, 20));
        add(tituloLogin, BorderLayout.NORTH); // Adiciona o título no topo da tela

        JLabel rodapeLogin = new JLabel("© 2025 - Desenvolvido por: Arthur Vital Fontana, João Henrique Nazar Tavares e Rafael Mele Porto | 2.0", SwingConstants.CENTER);
        rodapeLogin.setFont(new Font("Arial", Font.BOLD, 10));
        add(rodapeLogin, BorderLayout.SOUTH); // Adiciona o rodapé na parte inferior da tela

        // Componentes de login que aparecem na tela, com as configurações de GridBagConstraints
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes

        // Componentes da tela
        JLabel lblUsuario = new JLabel("Usuário:");
        txtUsuario = new JTextField(15);
        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(15);
        btnLogin = new JButton("LOGIN");
        btnLogin.setMnemonic(KeyEvent.VK_S);

        // Posicionamento dos componentes
        // Texto "Usuário:"
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        // Caixa de texto da aba "Usuário"
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        // Texto "Senha"
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblSenha, gbc);

        // Caixa de texto da aba "Senha"
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        // Botão de "LOGIN"
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        // Torna o botão ENTER padrão ao pressionar
        getRootPane().setDefaultButton(btnLogin);

        // Criar botão "Registrar"
        btnRegistrar = new JButton("REGISTRAR-SE");


        // Posicionar o botão "Registrar" na tela
        gbc.gridy = 3;
        panel.add(btnRegistrar, gbc);

        add(panel);
        setVisible(true); // Tornar a tela visível
    }

    public String getUsuario() {
        return txtUsuario.getText();
    }

    public String getSenha() {
        return new String(txtSenha.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
