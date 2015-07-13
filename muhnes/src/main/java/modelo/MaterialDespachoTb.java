/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "material_despacho_tb")
@NamedQueries({
    @NamedQuery(name = "MaterialDespachoTb.findAll", query = "SELECT m FROM MaterialDespachoTb m"),
    @NamedQuery(name = "MaterialDespachoTb.findByEIdmaterial", query = "SELECT m FROM MaterialDespachoTb m WHERE m.materialDespachoTbPK.eIdmaterial = :eIdmaterial"),
    @NamedQuery(name = "MaterialDespachoTb.findByEIddespacho", query = "SELECT m FROM MaterialDespachoTb m WHERE m.materialDespachoTbPK.eIddespacho = :eIddespacho"),
    @NamedQuery(name = "MaterialDespachoTb.findByDCantidad", query = "SELECT m FROM MaterialDespachoTb m WHERE m.dCantidad = :dCantidad"),
    @NamedQuery(name = "MaterialDespachoTb.findByDRegreso", query = "SELECT m FROM MaterialDespachoTb m WHERE m.dRegreso = :dRegreso")})
public class MaterialDespachoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialDespachoTbPK materialDespachoTbPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @Column(name = "d_regreso")
    private Double dRegreso;
    @JoinColumn(name = "e_idmaterial", referencedColumnName = "e_idmaterial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MaterialTb materialTb;
    @JoinColumn(name = "e_iddespacho", referencedColumnName = "e_iddespacho", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DespachoTb despachoTb;

    public MaterialDespachoTb() {
    }

    public MaterialDespachoTb(MaterialDespachoTbPK materialDespachoTbPK) {
        this.materialDespachoTbPK = materialDespachoTbPK;
    }

    public MaterialDespachoTb(int eIdmaterial, int eIddespacho) {
        this.materialDespachoTbPK = new MaterialDespachoTbPK(eIdmaterial, eIddespacho);
    }

    public MaterialDespachoTbPK getMaterialDespachoTbPK() {
        return materialDespachoTbPK;
    }

    public void setMaterialDespachoTbPK(MaterialDespachoTbPK materialDespachoTbPK) {
        this.materialDespachoTbPK = materialDespachoTbPK;
    }

    public Double getDCantidad() {
        return dCantidad;
    }

    public void setDCantidad(Double dCantidad) {
        this.dCantidad = dCantidad;
    }

    public Double getDRegreso() {
        return dRegreso;
    }

    public void setDRegreso(Double dRegreso) {
        this.dRegreso = dRegreso;
    }

    public MaterialTb getMaterialTb() {
        return materialTb;
    }

    public void setMaterialTb(MaterialTb materialTb) {
        this.materialTb = materialTb;
    }

    public DespachoTb getDespachoTb() {
        return despachoTb;
    }

    public void setDespachoTb(DespachoTb despachoTb) {
        this.despachoTb = despachoTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialDespachoTbPK != null ? materialDespachoTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialDespachoTb)) {
            return false;
        }
        MaterialDespachoTb other = (MaterialDespachoTb) object;
        if ((this.materialDespachoTbPK == null && other.materialDespachoTbPK != null) || (this.materialDespachoTbPK != null && !this.materialDespachoTbPK.equals(other.materialDespachoTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialDespachoTb[ materialDespachoTbPK=" + materialDespachoTbPK + " ]";
    }
    
}
