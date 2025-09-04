package org.ricardo.webapp.jsf3.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.ricardo.webapp.jsf3.entites.Producto;

import java.util.List;

@RequestScoped
public class ProductoRepositoryImpl implements ProductoRepository{

    @Inject
    private EntityManager em;
    @Override
    public List<Producto> listar() {
        return em.createQuery("select p from Producto p left outer join fetch p.categoria", Producto.class).getResultList();
    }

    @Override
    public Producto porId(Long id) {
//        return em.find(Producto.class, id);
        return em.createQuery("select p from Producto p left outer join fetch p.categoria where p.id = :id", Producto.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void guardar(Producto producto) {

        if (producto.getId() != null && producto.getId() > 0) {
            em.merge(producto);
        }else{
            em.persist(producto);
        }
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = porId(id);
        em.remove(producto);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        StringBuilder sql = new StringBuilder("select p from Producto p")
                .append(" left outer join fetch p.categoria")
                .append(" where p.nombre like :nombre");
        String nombreSql = "%" + nombre + "%";
        return em.createQuery(sql.toString(),Producto.class)
                .setParameter("nombre", nombreSql)
                .getResultList();

    }
}
