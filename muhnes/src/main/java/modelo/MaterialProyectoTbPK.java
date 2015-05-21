/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafa
 */
@Embeddable
public class MaterialProyectoTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idmaterial")
    private int eIdmaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idproyecto")
    private int eIdproyecto;

    public MaterialProyectoTbPK() {
    }

    public MaterialProyectoTbPK(int eIdmaterial, int eIdproyecto) {
        this.eIdmaterial = eIdmaterial;
        this.eIdproyecto = eIdproyecto;
    }

    public int getEIdmaterial() {
        return eIdmaterial;
    }

    public void setEIdmaterial(int eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    public int getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(int eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdmaterial;
        hash += (int) eIdproyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialProyectoTbPK)) {
            return false;
        }
        MaterialProyectoTbPK other = (MaterialProyectoTbPK) object;
        if (this.eIdmaterial != other.eIdmaterial) {
            return false;
        }
        if (this.eIdproyecto != other.eIdproyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialProyectoTbPK[ eIdmaterial=" + eIdmaterial + ", eIdproyecto=" + eIdproyecto + " ]";
    }
    
}
