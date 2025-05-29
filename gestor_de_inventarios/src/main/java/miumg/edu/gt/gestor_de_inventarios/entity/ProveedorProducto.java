/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;




/**
 *
 * @author danyt
 */
@Entity
@Table(name = "proveedor_producto", catalog = "Inventario", schema = "gestion_inventario")
public class ProveedorProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor_producto")
    private Integer idProveedorProducto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    private Producto producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idproveedor", referencedColumnName = "idproveedor")
    private Proveedor proveedor;

    // Opcional: campos adicionales
    @Column(name = "precio_negociado")
    private Double precioNegociado;

    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    // ===================== Constructores =====================

    public ProveedorProducto() {
    }

    public ProveedorProducto(Producto producto, Proveedor proveedor) {
        this.producto = producto;
        this.proveedor = proveedor;
    }

    // ===================== Getters y Setters =====================

    public Integer getIdProveedorProducto() {
        return idProveedorProducto;
    }

    public void setIdProveedorProducto(Integer idProveedorProducto) {
        this.idProveedorProducto = idProveedorProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPrecioNegociado() {
        return precioNegociado;
    }

    public void setPrecioNegociado(Double precioNegociado) {
        this.precioNegociado = precioNegociado;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // ===================== Métodos utilitarios =====================

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedorProducto != null ? idProveedorProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProveedorProducto)) {
            return false;
        }
        ProveedorProducto other = (ProveedorProducto) object;
        return (this.idProveedorProducto != null || other.idProveedorProducto == null) &&
               (this.idProveedorProducto == null || this.idProveedorProducto.equals(other.idProveedorProducto));
    }

    @Override
    public String toString() {
        return "ProveedorProducto[ id=" + idProveedorProducto + " ]";
    }
}