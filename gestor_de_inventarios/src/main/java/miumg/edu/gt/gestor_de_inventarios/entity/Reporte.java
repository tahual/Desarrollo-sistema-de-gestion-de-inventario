/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.entity;

import miumg.edu.gt.gestor_de_inventarios.entity.Usuario;
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
@Table(name = "reporte", catalog = "Inventario", schema = "gestion_inventario")
@NamedQueries({
    @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r"),
    @NamedQuery(name = "Reporte.findByIdreporte", query = "SELECT r FROM Reporte r WHERE r.idreporte = :idreporte"),
    @NamedQuery(name = "Reporte.findByTiporeporte", query = "SELECT r FROM Reporte r WHERE r.tiporeporte = :tiporeporte"),
    @NamedQuery(name = "Reporte.findByContenido", query = "SELECT r FROM Reporte r WHERE r.contenido = :contenido"),
    @NamedQuery(name = "Reporte.findByFechageneracion", query = "SELECT r FROM Reporte r WHERE r.fechageneracion = :fechageneracion")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreporte", nullable = false)
    private Integer idreporte;
    @Basic(optional = false)
    @Column(name = "tiporeporte", nullable = false, length = 100)
    private String tiporeporte;
    @Basic(optional = false)
    @Column(name = "contenido", nullable = false, length = 2147483647)
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fechageneracion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechageneracion;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    public Reporte() {
    }

    public Reporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public Reporte(Integer idreporte, String tiporeporte, String contenido, Date fechageneracion) {
        this.idreporte = idreporte;
        this.tiporeporte = tiporeporte;
        this.contenido = contenido;
        this.fechageneracion = fechageneracion;
    }

    public Integer getIdreporte() {
        return idreporte;
    }

    public void setIdreporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public String getTiporeporte() {
        return tiporeporte;
    }

    public void setTiporeporte(String tiporeporte) {
        this.tiporeporte = tiporeporte;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechageneracion() {
        return fechageneracion;
    }

    public void setFechageneracion(Date fechageneracion) {
        this.fechageneracion = fechageneracion;
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
        hash += (idreporte != null ? idreporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.idreporte == null && other.idreporte != null) || (this.idreporte != null && !this.idreporte.equals(other.idreporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "miumgedu.gt.db.Reporte[ idreporte=" + idreporte + " ]";
    }
    
}
