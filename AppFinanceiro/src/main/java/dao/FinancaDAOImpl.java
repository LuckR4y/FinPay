package dao;

import model.Financas;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Date;

public class FinancaDAOImpl implements FinancaDAO {

    private SessionFactory factory;

    public FinancaDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public void salvar(Financas financa) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(financa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

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

    public Financas buscarPorId(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Financas.class, id);
        }
    }

    public List<Financas> listarPorUsuario(Long usuarioId) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Financas f WHERE f.usuario.id = :uid", Financas.class)
                    .setParameter("uid", usuarioId)
                    .list();
        }
    }

    // TESTANDO
    public List<Financas> buscarPorData(Date dataInicial, Date dataFinal, Long usuarioId) {
        try (Session session = factory.openSession()) {
            String hql = "FROM Financas f WHERE f.usuario.id = :usuarioId AND f.data BETWEEN :dataInicio AND :dataFim";
            Query<Financas> query = session.createQuery(hql, Financas.class);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("dataInicio", dataInicial);
            query.setParameter("dataFim", dataFinal);
            return query.list();
        }
    }
}
