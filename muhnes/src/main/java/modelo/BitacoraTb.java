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
@Table(name = "bitacora_tb")
@NamedQueries({
    @NamedQuery(name = "BitacoraTb.findAll", query = "SELECT b FROM BitacoraTb b"),
    @NamedQuery(name = "BitacoraTb.findByEIdbitacora", query = "SELECT b FROM BitacoraTb b WHERE b.eIdbitacora = :eIdbitacora"),
    @NamedQuery(name = "BitacoraTb.findByMDescripcion", query = "SELECT b FROM BitacoraTb b WHERE b.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "BitacoraTb.findByFFecha", query = "SELECT b FROM BitacoraTb b WHERE b.fFecha = :fFecha")})
public class BitacoraTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idbitacora")
    private Integer eIdbitacora;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idusuario", referencedColumnName = "e_idusuario")
    @ManyToOne
    private UsuarioTb eIdusuario;

    public BitacoraTb() {
    }

    public BitacoraTb(Integer eIdbitacora) {
        this.eIdbitacora = eIdbitacora;
    }

    public Integer getEIdbitacora() {
        return eIdbitacora;
    }

    public void setEIdbitacora(Integer eIdbitacora) {
        this.eIdbitacora = eIdbitacora;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public UsuarioTb getEIdusuario() {
        return eIdusuario;
    }

    public void setEIdusuario(UsuarioTb eIdusuario) {
        this.eIdusuario = eIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdbitacora != null ? eIdbitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacoraTb)) {
            return false;
        }
        BitacoraTb other = (BitacoraTb) object;
        if ((this.eIdbitacora == null && other.eIdbitacora != null) || (this.eIdbitacora != null && !this.eIdbitacora.equals(other.eIdbitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.BitacoraTb[ eIdbitacora=" + eIdbitacora + " ]";
    }
    
}
