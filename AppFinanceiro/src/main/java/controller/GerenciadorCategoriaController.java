package controller;

import model.Categoria;
import view.CategoriaScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GerenciadorCategoriaController {

    private CategoriaScreen view;
    private CategoriaController categoriaController;

    public GerenciadorCategoriaController(CategoriaController categoriaController) {
        this.categoriaController = categoriaController;
        this.view = new CategoriaScreen();

        view.addAdicionarListener(e -> adicionarCategoria());
        view.addRemoverListener(e -> removerCategoria());
        view.addEditarListener(e -> editarCategoria());
        view.addSelecionarCorListener(e -> selecionarCor());

        atualizarListaCategorias();

        view.setVisible(true);
    }

    private void adicionarCategoria() {
        String nome = view.getNomeCategoria();
        if (nome.isEmpty()) {
            view.mostrarErro("Digite um nome para a categoria.");
            return;
        }

        categoriaController.adicionarCategoria(nome, view.getCorSelecionada());
        view.limparCampoNome();
        atualizarListaCategorias();
    }

    private void removerCategoria() {
        Categoria selecionada = view.getCategoriaSelecionada();
        if (selecionada == null) {
            view.mostrarErro("Selecione uma categoria para remover.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(view,
                "Deseja realmente remover a categoria " + selecionada.getNome() + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            categoriaController.deletarCategoria(selecionada);
            atualizarListaCategorias();
        }
    }

    private void editarCategoria() {
        Categoria selecionada = view.getCategoriaSelecionada();
        int index = view.getCategoriaSelecionadaIndex();

        if (selecionada == null) {
            view.mostrarErro("Selecione uma categoria para editar!");
            return;
        }

        JTextField campoNome = new JTextField(selecionada.getNome());
        JButton btnNovaCor = new JButton("Escolher cor");
        final Color[] novaCor = {selecionada.getColor()};

        btnNovaCor.addActionListener(e -> {
            Color escolhida = JColorChooser.showDialog(view, "Escolha uma nova cor!", novaCor[0]);
            if (escolhida != null) {
                novaCor[0] = escolhida;
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Nome:"));
        panel.add(campoNome);
        panel.add(btnNovaCor);

        int resultado = JOptionPane.showConfirmDialog(view, panel, "Editar Categoria", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String novoNome = campoNome.getText().trim();
            if (novoNome.isEmpty()) {
                view.mostrarErro("Digite um nome válido!");
                return;
            }

            categoriaController.editarCategoria(index, novoNome, novaCor[0]);
            atualizarListaCategorias();
        }
    }

    private void selecionarCor() {
        Color corEscolhida = JColorChooser.showDialog(view, "Escolha a cor da Categoria!", view.getCorSelecionada());
        if (corEscolhida != null) {
            view.setCorSelecionada(corEscolhida);
        }
    }

    private void atualizarListaCategorias() {
        List<Categoria> categorias = categoriaController.listarCategorias();
        view.atualizarListaCategorias(categorias);
    }
}
