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
@Table(name = "pedido_tb")
@NamedQueries({
    @NamedQuery(name = "PedidoTb.findAll", query = "SELECT p FROM PedidoTb p"),
    @NamedQuery(name = "PedidoTb.findByEIdpedido", query = "SELECT p FROM PedidoTb p WHERE p.eIdpedido = :eIdpedido"),
    @NamedQuery(name = "PedidoTb.findByMDescripcion", query = "SELECT p FROM PedidoTb p WHERE p.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "PedidoTb.findByFFecha", query = "SELECT p FROM PedidoTb p WHERE p.fFecha = :fFecha")})
public class PedidoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idpedido")
    private Integer eIdpedido;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @JoinColumn(name = "e_idmaterial", referencedColumnName = "e_idmaterial")
    @ManyToOne
    private MaterialTb eIdmaterial;

    public PedidoTb() {
    }

    public PedidoTb(Integer eIdpedido) {
        this.eIdpedido = eIdpedido;
    }

    public Integer getEIdpedido() {
        return eIdpedido;
    }

    public void setEIdpedido(Integer eIdpedido) {
        this.eIdpedido = eIdpedido;
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

    public MaterialTb getEIdmaterial() {
        return eIdmaterial;
    }

    public void setEIdmaterial(MaterialTb eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdpedido != null ? eIdpedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoTb)) {
            return false;
        }
        PedidoTb other = (PedidoTb) object;
        if ((this.eIdpedido == null && other.eIdpedido != null) || (this.eIdpedido != null && !this.eIdpedido.equals(other.eIdpedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PedidoTb[ eIdpedido=" + eIdpedido + " ]";
    }
    
}
