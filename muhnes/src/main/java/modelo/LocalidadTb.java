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
 * @author Endy
 */
@Entity
@Table(name = "localidad_tb")
@NamedQueries({
    @NamedQuery(name = "LocalidadTb.findAll", query = "SELECT l FROM LocalidadTb l"),
    @NamedQuery(name = "LocalidadTb.findByEIdlocalidad", query = "SELECT l FROM LocalidadTb l WHERE l.eIdlocalidad = :eIdlocalidad"),
    @NamedQuery(name = "LocalidadTb.findByCNombre", query = "SELECT l FROM LocalidadTb l WHERE l.cNombre = :cNombre"),
    @NamedQuery(name = "LocalidadTb.findByMDescripcion", query = "SELECT l FROM LocalidadTb l WHERE l.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "LocalidadTb.findByDLatitudDecimal", query = "SELECT l FROM LocalidadTb l WHERE l.dLatitudDecimal = :dLatitudDecimal"),
    @NamedQuery(name = "LocalidadTb.findByDLongitudDecimal", query = "SELECT l FROM LocalidadTb l WHERE l.dLongitudDecimal = :dLongitudDecimal"),
    @NamedQuery(name = "LocalidadTb.findByEAltitudMin", query = "SELECT l FROM LocalidadTb l WHERE l.eAltitudMin = :eAltitudMin"),
    @NamedQuery(name = "LocalidadTb.findByEAltitudMax", query = "SELECT l FROM LocalidadTb l WHERE l.eAltitudMax = :eAltitudMax"),
    @NamedQuery(name = "LocalidadTb.findByELatitudgrados", query = "SELECT l FROM LocalidadTb l WHERE l.eLatitudgrados = :eLatitudgrados"),
    @NamedQuery(name = "LocalidadTb.findByELatitudminutos", query = "SELECT l FROM LocalidadTb l WHERE l.eLatitudminutos = :eLatitudminutos"),
    @NamedQuery(name = "LocalidadTb.findByDLatitudsegundos", query = "SELECT l FROM LocalidadTb l WHERE l.dLatitudsegundos = :dLatitudsegundos"),
    @NamedQuery(name = "LocalidadTb.findByELongitudgrados", query = "SELECT l FROM LocalidadTb l WHERE l.eLongitudgrados = :eLongitudgrados"),
    @NamedQuery(name = "LocalidadTb.findByELongitudminutos", query = "SELECT l FROM LocalidadTb l WHERE l.eLongitudminutos = :eLongitudminutos"),
    @NamedQuery(name = "LocalidadTb.findByDLongitudsegundos", query = "SELECT l FROM LocalidadTb l WHERE l.dLongitudsegundos = :dLongitudsegundos")})
public class LocalidadTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idlocalidad")
    private Integer eIdlocalidad;
    @Size(max = 150)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_latitud_decimal")
    private Double dLatitudDecimal;
    @Column(name = "d_longitud_decimal")
    private Double dLongitudDecimal;
    @Column(name = "e_altitud_min")
    private Integer eAltitudMin;
    @Column(name = "e_altitud_max")
    private Integer eAltitudMax;
    @Column(name = "e_latitudgrados")
    private Integer eLatitudgrados;
    @Column(name = "e_latitudminutos")
    private Integer eLatitudminutos;
    @Column(name = "d_latitudsegundos")
    private Double dLatitudsegundos;
    @Column(name = "e_longitudgrados")
    private Integer eLongitudgrados;
    @Column(name = "e_longitudminutos")
    private Integer eLongitudminutos;
    @Column(name = "d_longitudsegundos")
    private Double dLongitudsegundos;
    @JoinColumn(name = "e_idcanton", referencedColumnName = "e_idcanton")
    @ManyToOne
    private CantonTb eIdcanton;
    @JoinColumn(name = "e_idarea", referencedColumnName = "e_idarea")
    @ManyToOne
    private AreaprotegidaTb eIdarea;
    
    
    

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

    public Integer getELatitudgrados() {
        return eLatitudgrados;
    }

    public void setELatitudgrados(Integer eLatitudgrados) {
        this.eLatitudgrados = eLatitudgrados;
    }

    public Integer getELatitudminutos() {
        return eLatitudminutos;
    }

    public void setELatitudminutos(Integer eLatitudminutos) {
        this.eLatitudminutos = eLatitudminutos;
    }

    public Double getDLatitudsegundos() {
        return dLatitudsegundos;
    }

    public void setDLatitudsegundos(Double dLatitudsegundos) {
        this.dLatitudsegundos = dLatitudsegundos;
    }

    public Integer getELongitudgrados() {
        return eLongitudgrados;
    }

    public void setELongitudgrados(Integer eLongitudgrados) {
        this.eLongitudgrados = eLongitudgrados;
    }

    public Integer getELongitudminutos() {
        return eLongitudminutos;
    }

    public void setELongitudminutos(Integer eLongitudminutos) {
        this.eLongitudminutos = eLongitudminutos;
    }

    public Double getDLongitudsegundos() {
        return dLongitudsegundos;
    }

    public void setDLongitudsegundos(Double dLongitudsegundos) {
        this.dLongitudsegundos = dLongitudsegundos;
    }

    public CantonTb getEIdcanton() {
        return eIdcanton;
    }

    public void setEIdcanton(CantonTb eIdcanton) {
        this.eIdcanton = eIdcanton;
    }

    public AreaprotegidaTb getEIdarea() {
        return eIdarea;
    }

    public void setEIdarea(AreaprotegidaTb eIdarea) {
        this.eIdarea = eIdarea;
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
