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
public class EjemplarParticipaExhibicionTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idejemplar")
    private int eIdejemplar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idexhibicion")
    private int eIdexhibicion;

    public EjemplarParticipaExhibicionTbPK() {
    }

    public EjemplarParticipaExhibicionTbPK(int eIdejemplar, int eIdexhibicion) {
        this.eIdejemplar = eIdejemplar;
        this.eIdexhibicion = eIdexhibicion;
    }

    public int getEIdejemplar() {
        return eIdejemplar;
    }

    public void setEIdejemplar(int eIdejemplar) {
        this.eIdejemplar = eIdejemplar;
    }

    public int getEIdexhibicion() {
        return eIdexhibicion;
    }

    public void setEIdexhibicion(int eIdexhibicion) {
        this.eIdexhibicion = eIdexhibicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdejemplar;
        hash += (int) eIdexhibicion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarParticipaExhibicionTbPK)) {
            return false;
        }
        EjemplarParticipaExhibicionTbPK other = (EjemplarParticipaExhibicionTbPK) object;
        if (this.eIdejemplar != other.eIdejemplar) {
            return false;
        }
        if (this.eIdexhibicion != other.eIdexhibicion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarParticipaExhibicionTbPK[ eIdejemplar=" + eIdejemplar + ", eIdexhibicion=" + eIdexhibicion + " ]";
    }
    
}
