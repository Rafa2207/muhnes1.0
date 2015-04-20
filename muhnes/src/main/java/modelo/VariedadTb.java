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
@Table(name = "variedad_tb")
@NamedQueries({
    @NamedQuery(name = "VariedadTb.findAll", query = "SELECT v FROM VariedadTb v"),
    @NamedQuery(name = "VariedadTb.findByEIdvariedad", query = "SELECT v FROM VariedadTb v WHERE v.eIdvariedad = :eIdvariedad"),
    @NamedQuery(name = "VariedadTb.findByCNombre", query = "SELECT v FROM VariedadTb v WHERE v.cNombre = :cNombre"),
    @NamedQuery(name = "VariedadTb.findByCEstado", query = "SELECT v FROM VariedadTb v WHERE v.cEstado = :cEstado"),
    @NamedQuery(name = "VariedadTb.findByEIdsubespecie", query = "SELECT v FROM VariedadTb v WHERE v.eIdsubespecie = :eIdsubespecie")})
public class VariedadTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idvariedad")
    private Integer eIdvariedad;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @Column(name = "e_idsubespecie")
    private Integer eIdsubespecie;
    @OneToMany(mappedBy = "eVariedad")
    private List<EjemplarTb> ejemplarTbList;

    public VariedadTb() {
    }

    public VariedadTb(Integer eIdvariedad) {
        this.eIdvariedad = eIdvariedad;
    }

    public Integer getEIdvariedad() {
        return eIdvariedad;
    }

    public void setEIdvariedad(Integer eIdvariedad) {
        this.eIdvariedad = eIdvariedad;
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

    public Integer getEIdsubespecie() {
        return eIdsubespecie;
    }

    public void setEIdsubespecie(Integer eIdsubespecie) {
        this.eIdsubespecie = eIdsubespecie;
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
        hash += (eIdvariedad != null ? eIdvariedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariedadTb)) {
            return false;
        }
        VariedadTb other = (VariedadTb) object;
        if ((this.eIdvariedad == null && other.eIdvariedad != null) || (this.eIdvariedad != null && !this.eIdvariedad.equals(other.eIdvariedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VariedadTb[ eIdvariedad=" + eIdvariedad + " ]";
    }
    
}
