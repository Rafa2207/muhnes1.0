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
@Table(name = "exhibicion_tb")
@NamedQueries({
    @NamedQuery(name = "ExhibicionTb.findAll", query = "SELECT e FROM ExhibicionTb e"),
    @NamedQuery(name = "ExhibicionTb.findByEIdexhibicion", query = "SELECT e FROM ExhibicionTb e WHERE e.eIdexhibicion = :eIdexhibicion"),
    @NamedQuery(name = "ExhibicionTb.findByCTipo", query = "SELECT e FROM ExhibicionTb e WHERE e.cTipo = :cTipo"),
    @NamedQuery(name = "ExhibicionTb.findByMDestino", query = "SELECT e FROM ExhibicionTb e WHERE e.mDestino = :mDestino"),
    @NamedQuery(name = "ExhibicionTb.findByMObservaciones", query = "SELECT e FROM ExhibicionTb e WHERE e.mObservaciones = :mObservaciones"),
    @NamedQuery(name = "ExhibicionTb.findByFFechaSalida", query = "SELECT e FROM ExhibicionTb e WHERE e.fFechaSalida = :fFechaSalida"),
    @NamedQuery(name = "ExhibicionTb.findByFFechaReingreso", query = "SELECT e FROM ExhibicionTb e WHERE e.fFechaReingreso = :fFechaReingreso"),
    @NamedQuery(name = "ExhibicionTb.findByMDireccion", query = "SELECT e FROM ExhibicionTb e WHERE e.mDireccion = :mDireccion"),
    @NamedQuery(name = "ExhibicionTb.findByCResponsable", query = "SELECT e FROM ExhibicionTb e WHERE e.cResponsable = :cResponsable")})
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
    @Column(name = "f_fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fFechaSalida;
    @Column(name = "f_fecha_reingreso")
    @Temporal(TemporalType.DATE)
    private Date fFechaReingreso;
    @Size(max = 2147483647)
    @Column(name = "m_direccion")
    private String mDireccion;
    @Size(max = 100)
    @Column(name = "c_responsable")
    private String cResponsable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exhibicionTb")
    private List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "e_idusuario")
    @ManyToOne
    private UsuarioTb idUsuario;

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

    public Date getFFechaSalida() {
        return fFechaSalida;
    }

    public void setFFechaSalida(Date fFechaSalida) {
        this.fFechaSalida = fFechaSalida;
    }

    public Date getFFechaReingreso() {
        return fFechaReingreso;
    }

    public void setFFechaReingreso(Date fFechaReingreso) {
        this.fFechaReingreso = fFechaReingreso;
    }

    public String getMDireccion() {
        return mDireccion;
    }

    public void setMDireccion(String mDireccion) {
        this.mDireccion = mDireccion;
    }

    public String getCResponsable() {
        return cResponsable;
    }

    public void setCResponsable(String cResponsable) {
        this.cResponsable = cResponsable;
    }

    public List<EjemplarParticipaExhibicionTb> getEjemplarParticipaExhibicionTbList() {
        return ejemplarParticipaExhibicionTbList;
    }

    public void setEjemplarParticipaExhibicionTbList(List<EjemplarParticipaExhibicionTb> ejemplarParticipaExhibicionTbList) {
        this.ejemplarParticipaExhibicionTbList = ejemplarParticipaExhibicionTbList;
    }

    public UsuarioTb getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioTb idUsuario) {
        this.idUsuario = idUsuario;
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
