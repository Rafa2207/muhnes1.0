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
@Table(name = "pais_tb")
@NamedQueries({
    @NamedQuery(name = "PaisTb.findAll", query = "SELECT p FROM PaisTb p"),
    @NamedQuery(name = "PaisTb.findByEIdpais", query = "SELECT p FROM PaisTb p WHERE p.eIdpais = :eIdpais"),
    @NamedQuery(name = "PaisTb.findByCNombre", query = "SELECT p FROM PaisTb p WHERE p.cNombre = :cNombre"),
    @NamedQuery(name = "PaisTb.findByCGentilicio", query = "SELECT p FROM PaisTb p WHERE p.cGentilicio = :cGentilicio"),
    @NamedQuery(name = "PaisTb.findByCIdioma", query = "SELECT p FROM PaisTb p WHERE p.cIdioma = :cIdioma")})
public class PaisTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idpais")
    private Integer eIdpais;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 80)
    @Column(name = "c_gentilicio")
    private String cGentilicio;
    @Size(max = 25)
    @Column(name = "c_idioma")
    private String cIdioma;
    @OneToMany(mappedBy = "eIdpais")
    private List<DepartamentoTb> departamentoTbList;
    @OneToMany(mappedBy = "eIdpais")
    private List<InstitucionTb> institucionTbList;

    public PaisTb() {
    }

    public PaisTb(Integer eIdpais) {
        this.eIdpais = eIdpais;
    }

    public Integer getEIdpais() {
        return eIdpais;
    }

    public void setEIdpais(Integer eIdpais) {
        this.eIdpais = eIdpais;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCGentilicio() {
        return cGentilicio;
    }

    public void setCGentilicio(String cGentilicio) {
        this.cGentilicio = cGentilicio;
    }

    public String getCIdioma() {
        return cIdioma;
    }

    public void setCIdioma(String cIdioma) {
        this.cIdioma = cIdioma;
    }

    public List<DepartamentoTb> getDepartamentoTbList() {
        return departamentoTbList;
    }

    public void setDepartamentoTbList(List<DepartamentoTb> departamentoTbList) {
        this.departamentoTbList = departamentoTbList;
    }

    public List<InstitucionTb> getInstitucionTbList() {
        return institucionTbList;
    }

    public void setInstitucionTbList(List<InstitucionTb> institucionTbList) {
        this.institucionTbList = institucionTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdpais != null ? eIdpais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaisTb)) {
            return false;
        }
        PaisTb other = (PaisTb) object;
        if ((this.eIdpais == null && other.eIdpais != null) || (this.eIdpais != null && !this.eIdpais.equals(other.eIdpais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PaisTb[ eIdpais=" + eIdpais + " ]";
    }
    
}
