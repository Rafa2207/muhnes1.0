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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "ejemplar_tb")
@NamedQueries({
    @NamedQuery(name = "EjemplarTb.findAll", query = "SELECT e FROM EjemplarTb e"),
    @NamedQuery(name = "EjemplarTb.findByEIdejemplar", query = "SELECT e FROM EjemplarTb e WHERE e.eIdejemplar = :eIdejemplar"),
    @NamedQuery(name = "EjemplarTb.findByMDescripcion", query = "SELECT e FROM EjemplarTb e WHERE e.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "EjemplarTb.findByECantDuplicado", query = "SELECT e FROM EjemplarTb e WHERE e.eCantDuplicado = :eCantDuplicado"),
    @NamedQuery(name = "EjemplarTb.findByCCalificativo", query = "SELECT e FROM EjemplarTb e WHERE e.cCalificativo = :cCalificativo"),
    @NamedQuery(name = "EjemplarTb.findByECorrelativo", query = "SELECT e FROM EjemplarTb e WHERE e.eCorrelativo = :eCorrelativo"),
    @NamedQuery(name = "EjemplarTb.findByFFechaInicioIdent", query = "SELECT e FROM EjemplarTb e WHERE e.fFechaInicioIdent = :fFechaInicioIdent"),
    @NamedQuery(name = "EjemplarTb.findByFFechaFinIdent", query = "SELECT e FROM EjemplarTb e WHERE e.fFechaFinIdent = :fFechaFinIdent"),
    @NamedQuery(name = "EjemplarTb.findByBEstadonotas", query = "SELECT e FROM EjemplarTb e WHERE e.bEstadonotas = :bEstadonotas"),
    @NamedQuery(name = "EjemplarTb.findByEResponsable", query = "SELECT e FROM EjemplarTb e WHERE e.eResponsable = :eResponsable"),
    @NamedQuery(name = "EjemplarTb.findByCCodigoentrada", query = "SELECT e FROM EjemplarTb e WHERE e.cCodigoentrada = :cCodigoentrada")})
public class EjemplarTb implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ejemplarTb")
    private List<EjemplarDonacionTb> ejemplarDonacionTbList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ejemplarTb")
    private List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idejemplar")
    private Integer eIdejemplar;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Column(name = "e_cant_duplicado")
    private Integer eCantDuplicado;
    @Size(max = 7)
    @Column(name = "c_calificativo")
    private String cCalificativo;
    @Column(name = "e_correlativo")
    private Integer eCorrelativo;
    @Column(name = "f_fecha_inicio_ident")
    @Temporal(TemporalType.DATE)
    private Date fFechaInicioIdent;
    @Column(name = "f_fecha_fin_ident")
    @Temporal(TemporalType.DATE)
    private Date fFechaFinIdent;
    @Column(name = "b_estadonotas")
    private Boolean bEstadonotas;
    @Column(name = "e_responsable")
    private Integer eResponsable;
    @Size(max = 20)
    @Column(name = "c_codigoentrada")
    private String cCodigoentrada;
    @Column(name = "e_estado")
    private Integer eEstado;
    @JoinColumn(name = "e_idtaxonomia", referencedColumnName = "e_idtaxonomia")
    @ManyToOne
    private TaxonomiaTb eIdtaxonomia;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;
    @JoinColumn(name = "e_idlocalidad", referencedColumnName = "e_idlocalidad")
    @ManyToOne
    private LocalidadTb eIdlocalidad;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ejemplarTb")
    private List<AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbList;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ejemplarTb")
    private List<AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbIDList;

    public EjemplarTb() {
    }

    public List<AgenteIdentificaEjemplarTb> getAgenteIdentificaEjemplarTbIDList() {
        return AgenteIdentificaEjemplarTbIDList;
    }

    public void setAgenteIdentificaEjemplarTbIDList(List<AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbIDList) {
        this.AgenteIdentificaEjemplarTbIDList = AgenteIdentificaEjemplarTbIDList;
    }

    public List<AgenteIdentificaEjemplarTb> getAgenteIdentificaEjemplarTbList() {
        return AgenteIdentificaEjemplarTbList;
    }

    public void setAgenteIdentificaEjemplarTbList(List<AgenteIdentificaEjemplarTb> AgenteIdentificaEjemplarTbList) {
        this.AgenteIdentificaEjemplarTbList = AgenteIdentificaEjemplarTbList;
    }

    public EjemplarTb(Integer eIdejemplar) {
        this.eIdejemplar = eIdejemplar;
    }

    public Integer getEIdejemplar() {
        return eIdejemplar;
    }

    public void setEIdejemplar(Integer eIdejemplar) {
        this.eIdejemplar = eIdejemplar;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public Integer getECantDuplicado() {
        return eCantDuplicado;
    }

    public void setECantDuplicado(Integer eCantDuplicado) {
        this.eCantDuplicado = eCantDuplicado;
    }

    public String getCCalificativo() {
        return cCalificativo;
    }

    public void setCCalificativo(String cCalificativo) {
        this.cCalificativo = cCalificativo;
    }

    public Integer getECorrelativo() {
        return eCorrelativo;
    }

    public void setECorrelativo(Integer eCorrelativo) {
        this.eCorrelativo = eCorrelativo;
    }

    public Date getFFechaInicioIdent() {
        return fFechaInicioIdent;
    }

    public void setFFechaInicioIdent(Date fFechaInicioIdent) {
        this.fFechaInicioIdent = fFechaInicioIdent;
    }

    public Date getFFechaFinIdent() {
        return fFechaFinIdent;
    }

    public void setFFechaFinIdent(Date fFechaFinIdent) {
        this.fFechaFinIdent = fFechaFinIdent;
    }

    public Boolean getBEstadonotas() {
        return bEstadonotas;
    }

    public void setBEstadonotas(Boolean bEstadonotas) {
        this.bEstadonotas = bEstadonotas;
    }

    public Integer getEResponsable() {
        return eResponsable;
    }

    public void setEResponsable(Integer eResponsable) {
        this.eResponsable = eResponsable;
    }

    public String getCCodigoentrada() {
        return cCodigoentrada;
    }

    public void setCCodigoentrada(String cCodigoentrada) {
        this.cCodigoentrada = cCodigoentrada;
    }

    public TaxonomiaTb getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(TaxonomiaTb eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public LocalidadTb getEIdlocalidad() {
        return eIdlocalidad;
    }

    public Integer geteEstado() {
        return eEstado;
    }

    public void seteEstado(Integer eEstado) {
        this.eEstado = eEstado;
    }

    public void setEIdlocalidad(LocalidadTb eIdlocalidad) {
        this.eIdlocalidad = eIdlocalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdejemplar != null ? eIdejemplar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjemplarTb)) {
            return false;
        }
        EjemplarTb other = (EjemplarTb) object;
        if ((this.eIdejemplar == null && other.eIdejemplar != null) || (this.eIdejemplar != null && !this.eIdejemplar.equals(other.eIdejemplar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EjemplarTb[ eIdejemplar=" + eIdejemplar + " ]";
    }

    @XmlTransient
    public List<EjemplarParticipaExhibicionTb> getEjemplarParticipaExhibicionTbList() {
        return ejemplarParticipaExhibicionTbList;
    }

    public void setEjemplarParticipaExhibicionTbList(List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList) {
        this.ejemplarParticipaExhibicionTbList = ejemplarParticipaExhibicionTbList;
    }

    public List<EjemplarDonacionTb> getEjemplarDonacionTbList() {
        return ejemplarDonacionTbList;
    }

    public void setEjemplarDonacionTbList(List<EjemplarDonacionTb> ejemplarDonacionTbList) {
        this.ejemplarDonacionTbList = ejemplarDonacionTbList;
    }
    
}
