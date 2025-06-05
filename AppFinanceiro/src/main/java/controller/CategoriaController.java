package controller;

import dao.CategoriaDAOImpl;
import model.Categoria;
import model.Usuario;
import org.hibernate.SessionFactory;

import java.awt.Color;
import java.util.List;

public class CategoriaController {

    private final CategoriaDAOImpl dao;
    private final Usuario usuario;

    public CategoriaController(Usuario usuario, SessionFactory factory) {
        this.usuario = usuario;
        this.dao = new CategoriaDAOImpl(factory, usuario);
    }

    public void adicionarCategoria(Categoria categoria) {
        dao.salvar(categoria);
    }

    public List<Categoria> listarCategorias() {
        return dao.listarPorUsuario();
    }

    public void editarCategoria(int index, String nome, Color novaCor) {
        List<Categoria> categorias = listarCategorias();
        if (index >= 0 && index < categorias.size()) {
            Categoria categoria = categorias.get(index);
            categoria.setNome(nome);
            categoria.setColor(novaCor);
            dao.atualizar(categoria);
        } else {
            System.out.println("Índice de categoria inválido.");
        }
    }

    public void deletarCategoria(Categoria categoria) {
        dao.deletar(categoria);
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
