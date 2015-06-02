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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "procesoejemplar_tb")
@NamedQueries({
    @NamedQuery(name = "ProcesoejemplarTb.findAll", query = "SELECT p FROM ProcesoejemplarTb p"),
    @NamedQuery(name = "ProcesoejemplarTb.findByMDescripcion", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ProcesoejemplarTb.findByECantidad", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.eCantidad = :eCantidad"),
    @NamedQuery(name = "ProcesoejemplarTb.findByCTiempo", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.cTiempo = :cTiempo"),
    @NamedQuery(name = "ProcesoejemplarTb.findByCTipo", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.cTipo = :cTipo"),
    @NamedQuery(name = "ProcesoejemplarTb.findByFFecha", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.fFecha = :fFecha"),
    @NamedQuery(name = "ProcesoejemplarTb.findByMIdproceso", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.mIdproceso = :mIdproceso")})
public class ProcesoejemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @Size(max = 20)
    @Column(name = "c_tiempo")
    private String cTiempo;
    @Size(max = 20)
    @Column(name = "c_tipo")
    private String cTipo;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "m_idproceso")
    private String mIdproceso;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;

    public ProcesoejemplarTb() {
    }

    public ProcesoejemplarTb(String mIdproceso) {
        this.mIdproceso = mIdproceso;
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

    public String getCTiempo() {
        return cTiempo;
    }

    public void setCTiempo(String cTiempo) {
        this.cTiempo = cTiempo;
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

    public String getMIdproceso() {
        return mIdproceso;
    }

    public void setMIdproceso(String mIdproceso) {
        this.mIdproceso = mIdproceso;
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
        hash += (mIdproceso != null ? mIdproceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoejemplarTb)) {
            return false;
        }
        ProcesoejemplarTb other = (ProcesoejemplarTb) object;
        if ((this.mIdproceso == null && other.mIdproceso != null) || (this.mIdproceso != null && !this.mIdproceso.equals(other.mIdproceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProcesoejemplarTb[ mIdproceso=" + mIdproceso + " ]";
    }
    
}
