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
 * @author Endy
 */
@Entity
@Table(name = "prorroga_proyecto_tb")
@NamedQueries({
    @NamedQuery(name = "ProrrogaProyectoTb.findAll", query = "SELECT p FROM ProrrogaProyectoTb p"),
    @NamedQuery(name = "ProrrogaProyectoTb.findByEIdprorroga", query = "SELECT p FROM ProrrogaProyectoTb p WHERE p.eIdprorroga = :eIdprorroga"),
    @NamedQuery(name = "ProrrogaProyectoTb.findByENumprorroga", query = "SELECT p FROM ProrrogaProyectoTb p WHERE p.eNumprorroga = :eNumprorroga"),
    @NamedQuery(name = "ProrrogaProyectoTb.findByMJustificacion", query = "SELECT p FROM ProrrogaProyectoTb p WHERE p.mJustificacion = :mJustificacion"),
    @NamedQuery(name = "ProrrogaProyectoTb.findByFFechaInicio", query = "SELECT p FROM ProrrogaProyectoTb p WHERE p.fFechaInicio = :fFechaInicio"),
    @NamedQuery(name = "ProrrogaProyectoTb.findByFFechaFin", query = "SELECT p FROM ProrrogaProyectoTb p WHERE p.fFechaFin = :fFechaFin")})
public class ProrrogaProyectoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idprorroga")
    private Integer eIdprorroga;
    @Column(name = "e_numprorroga")
    private Integer eNumprorroga;
    @Size(max = 2147483647)
    @Column(name = "m_justificacion")
    private String mJustificacion;
    @Column(name = "f_fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fFechaInicio;
    @Column(name = "f_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fFechaFin;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;

    public ProrrogaProyectoTb() {
    }

    public ProrrogaProyectoTb(Integer eIdprorroga) {
        this.eIdprorroga = eIdprorroga;
    }

    public Integer getEIdprorroga() {
        return eIdprorroga;
    }

    public void setEIdprorroga(Integer eIdprorroga) {
        this.eIdprorroga = eIdprorroga;
    }

    public Integer getENumprorroga() {
        return eNumprorroga;
    }

    public void setENumprorroga(Integer eNumprorroga) {
        this.eNumprorroga = eNumprorroga;
    }

    public String getMJustificacion() {
        return mJustificacion;
    }

    public void setMJustificacion(String mJustificacion) {
        this.mJustificacion = mJustificacion;
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

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdprorroga != null ? eIdprorroga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProrrogaProyectoTb)) {
            return false;
        }
        ProrrogaProyectoTb other = (ProrrogaProyectoTb) object;
        if ((this.eIdprorroga == null && other.eIdprorroga != null) || (this.eIdprorroga != null && !this.eIdprorroga.equals(other.eIdprorroga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProrrogaProyectoTb[ eIdprorroga=" + eIdprorroga + " ]";
    }
    
}
