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
    @NamedQuery(name = "ProcesoejemplarTb.findByEIdproceso", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.eIdproceso = :eIdproceso"),
    @NamedQuery(name = "ProcesoejemplarTb.findByMDescripcion", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ProcesoejemplarTb.findByDCantidad", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.dCantidad = :dCantidad"),
    @NamedQuery(name = "ProcesoejemplarTb.findByCTiempo", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.cTiempo = :cTiempo"),
    @NamedQuery(name = "ProcesoejemplarTb.findByCTipo", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.cTipo = :cTipo"),
    @NamedQuery(name = "ProcesoejemplarTb.findByFFecha", query = "SELECT p FROM ProcesoejemplarTb p WHERE p.fFecha = :fFecha")})
public class ProcesoejemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idproceso")
    private Integer eIdproceso;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @Size(max = 20)
    @Column(name = "c_tiempo")
    private String cTiempo;
    @Size(max = 10)
    @Column(name = "c_tipo")
    private String cTipo;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;

    public ProcesoejemplarTb() {
    }

    public ProcesoejemplarTb(Integer eIdproceso) {
        this.eIdproceso = eIdproceso;
    }

    public Integer getEIdproceso() {
        return eIdproceso;
    }

    public void setEIdproceso(Integer eIdproceso) {
        this.eIdproceso = eIdproceso;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Double getDCantidad() {
        return dCantidad;
    }

    public void setDCantidad(Double dCantidad) {
        this.dCantidad = dCantidad;
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
