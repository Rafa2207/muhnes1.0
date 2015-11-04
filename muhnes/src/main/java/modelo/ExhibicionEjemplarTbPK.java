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
 * @author Endy
 */
@Embeddable
public class ExhibicionEjemplarTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_id_ejemplar")
    private int eIdEjemplar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_id_exhibicion")
    private int eIdExhibicion;

    public ExhibicionEjemplarTbPK() {
    }

    public ExhibicionEjemplarTbPK(int eIdEjemplar, int eIdExhibicion) {
        this.eIdEjemplar = eIdEjemplar;
        this.eIdExhibicion = eIdExhibicion;
    }

    public int getEIdEjemplar() {
        return eIdEjemplar;
    }

    public void setEIdEjemplar(int eIdEjemplar) {
        this.eIdEjemplar = eIdEjemplar;
    }

    public int getEIdExhibicion() {
        return eIdExhibicion;
    }

    public void setEIdExhibicion(int eIdExhibicion) {
        this.eIdExhibicion = eIdExhibicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdEjemplar;
        hash += (int) eIdExhibicion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExhibicionEjemplarTbPK)) {
            return false;
        }
        ExhibicionEjemplarTbPK other = (ExhibicionEjemplarTbPK) object;
        if (this.eIdEjemplar != other.eIdEjemplar) {
            return false;
        }
        if (this.eIdExhibicion != other.eIdExhibicion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ExhibicionEjemplarTbPK[ eIdEjemplar=" + eIdEjemplar + ", eIdExhibicion=" + eIdExhibicion + " ]";
    }
    
}
