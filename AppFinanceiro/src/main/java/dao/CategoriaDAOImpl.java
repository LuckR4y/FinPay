package dao;

import model.Categoria;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    private final SessionFactory factory;
    private final Usuario usuario;

    public CategoriaDAOImpl(SessionFactory factory, Usuario usuario) {
        this.factory = factory;
        this.usuario = usuario;
    }

    @Override
    public void salvar(Categoria categoria) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            categoria.setUsuario(usuario);
            session.save(categoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Categoria categoria) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(categoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Função para verificar a quantidade de finanças que estão cadastradas em tal categoria. -> RECÉM IMPLEMENTADA
    public Long contarFinancasPorCategoria(Categoria categoria) {
        try (Session session = factory.openSession()) {
            Categoria catGerenciada = session.get(Categoria.class, categoria.getId());
            if (catGerenciada == null) return 0L;

            String hql = "select count(f) from Financas f where f.categoria = :categoria";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("categoria", catGerenciada);
            return query.uniqueResult();
        }
    }

    // Função para deletar (ATUALIZADA) - Verifica se existe ao menos uma finança associada a categoria, seu tiver não permite a exclusão, caso contrário a categoria é excluída do banco.
    @Override
    public void deletar(Categoria categoria) {
        Long totalFinancas = contarFinancasPorCategoria(categoria);

        if (totalFinancas != null && totalFinancas > 0) {
            JOptionPane.showMessageDialog(null,
                    "Não pode excluir essa categoria pois ela já possui finança(s) cadastrada(s).",
                    "Erro ao excluir categoria",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(categoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Categoria> listarPorUsuario() {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Categoria c WHERE c.usuario.id = :usuarioId", Categoria.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }
}
