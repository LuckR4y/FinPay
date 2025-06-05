package dao;

import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private final SessionFactory factory;

    public UsuarioDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void salvar(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(usuario);
            session.getTransaction().commit();
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deletar(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Usuario.class, id);
        }
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            System.out.println("Login vazio ou nulo!");
            return null;
        }

        try (Session session = factory.openSession()) {
            String hql = "FROM Usuario u WHERE u.login = :login";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("login", login);

            System.out.println("Consultando usuário com login: " + login);

            Usuario usuario = query.uniqueResult();
            if (usuario != null) {
                System.out.println("Usuário encontrado: " + usuario.getNome());
            } else {
                System.out.println("Nenhum usuário encontrado com o login: " + login);
            }

            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }
}
