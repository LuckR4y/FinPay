package dao;

import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    public Usuario buscarPorId (Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Usuario.class, id);
        }
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT u FROM Usuario u LEFT JOIN FETCH u.categorias WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .uniqueResult();
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }
}
