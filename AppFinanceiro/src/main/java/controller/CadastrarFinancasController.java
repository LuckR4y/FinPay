package controller;

import dao.FinancaDAO;
import dao.CategoriaDAO;
import model.Categoria;
import model.Financas;
import model.Usuario;
import view.CadastrarFinancasScreen;

import java.util.Date;
import java.util.List;

public class CadastrarFinancasController {

    private final CadastrarFinancasScreen view;
    private final FinancaDAO financaDAO;
    private final CategoriaDAO categoriaDAO;
    private final Usuario usuario;

    public CadastrarFinancasController(FinancaDAO financaDAO, CategoriaDAO categoriaDAO, Usuario usuario) {
        this.financaDAO = financaDAO;
        this.categoriaDAO = categoriaDAO;
        this.usuario = usuario;
        this.view = new CadastrarFinancasScreen();

        // Atualiza combo de categorias na tela
        atualizarCategorias();

        view.addCadastrarListener(e -> cadastrarFinancas());
        view.setVisible(true);
    }

    private void atualizarCategorias() {
        List<Categoria> categorias = categoriaDAO.listarPorUsuario();
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

            Financas financa = new Financas(descricao, valor, categoria, data, usuario);
            financaDAO.salvar(financa);

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
