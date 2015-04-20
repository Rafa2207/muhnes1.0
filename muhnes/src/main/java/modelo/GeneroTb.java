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
@Table(name = "genero_tb")
@NamedQueries({
    @NamedQuery(name = "GeneroTb.findAll", query = "SELECT g FROM GeneroTb g"),
    @NamedQuery(name = "GeneroTb.findByEIdgenero", query = "SELECT g FROM GeneroTb g WHERE g.eIdgenero = :eIdgenero"),
    @NamedQuery(name = "GeneroTb.findByCNombre", query = "SELECT g FROM GeneroTb g WHERE g.cNombre = :cNombre"),
    @NamedQuery(name = "GeneroTb.findByCEstado", query = "SELECT g FROM GeneroTb g WHERE g.cEstado = :cEstado"),
    @NamedQuery(name = "GeneroTb.findByEIdfamilia", query = "SELECT g FROM GeneroTb g WHERE g.eIdfamilia = :eIdfamilia")})
public class GeneroTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idgenero")
    private Integer eIdgenero;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @Column(name = "e_idfamilia")
    private Integer eIdfamilia;
    @OneToMany(mappedBy = "eIdgenero")
    private List<EjemplarTb> ejemplarTbList;

    public GeneroTb() {
    }

    public GeneroTb(Integer eIdgenero) {
        this.eIdgenero = eIdgenero;
    }

    public Integer getEIdgenero() {
        return eIdgenero;
    }

    public void setEIdgenero(Integer eIdgenero) {
        this.eIdgenero = eIdgenero;
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

    public Integer getEIdfamilia() {
        return eIdfamilia;
    }

    public void setEIdfamilia(Integer eIdfamilia) {
        this.eIdfamilia = eIdfamilia;
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
        hash += (eIdgenero != null ? eIdgenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneroTb)) {
            return false;
        }
        GeneroTb other = (GeneroTb) object;
        if ((this.eIdgenero == null && other.eIdgenero != null) || (this.eIdgenero != null && !this.eIdgenero.equals(other.eIdgenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GeneroTb[ eIdgenero=" + eIdgenero + " ]";
    }
    
}
