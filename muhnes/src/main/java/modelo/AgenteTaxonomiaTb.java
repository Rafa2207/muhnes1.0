/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "agente_taxonomia_tb")
@NamedQueries({
    @NamedQuery(name = "AgenteTaxonomiaTb.findAll", query = "SELECT a FROM AgenteTaxonomiaTb a"),
    @NamedQuery(name = "AgenteTaxonomiaTb.findByEIdagente", query = "SELECT a FROM AgenteTaxonomiaTb a WHERE a.agenteTaxonomiaTbPK.eIdagente = :eIdagente"),
    @NamedQuery(name = "AgenteTaxonomiaTb.findByEIdtaxonomia", query = "SELECT a FROM AgenteTaxonomiaTb a WHERE a.agenteTaxonomiaTbPK.eIdtaxonomia = :eIdtaxonomia"),
    @NamedQuery(name = "AgenteTaxonomiaTb.findByESecuecia", query = "SELECT a FROM AgenteTaxonomiaTb a WHERE a.eSecuecia = :eSecuecia"),
    @NamedQuery(name = "AgenteTaxonomiaTb.findByCConector", query = "SELECT a FROM AgenteTaxonomiaTb a WHERE a.cConector = :cConector"),
    @NamedQuery(name = "AgenteTaxonomiaTb.findByBAutorbasionimio", query = "SELECT a FROM AgenteTaxonomiaTb a WHERE a.bAutorbasionimio = :bAutorbasionimio")})
public class AgenteTaxonomiaTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgenteTaxonomiaTbPK agenteTaxonomiaTbPK;
    @Column(name = "e_secuecia")
    private Integer eSecuecia;
    @Size(max = 20)
    @Column(name = "c_conector")
    private String cConector;
    @Column(name = "b_autorbasionimio")
    private Boolean bAutorbasionimio;
    @JoinColumn(name = "e_idtaxonomia", referencedColumnName = "e_idtaxonomia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TaxonomiaTb taxonomiaTb;
    @JoinColumn(name = "e_idagente", referencedColumnName = "e_idagente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AgenteTb agenteTb;

    public AgenteTaxonomiaTb() {
    }

    public AgenteTaxonomiaTb(AgenteTaxonomiaTbPK agenteTaxonomiaTbPK) {
        this.agenteTaxonomiaTbPK = agenteTaxonomiaTbPK;
    }

    public AgenteTaxonomiaTb(int eIdagente, int eIdtaxonomia) {
        this.agenteTaxonomiaTbPK = new AgenteTaxonomiaTbPK(eIdagente, eIdtaxonomia);
    }

    public AgenteTaxonomiaTbPK getAgenteTaxonomiaTbPK() {
        return agenteTaxonomiaTbPK;
    }

    public void setAgenteTaxonomiaTbPK(AgenteTaxonomiaTbPK agenteTaxonomiaTbPK) {
        this.agenteTaxonomiaTbPK = agenteTaxonomiaTbPK;
    }

    public Integer getESecuecia() {
        return eSecuecia;
    }

    public void setESecuecia(Integer eSecuecia) {
        this.eSecuecia = eSecuecia;
    }

    public String getCConector() {
        return cConector;
    }

    public void setCConector(String cConector) {
        this.cConector = cConector;
    }

    public Boolean getBAutorbasionimio() {
        return bAutorbasionimio;
    }

    public void setBAutorbasionimio(Boolean bAutorbasionimio) {
        this.bAutorbasionimio = bAutorbasionimio;
    }

    public TaxonomiaTb getTaxonomiaTb() {
        return taxonomiaTb;
    }

    public void setTaxonomiaTb(TaxonomiaTb taxonomiaTb) {
        this.taxonomiaTb = taxonomiaTb;
    }

    public AgenteTb getAgenteTb() {
        return agenteTb;
    }

    public void setAgenteTb(AgenteTb agenteTb) {
        this.agenteTb = agenteTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agenteTaxonomiaTbPK != null ? agenteTaxonomiaTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteTaxonomiaTb)) {
            return false;
        }
        AgenteTaxonomiaTb other = (AgenteTaxonomiaTb) object;
        if ((this.agenteTaxonomiaTbPK == null && other.agenteTaxonomiaTbPK != null) || (this.agenteTaxonomiaTbPK != null && !this.agenteTaxonomiaTbPK.equals(other.agenteTaxonomiaTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteTaxonomiaTb[ agenteTaxonomiaTbPK=" + agenteTaxonomiaTbPK + " ]";
    }
    
}
