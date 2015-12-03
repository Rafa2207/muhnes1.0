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
@Table(name = "ejemplar_donacion_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarDonacionTb.findAll", query = "SELECT e FROM EjemplarDonacionTb e"),
    @NamedQuery(name = "EjemplarDonacionTb.findByEIddonacion", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.ejemplarDonacionTbPK.eIddonacion = :eIddonacion"),
    @NamedQuery(name = "EjemplarDonacionTb.findByEIdejemplar", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.ejemplarDonacionTbPK.eIdejemplar = :eIdejemplar"),
    @NamedQuery(name = "EjemplarDonacionTb.findByECantidad", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.eCantidad = :eCantidad")})
public class EjemplarDonacionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EjemplarDonacionTbPK ejemplarDonacionTbPK;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @JoinColumn(name = "e_idejemplar", referencedColumnName = "e_idejemplar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EjemplarTb ejemplarTb;
    @JoinColumn(name = "e_iddonacion", referencedColumnName = "e_iddonacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DonacionTb donacionTb;

    public EjemplarDonacionTb() {
    }

    public EjemplarDonacionTb(EjemplarDonacionTbPK ejemplarDonacionTbPK) {
        this.ejemplarDonacionTbPK = ejemplarDonacionTbPK;
    }

    public EjemplarDonacionTb(int eIddonacion, int eIdejemplar) {
        this.ejemplarDonacionTbPK = new EjemplarDonacionTbPK(eIddonacion, eIdejemplar);
    }

    public EjemplarDonacionTbPK getEjemplarDonacionTbPK() {
        return ejemplarDonacionTbPK;
    }

    public void setEjemplarDonacionTbPK(EjemplarDonacionTbPK ejemplarDonacionTbPK) {
        this.ejemplarDonacionTbPK = ejemplarDonacionTbPK;
    }

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
    }

    public EjemplarTb getEjemplarTb() {
        return ejemplarTb;
    }

    public void setEjemplarTb(EjemplarTb ejemplarTb) {
        this.ejemplarTb = ejemplarTb;
    }

    public DonacionTb getDonacionTb() {
        return donacionTb;
    }

    public void setDonacionTb(DonacionTb donacionTb) {
        this.donacionTb = donacionTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ejemplarDonacionTbPK != null ? ejemplarDonacionTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarDonacionTb)) {
            return false;
        }
        EjemplarDonacionTb other = (EjemplarDonacionTb) object;
        if ((this.ejemplarDonacionTbPK == null && other.ejemplarDonacionTbPK != null) || (this.ejemplarDonacionTbPK != null && !this.ejemplarDonacionTbPK.equals(other.ejemplarDonacionTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarDonacionTb[ ejemplarDonacionTbPK=" + ejemplarDonacionTbPK + " ]";
    }
    
}
