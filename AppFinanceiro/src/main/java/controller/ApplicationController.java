package controller;

import model.Financas;
import model.Usuario;
import view.FinanceiroScreen;
import view.LoginScreen;
import dao.FinancaDAO;
import dao.FinancaDAOImpl;

import javax.swing.*;
import java.util.List;

public class ApplicationController {

    private FinanceiroScreen view;
    private UsuariosController usuariosController;
    private FinancasController financasController;
    private CategoriaController categoriaController;

    public ApplicationController(UsuariosController usuariosController) {
        this.usuariosController = usuariosController;
        Usuario usuario = usuariosController.getUsuarioAtual();

        FinancaDAO financaDAO = new FinancaDAOImpl(usuariosController.getSessionFactory());

        this.financasController = new FinancasController(usuario, financaDAO);
        this.categoriaController = new CategoriaController(usuario, usuariosController.getSessionFactory());

        this.view = new FinanceiroScreen(usuario.getNome());

        this.view.addAdicionarFinancaListener(e -> abrirTelaAdicionarFinanca());
        this.view.addHistoricoFinancaListener(e -> abrirTelaHistorico());
        this.view.addGerenciarCategoriaListener(e -> abrirTelaCategorias());
        this.view.addExibirTotalFinancasListener(e -> exibirTotalFinancas());
        this.view.addSairListener(e -> confirmarSaida());
    }

    private void abrirTelaAdicionarFinanca() {
        new CadastrarFinancasController(financasController, categoriaController);
    }

    private void abrirTelaHistorico() {
        List<Financas> lista = financasController.listarFinancas();
        if (lista.isEmpty()) {
            view.mostrarMensagem("Nenhuma finança cadastrada ainda.");
        } else {
            new HistoricoFinancasController(financasController, categoriaController);
        }
    }

    private void abrirTelaCategorias() {
        new GerenciadorCategoriaController(categoriaController);
    }

    private void exibirTotalFinancas() {
        double totalReceitas = 0;
        double totalDespesas = 0;

        for (Financas f : financasController.listarFinancas()) {
            if (f.getValor() > 0) {
                totalReceitas += f.getValor();
            } else {
                totalDespesas += f.getValor();
            }
        }

        double saldo = totalReceitas + totalDespesas;

        String mensagem = String.format("Total de Finanças:\n" +
                        "Receitas: R$ %.2f\n" +
                        "Despesas: R$ %.2f\n" +
                        "Saldo: R$ %.2f\n" +
                        (saldo >= 0 ? "Saldo Positivo! =D" : "Saldo Negativo! :("),
                totalReceitas, totalDespesas, saldo);

        view.mostrarMensagem(mensagem);
    }

    private void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(
                view.getFrame(),
                "Deseja realmente sair do seu usuário?",
                "Trocar usuário",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            view.dispose();
            LoginScreen novaTelaLogin = new LoginScreen();
            new LoginController(novaTelaLogin, usuariosController);
        }
    }
}
