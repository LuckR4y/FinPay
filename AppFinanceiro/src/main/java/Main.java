import controller.UsuariosController;
import controller.LoginController;
import view.LoginScreen;

public class Main {
    public static void main(String[] args) {
        UsuariosController usuariosController = new UsuariosController();

        LoginScreen loginScreen = new LoginScreen();
        new LoginController(loginScreen, usuariosController);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            usuariosController.getSessionFactory().close();
        }));
    }
}