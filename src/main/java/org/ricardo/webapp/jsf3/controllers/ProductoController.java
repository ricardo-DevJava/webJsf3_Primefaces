package org.ricardo.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.ricardo.webapp.jsf3.entites.Categoria;
import org.ricardo.webapp.jsf3.entites.Producto;
import org.ricardo.webapp.jsf3.services.ProductoService;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Model
public class ProductoController {

    @Inject
    @Named("faces")
    private FacesContext faces;
    @Inject
    private ProductoService service;

    @Inject
    private ResourceBundle bundle;

    private Producto producto;
    private Long id = 0L;

    private List<Producto> productos;

    private String textoBusqueda;

    @Produces
    @Model
    public String titulo(){
        return bundle.getString("producto.texto.titulo");
    }

    /*@Produces
    @RequestScoped
    @Named("listado")
    public List<Producto> findAll(){
        return Arrays.asList(new Producto("Banano")
                , new Producto("Mandarina")
                , new Producto("Naranja"));
        return service.listar();
    }*/

    /*@Produces
    @Model*/
    public Producto producto(){
        this.producto = new Producto();

        if (id != null && id > 0) {
            service.porId(id).ifPresent(p ->{
                    this.producto = p;
            });
        }

        return this.producto;
    }

    public void guardar(){

        System.out.println(producto);

        if (producto.getId() != null && producto.getId()>0) {

            faces.addMessage("", new FacesMessage(
                    String.format(bundle.getString("producto.mensaje.editar"), producto.getNombre())));

        }else{

            faces.addMessage("", new FacesMessage(
                    String.format(bundle.getString("producto.mensaje.crear"), producto.getNombre())));

        }

        service.guardar(producto);
        this.productos = service.listar();
        this.producto = new Producto();

    }

    public void editar(Long id){
        this.id = id;
        this.producto();
    }

    public void eliminar(Producto producto){

        service.eliminar(producto.getId());
        faces.addMessage("", new FacesMessage(
                String.format(bundle.getString("producto.mensaje.eliminar"), producto.getNombre())));
        this.productos = service.listar();

    }

    @Produces
    @Model
    public List<Categoria> categorias(){
        return service.listarCategorias();
    }

    public void buscar(){
        this.productos = service.buscarPorNombre(this.textoBusqueda);
    }

    public void cerrarDialogo(){
        System.out.println("Cerrando la ventana de dialogo....!");
        this.producto = new Producto();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @PostConstruct
    public void init(){
        this.productos = service.listar();
        this.producto = new Producto();
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
