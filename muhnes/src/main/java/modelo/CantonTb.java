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
@Table(name = "canton_tb")
@NamedQueries({
    @NamedQuery(name = "CantonTb.findAll", query = "SELECT c FROM CantonTb c"),
    @NamedQuery(name = "CantonTb.findByEIdcanton", query = "SELECT c FROM CantonTb c WHERE c.eIdcanton = :eIdcanton"),
    @NamedQuery(name = "CantonTb.findByCNombre", query = "SELECT c FROM CantonTb c WHERE c.cNombre = :cNombre")})
public class CantonTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idcanton")
    private Integer eIdcanton;
    @Size(max = 80)
    @Column(name = "c_nombre")
    private String cNombre;
    @JoinColumn(name = "e_idmunicipio", referencedColumnName = "e_idmunicipio")
    @ManyToOne
    private MunicipioTb eIdmunicipio;
    @OneToMany(mappedBy = "eIdcanton")
    private List<LocalidadTb> localidadTbList;

    public CantonTb() {
    }

    public CantonTb(Integer eIdcanton) {
        this.eIdcanton = eIdcanton;
    }

    public Integer getEIdcanton() {
        return eIdcanton;
    }

    public void setEIdcanton(Integer eIdcanton) {
        this.eIdcanton = eIdcanton;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public MunicipioTb getEIdmunicipio() {
        return eIdmunicipio;
    }

    public void setEIdmunicipio(MunicipioTb eIdmunicipio) {
        this.eIdmunicipio = eIdmunicipio;
    }

    public List<LocalidadTb> getLocalidadTbList() {
        return localidadTbList;
    }

    public void setLocalidadTbList(List<LocalidadTb> localidadTbList) {
        this.localidadTbList = localidadTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdcanton != null ? eIdcanton.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CantonTb)) {
            return false;
        }
        CantonTb other = (CantonTb) object;
        if ((this.eIdcanton == null && other.eIdcanton != null) || (this.eIdcanton != null && !this.eIdcanton.equals(other.eIdcanton))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CantonTb[ eIdcanton=" + eIdcanton + " ]";
    }
    
}
