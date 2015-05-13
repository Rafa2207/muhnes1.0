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

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "agente_perfil_tb")
@NamedQueries({
    @NamedQuery(name = "AgentePerfilTb.findAll", query = "SELECT a FROM AgentePerfilTb a"),
    @NamedQuery(name = "AgentePerfilTb.findByEIdagente", query = "SELECT a FROM AgentePerfilTb a WHERE a.agentePerfilTbPK.eIdagente = :eIdagente"),
    @NamedQuery(name = "AgentePerfilTb.findByEIdperfil", query = "SELECT a FROM AgentePerfilTb a WHERE a.agentePerfilTbPK.eIdperfil = :eIdperfil"),
    @NamedQuery(name = "AgentePerfilTb.findByEEntero", query = "SELECT a FROM AgentePerfilTb a WHERE a.eEntero = :eEntero")})
public class AgentePerfilTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgentePerfilTbPK agentePerfilTbPK;
    @Column(name = "e_entero")
    private Integer eEntero;
    @JoinColumn(name = "e_idperfil", referencedColumnName = "e_idperfil", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilTb perfilTb;
    @JoinColumn(name = "e_idagente", referencedColumnName = "e_idagente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AgenteTb agenteTb;

    public AgentePerfilTb() {
    }

    public AgentePerfilTb(AgentePerfilTbPK agentePerfilTbPK) {
        this.agentePerfilTbPK = agentePerfilTbPK;
    }

    public AgentePerfilTb(int eIdagente, int eIdperfil) {
        this.agentePerfilTbPK = new AgentePerfilTbPK(eIdagente, eIdperfil);
    }

    public AgentePerfilTbPK getAgentePerfilTbPK() {
        return agentePerfilTbPK;
    }

    public void setAgentePerfilTbPK(AgentePerfilTbPK agentePerfilTbPK) {
        this.agentePerfilTbPK = agentePerfilTbPK;
    }

    public Integer getEEntero() {
        return eEntero;
    }

    public void setEEntero(Integer eEntero) {
        this.eEntero = eEntero;
    }

    public PerfilTb getPerfilTb() {
        return perfilTb;
    }

    public void setPerfilTb(PerfilTb perfilTb) {
        this.perfilTb = perfilTb;
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
        hash += (agentePerfilTbPK != null ? agentePerfilTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgentePerfilTb)) {
            return false;
        }
        AgentePerfilTb other = (AgentePerfilTb) object;
        if ((this.agentePerfilTbPK == null && other.agentePerfilTbPK != null) || (this.agentePerfilTbPK != null && !this.agentePerfilTbPK.equals(other.agentePerfilTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgentePerfilTb[ agentePerfilTbPK=" + agentePerfilTbPK + " ]";
    }
    
}
