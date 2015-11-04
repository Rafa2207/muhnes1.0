/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "exhibicion_ejemplar_tb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExhibicionEjemplarTb.findAll", query = "SELECT e FROM ExhibicionEjemplarTb e"),
    @NamedQuery(name = "ExhibicionEjemplarTb.findByEIdEjemplar", query = "SELECT e FROM ExhibicionEjemplarTb e WHERE e.exhibicionEjemplarTbPK.eIdEjemplar = :eIdEjemplar"),
    @NamedQuery(name = "ExhibicionEjemplarTb.findByEIdExhibicion", query = "SELECT e FROM ExhibicionEjemplarTb e WHERE e.exhibicionEjemplarTbPK.eIdExhibicion = :eIdExhibicion"),
    @NamedQuery(name = "ExhibicionEjemplarTb.findByECantidad", query = "SELECT e FROM ExhibicionEjemplarTb e WHERE e.eCantidad = :eCantidad")})
public class ExhibicionEjemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExhibicionEjemplarTbPK exhibicionEjemplarTbPK;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @JoinColumn(name = "e_id_exhibicion", referencedColumnName = "e_idexhibicion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ExhibicionTb exhibicionTb;
    @JoinColumn(name = "e_id_ejemplar", referencedColumnName = "e_idejemplar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EjemplarTb ejemplarTb;

    public ExhibicionEjemplarTb() {
    }

    public ExhibicionEjemplarTb(ExhibicionEjemplarTbPK exhibicionEjemplarTbPK) {
        this.exhibicionEjemplarTbPK = exhibicionEjemplarTbPK;
    }

    public ExhibicionEjemplarTb(int eIdEjemplar, int eIdExhibicion) {
        this.exhibicionEjemplarTbPK = new ExhibicionEjemplarTbPK(eIdEjemplar, eIdExhibicion);
    }

    public ExhibicionEjemplarTbPK getExhibicionEjemplarTbPK() {
        return exhibicionEjemplarTbPK;
    }

    public void setExhibicionEjemplarTbPK(ExhibicionEjemplarTbPK exhibicionEjemplarTbPK) {
        this.exhibicionEjemplarTbPK = exhibicionEjemplarTbPK;
    }

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
    }

    public ExhibicionTb getExhibicionTb() {
        return exhibicionTb;
    }

    public void setExhibicionTb(ExhibicionTb exhibicionTb) {
        this.exhibicionTb = exhibicionTb;
    }

    public EjemplarTb getEjemplarTb() {
        return ejemplarTb;
    }

    public void setEjemplarTb(EjemplarTb ejemplarTb) {
        this.ejemplarTb = ejemplarTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exhibicionEjemplarTbPK != null ? exhibicionEjemplarTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExhibicionEjemplarTb)) {
            return false;
        }
        ExhibicionEjemplarTb other = (ExhibicionEjemplarTb) object;
        if ((this.exhibicionEjemplarTbPK == null && other.exhibicionEjemplarTbPK != null) || (this.exhibicionEjemplarTbPK != null && !this.exhibicionEjemplarTbPK.equals(other.exhibicionEjemplarTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ExhibicionEjemplarTb[ exhibicionEjemplarTbPK=" + exhibicionEjemplarTbPK + " ]";
    }
    
}
