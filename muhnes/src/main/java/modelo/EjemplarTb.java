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
    @NamedQuery(name = "EjemplarTb.findByFFechaFinIdent", query = "SELECT e FROM EjemplarTb e WHERE e.fFechaFinIdent = :fFechaFinIdent")})
public class EjemplarTb implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ejemplarTb")
    private List<AgenteIdentificaEjemplarTb> agenteIdentificaEjemplarTbList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ejemplarTb")
    private List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;
    @OneToMany(mappedBy = "eIdejemplar")
    private List<NombrecomunTb> nombrecomunTbList;
    @JoinColumn(name = "e_variedad", referencedColumnName = "e_idvariedad")
    @ManyToOne
    private VariedadTb eVariedad;
    @JoinColumn(name = "e_idsubespecie", referencedColumnName = "e_idsubespecie")
    @ManyToOne
    private SubespecieTb eIdsubespecie;
    @JoinColumn(name = "e_idproyecto", referencedColumnName = "e_idproyecto")
    @ManyToOne
    private ProyectoTb eIdproyecto;
    @JoinColumn(name = "e_idgenero", referencedColumnName = "e_idgenero")
    @ManyToOne
    private GeneroTb eIdgenero;
    @JoinColumn(name = "e_idfamilia", referencedColumnName = "e_idfamilia")
    @ManyToOne
    private FamiliaTb eIdfamilia;
    @JoinColumn(name = "e_idespecie", referencedColumnName = "e_idespecie")
    @ManyToOne
    private EspecieTb eIdespecie;
    @OneToMany(mappedBy = "eIdejemplar")
    private List<ImagenTb> imagenTbList;

    public EjemplarTb() {
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

    public List<AgenteIdentificaEjemplarTb> getAgenteIdentificaEjemplarTbList() {
        return agenteIdentificaEjemplarTbList;
    }

    public void setAgenteIdentificaEjemplarTbList(List<AgenteIdentificaEjemplarTb> agenteIdentificaEjemplarTbList) {
        this.agenteIdentificaEjemplarTbList = agenteIdentificaEjemplarTbList;
    }

    public List<EjemplarParticipaExhibicionTb> getEjemplarParticipaExhibicionTbList() {
        return ejemplarParticipaExhibicionTbList;
    }

    public void setEjemplarParticipaExhibicionTbList(List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList) {
        this.ejemplarParticipaExhibicionTbList = ejemplarParticipaExhibicionTbList;
    }

    public List<NombrecomunTb> getNombrecomunTbList() {
        return nombrecomunTbList;
    }

    public void setNombrecomunTbList(List<NombrecomunTb> nombrecomunTbList) {
        this.nombrecomunTbList = nombrecomunTbList;
    }

    public VariedadTb getEVariedad() {
        return eVariedad;
    }

    public void setEVariedad(VariedadTb eVariedad) {
        this.eVariedad = eVariedad;
    }

    public SubespecieTb getEIdsubespecie() {
        return eIdsubespecie;
    }

    public void setEIdsubespecie(SubespecieTb eIdsubespecie) {
        this.eIdsubespecie = eIdsubespecie;
    }

    public ProyectoTb getEIdproyecto() {
        return eIdproyecto;
    }

    public void setEIdproyecto(ProyectoTb eIdproyecto) {
        this.eIdproyecto = eIdproyecto;
    }

    public GeneroTb getEIdgenero() {
        return eIdgenero;
    }

    public void setEIdgenero(GeneroTb eIdgenero) {
        this.eIdgenero = eIdgenero;
    }

    public FamiliaTb getEIdfamilia() {
        return eIdfamilia;
    }

    public void setEIdfamilia(FamiliaTb eIdfamilia) {
        this.eIdfamilia = eIdfamilia;
    }

    public EspecieTb getEIdespecie() {
        return eIdespecie;
    }

    public void setEIdespecie(EspecieTb eIdespecie) {
        this.eIdespecie = eIdespecie;
    }

    public List<ImagenTb> getImagenTbList() {
        return imagenTbList;
    }

    public void setImagenTbList(List<ImagenTb> imagenTbList) {
        this.imagenTbList = imagenTbList;
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
    
}
