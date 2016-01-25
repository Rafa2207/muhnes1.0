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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "ejemplar_donacion_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarDonacionTb.findAll", query = "SELECT e FROM EjemplarDonacionTb e"),
    @NamedQuery(name = "EjemplarDonacionTb.findByEIdejemplardonacion", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.eIdejemplardonacion = :eIdejemplardonacion"),
    @NamedQuery(name = "EjemplarDonacionTb.findByEIddonacion", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.eIddonacion = :eIddonacion"),
    @NamedQuery(name = "EjemplarDonacionTb.findByBEstado", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.bEstado = :bEstado"),
    @NamedQuery(name = "EjemplarDonacionTb.findByEIdinstitucion", query = "SELECT e FROM EjemplarDonacionTb e WHERE e.eIdinstitucion = :eIdinstitucion")})
public class EjemplarDonacionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idejemplardonacion")
    private Integer eIdejemplardonacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_iddonacion")
    private int eIddonacion;
    @Column(name = "b_estado")
    private boolean bEstado;
    @Column(name = "e_idinstitucion")
    private Integer eIdinstitucion;
    @JoinColumn(name = "e_idejemplar", referencedColumnName = "e_idejemplar")
    @ManyToOne(optional = false)
    private EjemplarTb eIdejemplar;

    public EjemplarDonacionTb() {
    }

    public EjemplarDonacionTb(Integer eIdejemplardonacion) {
        this.eIdejemplardonacion = eIdejemplardonacion;
    }

    public EjemplarDonacionTb(Integer eIdejemplardonacion, int eIddonacion) {
        this.eIdejemplardonacion = eIdejemplardonacion;
        this.eIddonacion = eIddonacion;
    }

    public Integer getEIdejemplardonacion() {
        return eIdejemplardonacion;
    }

    public void setEIdejemplardonacion(Integer eIdejemplardonacion) {
        this.eIdejemplardonacion = eIdejemplardonacion;
    }

    public int getEIddonacion() {
        return eIddonacion;
    }

    public void setEIddonacion(int eIddonacion) {
        this.eIddonacion = eIddonacion;
    }

    public boolean getBEstado() {
        return bEstado;
    }

    public void setBEstado(boolean bEstado) {
        this.bEstado = bEstado;
    }

    public Integer getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(Integer eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
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
        hash += (eIdejemplardonacion != null ? eIdejemplardonacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarDonacionTb)) {
            return false;
        }
        EjemplarDonacionTb other = (EjemplarDonacionTb) object;
        if ((this.eIdejemplardonacion == null && other.eIdejemplardonacion != null) || (this.eIdejemplardonacion != null && !this.eIdejemplardonacion.equals(other.eIdejemplardonacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarDonacionTb[ eIdejemplardonacion=" + eIdejemplardonacion + " ]";
    }
    
}
