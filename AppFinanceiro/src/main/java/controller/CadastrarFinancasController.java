package controller;

import model.Categoria;
import view.CadastrarFinancasScreen;

import java.util.Date;
import java.util.List;

public class CadastrarFinancasController {

    private CadastrarFinancasScreen view;
    private FinancasController financasController;
    private CategoriaController categoriaController;

    public CadastrarFinancasController(FinancasController financasController, CategoriaController categoriaController) {
        this.financasController = financasController;
        this.categoriaController = categoriaController;
        this.view = new CadastrarFinancasScreen();

        // Atualiza combo de categorias na tela
        atualizarCategorias();

        view.addCadastrarListener(e -> cadastrarFinancas());
        view.setVisible(true);
    }

    private void atualizarCategorias() {
        List<Categoria> categorias = categoriaController.listarCategorias();
        view.atualizarCategorias(categorias);
    }

    private void cadastrarFinancas() {
        String descricao = view.getDescricao();
        String valorCampo = view.getValor();
        String tipo = view.getTipo();
        Categoria categoria = view.getCategoriaSelecionada();
        Date data = view.getData();

        if (descricao.isEmpty() || valorCampo.isEmpty() || categoria == null || tipo == null) {
            view.mostrarErro("Preencha todos os campos!");
            return;
        }

        try {
            valorCampo = valorCampo.replace(",", ".");
            double valor = Double.parseDouble(valorCampo);

            // Se for despesa, inverte o sinal
            if (tipo.equalsIgnoreCase("Despesa")) {
                valor = -valor;
            }

            financasController.cadastrarFinanca(descricao, valor, categoria, data);
            String valorFormatado = String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", Math.abs(valor));
            String tipoMsg = valor > 0 ? "Receita" : "Despesa";

            view.mostrarMensagem(tipoMsg + " cadastrada com sucesso no valor de R$ " + valorFormatado.replace('.', ',') + "!");
            view.limparCampos();

        } catch (NumberFormatException ex) {
            view.mostrarErro("Valor inválido!");
        } catch (Exception ex) {
            view.mostrarErro("Erro ao cadastrar finança: " + ex.getMessage());
        }
    }
}
