package controller;

import model.Usuario;
import view.RegistroScreen;

public class RegistroController {

    private RegistroScreen view;
    private UsuariosController usuariosController;

    public RegistroController(UsuariosController usuariosController) {
        this.usuariosController = usuariosController;
        this.view = new RegistroScreen();

        this.view.addRegistrarListener(e -> registrarUsuario());
        this.view.addVoltarListener(e -> view.dispose());
    }

    private void registrarUsuario() {
        String nome = view.getNome();
        String login = view.getUsuario();
        String senha = view.getSenha();

        if (!nome.isEmpty() && !login.isEmpty() && !senha.isEmpty()) {
            Usuario novo = new Usuario(nome, login, senha);
            usuariosController.adicionarUsuario(novo);
            view.mostrarMensagem("Usuário registrado com sucesso!");
            view.dispose();
        } else {
            view.mostrarErro("Preencha todos os campos!");
        }
    }
}
