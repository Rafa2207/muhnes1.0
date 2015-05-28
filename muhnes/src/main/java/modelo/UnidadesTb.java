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
@Table(name = "unidades_tb")
@NamedQueries({
    @NamedQuery(name = "UnidadesTb.findAll", query = "SELECT u FROM UnidadesTb u"),
    @NamedQuery(name = "UnidadesTb.findByEIdunidad", query = "SELECT u FROM UnidadesTb u WHERE u.eIdunidad = :eIdunidad"),
    @NamedQuery(name = "UnidadesTb.findByCNombre", query = "SELECT u FROM UnidadesTb u WHERE u.cNombre = :cNombre"),
    @NamedQuery(name = "UnidadesTb.findByCAbreviatura", query = "SELECT u FROM UnidadesTb u WHERE u.cAbreviatura = :cAbreviatura"),
    @NamedQuery(name = "UnidadesTb.findByCTipo", query = "SELECT u FROM UnidadesTb u WHERE u.cTipo = :cTipo")})
public class UnidadesTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idunidad")
    private Integer eIdunidad;
    @Size(max = 150)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 10)
    @Column(name = "c_abreviatura")
    private String cAbreviatura;
    @Size(max = 50)
    @Column(name = "c_tipo")
    private String cTipo;
    @OneToMany(mappedBy = "eIdunidad")
    private List<InsumoTb> insumoTbList;

    public UnidadesTb() {
    }

    public UnidadesTb(Integer eIdunidad) {
        this.eIdunidad = eIdunidad;
    }

    public Integer getEIdunidad() {
        return eIdunidad;
    }

    public void setEIdunidad(Integer eIdunidad) {
        this.eIdunidad = eIdunidad;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCAbreviatura() {
        return cAbreviatura;
    }

    public void setCAbreviatura(String cAbreviatura) {
        this.cAbreviatura = cAbreviatura;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public List<InsumoTb> getInsumoTbList() {
        return insumoTbList;
    }

    public void setInsumoTbList(List<InsumoTb> insumoTbList) {
        this.insumoTbList = insumoTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdunidad != null ? eIdunidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadesTb)) {
            return false;
        }
        UnidadesTb other = (UnidadesTb) object;
        if ((this.eIdunidad == null && other.eIdunidad != null) || (this.eIdunidad != null && !this.eIdunidad.equals(other.eIdunidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UnidadesTb[ eIdunidad=" + eIdunidad + " ]";
    }
    
}
