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
public class AgenteEspecieTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idagente")
    private int eIdagente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idespecie")
    private int eIdespecie;

    public AgenteEspecieTbPK() {
    }

    public AgenteEspecieTbPK(int eIdagente, int eIdespecie) {
        this.eIdagente = eIdagente;
        this.eIdespecie = eIdespecie;
    }

    public int getEIdagente() {
        return eIdagente;
    }

    public void setEIdagente(int eIdagente) {
        this.eIdagente = eIdagente;
    }

    public int getEIdespecie() {
        return eIdespecie;
    }

    public void setEIdespecie(int eIdespecie) {
        this.eIdespecie = eIdespecie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdagente;
        hash += (int) eIdespecie;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteEspecieTbPK)) {
            return false;
        }
        AgenteEspecieTbPK other = (AgenteEspecieTbPK) object;
        if (this.eIdagente != other.eIdagente) {
            return false;
        }
        if (this.eIdespecie != other.eIdespecie) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteEspecieTbPK[ eIdagente=" + eIdagente + ", eIdespecie=" + eIdespecie + " ]";
    }
    
}
