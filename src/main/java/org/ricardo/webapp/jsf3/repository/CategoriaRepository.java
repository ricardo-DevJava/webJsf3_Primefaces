package org.ricardo.webapp.jsf3.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.ricardo.webapp.jsf3.entites.Categoria;

import java.util.List;

public class CategoriaRepository implements CrudRepository<Categoria>{

    @Inject
    private EntityManager em;

    @Override
    public List<Categoria> listar() {
        return em.createQuery("from Categoria", Categoria.class).getResultList();
    }

    @Override
    public Categoria porId(Long id) {
        return em.find(Categoria.class, id);
    }

    @Override
    public void guardar(Categoria categoria) {

        if (categoria.getId() != null && categoria.getId() > 0) {
            em.merge(categoria);
        }else {
            em.persist(categoria);
        }

    }

    @Override
    public void eliminar(Long id) {
        em.remove(id);
    }
}
