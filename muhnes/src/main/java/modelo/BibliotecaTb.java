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
@Table(name = "biblioteca_tb")
@NamedQueries({
    @NamedQuery(name = "BibliotecaTb.findAll", query = "SELECT b FROM BibliotecaTb b"),
    @NamedQuery(name = "BibliotecaTb.findByEIdbiblioteca", query = "SELECT b FROM BibliotecaTb b WHERE b.eIdbiblioteca = :eIdbiblioteca"),
    @NamedQuery(name = "BibliotecaTb.findByMMision", query = "SELECT b FROM BibliotecaTb b WHERE b.mMision = :mMision"),
    @NamedQuery(name = "BibliotecaTb.findByMVision", query = "SELECT b FROM BibliotecaTb b WHERE b.mVision = :mVision"),
    @NamedQuery(name = "BibliotecaTb.findByMHistoria", query = "SELECT b FROM BibliotecaTb b WHERE b.mHistoria = :mHistoria"),
    @NamedQuery(name = "BibliotecaTb.findByMObjetivo", query = "SELECT b FROM BibliotecaTb b WHERE b.mObjetivo = :mObjetivo"),
    @NamedQuery(name = "BibliotecaTb.findByMInformacion", query = "SELECT b FROM BibliotecaTb b WHERE b.mInformacion = :mInformacion"),
    @NamedQuery(name = "BibliotecaTb.findByMHistoriab", query = "SELECT b FROM BibliotecaTb b WHERE b.mHistoriab = :mHistoriab")})
public class BibliotecaTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idbiblioteca")
    private Integer eIdbiblioteca;
    @Size(max = 2147483647)
    @Column(name = "m_mision")
    private String mMision;
    @Size(max = 2147483647)
    @Column(name = "m_vision")
    private String mVision;
    @Size(max = 2147483647)
    @Column(name = "m_historia")
    private String mHistoria;
    @Size(max = 2147483647)
    @Column(name = "m_objetivo")
    private String mObjetivo;
    @Size(max = 2147483647)
    @Column(name = "m_informacion")
    private String mInformacion;
    @Size(max = 2147483647)
    @Column(name = "m_historiab")
    private String mHistoriab;
    @JoinColumn(name = "e_idinstitucion", referencedColumnName = "e_idinstitucion")
    @ManyToOne
    private InstitucionTb eIdinstitucion;

    public BibliotecaTb() {
    }

    public BibliotecaTb(Integer eIdbiblioteca) {
        this.eIdbiblioteca = eIdbiblioteca;
    }

    public Integer getEIdbiblioteca() {
        return eIdbiblioteca;
    }

    public void setEIdbiblioteca(Integer eIdbiblioteca) {
        this.eIdbiblioteca = eIdbiblioteca;
    }

    public String getMMision() {
        return mMision;
    }

    public void setMMision(String mMision) {
        this.mMision = mMision;
    }

    public String getMVision() {
        return mVision;
    }

    public void setMVision(String mVision) {
        this.mVision = mVision;
    }

    public String getMHistoria() {
        return mHistoria;
    }

    public void setMHistoria(String mHistoria) {
        this.mHistoria = mHistoria;
    }

    public String getMObjetivo() {
        return mObjetivo;
    }

    public void setMObjetivo(String mObjetivo) {
        this.mObjetivo = mObjetivo;
    }

    public String getMInformacion() {
        return mInformacion;
    }

    public void setMInformacion(String mInformacion) {
        this.mInformacion = mInformacion;
    }

    public String getMHistoriab() {
        return mHistoriab;
    }

    public void setMHistoriab(String mHistoriab) {
        this.mHistoriab = mHistoriab;
    }

    public InstitucionTb getEIdinstitucion() {
        return eIdinstitucion;
    }

    public void setEIdinstitucion(InstitucionTb eIdinstitucion) {
        this.eIdinstitucion = eIdinstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdbiblioteca != null ? eIdbiblioteca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BibliotecaTb)) {
            return false;
        }
        BibliotecaTb other = (BibliotecaTb) object;
        if ((this.eIdbiblioteca == null && other.eIdbiblioteca != null) || (this.eIdbiblioteca != null && !this.eIdbiblioteca.equals(other.eIdbiblioteca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.BibliotecaTb[ eIdbiblioteca=" + eIdbiblioteca + " ]";
    }
    
}
