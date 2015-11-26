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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "exhibicion_tb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExhibicionTb.findAll", query = "SELECT e FROM ExhibicionTb e"),
    @NamedQuery(name = "ExhibicionTb.findByEIdexhibicion", query = "SELECT e FROM ExhibicionTb e WHERE e.eIdexhibicion = :eIdexhibicion"),
    @NamedQuery(name = "ExhibicionTb.findByCTipo", query = "SELECT e FROM ExhibicionTb e WHERE e.cTipo = :cTipo"),
    @NamedQuery(name = "ExhibicionTb.findByMDestino", query = "SELECT e FROM ExhibicionTb e WHERE e.mDestino = :mDestino"),
    @NamedQuery(name = "ExhibicionTb.findByMObservaciones", query = "SELECT e FROM ExhibicionTb e WHERE e.mObservaciones = :mObservaciones"),
    @NamedQuery(name = "ExhibicionTb.findByMDescripcion", query = "SELECT e FROM ExhibicionTb e WHERE e.mDescripcion = :mDescripcion"),
    @NamedQuery(name = "ExhibicionTb.findByMNombre", query = "SELECT e FROM ExhibicionTb e WHERE e.mNombre = :mNombre"),
    @NamedQuery(name = "ExhibicionTb.findByEEstado", query = "SELECT e FROM ExhibicionTb e WHERE e.eEstado = :eEstado"),
    @NamedQuery(name = "ExhibicionTb.findByMSolicitante", query = "SELECT e FROM ExhibicionTb e WHERE e.mSolicitante = :mSolicitante"),
    @NamedQuery(name = "ExhibicionTb.findByEIdEjemplar", query = "SELECT e FROM ExhibicionTb e WHERE e.eIdEjemplar = :eIdEjemplar"),
    @NamedQuery(name = "ExhibicionTb.findByEIdResponsable", query = "SELECT e FROM ExhibicionTb e WHERE e.eIdResponsable = :eIdResponsable"),
    @NamedQuery(name = "ExhibicionTb.findByFFechaPrestamo", query = "SELECT e FROM ExhibicionTb e WHERE e.fFechaPrestamo = :fFechaPrestamo"),
    @NamedQuery(name = "ExhibicionTb.findByFFechaRecibido", query = "SELECT e FROM ExhibicionTb e WHERE e.fFechaRecibido = :fFechaRecibido"),
    @NamedQuery(name = "ExhibicionTb.findByECustodio", query = "SELECT e FROM ExhibicionTb e WHERE e.eCustodio = :eCustodio"),
    @NamedQuery(name = "ExhibicionTb.findByHHoraPrestamo", query = "SELECT e FROM ExhibicionTb e WHERE e.hHoraPrestamo = :hHoraPrestamo"),
    @NamedQuery(name = "ExhibicionTb.findByHHoraRecibido", query = "SELECT e FROM ExhibicionTb e WHERE e.hHoraRecibido = :hHoraRecibido")})
