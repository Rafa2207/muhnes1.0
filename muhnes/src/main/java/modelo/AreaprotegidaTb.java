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
@Table(name = "areaprotegida_tb")
@NamedQueries({
    @NamedQuery(name = "AreaprotegidaTb.findAll", query = "SELECT a FROM AreaprotegidaTb a"),
    @NamedQuery(name = "AreaprotegidaTb.findByEIdarea", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eIdarea = :eIdarea"),
    @NamedQuery(name = "AreaprotegidaTb.findByCNombre", query = "SELECT a FROM AreaprotegidaTb a WHERE a.cNombre = :cNombre"),
    @NamedQuery(name = "AreaprotegidaTb.findByMDescripcion", query = "SELECT a FROM AreaprotegidaTb a WHERE a.mDescripcion = :mDescripcion")})
public class AreaprotegidaTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idarea")
    private Integer eIdarea;
    @Size(max = 20)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @JoinColumn(name = "e_idmunicipio", referencedColumnName = "e_idmunicipio")
    @ManyToOne
    private MunicipioTb eIdmunicipio;

    public AreaprotegidaTb() {
    }

    public AreaprotegidaTb(Integer eIdarea) {
        this.eIdarea = eIdarea;
    }

    public Integer getEIdarea() {
        return eIdarea;
    }

    public void setEIdarea(Integer eIdarea) {
        this.eIdarea = eIdarea;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public MunicipioTb getEIdmunicipio() {
        return eIdmunicipio;
    }

    public void setEIdmunicipio(MunicipioTb eIdmunicipio) {
        this.eIdmunicipio = eIdmunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdarea != null ? eIdarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaprotegidaTb)) {
            return false;
        }
        AreaprotegidaTb other = (AreaprotegidaTb) object;
        if ((this.eIdarea == null && other.eIdarea != null) || (this.eIdarea != null && !this.eIdarea.equals(other.eIdarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AreaprotegidaTb[ eIdarea=" + eIdarea + " ]";
    }
    
}
