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
@Table(name = "material_proyecto_tb")
@NamedQueries({
    @NamedQuery(name = "MaterialProyectoTb.findAll", query = "SELECT m FROM MaterialProyectoTb m"),
    @NamedQuery(name = "MaterialProyectoTb.findByEIdmaterial", query = "SELECT m FROM MaterialProyectoTb m WHERE m.materialProyectoTbPK.eIdmaterial = :eIdmaterial"),
    @NamedQuery(name = "MaterialProyectoTb.findByEIdproyecto", query = "SELECT m FROM MaterialProyectoTb m WHERE m.materialProyectoTbPK.eIdproyecto = :eIdproyecto"),
    @NamedQuery(name = "MaterialProyectoTb.findByDCantidad", query = "SELECT m FROM MaterialProyectoTb m WHERE m.dCantidad = :dCantidad"),
    @NamedQuery(name = "MaterialProyectoTb.findByDRegreso", query = "SELECT m FROM MaterialProyectoTb m WHERE m.dRegreso = :dRegreso")})
public class MaterialProyectoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialProyectoTbPK materialProyectoTbPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @Column(name = "d_regreso")
    private Double dRegreso;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProyectoTb proyectoTb;
    @JoinColumn(name = "e_idmaterial", referencedColumnName = "e_idmaterial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MaterialTb materialTb;

    public MaterialProyectoTb() {
    }

    public MaterialProyectoTb(MaterialProyectoTbPK materialProyectoTbPK) {
        this.materialProyectoTbPK = materialProyectoTbPK;
    }

    public MaterialProyectoTb(int eIdmaterial, int eIdproyecto) {
        this.materialProyectoTbPK = new MaterialProyectoTbPK(eIdmaterial, eIdproyecto);
    }

    public MaterialProyectoTbPK getMaterialProyectoTbPK() {
        return materialProyectoTbPK;
    }

    public void setMaterialProyectoTbPK(MaterialProyectoTbPK materialProyectoTbPK) {
        this.materialProyectoTbPK = materialProyectoTbPK;
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

    public ProyectoTb getProyectoTb() {
        return proyectoTb;
    }

    public void setProyectoTb(ProyectoTb proyectoTb) {
        this.proyectoTb = proyectoTb;
    }

    public MaterialTb getMaterialTb() {
        return materialTb;
    }

    public void setMaterialTb(MaterialTb materialTb) {
        this.materialTb = materialTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialProyectoTbPK != null ? materialProyectoTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialProyectoTb)) {
            return false;
        }
        MaterialProyectoTb other = (MaterialProyectoTb) object;
        if ((this.materialProyectoTbPK == null && other.materialProyectoTbPK != null) || (this.materialProyectoTbPK != null && !this.materialProyectoTbPK.equals(other.materialProyectoTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialProyectoTb[ materialProyectoTbPK=" + materialProyectoTbPK + " ]";
    }
    
}
