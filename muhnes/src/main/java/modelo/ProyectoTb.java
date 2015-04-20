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
    @NamedQuery(name = "ProyectoTb.findByCNombre", query = "SELECT p FROM ProyectoTb p WHERE p.cNombre = :cNombre"),
    @NamedQuery(name = "ProyectoTb.findByCResponsable", query = "SELECT p FROM ProyectoTb p WHERE p.cResponsable = :cResponsable"),
    @NamedQuery(name = "ProyectoTb.findByFFechaInicio", query = "SELECT p FROM ProyectoTb p WHERE p.fFechaInicio = :fFechaInicio"),
    @NamedQuery(name = "ProyectoTb.findByFFechaFin", query = "SELECT p FROM ProyectoTb p WHERE p.fFechaFin = :fFechaFin")})
public class ProyectoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idproyecto")
    private Integer eIdproyecto;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 50)
    @Column(name = "c_responsable")
    private String cResponsable;
    @Column(name = "f_fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fFechaInicio;
    @Column(name = "f_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fFechaFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eIdproyecto")
    private List<PresupuestoTb> presupuestoTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<ActividadTb> actividadTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<EjemplarTb> ejemplarTbList;
    @OneToMany(mappedBy = "eIdproyecto")
    private List<ProcesoejemplarTb> procesoejemplarTbList;

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

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCResponsable() {
        return cResponsable;
    }

    public void setCResponsable(String cResponsable) {
        this.cResponsable = cResponsable;
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

    public List<PresupuestoTb> getPresupuestoTbList() {
        return presupuestoTbList;
    }

    public void setPresupuestoTbList(List<PresupuestoTb> presupuestoTbList) {
        this.presupuestoTbList = presupuestoTbList;
    }

    public List<ActividadTb> getActividadTbList() {
        return actividadTbList;
    }

    public void setActividadTbList(List<ActividadTb> actividadTbList) {
        this.actividadTbList = actividadTbList;
    }

    public List<EjemplarTb> getEjemplarTbList() {
        return ejemplarTbList;
    }

    public void setEjemplarTbList(List<EjemplarTb> ejemplarTbList) {
        this.ejemplarTbList = ejemplarTbList;
    }

    public List<ProcesoejemplarTb> getProcesoejemplarTbList() {
        return procesoejemplarTbList;
    }

    public void setProcesoejemplarTbList(List<ProcesoejemplarTb> procesoejemplarTbList) {
        this.procesoejemplarTbList = procesoejemplarTbList;
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
    
}
