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
@Table(name = "municipio_tb")
@NamedQueries({
    @NamedQuery(name = "MunicipioTb.findAll", query = "SELECT m FROM MunicipioTb m"),
    @NamedQuery(name = "MunicipioTb.findByEIdmunicipio", query = "SELECT m FROM MunicipioTb m WHERE m.eIdmunicipio = :eIdmunicipio"),
    @NamedQuery(name = "MunicipioTb.findByCNombre", query = "SELECT m FROM MunicipioTb m WHERE m.cNombre = :cNombre")})
public class MunicipioTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idmunicipio")
    private Integer eIdmunicipio;
    @Size(max = 20)
    @Column(name = "c_nombre")
    private String cNombre;
    @OneToMany(mappedBy = "eIdmunicipio")
    private List<AreaprotegidaTb> areaprotegidaTbList;
    @OneToMany(mappedBy = "eIdmunicipio")
    private List<CantonTb> cantonTbList;
    @JoinColumn(name = "e_iddepto", referencedColumnName = "e_iddepto")
    @ManyToOne
    private DepartamentoTb eIddepto;

    public MunicipioTb() {
    }

    public MunicipioTb(Integer eIdmunicipio) {
        this.eIdmunicipio = eIdmunicipio;
    }

    public Integer getEIdmunicipio() {
        return eIdmunicipio;
    }

    public void setEIdmunicipio(Integer eIdmunicipio) {
        this.eIdmunicipio = eIdmunicipio;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public List<AreaprotegidaTb> getAreaprotegidaTbList() {
        return areaprotegidaTbList;
    }

    public void setAreaprotegidaTbList(List<AreaprotegidaTb> areaprotegidaTbList) {
        this.areaprotegidaTbList = areaprotegidaTbList;
    }

    public List<CantonTb> getCantonTbList() {
        return cantonTbList;
    }

    public void setCantonTbList(List<CantonTb> cantonTbList) {
        this.cantonTbList = cantonTbList;
    }

    public DepartamentoTb getEIddepto() {
        return eIddepto;
    }

    public void setEIddepto(DepartamentoTb eIddepto) {
        this.eIddepto = eIddepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdmunicipio != null ? eIdmunicipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipioTb)) {
            return false;
        }
        MunicipioTb other = (MunicipioTb) object;
        if ((this.eIdmunicipio == null && other.eIdmunicipio != null) || (this.eIdmunicipio != null && !this.eIdmunicipio.equals(other.eIdmunicipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MunicipioTb[ eIdmunicipio=" + eIdmunicipio + " ]";
    }
    
}
