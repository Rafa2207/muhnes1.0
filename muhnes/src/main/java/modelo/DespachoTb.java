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
 * @author Rafa
 */
@Entity
@Table(name = "despacho_tb")
@NamedQueries({
    @NamedQuery(name = "DespachoTb.findAll", query = "SELECT d FROM DespachoTb d"),
    @NamedQuery(name = "DespachoTb.findByEIddespacho", query = "SELECT d FROM DespachoTb d WHERE d.eIddespacho = :eIddespacho"),
    @NamedQuery(name = "DespachoTb.findByMDescripcion", query = "SELECT d FROM DespachoTb d WHERE d.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "DespachoTb.findByFFecha", query = "SELECT d FROM DespachoTb d WHERE d.fFecha = :fFecha")})
public class DespachoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_iddespacho")
    private Integer eIddespacho;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "despachoTb")
    private List<MaterialDespachoTb> materialDespachoTbList;

    public DespachoTb() {
    }

    public DespachoTb(Integer eIddespacho) {
        this.eIddespacho = eIddespacho;
    }

    public Integer getEIddespacho() {
        return eIddespacho;
    }

    public void setEIddespacho(Integer eIddespacho) {
        this.eIddespacho = eIddespacho;
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

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public List<MaterialDespachoTb> getMaterialDespachoTbList() {
        return materialDespachoTbList;
    }

    public void setMaterialDespachoTbList(List<MaterialDespachoTb> materialDespachoTbList) {
        this.materialDespachoTbList = materialDespachoTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIddespacho != null ? eIddespacho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DespachoTb)) {
            return false;
        }
        DespachoTb other = (DespachoTb) object;
        if ((this.eIddespacho == null && other.eIddespacho != null) || (this.eIddespacho != null && !this.eIddespacho.equals(other.eIddespacho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DespachoTb[ eIddespacho=" + eIddespacho + " ]";
    }
    
}
