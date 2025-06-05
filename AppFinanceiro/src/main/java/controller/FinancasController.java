package controller;

import dao.FinancaDAO;
import model.Financas;
import model.Usuario;
import model.Categoria;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

public class FinancasController {

    private final FinancaDAO dao;
    private final Usuario usuario;

    public FinancasController(Usuario usuario, FinancaDAO dao) {
        this.usuario = usuario;
        this.dao = dao;
    }

    public void cadastrarFinanca(String descricao, double valor, Categoria categoria, Date data) {
        Financas f = new Financas(descricao, valor, categoria, data, usuario);
        dao.salvar(f);
    }

    public List<Financas> listarFinancas() {
        return dao.listarPorUsuario();
    }

    // Métodos para editar, excluir e buscar
    public void atualizarFinanca(Financas financa) {
        dao.atualizar(financa);
        System.out.println("Finança atualizada com sucesso.");
    }

    public void deletarFinanca(Financas financa) {
        dao.deletar(financa);
    }

    public List<Financas> buscarPorPeriodo(Date dataInicial, Date dataFinal) {
        // Ajusta a dataInicial para 00:00:00 e dataFinal para 23:59:59
        Date dataInicio = normalizarDataInicio(dataInicial);
        Date dataFim = normalizarDataFim(dataFinal);
        return dao.buscarPorData(dataInicio, dataFim);
    }

    private Date normalizarDataInicio(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date normalizarDataFim(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public Map<String, Double> calcularResumoFinanceiro(Date dataInicial, Date dataFinal) {
        List<Financas> financas = buscarPorPeriodo(dataInicial, dataFinal);
        double totalReceitas = 0.0;
        double totalDespesas = 0.0;

        for (Financas financa : financas) {
            if (financa.getValor() > 0) {
                totalReceitas += financa.getValor();  // Se for positiva, é uma receita
            } else {
                totalDespesas += financa.getValor();  // Se for negativa, é uma despesa
            }
        }

        Map<String, Double> resumo = new HashMap<>();
        resumo.put("Total de Receitas", totalReceitas);
        resumo.put("Total de Despesas", totalDespesas);
        resumo.put("Saldo Final", totalReceitas + totalDespesas);

        return resumo;
    }
}
