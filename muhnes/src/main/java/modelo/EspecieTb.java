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
@Table(name = "especie_tb")
@NamedQueries({
    @NamedQuery(name = "EspecieTb.findAll", query = "SELECT e FROM EspecieTb e"),
    @NamedQuery(name = "EspecieTb.findByEIdespecie", query = "SELECT e FROM EspecieTb e WHERE e.eIdespecie = :eIdespecie"),
    @NamedQuery(name = "EspecieTb.findByCNombre", query = "SELECT e FROM EspecieTb e WHERE e.cNombre = :cNombre"),
    @NamedQuery(name = "EspecieTb.findByCEstado", query = "SELECT e FROM EspecieTb e WHERE e.cEstado = :cEstado"),
    @NamedQuery(name = "EspecieTb.findByEIdgenero", query = "SELECT e FROM EspecieTb e WHERE e.eIdgenero = :eIdgenero")})
public class EspecieTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idespecie")
    private Integer eIdespecie;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @Column(name = "e_idgenero")
    private Integer eIdgenero;
    @OneToMany(mappedBy = "eIdespecie")
    private List<EjemplarTb> ejemplarTbList;

    public EspecieTb() {
    }

    public EspecieTb(Integer eIdespecie) {
        this.eIdespecie = eIdespecie;
    }

    public Integer getEIdespecie() {
        return eIdespecie;
    }

    public void setEIdespecie(Integer eIdespecie) {
        this.eIdespecie = eIdespecie;
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

    public Integer getEIdgenero() {
        return eIdgenero;
    }

    public void setEIdgenero(Integer eIdgenero) {
        this.eIdgenero = eIdgenero;
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
        hash += (eIdespecie != null ? eIdespecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EspecieTb)) {
            return false;
        }
        EspecieTb other = (EspecieTb) object;
        if ((this.eIdespecie == null && other.eIdespecie != null) || (this.eIdespecie != null && !this.eIdespecie.equals(other.eIdespecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EspecieTb[ eIdespecie=" + eIdespecie + " ]";
    }
    
}
