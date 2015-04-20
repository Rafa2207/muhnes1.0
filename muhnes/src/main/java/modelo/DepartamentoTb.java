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
@Table(name = "departamento_tb")
@NamedQueries({
    @NamedQuery(name = "DepartamentoTb.findAll", query = "SELECT d FROM DepartamentoTb d"),
    @NamedQuery(name = "DepartamentoTb.findByEIddepto", query = "SELECT d FROM DepartamentoTb d WHERE d.eIddepto = :eIddepto"),
    @NamedQuery(name = "DepartamentoTb.findByCNombre", query = "SELECT d FROM DepartamentoTb d WHERE d.cNombre = :cNombre")})
public class DepartamentoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_iddepto")
    private Integer eIddepto;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @JoinColumn(name = "e_idpais", referencedColumnName = "e_idpais")
    @ManyToOne
    private PaisTb eIdpais;
    @OneToMany(mappedBy = "eIddepto")
    private List<MunicipioTb> municipioTbList;

    public DepartamentoTb() {
    }

    public DepartamentoTb(Integer eIddepto) {
        this.eIddepto = eIddepto;
    }

    public Integer getEIddepto() {
        return eIddepto;
    }

    public void setEIddepto(Integer eIddepto) {
        this.eIddepto = eIddepto;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public PaisTb getEIdpais() {
        return eIdpais;
    }

    public void setEIdpais(PaisTb eIdpais) {
        this.eIdpais = eIdpais;
    }

    public List<MunicipioTb> getMunicipioTbList() {
        return municipioTbList;
    }

    public void setMunicipioTbList(List<MunicipioTb> municipioTbList) {
        this.municipioTbList = municipioTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIddepto != null ? eIddepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoTb)) {
            return false;
        }
        DepartamentoTb other = (DepartamentoTb) object;
        if ((this.eIddepto == null && other.eIddepto != null) || (this.eIddepto != null && !this.eIddepto.equals(other.eIddepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DepartamentoTb[ eIddepto=" + eIddepto + " ]";
    }
    
}
