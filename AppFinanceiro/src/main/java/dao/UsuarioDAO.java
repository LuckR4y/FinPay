package dao;

import model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void salvar(Usuario usuario);
    void atualizar(Usuario usuario);
    void deletar(Usuario usuario);
    Usuario buscarPorId(Long id);
    Usuario buscarPorLogin(String login);
    List<Usuario> listarTodos();
}
