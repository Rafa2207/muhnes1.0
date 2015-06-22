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
 * @author Rafa
 */
@Entity
@Table(name = "procesoejemplar_tb")
@NamedQueries({
    @NamedQuery(name = "ProcesoejemplarTb.findAll", query = "SELECT p FROM ProcesoejemplarTb p"),
    @NamedQuery(name = "ProcesoejemplarTb.findByMDescripcion", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ProcesoejemplarTb.findByECantidad", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.eCantidad = :eCantidad"),
    @NamedQuery(name = "ProcesoejemplarTb.findByCTipo", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.cTipo = :cTipo"),
    @NamedQuery(name = "ProcesoejemplarTb.findByFFecha", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.fFecha = :fFecha"),
    @NamedQuery(name = "ProcesoejemplarTb.findByMNombre", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.mNombre = :mNombre"),
    @NamedQuery(name = "ProcesoejemplarTb.findByFFechafin", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.fFechafin = :fFechafin"),
    @NamedQuery(name = "ProcesoejemplarTb.findByEIdproceso", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.eIdproceso = :eIdproceso"),
    @NamedQuery(name = "ProcesoejemplarTb.findByERelacion", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.eRelacion = :eRelacion")})
public class ProcesoejemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @Size(max = 20)
    @Column(name = "c_tipo")
    private String cTipo;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    @Column(name = "f_fechafin")
    @Temporal(TemporalType.DATE)
    private Date fFechafin;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idproceso")
    private Integer eIdproceso;
    @Column(name = "e_relacion")
    private Integer eRelacion;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;

    public ProcesoejemplarTb() {
    }

    public ProcesoejemplarTb(Integer eIdproceso) {
        this.eIdproceso = eIdproceso;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public String getMNombre() {
        return mNombre;
    }

    public void setMNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public Date getFFechafin() {
        return fFechafin;
    }

    public void setFFechafin(Date fFechafin) {
        this.fFechafin = fFechafin;
    }

    public Integer getEIdproceso() {
        return eIdproceso;
    }

    public void setEIdproceso(Integer eIdproceso) {
        this.eIdproceso = eIdproceso;
    }

    public Integer getERelacion() {
        return eRelacion;
    }

    public void setERelacion(Integer eRelacion) {
        this.eRelacion = eRelacion;
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
        hash += (eIdproceso != null ? eIdproceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoejemplarTb)) {
            return false;
        }
        ProcesoejemplarTb other = (ProcesoejemplarTb) object;
        if ((this.eIdproceso == null && other.eIdproceso != null) || (this.eIdproceso != null && !this.eIdproceso.equals(other.eIdproceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProcesoejemplarTb[ eIdproceso=" + eIdproceso + " ]";
    }
    
}
