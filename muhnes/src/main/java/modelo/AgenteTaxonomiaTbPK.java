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
public class AgenteTaxonomiaTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idagente")
    private int eIdagente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idtaxonomia")
    private int eIdtaxonomia;

    public AgenteTaxonomiaTbPK() {
    }

    public AgenteTaxonomiaTbPK(int eIdagente, int eIdtaxonomia) {
        this.eIdagente = eIdagente;
        this.eIdtaxonomia = eIdtaxonomia;
    }

    public int getEIdagente() {
        return eIdagente;
    }

    public void setEIdagente(int eIdagente) {
        this.eIdagente = eIdagente;
    }

    public int getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(int eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdagente;
        hash += (int) eIdtaxonomia;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteTaxonomiaTbPK)) {
            return false;
        }
        AgenteTaxonomiaTbPK other = (AgenteTaxonomiaTbPK) object;
        if (this.eIdagente != other.eIdagente) {
            return false;
        }
        if (this.eIdtaxonomia != other.eIdtaxonomia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteTaxonomiaTbPK[ eIdagente=" + eIdagente + ", eIdtaxonomia=" + eIdtaxonomia + " ]";
    }
    
}
