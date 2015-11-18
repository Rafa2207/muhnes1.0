/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "disminuir_ejemplar_tb")
@NamedQueries({
    @NamedQuery(name = "DisminuirEjemplarTb.findAll", query = "SELECT d FROM DisminuirEjemplarTb d"),
    @NamedQuery(name = "DisminuirEjemplarTb.findByEIddisminuirEjemplar", query = "SELECT d FROM DisminuirEjemplarTb d WHERE d.eIddisminuirEjemplar = :eIddisminuirEjemplar"),
    @NamedQuery(name = "DisminuirEjemplarTb.findByMMotivo", query = "SELECT d FROM DisminuirEjemplarTb d WHERE d.mMotivo = :mMotivo"),
    @NamedQuery(name = "DisminuirEjemplarTb.findByECantidad", query = "SELECT d FROM DisminuirEjemplarTb d WHERE d.eCantidad = :eCantidad"),
    @NamedQuery(name = "DisminuirEjemplarTb.findByFFecha", query = "SELECT d FROM DisminuirEjemplarTb d WHERE d.fFecha = :fFecha")})
public class DisminuirEjemplarTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_iddisminuir_ejemplar")
    private Integer eIddisminuirEjemplar;
    @Size(max = 2147483647)
    @Column(name = "m_motivo")
    private String mMotivo;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idejemplar_vivo", referencedColumnName = "e_idejemplar_vivo")
    @ManyToOne
    private EjemplarVivoTb eIdejemplarVivo;

    public DisminuirEjemplarTb() {
    }

    public DisminuirEjemplarTb(Integer eIddisminuirEjemplar) {
        this.eIddisminuirEjemplar = eIddisminuirEjemplar;
    }

    public Integer getEIddisminuirEjemplar() {
        return eIddisminuirEjemplar;
    }

    public void setEIddisminuirEjemplar(Integer eIddisminuirEjemplar) {
        this.eIddisminuirEjemplar = eIddisminuirEjemplar;
    }

    public String getMMotivo() {
        return mMotivo;
    }

    public void setMMotivo(String mMotivo) {
        this.mMotivo = mMotivo;
    }

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public EjemplarVivoTb getEIdejemplarVivo() {
        return eIdejemplarVivo;
    }

    public void setEIdejemplarVivo(EjemplarVivoTb eIdejemplarVivo) {
        this.eIdejemplarVivo = eIdejemplarVivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIddisminuirEjemplar != null ? eIddisminuirEjemplar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisminuirEjemplarTb)) {
            return false;
        }
        DisminuirEjemplarTb other = (DisminuirEjemplarTb) object;
        if ((this.eIddisminuirEjemplar == null && other.eIddisminuirEjemplar != null) || (this.eIddisminuirEjemplar != null && !this.eIddisminuirEjemplar.equals(other.eIddisminuirEjemplar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DisminuirEjemplarTb[ eIddisminuirEjemplar=" + eIddisminuirEjemplar + " ]";
    }
    
}
