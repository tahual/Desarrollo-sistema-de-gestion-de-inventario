/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumgedu.gt.db;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author danyt
 */
@Entity
@Table(name = "ordencompra", catalog = "Inventario", schema = "gestion_inventario")
@NamedQueries({
    @NamedQuery(name = "Ordencompra.findAll", query = "SELECT o FROM Ordencompra o"),
    @NamedQuery(name = "Ordencompra.findByIdordencompra", query = "SELECT o FROM Ordencompra o WHERE o.idordencompra = :idordencompra"),
    @NamedQuery(name = "Ordencompra.findByCantidad", query = "SELECT o FROM Ordencompra o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Ordencompra.findByFechaorden", query = "SELECT o FROM Ordencompra o WHERE o.fechaorden = :fechaorden"),
    @NamedQuery(name = "Ordencompra.findByEstado", query = "SELECT o FROM Ordencompra o WHERE o.estado = :estado")})
public class Ordencompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idordencompra", nullable = false)
    private Integer idordencompra;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "fechaorden", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaorden;
    @Basic(optional = false)
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne
    private Producto idproducto;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    public Ordencompra() {
    }

    public Ordencompra(Integer idordencompra) {
        this.idordencompra = idordencompra;
    }

    public Ordencompra(Integer idordencompra, int cantidad, Date fechaorden, String estado) {
        this.idordencompra = idordencompra;
        this.cantidad = cantidad;
        this.fechaorden = fechaorden;
        this.estado = estado;
    }

    public Integer getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(Integer idordencompra) {
        this.idordencompra = idordencompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Date fechaorden) {
        this.fechaorden = fechaorden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idordencompra != null ? idordencompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencompra)) {
            return false;
        }
        Ordencompra other = (Ordencompra) object;
        if ((this.idordencompra == null && other.idordencompra != null) || (this.idordencompra != null && !this.idordencompra.equals(other.idordencompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "miumgedu.gt.db.Ordencompra[ idordencompra=" + idordencompra + " ]";
    }
    
}
