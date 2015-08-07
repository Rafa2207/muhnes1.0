/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "proyecto_tb")
@NamedQueries({
    @NamedQuery(name = "ProyectoTb.findAll", query = "SELECT p FROM ProyectoTb p"),
    @NamedQuery(name = "ProyectoTb.findByEIdproyecto", query = "SELECT p FROM ProyectoTb p WHERE p.eIdproyecto = :eIdproyecto"),
    @NamedQuery(name = "ProyectoTb.findByMDescripcion", query = "SELECT p FROM ProyectoTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ProyectoTb.findByMNombre", query = "SELECT p FROM ProyectoTb p WHERE p.mNombre = :mNombre"),
    @NamedQuery(name = "ProyectoTb.findByFFechaInicio", query = "SELECT p FROM ProyectoTb p WHERE p.fFechaInicio = :fFechaInicio"),
    @NamedQuery(name = "ProyectoTb.findByFFechaFin", query = "SELECT p FROM ProyectoTb p WHERE p.fFechaFin = :fFechaFin"),
    @NamedQuery(name = "ProyectoTb.findByEResponsable", query = "SELECT p FROM ProyectoTb p WHERE p.eResponsable = :eResponsable"),
    @NamedQuery(name = "ProyectoTb.findByEEstado", query = "SELECT p FROM ProyectoTb p WHERE p.eEstado = :eEstado")})
public class ProyectoTb implements Serializable {
    @OneToMany(mappedBy = "eIdproyecto", orphanRemoval = true)
    private List<EjemplarTb> ejemplarTbList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idproyecto")
    private Integer eIdproyecto;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    @Column(name = "f_fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fFechaInicio;
    @Column(name = "f_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fFechaFin;
    @Column(name = "e_responsable")
    private Integer eResponsable;
    @Column(name = "e_estado")
    private Integer eEstado;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<DespachoTb> despachoTbList;
    @OneToMany(mappedBy = "eIdproyecto", orphanRemoval = true, cascade= CascadeType.ALL)
    private List<ActividadTb> actividadTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<ProcesoejemplarTb> procesoejemplarTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<NotapreliminarTb> notapreliminarTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<ProrrogaProyectoTb> prorrogaProyectoTbList;

    public ProyectoTb() {
    }

    public ProyectoTb(Integer eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public Integer getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(Integer eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getMNombre() {
        return mNombre;
    }

    public void setMNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public Date getFFechaInicio() {
        return fFechaInicio;
    }

    public void setFFechaInicio(Date fFechaInicio) {
        this.fFechaInicio = fFechaInicio;
    }

    public Date getFFechaFin() {
        return fFechaFin;
    }

    public void setFFechaFin(Date fFechaFin) {
        this.fFechaFin = fFechaFin;
    }

    public Integer getEResponsable() {
        return eResponsable;
    }

    public void setEResponsable(Integer eResponsable) {
        this.eResponsable = eResponsable;
    }

    public Integer getEEstado() {
        return eEstado;
    }

    public void setEEstado(Integer eEstado) {
        this.eEstado = eEstado;
    }

    public List<DespachoTb> getDespachoTbList() {
        return despachoTbList;
    }

    public void setDespachoTbList(List<DespachoTb> despachoTbList) {
        this.despachoTbList = despachoTbList;
    }

    public List<ActividadTb> getActividadTbList() {
        return actividadTbList;
    }

    public void setActividadTbList(List<ActividadTb> actividadTbList) {
        this.actividadTbList = actividadTbList;
    }

    public List<ProcesoejemplarTb> getProcesoejemplarTbList() {
        return procesoejemplarTbList;
    }

    public void setProcesoejemplarTbList(List<ProcesoejemplarTb> procesoejemplarTbList) {
        this.procesoejemplarTbList = procesoejemplarTbList;
    }

    public List<NotapreliminarTb> getNotapreliminarTbList() {
        return notapreliminarTbList;
    }

    public void setNotapreliminarTbList(List<NotapreliminarTb> notapreliminarTbList) {
        this.notapreliminarTbList = notapreliminarTbList;
    }

    public List<ProrrogaProyectoTb> getProrrogaProyectoTbList() {
        return prorrogaProyectoTbList;
    }

    public void setProrrogaProyectoTbList(List<ProrrogaProyectoTb> prorrogaProyectoTbList) {
        this.prorrogaProyectoTbList = prorrogaProyectoTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdproyecto != null ? eIdproyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectoTb)) {
            return false;
        }
        ProyectoTb other = (ProyectoTb) object;
        if ((this.eIdproyecto == null && other.eIdproyecto != null) || (this.eIdproyecto != null && !this.eIdproyecto.equals(other.eIdproyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProyectoTb[ eIdproyecto=" + eIdproyecto + " ]";
    }

    public List<EjemplarTb> getEjemplarTbList() {
        return ejemplarTbList;
    }

    public void setEjemplarTbList(List<EjemplarTb> ejemplarTbList) {
        this.ejemplarTbList = ejemplarTbList;
    }
    
}
