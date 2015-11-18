/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "ejemplar_vivo_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarVivoTb.findAll", query = "SELECT e FROM EjemplarVivoTb e"),
    @NamedQuery(name = "EjemplarVivoTb.findByEIdejemplarVivo", query = "SELECT e FROM EjemplarVivoTb e WHERE e.eIdejemplarVivo = :eIdejemplarVivo"),
    @NamedQuery(name = "EjemplarVivoTb.findByCNombre", query = "SELECT e FROM EjemplarVivoTb e WHERE e.cNombre = :cNombre"),
    @NamedQuery(name = "EjemplarVivoTb.findByMDescripcion", query = "SELECT e FROM EjemplarVivoTb e WHERE e.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "EjemplarVivoTb.findByFFecha", query = "SELECT e FROM EjemplarVivoTb e WHERE e.fFecha = :fFecha"),
    @NamedQuery(name = "EjemplarVivoTb.findByECantidad", query = "SELECT e FROM EjemplarVivoTb e WHERE e.eCantidad = :eCantidad"),
    @NamedQuery(name = "EjemplarVivoTb.findByCJardin", query = "SELECT e FROM EjemplarVivoTb e WHERE e.cJardin = :cJardin")})
public class EjemplarVivoTb implements Serializable {
    @OneToMany(mappedBy = "eIdejemplarVivo")
    private List<DisminuirEjemplarTb> disminuirEjemplarTbList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idejemplar_vivo")
    private Integer eIdejemplarVivo;
    @Size(max = 80)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "f_fecha")
    @Temporal(TemporalType.DATE)
    private Date fFecha;
    @Column(name = "e_cantidad")
    private Integer eCantidad;
    @Size(max = 50)
    @Column(name = "c_jardin")
    private String cJardin;
    @JoinColumn(name = "e_idtaxonomia", referencedColumnName = "e_idtaxonomia")
    @ManyToOne
    private TaxonomiaTb eIdtaxonomia;

    public EjemplarVivoTb() {
    }

    public EjemplarVivoTb(Integer eIdejemplarVivo) {
        this.eIdejemplarVivo = eIdejemplarVivo;
    }

    public Integer getEIdejemplarVivo() {
        return eIdejemplarVivo;
    }

    public void setEIdejemplarVivo(Integer eIdejemplarVivo) {
        this.eIdejemplarVivo = eIdejemplarVivo;
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

    public Date getFFecha() {
        return fFecha;
    }

    public void setFFecha(Date fFecha) {
        this.fFecha = fFecha;
    }

    public Integer getECantidad() {
        return eCantidad;
    }

    public void setECantidad(Integer eCantidad) {
        this.eCantidad = eCantidad;
    }

    public String getCJardin() {
        return cJardin;
    }

    public void setCJardin(String cJardin) {
        this.cJardin = cJardin;
    }

    public TaxonomiaTb getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(TaxonomiaTb eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdejemplarVivo != null ? eIdejemplarVivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarVivoTb)) {
            return false;
        }
        EjemplarVivoTb other = (EjemplarVivoTb) object;
        if ((this.eIdejemplarVivo == null && other.eIdejemplarVivo != null) || (this.eIdejemplarVivo != null && !this.eIdejemplarVivo.equals(other.eIdejemplarVivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarVivoTb[ eIdejemplarVivo=" + eIdejemplarVivo + " ]";
    }

    public List<DisminuirEjemplarTb> getDisminuirEjemplarTbList() {
        return disminuirEjemplarTbList;
    }

    public void setDisminuirEjemplarTbList(List<DisminuirEjemplarTb> disminuirEjemplarTbList) {
        this.disminuirEjemplarTbList = disminuirEjemplarTbList;
    }
    
}
