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
@Table(name = "donacion_tb")
@NamedQueries({
    @NamedQuery(name = "DonacionTb.findAll", query = "SELECT d FROM DonacionTb d"),
    @NamedQuery(name = "DonacionTb.findByEIddonacion", query = "SELECT d FROM DonacionTb d WHERE d.eIddonacion = :eIddonacion"),
    @NamedQuery(name = "DonacionTb.findByMDescripcion", query = "SELECT d FROM DonacionTb d WHERE d.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "DonacionTb.findByFFecha", query = "SELECT d FROM DonacionTb d WHERE d.fFecha = :fFecha"),
    @NamedQuery(name = "DonacionTb.findByEIdinstitucion", query = "SELECT d FROM DonacionTb d WHERE d.eIdinstitucion = :eIdinstitucion")})
public class DonacionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_iddonacion")
    private Integer eIddonacion;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Column(name = "e_idinstitucion")
    private Integer eIdinstitucion;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "donacionTb")
    private List<EjemplarDonacionTb> ejemplarDonacionTbList;

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

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public Integer getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(Integer eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    public List<EjemplarDonacionTb> getEjemplarDonacionTbList() {
        return ejemplarDonacionTbList;
    }

    public void setEjemplarDonacionTbList(List<EjemplarDonacionTb> ejemplarDonacionTbList) {
        this.ejemplarDonacionTbList = ejemplarDonacionTbList;
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
