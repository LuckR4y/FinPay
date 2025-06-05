package controller;

import dao.*;
import model.Financas;
import model.Usuario;
import view.FinanceiroScreen;
import util.HibernateUtil;
import view.LoginScreen;

import javax.swing.*;
import java.util.List;

public class ApplicationController {

    private final FinanceiroScreen view;
    private final Usuario usuario;

    public ApplicationController(Usuario usuario, FinancaDAO financaDAO) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        this.view = new FinanceiroScreen(usuario.getNome());
        this.usuario = usuario;

        CategoriaDAO categoriaDAO = new CategoriaDAOImpl(HibernateUtil.getSessionFactory(), usuario);

        this.view.addAdicionarFinancaListener(e -> abrirTelaAdicionarFinanca(financaDAO, categoriaDAO, usuario));
        this.view.addHistoricoFinancaListener(e -> abrirTelaHistorico(financaDAO, categoriaDAO, usuario));
        this.view.addGerenciarCategoriaListener(e -> abrirTelaCategorias(categoriaDAO));
        this.view.addExibirTotalFinancasListener(e -> exibirTotalFinancas(financaDAO, usuario));
        this.view.addSairListener(e -> confirmarSaida(usuario));
    }

    private void abrirTelaAdicionarFinanca(FinancaDAO financaDAO, CategoriaDAO categoriaDAO, Usuario usuario) {
        new CadastrarFinancasController(financaDAO, categoriaDAO, usuario);
    }

    private void abrirTelaHistorico(FinancaDAO financaDAO, CategoriaDAO categoriaDAO, Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            view.mostrarMensagem("Erro: Usuário não encontrado.");
            return;
        }

        List<Financas> lista = financaDAO.listarPorUsuario();
        if (lista.isEmpty()) {
            view.mostrarMensagem("Nenhuma finança cadastrada ainda.");
        } else {
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl(HibernateUtil.getSessionFactory());
            new HistoricoFinancasController(financaDAO, categoriaDAO, usuarioDAO, usuario);
        }
    }

    private void abrirTelaCategorias(CategoriaDAO categoriaDAO) {
        CategoriaController categoriaController = new CategoriaController(usuario, HibernateUtil.getSessionFactory());
        new GerenciadorCategoriaController(categoriaController);
    }

    private void exibirTotalFinancas(FinancaDAO financaDAO, Usuario usuario) {
        if (usuario == null) {
            view.mostrarMensagem("Erro: Usuário não encontrado.");
            return;
        }

        double totalReceitas = 0;
        double totalDespesas = 0;

        for (Financas f : financaDAO.listarPorUsuario()) {
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

    private void confirmarSaida(Usuario usuario) {
        int resposta = JOptionPane.showConfirmDialog(
                view.getFrame(),
                "Deseja realmente sair do seu usuário?",
                "Trocar usuário",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            view.dispose();

            LoginScreen novaTelaLogin = new LoginScreen();

            new LoginController(novaTelaLogin, new UsuarioDAOImpl(HibernateUtil.getSessionFactory()), new FinancaDAOImpl(HibernateUtil.getSessionFactory(), usuario));
        }
    }
}
