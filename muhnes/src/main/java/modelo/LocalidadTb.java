/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "localidad_tb")
@NamedQueries({
    @NamedQuery(name = "LocalidadTb.findAll", query = "SELECT l FROM LocalidadTb l"),
    @NamedQuery(name = "LocalidadTb.findByEIdlocalidad", query = "SELECT l FROM LocalidadTb l WHERE l.eIdlocalidad = :eIdlocalidad"),
    @NamedQuery(name = "LocalidadTb.findByCNombre", query = "SELECT l FROM LocalidadTb l WHERE l.cNombre = :cNombre"),
    @NamedQuery(name = "LocalidadTb.findByMDescripcion", query = "SELECT l FROM LocalidadTb l WHERE l.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "LocalidadTb.findByCLatitud", query = "SELECT l FROM LocalidadTb l WHERE l.cLatitud = :cLatitud"),
    @NamedQuery(name = "LocalidadTb.findByCLongitud", query = "SELECT l FROM LocalidadTb l WHERE l.cLongitud = :cLongitud"),
    @NamedQuery(name = "LocalidadTb.findByDLatitudDecimal", query = "SELECT l FROM LocalidadTb l WHERE l.dLatitudDecimal = :dLatitudDecimal"),
    @NamedQuery(name = "LocalidadTb.findByDLongitudDecimal", query = "SELECT l FROM LocalidadTb l WHERE l.dLongitudDecimal = :dLongitudDecimal"),
    @NamedQuery(name = "LocalidadTb.findByEAltitudMin", query = "SELECT l FROM LocalidadTb l WHERE l.eAltitudMin = :eAltitudMin"),
    @NamedQuery(name = "LocalidadTb.findByEAltitudMax", query = "SELECT l FROM LocalidadTb l WHERE l.eAltitudMax = :eAltitudMax")})
public class LocalidadTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idlocalidad")
    private Integer eIdlocalidad;
    @Size(max = 70)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 15)
    @Column(name = "c_latitud")
    private String cLatitud;
    @Size(max = 15)
    @Column(name = "c_longitud")
    private String cLongitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_latitud_decimal")
    private Double dLatitudDecimal;
    @Column(name = "d_longitud_decimal")
    private Double dLongitudDecimal;
    @Column(name = "e_altitud_min")
    private Integer eAltitudMin;
    @Column(name = "e_altitud_max")
    private Integer eAltitudMax;
    @JoinColumn(name = "e_idcanton", referencedColumnName = "e_idcanton")
    @ManyToOne
    private CantonTb eIdcanton;

    public LocalidadTb() {
    }

    public LocalidadTb(Integer eIdlocalidad) {
        this.eIdlocalidad = eIdlocalidad;
    }

    public Integer getEIdlocalidad() {
        return eIdlocalidad;
    }

    public void setEIdlocalidad(Integer eIdlocalidad) {
        this.eIdlocalidad = eIdlocalidad;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getCLatitud() {
        return cLatitud;
    }

    public void setCLatitud(String cLatitud) {
        this.cLatitud = cLatitud;
    }

    public String getCLongitud() {
        return cLongitud;
    }

    public void setCLongitud(String cLongitud) {
        this.cLongitud = cLongitud;
    }

    public Double getDLatitudDecimal() {
        return dLatitudDecimal;
    }

    public void setDLatitudDecimal(Double dLatitudDecimal) {
        this.dLatitudDecimal = dLatitudDecimal;
    }

    public Double getDLongitudDecimal() {
        return dLongitudDecimal;
    }

    public void setDLongitudDecimal(Double dLongitudDecimal) {
        this.dLongitudDecimal = dLongitudDecimal;
    }

    public Integer getEAltitudMin() {
        return eAltitudMin;
    }

    public void setEAltitudMin(Integer eAltitudMin) {
        this.eAltitudMin = eAltitudMin;
    }

    public Integer getEAltitudMax() {
        return eAltitudMax;
    }

    public void setEAltitudMax(Integer eAltitudMax) {
        this.eAltitudMax = eAltitudMax;
    }

    public CantonTb getEIdcanton() {
        return eIdcanton;
    }

    public void setEIdcanton(CantonTb eIdcanton) {
        this.eIdcanton = eIdcanton;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdlocalidad != null ? eIdlocalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocalidadTb)) {
            return false;
        }
        LocalidadTb other = (LocalidadTb) object;
        if ((this.eIdlocalidad == null && other.eIdlocalidad != null) || (this.eIdlocalidad != null && !this.eIdlocalidad.equals(other.eIdlocalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.LocalidadTb[ eIdlocalidad=" + eIdlocalidad + " ]";
    }
    
}
