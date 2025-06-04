package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class FinanceiroScreen extends JFrame {

    private JLabel tituloFinanceiro;
    private JButton btnAdicionarFinanca;
    private JButton btnHistoricoFinancas;
    private JButton btnGerenciarCategoria;
    private JButton btnExibirTotalFinancas;
    private JButton btnSair;

    public FinanceiroScreen(String nomeUsuario) {

        setTitle("FinPay - Dashboard");
        setSize(750, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // IMAGEM DA APLICAÇÃO: Tratamento de erros caso não consiga carregar a imagem que colocamos
        try {
            InputStream imgFinpay = getClass().getClassLoader().getResourceAsStream("Finpay.png");
            if (imgFinpay!= null) {
                ImageIcon iconFinanceiro = new ImageIcon(ImageIO.read(imgFinpay));
                setIconImage(iconFinanceiro.getImage());
            } else {
                System.err.println("A imagem não foi encontrada no classpath!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Responsável por organizar o layout na vertical
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza todo o conteúdo

        // Texto do topo da tela
        tituloFinanceiro = new JLabel(nomeUsuario + ", bem-vindo ao dashboard do FinPay!", SwingConstants.CENTER);
        tituloFinanceiro.setFont(new Font("Arial", Font.BOLD, 20));
        tituloFinanceiro.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o título
        mainPanel.add(tituloFinanceiro);

        // Painel para a imagem do meio da tela
        JLabel imagemLabel = new JLabel();
        try {
            ImageIcon imagem = new ImageIcon(getClass().getClassLoader().getResource("Finpay.png"));
            imagemLabel.setIcon(imagem);
            imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza a imagem
            mainPanel.add(imagemLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15)); // Centraliza os botões

        btnAdicionarFinanca = new JButton("Adicionar Finança");
        btnHistoricoFinancas = new JButton("Histórico de Finanças");
        btnGerenciarCategoria = new JButton("Gerenciar Categorias");
        btnExibirTotalFinancas = new JButton("Exibir Total de Finanças");

        painelBotoes.add(btnAdicionarFinanca);
        painelBotoes.add(btnHistoricoFinancas);
        painelBotoes.add(btnGerenciarCategoria);

        btnExibirTotalFinancas = new JButton("Exibir Total de Finanças");
        painelBotoes.add(btnExibirTotalFinancas);  // Adiciona o botão no painel de botões

        // Adiciona o painel de botões ao painel principal
        mainPanel.add(painelBotoes);

        // Painel inferior para o botão "Sair" e o rodapé
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BoxLayout(painelInferior, BoxLayout.Y_AXIS));

        // Botão "Sair"
        btnSair = new JButton("Sair");
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão

        // Rodapé com os nomes dos desenvolvedores
        JLabel rodape = new JLabel(" © 2025 - Desenvolvido por: Arthur Vital Fontana, João Henrique Nazar Tavares e Rafael Mele Porto | 2.0", SwingConstants.CENTER);
        rodape.setFont(new Font("Arial", Font.BOLD, 10));
        rodape.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o rodapé

        // Adicionando o botão e o rodapé ao painel inferior
        painelInferior.add(btnSair);
        painelInferior.add(Box.createVerticalStrut(5)); // Espaço entre botão e rodapé
        painelInferior.add(rodape);

        mainPanel.add(painelInferior); // Adiciona o painel inferior no painel principal

        add(mainPanel); // Adiciona o painel principal ao JFrame

        setVisible(true);
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void addAdicionarFinancaListener(ActionListener listener) {
        btnAdicionarFinanca.addActionListener(listener);
    }

    public void addHistoricoFinancaListener(ActionListener listener) {
        btnHistoricoFinancas.addActionListener(listener);
    }

    public void addGerenciarCategoriaListener(ActionListener listener) {
        btnGerenciarCategoria.addActionListener(listener);
    }

    public void addExibirTotalFinancasListener(ActionListener listener) {
        btnExibirTotalFinancas.addActionListener(listener);
    }

    public void addSairListener(ActionListener listener) {
        btnSair.addActionListener(listener);
    }

    public JFrame getFrame() {
        return this;
    }

}
