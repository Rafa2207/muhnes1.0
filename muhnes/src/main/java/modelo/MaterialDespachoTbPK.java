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
public class MaterialDespachoTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idmaterial")
    private int eIdmaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_iddespacho")
    private int eIddespacho;

    public MaterialDespachoTbPK() {
    }

    public MaterialDespachoTbPK(int eIdmaterial, int eIddespacho) {
        this.eIdmaterial = eIdmaterial;
        this.eIddespacho = eIddespacho;
    }

    public int getEIdmaterial() {
        return eIdmaterial;
    }

    public void setEIdmaterial(int eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    public int getEIddespacho() {
        return eIddespacho;
    }

    public void setEIddespacho(int eIddespacho) {
        this.eIddespacho = eIddespacho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdmaterial;
        hash += (int) eIddespacho;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialDespachoTbPK)) {
            return false;
        }
        MaterialDespachoTbPK other = (MaterialDespachoTbPK) object;
        if (this.eIdmaterial != other.eIdmaterial) {
            return false;
        }
        if (this.eIddespacho != other.eIddespacho) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialDespachoTbPK[ eIdmaterial=" + eIdmaterial + ", eIddespacho=" + eIddespacho + " ]";
    }
    
}
