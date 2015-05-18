/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "presupuesto_tb")
@NamedQueries({
    @NamedQuery(name = "PresupuestoTb.findAll", query = "SELECT p FROM PresupuestoTb p"),
    @NamedQuery(name = "PresupuestoTb.findByEIdpresupuesto", query = "SELECT p FROM PresupuestoTb p WHERE p.eIdpresupuesto = :eIdpresupuesto"),
    @NamedQuery(name = "PresupuestoTb.findByCTipo", query = "SELECT p FROM PresupuestoTb p WHERE p.cTipo = :cTipo"),
    @NamedQuery(name = "PresupuestoTb.findByMNombre", query = "SELECT p FROM PresupuestoTb p WHERE p.mNombre = :mNombre"),
    @NamedQuery(name = "PresupuestoTb.findByMDescripcion", query = "SELECT p FROM PresupuestoTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "PresupuestoTb.findByDTotal", query = "SELECT p FROM PresupuestoTb p WHERE p.dTotal = :dTotal")})
public class PresupuestoTb implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idpresupuesto")
    private Integer eIdpresupuesto;
    @Size(max = 10)
    @Column(name = "c_tipo")
    private String cTipo;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "d_total")
    private Double dTotal;
    @OneToMany(mappedBy = "eIdpresupuesto", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<InsumoTb> insumoTbList;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne(optional = false)
    private ProyectoTb eIdproyecto;

    public PresupuestoTb() {
    }

    public PresupuestoTb(Integer eIdpresupuesto) {
        this.eIdpresupuesto = eIdpresupuesto;
    }

    public Integer getEIdpresupuesto() {
        return eIdpresupuesto;
    }

    public void setEIdpresupuesto(Integer eIdpresupuesto) {
        this.eIdpresupuesto = eIdpresupuesto;
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

    public Double getDTotal() {
        return dTotal;
    }

    public void setDTotal(Double dTotal) {
        this.dTotal = dTotal;
    }

    public List<InsumoTb> getInsumoTbList() {
        return insumoTbList;
    }

    public void setInsumoTbList(List<InsumoTb> insumoTbList) {
        this.insumoTbList = insumoTbList;
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
        hash += (eIdpresupuesto != null ? eIdpresupuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoTb)) {
            return false;
        }
        PresupuestoTb other = (PresupuestoTb) object;
        if ((this.eIdpresupuesto == null && other.eIdpresupuesto != null) || (this.eIdpresupuesto != null && !this.eIdpresupuesto.equals(other.eIdpresupuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PresupuestoTb[ eIdpresupuesto=" + eIdpresupuesto + " ]";
    }

    public String getMNombre() {
        return mNombre;
    }

    public void setMNombre(String mNombre) {
        this.mNombre = mNombre;
    }

}
