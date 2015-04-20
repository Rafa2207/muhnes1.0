/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "agente_tb")
@NamedQueries({
    @NamedQuery(name = "AgenteTb.findAll", query = "SELECT a FROM AgenteTb a"),
    @NamedQuery(name = "AgenteTb.findByEIdagente", query = "SELECT a FROM AgenteTb a WHERE a.eIdagente = :eIdagente"),
    @NamedQuery(name = "AgenteTb.findByCNombre", query = "SELECT a FROM AgenteTb a WHERE a.cNombre = :cNombre"),
    @NamedQuery(name = "AgenteTb.findByCApellido", query = "SELECT a FROM AgenteTb a WHERE a.cApellido = :cApellido"),
    @NamedQuery(name = "AgenteTb.findByCIniciales", query = "SELECT a FROM AgenteTb a WHERE a.cIniciales = :cIniciales"),
    @NamedQuery(name = "AgenteTb.findByCOcupacion", query = "SELECT a FROM AgenteTb a WHERE a.cOcupacion = :cOcupacion"),
    @NamedQuery(name = "AgenteTb.findByCEmail", query = "SELECT a FROM AgenteTb a WHERE a.cEmail = :cEmail"),
    @NamedQuery(name = "AgenteTb.findByEPostal", query = "SELECT a FROM AgenteTb a WHERE a.ePostal = :ePostal"),
    @NamedQuery(name = "AgenteTb.findByCTelefono", query = "SELECT a FROM AgenteTb a WHERE a.cTelefono = :cTelefono"),
    @NamedQuery(name = "AgenteTb.findByCFax", query = "SELECT a FROM AgenteTb a WHERE a.cFax = :cFax"),
    @NamedQuery(name = "AgenteTb.findByMDireccion", query = "SELECT a FROM AgenteTb a WHERE a.mDireccion = :mDireccion"),
    @NamedQuery(name = "AgenteTb.findByFFechanac", query = "SELECT a FROM AgenteTb a WHERE a.fFechanac = :fFechanac"),
    @NamedQuery(name = "AgenteTb.findByFFecham", query = "SELECT a FROM AgenteTb a WHERE a.fFecham = :fFecham")})
public class AgenteTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idagente")
    private Integer eIdagente;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 50)
    @Column(name = "c_apellido")
    private String cApellido;
    @Size(max = 10)
    @Column(name = "c_iniciales")
    private String cIniciales;
    @Size(max = 150)
    @Column(name = "c_ocupacion")
    private String cOcupacion;
    @Size(max = 80)
    @Column(name = "c_email")
    private String cEmail;
    @Column(name = "e_postal")
    private Integer ePostal;
    @Size(max = 20)
    @Column(name = "c_telefono")
    private String cTelefono;
    @Size(max = 20)
    @Column(name = "c_fax")
    private String cFax;
    @Size(max = 2147483647)
    @Column(name = "m_direccion")
    private String mDireccion;
    @Column(name = "f_fechanac")
    @Temporal(TemporalType.DATE)
    private Date fFechanac;
    @Column(name = "f_fecham")
    @Temporal(TemporalType.DATE)
    private Date fFecham;
    @JoinTable(name = "agente_perfil_tb", joinColumns = {
        @JoinColumn(name = "e_idagente", referencedColumnName = "e_idagente")}, inverseJoinColumns = {
        @JoinColumn(name = "e_idperfil", referencedColumnName = "e_idperfil")})
    @ManyToMany
    private List<PerfilTb> perfilTbList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agenteTb")
    private List<AgenteIdentificaEjemplarTb> agenteIdentificaEjemplarTbList;
    @JoinColumn(name = "e_idinstitucion", referencedColumnName = "e_idinstitucion")
    @ManyToOne
    private InstitucionTb eIdinstitucion;

    public AgenteTb() {
    }

    public AgenteTb(Integer eIdagente) {
        this.eIdagente = eIdagente;
    }

    public Integer getEIdagente() {
        return eIdagente;
    }

    public void setEIdagente(Integer eIdagente) {
        this.eIdagente = eIdagente;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCApellido() {
        return cApellido;
    }

    public void setCApellido(String cApellido) {
        this.cApellido = cApellido;
    }

    public String getCIniciales() {
        return cIniciales;
    }

    public void setCIniciales(String cIniciales) {
        this.cIniciales = cIniciales;
    }

    public String getCOcupacion() {
        return cOcupacion;
    }

    public void setCOcupacion(String cOcupacion) {
        this.cOcupacion = cOcupacion;
    }

    public String getCEmail() {
        return cEmail;
    }

    public void setCEmail(String cEmail) {
        this.cEmail = cEmail;
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

    public String getMDireccion() {
        return mDireccion;
    }

    public void setMDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public Date getFFechanac() {
        return fFechanac;
    }

    public void setFFechanac(Date fFechanac) {
        this.fFechanac = fFechanac;
    }

    public Date getFFecham() {
        return fFecham;
    }

    public void setFFecham(Date fFecham) {
        this.fFecham = fFecham;
    }

    public List<PerfilTb> getPerfilTbList() {
        return perfilTbList;
    }

    public void setPerfilTbList(List<PerfilTb> perfilTbList) {
        this.perfilTbList = perfilTbList;
    }

    public List<AgenteIdentificaEjemplarTb> getAgenteIdentificaEjemplarTbList() {
        return agenteIdentificaEjemplarTbList;
    }

    public void setAgenteIdentificaEjemplarTbList(List<AgenteIdentificaEjemplarTb> agenteIdentificaEjemplarTbList) {
        this.agenteIdentificaEjemplarTbList = agenteIdentificaEjemplarTbList;
    }

    public InstitucionTb getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(InstitucionTb eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdagente != null ? eIdagente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenteTb)) {
            return false;
        }
        AgenteTb other = (AgenteTb) object;
        if ((this.eIdagente == null && other.eIdagente != null) || (this.eIdagente != null && !this.eIdagente.equals(other.eIdagente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AgenteTb[ eIdagente=" + eIdagente + " ]";
    }
    
}
