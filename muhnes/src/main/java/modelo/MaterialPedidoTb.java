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
@Table(name = "material_pedido_tb")
@NamedQueries({
    @NamedQuery(name = "MaterialPedidoTb.findAll", query = "SELECT m FROM MaterialPedidoTb m"),
    @NamedQuery(name = "MaterialPedidoTb.findByEIdmaterial", query = "SELECT m FROM MaterialPedidoTb m WHERE m.materialPedidoTbPK.eIdmaterial = :eIdmaterial"),
    @NamedQuery(name = "MaterialPedidoTb.findByEIdpedido", query = "SELECT m FROM MaterialPedidoTb m WHERE m.materialPedidoTbPK.eIdpedido = :eIdpedido"),
    @NamedQuery(name = "MaterialPedidoTb.findByECantidad", query = "SELECT m FROM MaterialPedidoTb m WHERE m.eCantidad = :eCantidad")})
public class MaterialPedidoTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialPedidoTbPK materialPedidoTbPK;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
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

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
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
