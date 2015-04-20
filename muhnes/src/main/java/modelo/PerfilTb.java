/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "perfil_tb")
@NamedQueries({
    @NamedQuery(name = "PerfilTb.findAll", query = "SELECT p FROM PerfilTb p"),
    @NamedQuery(name = "PerfilTb.findByEIdperfil", query = "SELECT p FROM PerfilTb p WHERE p.eIdperfil = :eIdperfil"),
    @NamedQuery(name = "PerfilTb.findByCNombre", query = "SELECT p FROM PerfilTb p WHERE p.cNombre = :cNombre"),
    @NamedQuery(name = "PerfilTb.findByMDescripcion", query = "SELECT p FROM PerfilTb p WHERE p.mDescripcion = :mDescripcion")})
public class PerfilTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idperfil")
    private Integer eIdperfil;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @ManyToMany(mappedBy = "perfilTbList")
    private List<AgenteTb> agenteTbList;

    public PerfilTb() {
    }

    public PerfilTb(Integer eIdperfil) {
        this.eIdperfil = eIdperfil;
    }

    public Integer getEIdperfil() {
        return eIdperfil;
    }

    public void setEIdperfil(Integer eIdperfil) {
        this.eIdperfil = eIdperfil;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public List<AgenteTb> getAgenteTbList() {
        return agenteTbList;
    }

    public void setAgenteTbList(List<AgenteTb> agenteTbList) {
        this.agenteTbList = agenteTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdperfil != null ? eIdperfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilTb)) {
            return false;
        }
        PerfilTb other = (PerfilTb) object;
        if ((this.eIdperfil == null && other.eIdperfil != null) || (this.eIdperfil != null && !this.eIdperfil.equals(other.eIdperfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PerfilTb[ eIdperfil=" + eIdperfil + " ]";
    }
    
}
