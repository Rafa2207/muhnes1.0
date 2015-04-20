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
@Table(name = "donacion_tb")
@NamedQueries({
    @NamedQuery(name = "DonacionTb.findAll", query = "SELECT d FROM DonacionTb d"),
    @NamedQuery(name = "DonacionTb.findByEIddonacion", query = "SELECT d FROM DonacionTb d WHERE d.eIddonacion = :eIddonacion"),
    @NamedQuery(name = "DonacionTb.findByCResponsable", query = "SELECT d FROM DonacionTb d WHERE d.cResponsable = :cResponsable"),
    @NamedQuery(name = "DonacionTb.findByCCalificativo", query = "SELECT d FROM DonacionTb d WHERE d.cCalificativo = :cCalificativo"),
    @NamedQuery(name = "DonacionTb.findByCTipo", query = "SELECT d FROM DonacionTb d WHERE d.cTipo = :cTipo"),
    @NamedQuery(name = "DonacionTb.findByMDescripcion", query = "SELECT d FROM DonacionTb d WHERE d.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "DonacionTb.findByECantduplicados", query = "SELECT d FROM DonacionTb d WHERE d.eCantduplicados = :eCantduplicados"),
    @NamedQuery(name = "DonacionTb.findByFFecha", query = "SELECT d FROM DonacionTb d WHERE d.fFecha = :fFecha")})
public class DonacionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_iddonacion")
    private Integer eIddonacion;
    @Size(max = 150)
    @Column(name = "c_responsable")
    private String cResponsable;
    @Size(max = 80)
    @Column(name = "c_calificativo")
    private String cCalificativo;
    @Size(max = 50)
    @Column(name = "c_tipo")
    private String cTipo;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "e_cantduplicados")
    private Integer eCantduplicados;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idinstitucion", referencedColumnName = "e_idinstitucion")
    @ManyToOne
    private InstitucionTb eIdinstitucion;

    public DonacionTb() {
    }

    public DonacionTb(Integer eIddonacion) {
        this.eIddonacion = eIddonacion;
    }

    public Integer getEIddonacion() {
        return eIddonacion;
    }

    public void setEIddonacion(Integer eIddonacion) {
        this.eIddonacion = eIddonacion;
    }

    public String getCResponsable() {
        return cResponsable;
    }

    public void setCResponsable(String cResponsable) {
        this.cResponsable = cResponsable;
    }

    public String getCCalificativo() {
        return cCalificativo;
    }

    public void setCCalificativo(String cCalificativo) {
        this.cCalificativo = cCalificativo;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Integer getECantduplicados() {
        return eCantduplicados;
    }

    public void setECantduplicados(Integer eCantduplicados) {
        this.eCantduplicados = eCantduplicados;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public InstitucionTb getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(InstitucionTb eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIddonacion != null ? eIddonacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DonacionTb)) {
            return false;
        }
        DonacionTb other = (DonacionTb) object;
        if ((this.eIddonacion == null && other.eIddonacion != null) || (this.eIddonacion != null && !this.eIddonacion.equals(other.eIddonacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DonacionTb[ eIddonacion=" + eIddonacion + " ]";
    }
    
}
