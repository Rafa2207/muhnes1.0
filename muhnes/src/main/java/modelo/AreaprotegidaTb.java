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
import javax.validation.constraints.Size;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "areaprotegida_tb")
@NamedQueries({
    @NamedQuery(name = "AreaprotegidaTb.findAll", query = "SELECT a FROM AreaprotegidaTb a"),
    @NamedQuery(name = "AreaprotegidaTb.findByEIdarea", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eIdarea = :eIdarea"),
    @NamedQuery(name = "AreaprotegidaTb.findByCNombre", query = "SELECT a FROM AreaprotegidaTb a WHERE a.cNombre = :cNombre"),
    @NamedQuery(name = "AreaprotegidaTb.findByMDescripcion", query = "SELECT a FROM AreaprotegidaTb a WHERE a.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "AreaprotegidaTb.findByELatitudgrados", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eLatitudgrados = :eLatitudgrados"),
    @NamedQuery(name = "AreaprotegidaTb.findByELatitudminutos", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eLatitudminutos = :eLatitudminutos"),
    @NamedQuery(name = "AreaprotegidaTb.findByDLatitudsegundos", query = "SELECT a FROM AreaprotegidaTb a WHERE a.dLatitudsegundos = :dLatitudsegundos"),
    @NamedQuery(name = "AreaprotegidaTb.findByDLatituddecimal", query = "SELECT a FROM AreaprotegidaTb a WHERE a.dLatituddecimal = :dLatituddecimal"),
    @NamedQuery(name = "AreaprotegidaTb.findByELongitudgrados", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eLongitudgrados = :eLongitudgrados"),
    @NamedQuery(name = "AreaprotegidaTb.findByELongitudminutos", query = "SELECT a FROM AreaprotegidaTb a WHERE a.eLongitudminutos = :eLongitudminutos"),
    @NamedQuery(name = "AreaprotegidaTb.findByDLongitudsegundos", query = "SELECT a FROM AreaprotegidaTb a WHERE a.dLongitudsegundos = :dLongitudsegundos"),
    @NamedQuery(name = "AreaprotegidaTb.findByDLongituddecimal", query = "SELECT a FROM AreaprotegidaTb a WHERE a.dLongituddecimal = :dLongituddecimal")})
public class AreaprotegidaTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idarea")
    private Integer eIdarea;
    @Size(max = 150)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "e_latitudgrados")
    private Integer eLatitudgrados;
    @Column(name = "e_latitudminutos")
    private Integer eLatitudminutos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "d_latitudsegundos")
    private Double dLatitudsegundos;
    @Column(name = "d_latituddecimal")
    private Double dLatituddecimal;
    @Column(name = "e_longitudgrados")
    private Integer eLongitudgrados;
    @Column(name = "e_longitudminutos")
    private Integer eLongitudminutos;
    @Column(name = "d_longitudsegundos")
    private Double dLongitudsegundos;
    @Column(name = "d_longituddecimal")
    private Double dLongituddecimal;
    @JoinColumn(name = "e_idmunicipio", referencedColumnName = "e_idmunicipio")
    @ManyToOne
    private MunicipioTb eIdmunicipio;

    public AreaprotegidaTb() {
    }

    public AreaprotegidaTb(Integer eIdarea) {
        this.eIdarea = eIdarea;
    }

    public Integer getEIdarea() {
        return eIdarea;
    }

    public void setEIdarea(Integer eIdarea) {
        this.eIdarea = eIdarea;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Integer getELatitudgrados() {
        return eLatitudgrados;
    }

    public void setELatitudgrados(Integer eLatitudgrados) {
        this.eLatitudgrados = eLatitudgrados;
    }

    public Integer getELatitudminutos() {
        return eLatitudminutos;
    }

    public void setELatitudminutos(Integer eLatitudminutos) {
        this.eLatitudminutos = eLatitudminutos;
    }

    public Double getDLatitudsegundos() {
        return dLatitudsegundos;
    }

    public void setDLatitudsegundos(Double dLatitudsegundos) {
        this.dLatitudsegundos = dLatitudsegundos;
    }

    public Double getDLatituddecimal() {
        return dLatituddecimal;
    }

    public void setDLatituddecimal(Double dLatituddecimal) {
        this.dLatituddecimal = dLatituddecimal;
    }

    public Integer getELongitudgrados() {
        return eLongitudgrados;
    }

    public void setELongitudgrados(Integer eLongitudgrados) {
        this.eLongitudgrados = eLongitudgrados;
    }

    public Integer getELongitudminutos() {
        return eLongitudminutos;
    }

    public void setELongitudminutos(Integer eLongitudminutos) {
        this.eLongitudminutos = eLongitudminutos;
    }

    public Double getDLongitudsegundos() {
        return dLongitudsegundos;
    }

    public void setDLongitudsegundos(Double dLongitudsegundos) {
        this.dLongitudsegundos = dLongitudsegundos;
    }

    public Double getDLongituddecimal() {
        return dLongituddecimal;
    }

    public void setDLongituddecimal(Double dLongituddecimal) {
        this.dLongituddecimal = dLongituddecimal;
    }

    public MunicipioTb getEIdmunicipio() {
        return eIdmunicipio;
    }

    public void setEIdmunicipio(MunicipioTb eIdmunicipio) {
        this.eIdmunicipio = eIdmunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdarea != null ? eIdarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaprotegidaTb)) {
            return false;
        }
        AreaprotegidaTb other = (AreaprotegidaTb) object;
        if ((this.eIdarea == null && other.eIdarea != null) || (this.eIdarea != null && !this.eIdarea.equals(other.eIdarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AreaprotegidaTb[ eIdarea=" + eIdarea + " ]";
    }
    
}
