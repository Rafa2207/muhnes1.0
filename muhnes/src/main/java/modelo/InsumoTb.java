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
@Table(name = "insumo_tb")
@NamedQueries({
    @NamedQuery(name = "InsumoTb.findAll", query = "SELECT i FROM InsumoTb i"),
    @NamedQuery(name = "InsumoTb.findByEIdinsumo", query = "SELECT i FROM InsumoTb i WHERE i.eIdinsumo = :eIdinsumo"),
    @NamedQuery(name = "InsumoTb.findByMNombre", query = "SELECT i FROM InsumoTb i WHERE i.mNombre = :mNombre"),
    @NamedQuery(name = "InsumoTb.findByMTiempo", query = "SELECT i FROM InsumoTb i WHERE i.mTiempo = :mTiempo"),
    @NamedQuery(name = "InsumoTb.findByDGasto", query = "SELECT i FROM InsumoTb i WHERE i.dGasto = :dGasto"),
    @NamedQuery(name = "InsumoTb.findByDCantidad", query = "SELECT i FROM InsumoTb i WHERE i.dCantidad = :dCantidad")})
public class InsumoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idinsumo")
    private Integer eIdinsumo;
    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    @Size(max = 2147483647)
    @Column(name = "m_tiempo")
    private String mTiempo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_gasto")
    private Double dGasto;
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @JoinColumn(name = "e_idpresupuesto", referencedColumnName = "e_idpresupuesto")
    @ManyToOne
    private PresupuestoTb eIdpresupuesto;

    public InsumoTb() {
    }

    public InsumoTb(Integer eIdinsumo) {
        this.eIdinsumo = eIdinsumo;
    }

    public Integer getEIdinsumo() {
        return eIdinsumo;
    }

    public void setEIdinsumo(Integer eIdinsumo) {
        this.eIdinsumo = eIdinsumo;
    }

    public String getMNombre() {
        return mNombre;
    }

    public void setMNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getMTiempo() {
        return mTiempo;
    }

    public void setMTiempo(String mTiempo) {
        this.mTiempo = mTiempo;
    }

    public Double getDGasto() {
        return dGasto;
    }

    public void setDGasto(Double dGasto) {
        this.dGasto = dGasto;
    }

    public Double getDCantidad() {
        return dCantidad;
    }

    public void setDCantidad(Double dCantidad) {
        this.dCantidad = dCantidad;
    }

    public PresupuestoTb getEIdpresupuesto() {
        return eIdpresupuesto;
    }

    public void setEIdpresupuesto(PresupuestoTb eIdpresupuesto) {
        this.eIdpresupuesto = eIdpresupuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdinsumo != null ? eIdinsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsumoTb)) {
            return false;
        }
        InsumoTb other = (InsumoTb) object;
        if ((this.eIdinsumo == null && other.eIdinsumo != null) || (this.eIdinsumo != null && !this.eIdinsumo.equals(other.eIdinsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.InsumoTb[ eIdinsumo=" + eIdinsumo + " ]";
    }
    
}
