package controller;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Gerenciar os usuários do sistema
public class UsuariosController {

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioAtual;

    private static final SessionFactory factory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Usuario.class)
                    .addAnnotatedClass(model.Categoria.class)
                    .addAnnotatedClass(model.Financas.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Falha ao criar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public UsuariosController() {
        this.usuarioDAO = new UsuarioDAOImpl(factory);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarioDAO.salvar(usuario);
    }

    public Usuario buscarUsuario(String login) {
        return usuarioDAO.buscarPorLogin(login);
    }

    public boolean autenticarUsuario(String login, String senha) {
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

    public SessionFactory getSessionFactory() {
        return factory;
    }
}
