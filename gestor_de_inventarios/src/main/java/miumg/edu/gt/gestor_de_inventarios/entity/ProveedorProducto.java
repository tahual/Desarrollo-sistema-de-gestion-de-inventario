/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;



/**
 *
 * @author danyt
 */
@Entity
@IdClass(ProveedorProducto.ProveedorProductoId.class)
public class ProveedorProducto implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @Id
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    // Getters y Setters
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Clase ID compuesta
    public static class ProveedorProductoId implements Serializable {
        private int proveedor;
        private int producto;

        public ProveedorProductoId() {
        }

        public ProveedorProductoId(int proveedor, int producto) {
            this.proveedor = proveedor;
            this.producto = producto;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProveedorProductoId)) return false;
            ProveedorProductoId that = (ProveedorProductoId) o;
            return proveedor == that.proveedor &&
                   producto == that.producto;
        }

        @Override
        public int hashCode() {
            return Objects.hash(proveedor, producto);
        }
    }
}
