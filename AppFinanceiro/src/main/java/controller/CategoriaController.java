package controller;

import dao.CategoriaDAOImpl;
import model.Categoria;
import model.Usuario;
import org.hibernate.SessionFactory;

import java.awt.Color;
import java.util.List;

public class CategoriaController {

    private CategoriaDAOImpl dao;
    private Usuario usuario;

    public CategoriaController(Usuario usuario, SessionFactory factory) {
        this.usuario = usuario;
        this.dao = new CategoriaDAOImpl(factory);
    }

    public void adicionarCategoria(String nome, Color cor) {
        Categoria categoria = new Categoria(nome, cor, usuario);
        dao.salvar(categoria);
    }

    public List<Categoria> listarCategorias() {
        return dao.listarPorUsuario(usuario.getId());
    }

    public void editarCategoria(int index, String nome, Color novaCor) {
        List<Categoria> categorias = listarCategorias();
        if (index >= 0 && index < categorias.size()) {
            Categoria c = categorias.get(index);
            c.setNome(nome);
            c.setColor(novaCor);
            dao.atualizar(c);
        }
    }
    public void deletarCategoria(Categoria categoria) {
        dao.deletar(categoria);
    }
}
