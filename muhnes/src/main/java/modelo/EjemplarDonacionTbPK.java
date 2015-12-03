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
public class EjemplarDonacionTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_iddonacion")
    private int eIddonacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idejemplar")
    private int eIdejemplar;

    public EjemplarDonacionTbPK() {
    }

    public EjemplarDonacionTbPK(int eIddonacion, int eIdejemplar) {
        this.eIddonacion = eIddonacion;
        this.eIdejemplar = eIdejemplar;
    }

    public int getEIddonacion() {
        return eIddonacion;
    }

    public void setEIddonacion(int eIddonacion) {
        this.eIddonacion = eIddonacion;
    }

    public int getEIdejemplar() {
        return eIdejemplar;
    }

    public void setEIdejemplar(int eIdejemplar) {
        this.eIdejemplar = eIdejemplar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIddonacion;
        hash += (int) eIdejemplar;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarDonacionTbPK)) {
            return false;
        }
        EjemplarDonacionTbPK other = (EjemplarDonacionTbPK) object;
        if (this.eIddonacion != other.eIddonacion) {
            return false;
        }
        if (this.eIdejemplar != other.eIdejemplar) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarDonacionTbPK[ eIddonacion=" + eIddonacion + ", eIdejemplar=" + eIdejemplar + " ]";
    }
    
}
