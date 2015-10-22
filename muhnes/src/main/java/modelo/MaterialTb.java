/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "material_tb")
@NamedQueries({
    @NamedQuery(name = "MaterialTb.findAll", query = "SELECT m FROM MaterialTb m"),
    @NamedQuery(name = "MaterialTb.findByEIdmaterial", query = "SELECT m FROM MaterialTb m WHERE m.eIdmaterial = :eIdmaterial"),
    @NamedQuery(name = "MaterialTb.findByCNombre", query = "SELECT m FROM MaterialTb m WHERE m.cNombre = :cNombre"),
    @NamedQuery(name = "MaterialTb.findByDCantidad", query = "SELECT m FROM MaterialTb m WHERE m.dCantidad = :dCantidad"),
    @NamedQuery(name = "MaterialTb.findByMDescripcion", query = "SELECT m FROM MaterialTb m WHERE m.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "MaterialTb.findByCEstado", query = "SELECT m FROM MaterialTb m WHERE m.cEstado = :cEstado"),
    @NamedQuery(name = "MaterialTb.findByMCodigobarras", query = "SELECT m FROM MaterialTb m WHERE m.mCodigobarras = :mCodigobarras"),
    @NamedQuery(name = "MaterialTb.findByMMarca", query = "SELECT m FROM MaterialTb m WHERE m.mMarca = :mMarca"),
    @NamedQuery(name = "MaterialTb.findByDCantidadmin", query = "SELECT m FROM MaterialTb m WHERE m.dCantidadmin = :dCantidadmin"),
    @NamedQuery(name = "MaterialTb.findByETipo", query = "SELECT m FROM MaterialTb m WHERE m.eTipo = :eTipo")})
public class MaterialTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idmaterial")
    private Integer eIdmaterial;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_cantidad")
    private Double dCantidad;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @Size(max = 2147483647)
    @Column(name = "m_codigobarras")
    private String mCodigobarras;
    @Size(max = 2147483647)
    @Column(name = "m_marca")
    private String mMarca;
    @Column(name = "d_cantidadmin")
    private Double dCantidadmin;
    @Column(name = "e_tipo")
    private Integer eTipo;
    @JoinColumn(name = "e_idunidad", referencedColumnName = "e_idunidad")
    @ManyToOne
    private UnidadesTb eIdunidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialTb")
    private List<MaterialDespachoTb> materialDespachoTbList;

    public MaterialTb() {
    }

    public MaterialTb(Integer eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    public Integer getEIdmaterial() {
        return eIdmaterial;
    }

    public void setEIdmaterial(Integer eIdmaterial) {
        this.eIdmaterial = eIdmaterial;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public Double getDCantidad() {
        return dCantidad;
    }

    public void setDCantidad(Double dCantidad) {
        this.dCantidad = dCantidad;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public String getMCodigobarras() {
        return mCodigobarras;
    }

    public void setMCodigobarras(String mCodigobarras) {
        this.mCodigobarras = mCodigobarras;
    }

    public String getMMarca() {
        return mMarca;
    }

    public void setMMarca(String mMarca) {
        this.mMarca = mMarca;
    }

    public Double getDCantidadmin() {
        return dCantidadmin;
    }

    public void setDCantidadmin(Double dCantidadmin) {
        this.dCantidadmin = dCantidadmin;
    }

    public Integer getETipo() {
        return eTipo;
    }

    public void setETipo(Integer eTipo) {
        this.eTipo = eTipo;
    }

    public UnidadesTb getEIdunidad() {
        return eIdunidad;
    }

    public void setEIdunidad(UnidadesTb eIdunidad) {
        this.eIdunidad = eIdunidad;
    }

    public List<MaterialDespachoTb> getMaterialDespachoTbList() {
        return materialDespachoTbList;
    }

    public void setMaterialDespachoTbList(List<MaterialDespachoTb> materialDespachoTbList) {
        this.materialDespachoTbList = materialDespachoTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdmaterial != null ? eIdmaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialTb)) {
            return false;
        }
        MaterialTb other = (MaterialTb) object;
        if ((this.eIdmaterial == null && other.eIdmaterial != null) || (this.eIdmaterial != null && !this.eIdmaterial.equals(other.eIdmaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaterialTb[ eIdmaterial=" + eIdmaterial + " ]";
    }
    
}
