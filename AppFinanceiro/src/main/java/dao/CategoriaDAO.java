package dao;

import java.util.List;
import model.Categoria;

public interface CategoriaDAO {
    void salvar(Categoria categoria);
    void atualizar(Categoria categoria);
    void deletar(Categoria categoria);
    Long contarFinancasPorCategoria(Categoria categoria);
    List<Categoria> listarPorUsuario();
}
