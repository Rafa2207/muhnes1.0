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
@Table(name = "familia_tb")
@NamedQueries({
    @NamedQuery(name = "FamiliaTb.findAll", query = "SELECT f FROM FamiliaTb f"),
    @NamedQuery(name = "FamiliaTb.findByEIdfamilia", query = "SELECT f FROM FamiliaTb f WHERE f.eIdfamilia = :eIdfamilia"),
    @NamedQuery(name = "FamiliaTb.findByCNombre", query = "SELECT f FROM FamiliaTb f WHERE f.cNombre = :cNombre"),
    @NamedQuery(name = "FamiliaTb.findByCEstado", query = "SELECT f FROM FamiliaTb f WHERE f.cEstado = :cEstado")})
public class FamiliaTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idfamilia")
    private Integer eIdfamilia;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @OneToMany(mappedBy = "eIdfamilia")
    private List<EjemplarTb> ejemplarTbList;

    public FamiliaTb() {
    }

    public FamiliaTb(Integer eIdfamilia) {
        this.eIdfamilia = eIdfamilia;
    }

    public Integer getEIdfamilia() {
        return eIdfamilia;
    }

    public void setEIdfamilia(Integer eIdfamilia) {
        this.eIdfamilia = eIdfamilia;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public List<EjemplarTb> getEjemplarTbList() {
        return ejemplarTbList;
    }

    public void setEjemplarTbList(List<EjemplarTb> ejemplarTbList) {
        this.ejemplarTbList = ejemplarTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdfamilia != null ? eIdfamilia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamiliaTb)) {
            return false;
        }
        FamiliaTb other = (FamiliaTb) object;
        if ((this.eIdfamilia == null && other.eIdfamilia != null) || (this.eIdfamilia != null && !this.eIdfamilia.equals(other.eIdfamilia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.FamiliaTb[ eIdfamilia=" + eIdfamilia + " ]";
    }
    
}
