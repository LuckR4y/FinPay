import controller.ApplicationController;
import controller.LoginController;
import view.LoginScreen;
import util.HibernateUtil;
import dao.UsuarioDAOImpl;
import dao.FinancaDAOImpl;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl(sessionFactory);
        LoginScreen loginScreen = new LoginScreen();
        LoginController loginController = new LoginController(loginScreen, usuarioDAO, null);  // Passa `null` inicialmente para o FinancaDAO.
        loginController.setOnLoginSuccessListener(usuario -> {
            FinancaDAOImpl financaDAO = new FinancaDAOImpl(sessionFactory, usuario);

            new ApplicationController(usuario, financaDAO);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sessionFactory.close();
        }));
    }
}
