package dao;

import java.util.List;
import java.util.Date;
import model.Financas;

public interface FinancaDAO {
    void salvar(Financas financas);
    void atualizar(Financas financas);
    void deletar(Financas financas);
    Financas buscarPorId(Long id);
    List<Financas> listarPorUsuario(Long usuarioId);
    List<Financas> buscarPorData(Date dataInicio, Date dataFim, Long usuarioId);
}
