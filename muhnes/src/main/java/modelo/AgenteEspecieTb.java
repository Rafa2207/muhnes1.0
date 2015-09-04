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
@Table(name = "agente_especie_tb")
@NamedQueries({
    @NamedQuery(name = "AgenteEspecieTb.findAll", query = "SELECT a FROM AgenteEspecieTb a"),
    @NamedQuery(name = "AgenteEspecieTb.findByEIdagente", query = "SELECT a FROM AgenteEspecieTb a WHERE a.agenteEspecieTbPK.eIdagente = :eIdagente"),
    @NamedQuery(name = "AgenteEspecieTb.findByEIdespecie", query = "SELECT a FROM AgenteEspecieTb a WHERE a.agenteEspecieTbPK.eIdespecie = :eIdespecie"),
    @NamedQuery(name = "AgenteEspecieTb.findByESecuencia", query = "SELECT a FROM AgenteEspecieTb a WHERE a.eSecuencia = :eSecuencia"),
    @NamedQuery(name = "AgenteEspecieTb.findByCConector", query = "SELECT a FROM AgenteEspecieTb a WHERE a.cConector = :cConector"),
    @NamedQuery(name = "AgenteEspecieTb.findByBAutorBasionimio", query = "SELECT a FROM AgenteEspecieTb a WHERE a.bAutorBasionimio = :bAutorBasionimio")})
public class AgenteEspecieTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgenteEspecieTbPK agenteEspecieTbPK;
    @Column(name = "e_secuencia")
    private Integer eSecuencia;
    @Size(max = 20)
    @Column(name = "c_conector")
    private String cConector;
    @Column(name = "b_autor_basionimio")
    private Boolean bAutorBasionimio;
    @JoinColumn(name = "e_idespecie", referencedColumnName = "e_idespecie", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EspecieTb especieTb;
    @JoinColumn(name = "e_idagente", referencedColumnName = "e_idagente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AgenteTb agenteTb;

    public AgenteEspecieTb() {
    }

    public AgenteEspecieTb(AgenteEspecieTbPK agenteEspecieTbPK) {
        this.agenteEspecieTbPK = agenteEspecieTbPK;
    }

    public AgenteEspecieTb(int eIdagente, int eIdespecie) {
        this.agenteEspecieTbPK = new AgenteEspecieTbPK(eIdagente, eIdespecie);
    }

    public AgenteEspecieTbPK getAgenteEspecieTbPK() {
        return agenteEspecieTbPK;
    }

    public void setAgenteEspecieTbPK(AgenteEspecieTbPK agenteEspecieTbPK) {
        this.agenteEspecieTbPK = agenteEspecieTbPK;
    }

    public Integer getESecuencia() {
        return eSecuencia;
    }

    public void setESecuencia(Integer eSecuencia) {
        this.eSecuencia = eSecuencia;
    }

    public String getCConector() {
        return cConector;
    }

    public void setCConector(String cConector) {
        this.cConector = cConector;
    }

    public Boolean getBAutorBasionimio() {
        return bAutorBasionimio;
    }

    public void setBAutorBasionimio(Boolean bAutorBasionimio) {
        this.bAutorBasionimio = bAutorBasionimio;
    }

    public EspecieTb getEspecieTb() {
        return especieTb;
    }

    public void setEspecieTb(EspecieTb especieTb) {
        this.especieTb = especieTb;
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
        hash += (agenteEspecieTbPK != null ? agenteEspecieTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteEspecieTb)) {
            return false;
        }
        AgenteEspecieTb other = (AgenteEspecieTb) object;
        if ((this.agenteEspecieTbPK == null && other.agenteEspecieTbPK != null) || (this.agenteEspecieTbPK != null && !this.agenteEspecieTbPK.equals(other.agenteEspecieTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteEspecieTb[ agenteEspecieTbPK=" + agenteEspecieTbPK + " ]";
    }
    
}
