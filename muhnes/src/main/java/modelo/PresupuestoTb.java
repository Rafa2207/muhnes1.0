/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
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
    @NamedQuery(name = "PresupuestoTb.findByCNombre", query = "SELECT p FROM PresupuestoTb p WHERE p.cNombre = :cNombre"),
    @NamedQuery(name = "PresupuestoTb.findByMDescripcion", query = "SELECT p FROM PresupuestoTb p WHERE p.mDescripcion = :mDescripcion")})
public class PresupuestoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idpresupuesto")
    private Integer eIdpresupuesto;
    @Size(max = 10)
    @Column(name = "c_tipo")
    private String cTipo;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @OneToMany(mappedBy = "eIdpresupuesto")
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
    
}
