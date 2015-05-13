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
public class AgentePerfilTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idagente")
    private int eIdagente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idperfil")
    private int eIdperfil;

    public AgentePerfilTbPK() {
    }

    public AgentePerfilTbPK(int eIdagente, int eIdperfil) {
        this.eIdagente = eIdagente;
        this.eIdperfil = eIdperfil;
    }

    public int getEIdagente() {
        return eIdagente;
    }

    public void setEIdagente(int eIdagente) {
        this.eIdagente = eIdagente;
    }

    public int getEIdperfil() {
        return eIdperfil;
    }

    public void setEIdperfil(int eIdperfil) {
        this.eIdperfil = eIdperfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdagente;
        hash += (int) eIdperfil;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgentePerfilTbPK)) {
            return false;
        }
        AgentePerfilTbPK other = (AgentePerfilTbPK) object;
        if (this.eIdagente != other.eIdagente) {
            return false;
        }
        if (this.eIdperfil != other.eIdperfil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgentePerfilTbPK[ eIdagente=" + eIdagente + ", eIdperfil=" + eIdperfil + " ]";
    }
    
}
