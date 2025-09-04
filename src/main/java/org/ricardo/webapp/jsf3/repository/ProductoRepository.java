package org.ricardo.webapp.jsf3.repository;

import org.ricardo.webapp.jsf3.entites.Producto;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto>{
    List<Producto> buscarPorNombre(String nombre);
}
