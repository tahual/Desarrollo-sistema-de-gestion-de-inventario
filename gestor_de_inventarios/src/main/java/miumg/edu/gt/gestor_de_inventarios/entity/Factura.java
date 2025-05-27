/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.entity;

import miumg.edu.gt.gestor_de_inventarios.entity.Producto;
import miumg.edu.gt.gestor_de_inventarios.entity.Venta;
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
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author danyt
 */
@Entity
@Table(name = "factura", catalog = "Inventario", schema = "gestion_inventario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numerofactura"})})
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdfactura", query = "SELECT f FROM Factura f WHERE f.idfactura = :idfactura"),
    @NamedQuery(name = "Factura.findByNumerofactura", query = "SELECT f FROM Factura f WHERE f.numerofactura = :numerofactura"),
    @NamedQuery(name = "Factura.findByFechaemision", query = "SELECT f FROM Factura f WHERE f.fechaemision = :fechaemision"),
    @NamedQuery(name = "Factura.findByMontototal", query = "SELECT f FROM Factura f WHERE f.montototal = :montototal")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura", nullable = false)
    private Integer idfactura;
    @Basic(optional = false)
    @Column(name = "numerofactura", nullable = false, length = 50)
    private String numerofactura;
    @Basic(optional = false)
    @Column(name = "fechaemision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaemision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "montototal", nullable = false, precision = 10, scale = 2)
    private BigDecimal montototal;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto", nullable = false)
    @ManyToOne(optional = false)
    private Producto idproducto;
    @JoinColumn(name = "idventa", referencedColumnName = "idventa", nullable = false)
    @ManyToOne(optional = false)
    private Venta idventa;

    public Factura() {
    }

    public Factura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Factura(Integer idfactura, String numerofactura, Date fechaemision, BigDecimal montototal) {
        this.idfactura = idfactura;
        this.numerofactura = numerofactura;
        this.fechaemision = fechaemision;
        this.montototal = montototal;
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public BigDecimal getMontototal() {
        return montototal;
    }

    public void setMontototal(BigDecimal montototal) {
        this.montototal = montototal;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Venta getIdventa() {
        return idventa;
    }

    public void setIdventa(Venta idventa) {
        this.idventa = idventa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfactura != null ? idfactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idfactura == null && other.idfactura != null) || (this.idfactura != null && !this.idfactura.equals(other.idfactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "miumgedu.gt.db.Factura[ idfactura=" + idfactura + " ]";
    }
    
}
