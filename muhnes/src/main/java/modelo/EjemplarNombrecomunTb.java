/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "ejemplar_nombrecomun_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarNombrecomunTb.findAll", query = "SELECT e FROM EjemplarNombrecomunTb e"),
    @NamedQuery(name = "EjemplarNombrecomunTb.findByEIdejemplarnombrecomun", query = "SELECT e FROM EjemplarNombrecomunTb e WHERE e.eIdejemplarnombrecomun = :eIdejemplarnombrecomun"),
    @NamedQuery(name = "EjemplarNombrecomunTb.findByCNombrecomun", query = "SELECT e FROM EjemplarNombrecomunTb e WHERE e.cNombrecomun = :cNombrecomun")})
public class EjemplarNombrecomunTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idejemplarnombrecomun")
    private Integer eIdejemplarnombrecomun;
    @Size(max = 100)
    @Column(name = "c_nombrecomun")
    private String cNombrecomun;
    @JoinColumn(name = "e_idejemplar", referencedColumnName = "e_idejemplar")
    @ManyToOne
    private EjemplarTb eIdejemplar;

    public EjemplarNombrecomunTb() {
    }

    public EjemplarNombrecomunTb(Integer eIdejemplarnombrecomun) {
        this.eIdejemplarnombrecomun = eIdejemplarnombrecomun;
    }

    public Integer getEIdejemplarnombrecomun() {
        return eIdejemplarnombrecomun;
    }

    public void setEIdejemplarnombrecomun(Integer eIdejemplarnombrecomun) {
        this.eIdejemplarnombrecomun = eIdejemplarnombrecomun;
    }

    public String getCNombrecomun() {
        return cNombrecomun;
    }

    public void setCNombrecomun(String cNombrecomun) {
        this.cNombrecomun = cNombrecomun;
    }

    public EjemplarTb getEIdejemplar() {
        return eIdejemplar;
    }

    public void setEIdejemplar(EjemplarTb eIdejemplar) {
        this.eIdejemplar = eIdejemplar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdejemplarnombrecomun != null ? eIdejemplarnombrecomun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarNombrecomunTb)) {
            return false;
        }
        EjemplarNombrecomunTb other = (EjemplarNombrecomunTb) object;
        if ((this.eIdejemplarnombrecomun == null && other.eIdejemplarnombrecomun != null) || (this.eIdejemplarnombrecomun != null && !this.eIdejemplarnombrecomun.equals(other.eIdejemplarnombrecomun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarNombrecomunTb[ eIdejemplarnombrecomun=" + eIdejemplarnombrecomun + " ]";
    }
    
}
