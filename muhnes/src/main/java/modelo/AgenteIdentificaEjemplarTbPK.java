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
public class AgenteIdentificaEjemplarTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idagente")
    private int eIdagente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idejemplar")
    private int eIdejemplar;

    public AgenteIdentificaEjemplarTbPK() {
    }

    public AgenteIdentificaEjemplarTbPK(int eIdagente, int eIdejemplar) {
        this.eIdagente = eIdagente;
        this.eIdejemplar = eIdejemplar;
    }

    public int getEIdagente() {
        return eIdagente;
    }

    public void setEIdagente(int eIdagente) {
        this.eIdagente = eIdagente;
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
        hash += (int) eIdagente;
        hash += (int) eIdejemplar;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteIdentificaEjemplarTbPK)) {
            return false;
        }
        AgenteIdentificaEjemplarTbPK other = (AgenteIdentificaEjemplarTbPK) object;
        if (this.eIdagente != other.eIdagente) {
            return false;
        }
        if (this.eIdejemplar != other.eIdejemplar) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteIdentificaEjemplarTbPK[ eIdagente=" + eIdagente + ", eIdejemplar=" + eIdejemplar + " ]";
    }
    
}
