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
@Table(name = "nombrecomun_tb")
@NamedQueries({
    @NamedQuery(name = "NombrecomunTb.findAll", query = "SELECT n FROM NombrecomunTb n"),
    @NamedQuery(name = "NombrecomunTb.findByEIdnombrecomun", query = "SELECT n FROM NombrecomunTb n WHERE n.eIdnombrecomun = :eIdnombrecomun"),
    @NamedQuery(name = "NombrecomunTb.findByCNombre", query = "SELECT n FROM NombrecomunTb n WHERE n.cNombre = :cNombre"),
    @NamedQuery(name = "NombrecomunTb.findByCIdioma", query = "SELECT n FROM NombrecomunTb n WHERE n.cIdioma = :cIdioma")})
public class NombrecomunTb implements Serializable {
    @JoinColumn(name = "e_idtaxonomia", referencedColumnName = "e_idtaxonomia")
    @ManyToOne
    private TaxonomiaTb eIdtaxonomia;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idnombrecomun")
    private Integer eIdnombrecomun;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_idioma")
    private String cIdioma;

    public NombrecomunTb() {
    }

    public NombrecomunTb(Integer eIdnombrecomun) {
        this.eIdnombrecomun = eIdnombrecomun;
    }

    public Integer getEIdnombrecomun() {
        return eIdnombrecomun;
    }

    public void setEIdnombrecomun(Integer eIdnombrecomun) {
        this.eIdnombrecomun = eIdnombrecomun;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCIdioma() {
        return cIdioma;
    }

    public void setCIdioma(String cIdioma) {
        this.cIdioma = cIdioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdnombrecomun != null ? eIdnombrecomun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NombrecomunTb)) {
            return false;
        }
        NombrecomunTb other = (NombrecomunTb) object;
        if ((this.eIdnombrecomun == null && other.eIdnombrecomun != null) || (this.eIdnombrecomun != null && !this.eIdnombrecomun.equals(other.eIdnombrecomun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.NombrecomunTb[ eIdnombrecomun=" + eIdnombrecomun + " ]";
    }

    public TaxonomiaTb getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(TaxonomiaTb eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }
    
}
