package controller;

import dao.UsuarioDAO;
import model.Usuario;
import view.RegistroScreen;

public class RegistroController {

    private final RegistroScreen view;
    private final UsuarioDAO usuarioDAO;

    public RegistroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.view = new RegistroScreen();

        this.view.addRegistrarListener(e -> registrarUsuario());
        this.view.addVoltarListener(e -> view.dispose());
    }

    private void registrarUsuario() {
        String nome = view.getNome();
        String login = view.getUsuario();
        String senha = view.getSenha();

        if (!nome.isEmpty() && !login.isEmpty() && !senha.isEmpty()) {
            Usuario usuarioExistente = usuarioDAO.buscarPorLogin(login);
            if (usuarioExistente != null) {
                view.mostrarErro("Este login já está em uso. Tente outro.");
            } else {
                Usuario novo = new Usuario(nome, login, senha);
                usuarioDAO.salvar(novo);
                view.mostrarMensagem("Usuário registrado com sucesso!");
                view.dispose();
            }
        } else {
            view.mostrarErro("Preencha todos os campos!");
        }
    }
}
