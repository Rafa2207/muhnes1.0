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

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "ejemplar_participa_exhibicion_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarParticipaExhibicionTb.findAll", query = "SELECT e FROM EjemplarParticipaExhibicionTb e"),
    @NamedQuery(name = "EjemplarParticipaExhibicionTb.findByEIdejemplar", query = "SELECT e FROM EjemplarParticipaExhibicionTb e WHERE e.ejemplarParticipaExhibicionTbPK.eIdejemplar = :eIdejemplar"),
    @NamedQuery(name = "EjemplarParticipaExhibicionTb.findByEIdexhibicion", query = "SELECT e FROM EjemplarParticipaExhibicionTb e WHERE e.ejemplarParticipaExhibicionTbPK.eIdexhibicion = :eIdexhibicion"),
    @NamedQuery(name = "EjemplarParticipaExhibicionTb.findByEEstado", query = "SELECT e FROM EjemplarParticipaExhibicionTb e WHERE e.eEstado = :eEstado")})
public class EjemplarParticipaExhibicionTb implements Serializable {
    @Column(name = "e_estado")
    private Integer eEstado;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EjemplarParticipaExhibicionTbPK ejemplarParticipaExhibicionTbPK;
    @JoinColumn(name = "e_idexhibicion", referencedColumnName = "e_idexhibicion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ExhibicionTb exhibicionTb;
    @JoinColumn(name = "e_idejemplar", referencedColumnName = "e_idejemplar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EjemplarTb ejemplarTb;

    public EjemplarParticipaExhibicionTb() {
    }

    public EjemplarParticipaExhibicionTb(EjemplarParticipaExhibicionTbPK ejemplarParticipaExhibicionTbPK) {
        this.ejemplarParticipaExhibicionTbPK = ejemplarParticipaExhibicionTbPK;
    }

    public EjemplarParticipaExhibicionTb(int eIdejemplar, int eIdexhibicion) {
        this.ejemplarParticipaExhibicionTbPK = new EjemplarParticipaExhibicionTbPK(eIdejemplar, eIdexhibicion);
    }

    public EjemplarParticipaExhibicionTbPK getEjemplarParticipaExhibicionTbPK() {
        return ejemplarParticipaExhibicionTbPK;
    }

    public void setEjemplarParticipaExhibicionTbPK(EjemplarParticipaExhibicionTbPK ejemplarParticipaExhibicionTbPK) {
        this.ejemplarParticipaExhibicionTbPK = ejemplarParticipaExhibicionTbPK;
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
        hash += (ejemplarParticipaExhibicionTbPK != null ? ejemplarParticipaExhibicionTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarParticipaExhibicionTb)) {
            return false;
        }
        EjemplarParticipaExhibicionTb other = (EjemplarParticipaExhibicionTb) object;
        if ((this.ejemplarParticipaExhibicionTbPK == null && other.ejemplarParticipaExhibicionTbPK != null) || (this.ejemplarParticipaExhibicionTbPK != null && !this.ejemplarParticipaExhibicionTbPK.equals(other.ejemplarParticipaExhibicionTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarParticipaExhibicionTb[ ejemplarParticipaExhibicionTbPK=" + ejemplarParticipaExhibicionTbPK + " ]";
    }

    public Integer getEEstado() {
        return eEstado;
    }

    public void setEEstado(Integer eEstado) {
        this.eEstado = eEstado;
    }
    
}
