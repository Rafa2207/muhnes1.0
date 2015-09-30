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
import javax.validation.constraints.Size;

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "c_tipo")
    private String cTipo;

    public AgenteIdentificaEjemplarTbPK() {
    }

    public AgenteIdentificaEjemplarTbPK(int eIdagente, int eIdejemplar, String cTipo) {
        this.eIdagente = eIdagente;
        this.eIdejemplar = eIdejemplar;
        this.cTipo = cTipo;
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

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdagente;
        hash += (int) eIdejemplar;
        hash += (cTipo != null ? cTipo.hashCode() : 0);
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
        if ((this.cTipo == null && other.cTipo != null) || (this.cTipo != null && !this.cTipo.equals(other.cTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteIdentificaEjemplarTbPK[ eIdagente=" + eIdagente + ", eIdejemplar=" + eIdejemplar + ", cTipo=" + cTipo + " ]";
    }
    
}
