package dao;

import model.Financas;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Date;

public class FinancaDAOImpl implements FinancaDAO {

    private final SessionFactory factory;
    private final Usuario usuario;

    public FinancaDAOImpl(SessionFactory factory, Usuario usuario) {
        this.factory = factory;
        this.usuario = usuario;
    }

    @Override
    public void salvar(Financas financa) {
        financa.setUsuario(usuario);
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(financa);
            transaction.commit();
        }
    }

    @Override
    public void atualizar(Financas financa) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(financa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Financas financa) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(financa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Financas buscarPorId(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Financas.class, id);
        }
    }

    @Override
    public List<Financas> listarPorUsuario() {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Financas f WHERE f.usuario.id = :usuarioId", Financas.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }


    @Override
    public List<Financas> buscarPorData(Date dataInicial, Date dataFinal) {
        try (Session session = factory.openSession()) {
            String hql = "FROM Financas f WHERE f.usuario.id = :usuarioId AND f.data BETWEEN :dataInicio AND :dataFim";
            Query<Financas> query = session.createQuery(hql, Financas.class);

            query.setParameter("usuarioId", usuario.getId());
            query.setParameter("dataInicio", dataInicial);
            query.setParameter("dataFim", dataFinal);
            return query.list();
        }
    }
}
