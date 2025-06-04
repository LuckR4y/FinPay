package controller;

import model.Usuario;
import view.LoginScreen;

public class LoginController {

    private LoginScreen view;
    private UsuariosController usuariosController;

    public LoginController(LoginScreen view, UsuariosController usuariosController) {
        this.view = view;
        this.usuariosController = usuariosController;

        this.view.addLoginListener(e -> autenticar());
        this.view.addRegistrarListener(e -> abrirRegistro());
    }

    private void autenticar() {
        String login = view.getUsuario();
        String senha = view.getSenha();

        if (usuariosController.autenticarUsuario(login, senha)) {
            Usuario usuario = usuariosController.getUsuarioAtual();
            view.mostrarMensagem("Seja bem-vindo, " + usuario.getNome() + "!");
            view.dispose();
            new ApplicationController(usuariosController);
        } else {
            view.mostrarErro("Usuário ou senha incorretos!");
        }
    }
    private void abrirRegistro() {
        new RegistroController(usuariosController);
    }
}
