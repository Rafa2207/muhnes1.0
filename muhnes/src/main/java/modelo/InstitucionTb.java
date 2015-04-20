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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "institucion_tb")
@NamedQueries({
    @NamedQuery(name = "InstitucionTb.findAll", query = "SELECT i FROM InstitucionTb i"),
    @NamedQuery(name = "InstitucionTb.findByEIdinstitucion", query = "SELECT i FROM InstitucionTb i WHERE i.eIdinstitucion = :eIdinstitucion"),
    @NamedQuery(name = "InstitucionTb.findByCNombre", query = "SELECT i FROM InstitucionTb i WHERE i.cNombre = :cNombre"),
    @NamedQuery(name = "InstitucionTb.findByCAcronimo", query = "SELECT i FROM InstitucionTb i WHERE i.cAcronimo = :cAcronimo"),
    @NamedQuery(name = "InstitucionTb.findByMDireccion", query = "SELECT i FROM InstitucionTb i WHERE i.mDireccion = :mDireccion"),
    @NamedQuery(name = "InstitucionTb.findByEPostal", query = "SELECT i FROM InstitucionTb i WHERE i.ePostal = :ePostal"),
    @NamedQuery(name = "InstitucionTb.findByCTelefono", query = "SELECT i FROM InstitucionTb i WHERE i.cTelefono = :cTelefono"),
    @NamedQuery(name = "InstitucionTb.findByCFax", query = "SELECT i FROM InstitucionTb i WHERE i.cFax = :cFax"),
    @NamedQuery(name = "InstitucionTb.findByCUrl", query = "SELECT i FROM InstitucionTb i WHERE i.cUrl = :cUrl")})
public class InstitucionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idinstitucion")
    private Integer eIdinstitucion;
    @Size(max = 150)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_acronimo")
    private String cAcronimo;
    @Size(max = 2147483647)
    @Column(name = "m_direccion")
    private String mDireccion;
    @Column(name = "e_postal")
    private Integer ePostal;
    @Size(max = 20)
    @Column(name = "c_telefono")
    private String cTelefono;
    @Size(max = 20)
    @Column(name = "c_fax")
    private String cFax;
    @Size(max = 70)
    @Column(name = "c_url")
    private String cUrl;
    @OneToMany(mappedBy = "eIdinstitucion")
    private List<AgenteTb> agenteTbList;
    @OneToMany(mappedBy = "eIdinstitucion")
    private List<DonacionTb> donacionTbList;
    @JoinColumn(name = "e_idpais", referencedColumnName = "e_idpais")
    @ManyToOne
    private PaisTb eIdpais;

    public InstitucionTb() {
    }

    public InstitucionTb(Integer eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    public Integer getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(Integer eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCAcronimo() {
        return cAcronimo;
    }

    public void setCAcronimo(String cAcronimo) {
        this.cAcronimo = cAcronimo;
    }

    public String getMDireccion() {
        return mDireccion;
    }

    public void setMDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public Integer getEPostal() {
        return ePostal;
    }

    public void setEPostal(Integer ePostal) {
        this.ePostal = ePostal;
    }

    public String getCTelefono() {
        return cTelefono;
    }

    public void setCTelefono(String cTelefono) {
        this.cTelefono = cTelefono;
    }

    public String getCFax() {
        return cFax;
    }

    public void setCFax(String cFax) {
        this.cFax = cFax;
    }

    public String getCUrl() {
        return cUrl;
    }

    public void setCUrl(String cUrl) {
        this.cUrl = cUrl;
    }

    public List<AgenteTb> getAgenteTbList() {
        return agenteTbList;
    }

    public void setAgenteTbList(List<AgenteTb> agenteTbList) {
        this.agenteTbList = agenteTbList;
    }

    public List<DonacionTb> getDonacionTbList() {
        return donacionTbList;
    }

    public void setDonacionTbList(List<DonacionTb> donacionTbList) {
        this.donacionTbList = donacionTbList;
    }

    public PaisTb getEIdpais() {
        return eIdpais;
    }

    public void setEIdpais(PaisTb eIdpais) {
        this.eIdpais = eIdpais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdinstitucion != null ? eIdinstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionTb)) {
            return false;
        }
        InstitucionTb other = (InstitucionTb) object;
        if ((this.eIdinstitucion == null && other.eIdinstitucion != null) || (this.eIdinstitucion != null && !this.eIdinstitucion.equals(other.eIdinstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.InstitucionTb[ eIdinstitucion=" + eIdinstitucion + " ]";
    }
    
}
