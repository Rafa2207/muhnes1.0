/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Frank Martinez
 */
@Entity
@Table(name = "notapreliminar_tb")
@NamedQueries({
    @NamedQuery(name = "NotapreliminarTb.findAll", query = "SELECT n FROM NotapreliminarTb n"),
    @NamedQuery(name = "NotapreliminarTb.findByEIdnotapreliminar", query = "SELECT n FROM NotapreliminarTb n WHERE n.eIdnotapreliminar = :eIdnotapreliminar"),
    @NamedQuery(name = "NotapreliminarTb.findByFFecha", query = "SELECT n FROM NotapreliminarTb n WHERE n.fFecha = :fFecha"),
    @NamedQuery(name = "NotapreliminarTb.findByENumcorrelativo", query = "SELECT n FROM NotapreliminarTb n WHERE n.eNumcorrelativo = :eNumcorrelativo"),
    @NamedQuery(name = "NotapreliminarTb.findByMDescripcion", query = "SELECT n FROM NotapreliminarTb n WHERE n.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "NotapreliminarTb.findByCNombre", query = "SELECT n FROM NotapreliminarTb n WHERE n.cNombre = :cNombre"),
    @NamedQuery(name = "NotapreliminarTb.findByMAcompanantes", query = "SELECT n FROM NotapreliminarTb n WHERE n.mAcompanantes = :mAcompanantes"),
    @NamedQuery(name = "NotapreliminarTb.findByCUbicacion", query = "SELECT n FROM NotapreliminarTb n WHERE n.cUbicacion = :cUbicacion"),
    @NamedQuery(name = "NotapreliminarTb.findByEIdusuario", query = "SELECT n FROM NotapreliminarTb n WHERE n.eIdusuario = :eIdusuario")})
public class NotapreliminarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idnotapreliminar")
    private Integer eIdnotapreliminar;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Column(name = "e_numcorrelativo")
    private Integer eNumcorrelativo;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_acompanantes")
    private String mAcompanantes;
    @Size(max = 100)
    @Column(name = "c_ubicacion")
    private String cUbicacion;
    @Column(name = "e_idusuario")
    private Integer eIdusuario;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;

    public NotapreliminarTb() {
    }

    public NotapreliminarTb(Integer eIdnotapreliminar) {
        this.eIdnotapreliminar = eIdnotapreliminar;
    }

    public Integer getEIdnotapreliminar() {
        return eIdnotapreliminar;
    }

    public void setEIdnotapreliminar(Integer eIdnotapreliminar) {
        this.eIdnotapreliminar = eIdnotapreliminar;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public Integer getENumcorrelativo() {
        return eNumcorrelativo;
    }

    public void setENumcorrelativo(Integer eNumcorrelativo) {
        this.eNumcorrelativo = eNumcorrelativo;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getMAcompanantes() {
        return mAcompanantes;
    }

    public void setMAcompanantes(String mAcompanantes) {
        this.mAcompanantes = mAcompanantes;
    }

    public String getCUbicacion() {
        return cUbicacion;
    }

    public void setCUbicacion(String cUbicacion) {
        this.cUbicacion = cUbicacion;
    }

    public Integer getEIdusuario() {
        return eIdusuario;
    }

    public void setEIdusuario(Integer eIdusuario) {
        this.eIdusuario = eIdusuario;
    }

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdnotapreliminar != null ? eIdnotapreliminar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotapreliminarTb)) {
            return false;
        }
        NotapreliminarTb other = (NotapreliminarTb) object;
        if ((this.eIdnotapreliminar == null && other.eIdnotapreliminar != null) || (this.eIdnotapreliminar != null && !this.eIdnotapreliminar.equals(other.eIdnotapreliminar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.NotapreliminarTb[ eIdnotapreliminar=" + eIdnotapreliminar + " ]";
    }
    
}
