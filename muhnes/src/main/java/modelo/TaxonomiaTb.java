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
@Table(name = "taxonomia_tb")
@NamedQueries({
    @NamedQuery(name = "TaxonomiaTb.findAll", query = "SELECT t FROM TaxonomiaTb t"),
    @NamedQuery(name = "TaxonomiaTb.findByEIdtaxonomia", query = "SELECT t FROM TaxonomiaTb t WHERE t.eIdtaxonomia = :eIdtaxonomia"),
    @NamedQuery(name = "TaxonomiaTb.findByCNombre", query = "SELECT t FROM TaxonomiaTb t WHERE t.cNombre = :cNombre"),
    @NamedQuery(name = "TaxonomiaTb.findByCEstado", query = "SELECT t FROM TaxonomiaTb t WHERE t.cEstado = :cEstado"),
    @NamedQuery(name = "TaxonomiaTb.findByERango", query = "SELECT t FROM TaxonomiaTb t WHERE t.eRango = :eRango"),
    @NamedQuery(name = "TaxonomiaTb.findByCTipo", query = "SELECT t FROM TaxonomiaTb t WHERE t.cTipo = :cTipo")})
public class TaxonomiaTb implements Serializable {
    @OneToMany(mappedBy = "eIdtaxonomia")
    private List<EjemplarTb> ejemplarTbList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idtaxonomia")
    private Integer eIdtaxonomia;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 50)
    @Column(name = "c_estado")
    private String cEstado;
    @Column(name = "e_rango")
    private Integer eRango;
    @Size(max = 20)
    @Column(name = "c_tipo")
    private String cTipo;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "taxonomiaTb")
    private List<AgenteTaxonomiaTb> agenteTaxonomiaTbList;
    @OneToMany(mappedBy = "eIdniveltaxonomia")
    private List<TaxonomiaTb> taxonomiaTbList;
    @JoinColumn(name = "e_idniveltaxonomia", referencedColumnName = "e_idtaxonomia")
    @ManyToOne
    private TaxonomiaTb eIdniveltaxonomia;
    @OneToMany(orphanRemoval = true,mappedBy = "eIdtaxonomia")
    private List<ImagenTb> imagenTbList;
    @OneToMany(orphanRemoval = true, mappedBy = "eIdtaxonomia")
    private List<NombrecomunTb> nombrecomunTbList;

    public TaxonomiaTb() {
    }

    public TaxonomiaTb(Integer eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    public Integer getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(Integer eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCEstado() {
        return cEstado;
    }

    public void setCEstado(String cEstado) {
        this.cEstado = cEstado;
    }

    public Integer getERango() {
        return eRango;
    }

    public void setERango(Integer eRango) {
        this.eRango = eRango;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public List<AgenteTaxonomiaTb> getAgenteTaxonomiaTbList() {
        return agenteTaxonomiaTbList;
    }

    public void setAgenteTaxonomiaTbList(List<AgenteTaxonomiaTb> agenteTaxonomiaTbList) {
        this.agenteTaxonomiaTbList = agenteTaxonomiaTbList;
    }

    public List<TaxonomiaTb> getTaxonomiaTbList() {
        return taxonomiaTbList;
    }

    public void setTaxonomiaTbList(List<TaxonomiaTb> taxonomiaTbList) {
        this.taxonomiaTbList = taxonomiaTbList;
    }

    public TaxonomiaTb getEIdniveltaxonomia() {
        return eIdniveltaxonomia;
    }

    public void setEIdniveltaxonomia(TaxonomiaTb eIdniveltaxonomia) {
        this.eIdniveltaxonomia = eIdniveltaxonomia;
    }

    public List<ImagenTb> getImagenTbList() {
        return imagenTbList;
    }

    public void setImagenTbList(List<ImagenTb> imagenTbList) {
        this.imagenTbList = imagenTbList;
    }

    public List<NombrecomunTb> getNombrecomunTbList() {
        return nombrecomunTbList;
    }

    public void setNombrecomunTbList(List<NombrecomunTb> nombrecomunTbList) {
        this.nombrecomunTbList = nombrecomunTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdtaxonomia != null ? eIdtaxonomia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaxonomiaTb)) {
            return false;
        }
        TaxonomiaTb other = (TaxonomiaTb) object;
        if ((this.eIdtaxonomia == null && other.eIdtaxonomia != null) || (this.eIdtaxonomia != null && !this.eIdtaxonomia.equals(other.eIdtaxonomia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TaxonomiaTb[ eIdtaxonomia=" + eIdtaxonomia + " ]";
    }

    public List<EjemplarTb> getEjemplarTbList() {
        return ejemplarTbList;
    }

    public void setEjemplarTbList(List<EjemplarTb> ejemplarTbList) {
        this.ejemplarTbList = ejemplarTbList;
    }
    
}
