/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "material_pedido_tb")
@NamedQueries({
    @NamedQuery(name = "MaterialPedidoTb.findAll", query = "SELECT m FROM MaterialPedidoTb m"),
    @NamedQuery(name = "MaterialPedidoTb.findByEIdmaterial", query = "SELECT m FROM MaterialPedidoTb m WHERE m.materialPedidoTbPK.eIdmaterial = :eIdmaterial"),
    @NamedQuery(name = "MaterialPedidoTb.findByEIdpedido", query = "SELECT m FROM MaterialPedidoTb m WHERE m.materialPedidoTbPK.eIdpedido = :eIdpedido"),
    @NamedQuery(name = "MaterialPedidoTb.findByDCantidad", query = "SELECT m FROM MaterialPedidoTb m WHERE m.dCantidad = :dCantidad"),
    @NamedQuery(name = "MaterialPedidoTb.findByDEntrada", query = "SELECT m FROM MaterialPedidoTb m WHERE m.dEntrada = :dEntrada"),
    @NamedQuery(name = "MaterialPedidoTb.findByFFechaRecibido", query = "SELECT m FROM MaterialPedidoTb m WHERE m.fFechaRecibido = :fFechaRecibido")})
public class MaterialPedidoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialPedidoTbPK materialPedidoTbPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @Column(name = "d_entrada")
    private Double dEntrada;
    @Column(name = "f_fecha_recibido")
    @Temporal(TemporalType.DATE)
    private Date fFechaRecibido;
    @JoinColumn(name = "e_idpedido", referencedColumnName = "e_idpedido", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PedidoTb pedidoTb;
    @JoinColumn(name = "e_idmaterial", referencedColumnName = "e_idmaterial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MaterialTb materialTb;

    public MaterialPedidoTb() {
    }

    public MaterialPedidoTb(MaterialPedidoTbPK materialPedidoTbPK) {
        this.materialPedidoTbPK = materialPedidoTbPK;
    }

    public MaterialPedidoTb(int eIdmaterial, int eIdpedido) {
        this.materialPedidoTbPK = new MaterialPedidoTbPK(eIdmaterial, eIdpedido);
    }

    public MaterialPedidoTbPK getMaterialPedidoTbPK() {
        return materialPedidoTbPK;
    }

    public void setMaterialPedidoTbPK(MaterialPedidoTbPK materialPedidoTbPK) {
        this.materialPedidoTbPK = materialPedidoTbPK;
    }

    public Double getDCantidad() {
        return dCantidad;
    }

    public void setDCantidad(Double dCantidad) {
        this.dCantidad = dCantidad;
    }

    public Double getDEntrada() {
        return dEntrada;
    }

    public void setDEntrada(Double dEntrada) {
        this.dEntrada = dEntrada;
    }

    public Date getFFechaRecibido() {
        return fFechaRecibido;
    }

    public void setFFechaRecibido(Date fFechaRecibido) {
        this.fFechaRecibido = fFechaRecibido;
    }

    public PedidoTb getPedidoTb() {
        return pedidoTb;
    }

    public void setPedidoTb(PedidoTb pedidoTb) {
        this.pedidoTb = pedidoTb;
    }

    public MaterialTb getMaterialTb() {
        return materialTb;
    }

    public void setMaterialTb(MaterialTb materialTb) {
        this.materialTb = materialTb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialPedidoTbPK != null ? materialPedidoTbPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialPedidoTb)) {
            return false;
        }
        MaterialPedidoTb other = (MaterialPedidoTb) object;
        if ((this.materialPedidoTbPK == null && other.materialPedidoTbPK != null) || (this.materialPedidoTbPK != null && !this.materialPedidoTbPK.equals(other.materialPedidoTbPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialPedidoTb[ materialPedidoTbPK=" + materialPedidoTbPK + " ]";
    }
    
}
