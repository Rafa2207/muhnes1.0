/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafa
 */
@Embeddable
public class MaterialPedidoTbPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idmaterial")
    private int eIdmaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_idpedido")
    private int eIdpedido;

    public MaterialPedidoTbPK() {
    }

    public MaterialPedidoTbPK(int eIdmaterial, int eIdpedido) {
        this.eIdmaterial = eIdmaterial;
        this.eIdpedido = eIdpedido;
    }

    public int getEIdmaterial() {
        return eIdmaterial;
    }

    public void setEIdmaterial(int eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    public int getEIdpedido() {
        return eIdpedido;
    }

    public void setEIdpedido(int eIdpedido) {
        this.eIdpedido = eIdpedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eIdmaterial;
        hash += (int) eIdpedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialPedidoTbPK)) {
            return false;
        }
        MaterialPedidoTbPK other = (MaterialPedidoTbPK) object;
        if (this.eIdmaterial != other.eIdmaterial) {
            return false;
        }
        if (this.eIdpedido != other.eIdpedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialPedidoTbPK[ eIdmaterial=" + eIdmaterial + ", eIdpedido=" + eIdpedido + " ]";
    }
    
}
