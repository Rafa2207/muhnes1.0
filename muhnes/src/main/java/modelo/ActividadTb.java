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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "actividad_tb")
@NamedQueries({
    @NamedQuery(name = "ActividadTb.findAll", query = "SELECT a FROM ActividadTb a"),
    @NamedQuery(name = "ActividadTb.findByEIdactividad", query = "SELECT a FROM ActividadTb a WHERE a.eIdactividad = :eIdactividad"),
    @NamedQuery(name = "ActividadTb.findByFFecha", query = "SELECT a FROM ActividadTb a WHERE a.fFecha = :fFecha"),
    @NamedQuery(name = "ActividadTb.findByMDescripcion", query = "SELECT a FROM ActividadTb a WHERE a.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ActividadTb.findByMNombre", query = "SELECT a FROM ActividadTb a WHERE a.mNombre = :mNombre"),
    @NamedQuery(name = "ActividadTb.findByFFechafin", query = "SELECT a FROM ActividadTb a WHERE a.fFechafin = :fFechafin"),
    @NamedQuery(name = "ActividadTb.findByCTipo", query = "SELECT a FROM ActividadTb a WHERE a.cTipo = :cTipo"),
    @NamedQuery(name = "ActividadTb.findByDTotal", query = "SELECT a FROM ActividadTb a WHERE a.dTotal = :dTotal"),
    @NamedQuery(name = "ActividadTb.findByFFechaInicioReal", query = "SELECT a FROM ActividadTb a WHERE a.fFechaInicioReal = :fFechaInicioReal"),
    @NamedQuery(name = "ActividadTb.findByFFechaFinReal", query = "SELECT a FROM ActividadTb a WHERE a.fFechaFinReal = :fFechaFinReal"),
    @NamedQuery(name = "ActividadTb.findByMJustificacion", query = "SELECT a FROM ActividadTb a WHERE a.mJustificacion = :mJustificacion"),
    @NamedQuery(name = "ActividadTb.findByDGastoAdicional", query = "SELECT a FROM ActividadTb a WHERE a.dGastoAdicional = :dGastoAdicional")})
public class ActividadTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idactividad")
    private Integer eIdactividad;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    @Column(name = "f_fechafin")
    @Temporal(TemporalType.DATE)
    private Date fFechafin;
    @Size(max = 20)
    @Column(name = "c_tipo")
    private String cTipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_total")
    private Double dTotal;
    @Column(name = "f_fecha_inicio_real")
    @Temporal(TemporalType.DATE)
    private Date fFechaInicioReal;
    @Column(name = "f_fecha_fin_real")
    @Temporal(TemporalType.DATE)
    private Date fFechaFinReal;
    @Size(max = 2147483647)
    @Column(name = "m_justificacion")
    private String mJustificacion;
    @Column(name = "d_gasto_adicional")
    private Double dGastoAdicional;
    @Column(name = "e_estado")
    private Integer eEstado;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;
    @OneToMany(mappedBy = "eIdactividad", orphanRemoval = true, cascade= CascadeType.ALL)
    private List<InsumoTb> insumoTbList;

    public ActividadTb() {
    }

    public ActividadTb(Integer eIdactividad) {
        this.eIdactividad = eIdactividad;
    }

    public Integer getEIdactividad() {
        return eIdactividad;
    }

    public void setEIdactividad(Integer eIdactividad) {
        this.eIdactividad = eIdactividad;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
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

    public Date getFFechafin() {
        return fFechafin;
    }

    public void setFFechafin(Date fFechafin) {
        this.fFechafin = fFechafin;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public Double getDTotal() {
        return dTotal;
    }

    public void setDTotal(Double dTotal) {
        this.dTotal = dTotal;
    }

    public Date getFFechaInicioReal() {
        return fFechaInicioReal;
    }

    public void setFFechaInicioReal(Date fFechaInicioReal) {
        this.fFechaInicioReal = fFechaInicioReal;
    }

    public Date getFFechaFinReal() {
        return fFechaFinReal;
    }

    public void setFFechaFinReal(Date fFechaFinReal) {
        this.fFechaFinReal = fFechaFinReal;
    }

    public String getMJustificacion() {
        return mJustificacion;
    }

    public void setMJustificacion(String mJustificacion) {
        this.mJustificacion = mJustificacion;
    }

    public Double getDGastoAdicional() {
        return dGastoAdicional;
    }

    public void setDGastoAdicional(Double dGastoAdicional) {
        this.dGastoAdicional = dGastoAdicional;
    }

    public Integer getEEstado() {
        return eEstado;
    }

    public void setEEstado(Integer eEstado) {
        this.eEstado = eEstado;
    }
    
    

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public List<InsumoTb> getInsumoTbList() {
        return insumoTbList;
    }

    public void setInsumoTbList(List<InsumoTb> insumoTbList) {
        this.insumoTbList = insumoTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdactividad != null ? eIdactividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadTb)) {
            return false;
        }
        ActividadTb other = (ActividadTb) object;
        if ((this.eIdactividad == null && other.eIdactividad != null) || (this.eIdactividad != null && !this.eIdactividad.equals(other.eIdactividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ActividadTb[ eIdactividad=" + eIdactividad + " ]";
    }
    
}
