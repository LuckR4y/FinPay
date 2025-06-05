package controller;

import dao.UsuarioDAO;
import model.Usuario;

// Gerenciar os usuários do sistema
public class UsuariosController {

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioAtual;


    public UsuariosController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarioDAO.salvar(usuario);
    }

    public Usuario buscarUsuario(String login) {
        return usuarioDAO.buscarPorLogin(login);
    }

    public boolean autenticarUsuario(String login, String senha) {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            System.out.println("Login ou senha estão vazios.");
            return false;
        }

        Usuario usuario = buscarUsuario(login);
        if (usuario != null && usuario.verificaSenha(senha)) {
            usuarioAtual = usuario;
            return true;
        }
        return false;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

}
