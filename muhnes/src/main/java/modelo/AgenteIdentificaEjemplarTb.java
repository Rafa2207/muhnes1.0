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
 * @author Rafa
 */
@Entity
@Table(name = "agente_identifica_ejemplar_tb")
@NamedQueries({
    @NamedQuery(name = "AgenteIdentificaEjemplarTb.findAll", query = "SELECT a FROM AgenteIdentificaEjemplarTb a"),
    @NamedQuery(name = "AgenteIdentificaEjemplarTb.findByEIdagente", query = "SELECT a FROM AgenteIdentificaEjemplarTb a WHERE a.agenteIdentificaEjemplarTbPK.eIdagente = :eIdagente"),
    @NamedQuery(name = "AgenteIdentificaEjemplarTb.findByESecuencia", query = "SELECT a FROM AgenteIdentificaEjemplarTb a WHERE a.eSecuencia = :eSecuencia"),
    @NamedQuery(name = "AgenteIdentificaEjemplarTb.findByEIdejemplar", query = "SELECT a FROM AgenteIdentificaEjemplarTb a WHERE a.agenteIdentificaEjemplarTbPK.eIdejemplar = :eIdejemplar")})
public class AgenteIdentificaEjemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgenteIdentificaEjemplarTbPK agenteIdentificaEjemplarTbPK;
    @Column(name = "e_secuencia")
    private Integer eSecuencia;
    @JoinColumn(name = "e_idejemplar", referencedColumnName = "e_idejemplar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EjemplarTb ejemplarTb;
    @JoinColumn(name = "e_idagente", referencedColumnName = "e_idagente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AgenteTb agenteTb;

    public AgenteIdentificaEjemplarTb() {
    }

    public AgenteIdentificaEjemplarTb(AgenteIdentificaEjemplarTbPK agenteIdentificaEjemplarTbPK) {
        this.agenteIdentificaEjemplarTbPK = agenteIdentificaEjemplarTbPK;
    }

    public AgenteIdentificaEjemplarTb(int eIdagente, int eIdejemplar) {
        this.agenteIdentificaEjemplarTbPK = new AgenteIdentificaEjemplarTbPK(eIdagente, eIdejemplar);
    }

    public AgenteIdentificaEjemplarTbPK getAgenteIdentificaEjemplarTbPK() {
        return agenteIdentificaEjemplarTbPK;
    }

    public void setAgenteIdentificaEjemplarTbPK(AgenteIdentificaEjemplarTbPK agenteIdentificaEjemplarTbPK) {
        this.agenteIdentificaEjemplarTbPK = agenteIdentificaEjemplarTbPK;
    }

    public Integer getESecuencia() {
        return eSecuencia;
    }

    public void setESecuencia(Integer eSecuencia) {
        this.eSecuencia = eSecuencia;
    }

    public EjemplarTb getEjemplarTb() {
        return ejemplarTb;
    }

    public void setEjemplarTb(EjemplarTb ejemplarTb) {
        this.ejemplarTb = ejemplarTb;
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
        hash += (agenteIdentificaEjemplarTbPK != null ? agenteIdentificaEjemplarTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteIdentificaEjemplarTb)) {
            return false;
        }
        AgenteIdentificaEjemplarTb other = (AgenteIdentificaEjemplarTb) object;
        if ((this.agenteIdentificaEjemplarTbPK == null && other.agenteIdentificaEjemplarTbPK != null) || (this.agenteIdentificaEjemplarTbPK != null && !this.agenteIdentificaEjemplarTbPK.equals(other.agenteIdentificaEjemplarTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteIdentificaEjemplarTb[ agenteIdentificaEjemplarTbPK=" + agenteIdentificaEjemplarTbPK + " ]";
    }
    
}
