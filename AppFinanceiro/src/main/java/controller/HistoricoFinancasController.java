package controller;

import model.Categoria;
import model.Financas;
import view.HistoricoFinancasScreen;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoFinancasController {

    private final HistoricoFinancasScreen view;
    private final FinancasController financasController;
    private final CategoriaController categoriaController;
    private List<Financas> financasList;

    public HistoricoFinancasController(FinancasController financasController, CategoriaController categoriaController) {
        this.financasController = financasController;
        this.categoriaController = categoriaController;
        this.financasList = financasController.listarFinancas();

        this.view = new HistoricoFinancasScreen();
        inicializarView();
        view.setVisible(true);
    }

    private void inicializarView() {
        List<String> categorias = new ArrayList<>();
        categorias.add("Todas as Categorias");
        categorias.addAll(categoriaController.listarCategorias().stream().map(Categoria::getNome).toList());

        view.setComboCategorias(categorias);
        view.setComboTipos(List.of("Todos", "Receita", "Despesa"));

        atualizarLista(financasList);

        view.addFiltrarListener(e -> filtrar());
        view.addEditarListener(e -> editar());
        view.addExcluirListener(e -> excluir());
    }

    private void atualizarLista(List<Financas> lista) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> linhas = new ArrayList<>();
        List<Color> cores = new ArrayList<>();

        for (Financas f : lista) {
            linhas.add(String.format("Data: %s | Valor: R$ %.2f | Categoria: %s | Descrição: %s",
                    sdf.format(f.getData()),
                    f.getValor(),
                    f.getCategoria().getNome(),
                    f.getDescricao()));
            cores.add(f.getCategoria().getColor());
        }

        view.setListaFinancas(linhas);
        view.setCoresCategorias(cores);
    }

    // Função para filtrar - OPÇÃO DE FILTRAR POR DATA ATUALIZADA!
    private void filtrar() {
        try {
            // Filtro por data - ATUALIZADO
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Não aceita datas q nn existem

            Date dataInicial = view.getDataInicialFiltro().isEmpty()
                    ? new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime()
                    : sdf.parse(view.getDataInicialFiltro());

            Date dataFinal = view.getDataFinalFiltro().isEmpty()
                    ? new GregorianCalendar(2100, Calendar.DECEMBER, 31).getTime()
                    : sdf.parse(view.getDataFinalFiltro());

            List<Financas> listaFiltrada = financasController.buscarPorPeriodo(dataInicial, dataFinal);

            // Filtro por descrição
            String descricaoFiltro = view.getDescricaoFiltro().trim().toLowerCase();
            if (!descricaoFiltro.isEmpty()) {
                listaFiltrada = listaFiltrada.stream()
                        .filter(f -> f.getDescricao().toLowerCase().contains(descricaoFiltro))
                        .collect(Collectors.toList());
            }

            // Filtro por categoria
            String categoria = view.getCategoriaSelecionada();
            if (!categoria.equals("Todas as Categorias")) {
                listaFiltrada = listaFiltrada.stream()
                        .filter(f -> f.getCategoria().getNome().equals(categoria))
                        .collect(Collectors.toList());
            }

            // Filtro por tipo de finança
            String tipo = view.getTipoSelecionado();
            if (tipo.equals("Receita")) {
                listaFiltrada = listaFiltrada.stream().filter(f -> f.getValor() > 0).collect(Collectors.toList());
            } else if (tipo.equals("Despesa")) {
                listaFiltrada = listaFiltrada.stream().filter(f -> f.getValor() < 0).collect(Collectors.toList());
            }

            this.financasList = listaFiltrada;
            atualizarLista(listaFiltrada);
            exibirTotais(listaFiltrada);

        } catch (ParseException pe) {
            view.mostrarMensagem("Formato de data inválido! Use DD/MM/AAAA.");
        } catch (Exception ex) {
            view.mostrarMensagem("Erro ao aplicar filtros: " + ex.getMessage());
        }
    }

    private void editar() {
        int index = view.getItemSelecionadoIndex();
        if (index < 0 || index >= financasList.size()) return;

        Financas f = financasList.get(index);

        JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField txtDescricao = new JTextField(f.getDescricao());
        JTextField txtValor = new JTextField(String.valueOf(f.getValor()));
        JTextField txtCategoria = new JTextField(f.getCategoria().getNome());
        JTextField txtData = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(f.getData()));

        painel.add(new JLabel("Descrição:"));
        painel.add(txtDescricao);
        painel.add(new JLabel("Valor:"));
        painel.add(txtValor);
        painel.add(new JLabel("Categoria:"));
        painel.add(txtCategoria);
        painel.add(new JLabel("Data:"));
        painel.add(txtData);

        int resultado = JOptionPane.showConfirmDialog(view.getFrame(), painel, "Editar Finança", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                f.setDescricao(txtDescricao.getText().trim());
                f.setValor(Double.parseDouble(txtValor.getText().replace(",", ".")));
                f.getCategoria().setNome(txtCategoria.getText().trim());
                f.setData(new SimpleDateFormat("dd/MM/yyyy").parse(txtData.getText().trim()));

                financasController.atualizarFinanca(f);
                atualizarLista(financasList);
            } catch (ParseException pe) {
                view.mostrarMensagem("Formato de data inválido! Use DD/MM/AAAA.");
            } catch (NumberFormatException nfe) {
                view.mostrarMensagem("Valor inválido! Digite um número válido para o valor.");
            } catch (Exception e) {
                view.mostrarMensagem("Erro ao editar: " + e.getMessage());
            }
        }
    }

    private void excluir() {
        int index = view.getItemSelecionadoIndex();
        if (index < 0 || index >= financasList.size()) return;

        int confirm = JOptionPane.showConfirmDialog(view.getFrame(), "Tem certeza que deseja excluir?", "Remover", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Financas f = financasList.get(index);
            financasController.deletarFinanca(f);
            financasList.remove(index);
            atualizarLista(financasList);
        }
    }

    private void exibirTotais(List<Financas> lista) {
        double totalReceitas = 0, totalDespesas = 0;

        for (Financas f : lista) {
            if (f.getValor() > 0) totalReceitas += f.getValor();
            else totalDespesas += f.getValor();
        }

        double saldo = totalReceitas + totalDespesas;
        String mensagem = String.format(
                "Total no Período:\nReceitas: R$ %.2f\nDespesas: R$ %.2f\nSaldo: R$ %.2f\n%s",
                totalReceitas,
                totalDespesas,
                saldo,
                saldo >= 0 ? "Saldo Positivo! =D" : "Saldo Negativo! :("
        );
        view.mostrarMensagem(mensagem);
    }
}
