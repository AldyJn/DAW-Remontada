package com.crudproductosjstl.crudproductosjstl.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase modelo que representa un Producto
 * Corresponde a la tabla 'producto' en la base de datos
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idProducto;
    private String nombreProducto;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer idCategoria;

    // Atributo adicional para mostrar el nombre de la categoria
    private String nombreCategoria;

    /**
     * Constructor por defecto
     */
    public Producto() {
    }

    /**
     * Constructor completo con ID
     * @param idProducto Identificador unico del producto
     * @param nombreProducto Nombre del producto
     * @param descripcion Descripcion del producto
     * @param precio Precio del producto
     * @param stock Cantidad en inventario
     * @param idCategoria ID de la categoria asociada
     */
    public Producto(Integer idProducto, String nombreProducto, String descripcion,
                    BigDecimal precio, Integer stock, Integer idCategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    /**
     * Constructor sin ID (para crear nuevos productos)
     * @param nombreProducto Nombre del producto
     * @param descripcion Descripcion del producto
     * @param precio Precio del producto
     * @param stock Cantidad en inventario
     * @param idCategoria ID de la categoria asociada
     */
    public Producto(String nombreProducto, String descripcion,
                    BigDecimal precio, Integer stock, Integer idCategoria) {
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    // Getters y Setters

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        return idProducto != null ? idProducto.equals(producto.idProducto) : producto.idProducto == null;
    }

    @Override
    public int hashCode() {
        return idProducto != null ? idProducto.hashCode() : 0;
    }
}
