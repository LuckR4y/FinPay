package controller;

import dao.FinancaDAOImpl;
import dao.UsuarioDAO;
import dao.FinancaDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import util.HibernateUtil;
import view.LoginScreen;

public class LoginController {

    private final LoginScreen view;
    private final UsuarioDAO usuarioDAO;
    private final FinancaDAO financaDAO;

    // A interface OnLoginSuccessListener será usada para passar o usuário após o login
    private OnLoginSuccessListener onLoginSuccessListener;

    public LoginController(LoginScreen view, UsuarioDAO usuarioDAO, FinancaDAO financaDAO) {
        this.view = view;
        this.usuarioDAO = usuarioDAO;
        this.financaDAO = financaDAO;

        this.view.addLoginListener(e -> autenticar());
        this.view.addRegistrarListener(e -> abrirRegistro());
    }

    private void autenticar() {
        String login = view.getUsuario();
        String senha = view.getSenha();

        Usuario usuario = usuarioDAO.buscarPorLogin(login);
        if (usuario == null) {
            view.mostrarErro("Usuário não encontrado!");
            return;
        }

        if (usuario != null && usuario.verificaSenha(senha)) {
            view.mostrarMensagem("Seja bem-vindo, " + usuario.getNome() + "!");

            view.dispose();

            new ApplicationController(usuario, new FinancaDAOImpl(HibernateUtil.getSessionFactory(), usuario));
        } else {
            view.mostrarErro("Usuário ou senha incorretos!");
        }
    }

    private void abrirRegistro() {
        new RegistroController(new UsuarioDAOImpl(HibernateUtil.getSessionFactory()));
    }

    // Método para permitir que o listener seja configurado de fora
    public void setOnLoginSuccessListener(OnLoginSuccessListener listener) {
        this.onLoginSuccessListener = listener;
    }

    // Interface que define o comportamento do listener
    public interface OnLoginSuccessListener {
        void onLoginSuccess(Usuario usuario);
    }
}