public class ExhibicionTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idexhibicion")
    private Integer eIdexhibicion;
    @Size(max = 20)
    @Column(name = "c_tipo")
    private String cTipo;
    @Size(max = 2147483647)
    @Column(name = "m_destino")
    private String mDestino;
    @Size(max = 2147483647)
    @Column(name = "m_observaciones")
    private String mObservaciones;
    @Size(max = 2147483647)
    @Column(name = "m_descripcion")
    private String mDescripcion;
    @Size(max = 2147483647)
    @Column(name = "m_nombre")
    private String mNombre;
    @Column(name = "e_estado")
    private Integer eEstado;
    @Size(max = 2147483647)
    @Column(name = "m_solicitante")
    private String mSolicitante;
    @Column(name = "e_id_ejemplar")
    private Integer eIdEjemplar;
    @Column(name = "e_id_responsable")
    private Integer eIdResponsable;
    @Column(name = "f_fecha_prestamo")
    @Temporal(TemporalType.DATE)
    private Date fFechaPrestamo;
    @Column(name = "f_fecha_recibido")
    @Temporal(TemporalType.DATE)
    private Date fFechaRecibido;
    @Column(name = "e_custodio")
    private Integer eCustodio;
    @Column(name = "h_hora_prestamo")
    @Temporal(TemporalType.TIME)
    private Date hHoraPrestamo;
    @Column(name = "h_hora_recibido")
    @Temporal(TemporalType.TIME)
    private Date hHoraRecibido;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "exhibicionTb")
    private List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;

    public ExhibicionTb() {
    }

    public ExhibicionTb(Integer eIdexhibicion) {
        this.eIdexhibicion = eIdexhibicion;
    }

    public Integer getEIdexhibicion() {
        return eIdexhibicion;
    }

    public void setEIdexhibicion(Integer eIdexhibicion) {
        this.eIdexhibicion = eIdexhibicion;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public String getMDestino() {
        return mDestino;
    }

    public void setMDestino(String mDestino) {
        this.mDestino = mDestino;
    }

    public String getMObservaciones() {
        return mObservaciones;
    }

    public void setMObservaciones(String mObservaciones) {
        this.mObservaciones = mObservaciones;
    }

    public String getMDescripcion() {
        return mDescripcion;
    }

    public void setMDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public String getMNombre() {
        return mNombre;
    }

    public void setMNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public Integer getEEstado() {
        return eEstado;
    }

    public void setEEstado(Integer eEstado) {
        this.eEstado = eEstado;
    }

    public String getMSolicitante() {
        return mSolicitante;
    }

    public void setMSolicitante(String mSolicitante) {
        this.mSolicitante = mSolicitante;
    }

    public Integer getEIdEjemplar() {
        return eIdEjemplar;
    }

    public void setEIdEjemplar(Integer eIdEjemplar) {
        this.eIdEjemplar = eIdEjemplar;
    }

    public Integer getEIdResponsable() {
        return eIdResponsable;
    }

    public void setEIdResponsable(Integer eIdResponsable) {
        this.eIdResponsable = eIdResponsable;
    }

    public Date getFFechaPrestamo() {
        return fFechaPrestamo;
    }

    public void setFFechaPrestamo(Date fFechaPrestamo) {
        this.fFechaPrestamo = fFechaPrestamo;
    }

    public Date getFFechaRecibido() {
        return fFechaRecibido;
    }

    public void setFFechaRecibido(Date fFechaRecibido) {
        this.fFechaRecibido = fFechaRecibido;
    }

    public Integer getECustodio() {
        return eCustodio;
    }

    public void setECustodio(Integer eCustodio) {
        this.eCustodio = eCustodio;
    }

    public Date getHHoraPrestamo() {
        return hHoraPrestamo;
    }

    public void setHHoraPrestamo(Date hHoraPrestamo) {
        this.hHoraPrestamo = hHoraPrestamo;
    }

    public Date getHHoraRecibido() {
        return hHoraRecibido;
    }

    public void setHHoraRecibido(Date hHoraRecibido) {
        this.hHoraRecibido = hHoraRecibido;
    }

    @XmlTransient
    public List<EjemplarParticipaExhibicionTb> getEjemplarParticipaExhibicionTbList() {
        return ejemplarParticipaExhibicionTbList;
    }

    public void setEjemplarParticipaExhibicionTbList(List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList) {
        this.ejemplarParticipaExhibicionTbList = ejemplarParticipaExhibicionTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdexhibicion != null ? eIdexhibicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExhibicionTb)) {
            return false;
        }
        ExhibicionTb other = (ExhibicionTb) object;
        if ((this.eIdexhibicion == null && other.eIdexhibicion != null) || (this.eIdexhibicion != null && !this.eIdexhibicion.equals(other.eIdexhibicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ExhibicionTb[ eIdexhibicion=" + eIdexhibicion + " ]";
    }
    
}
