/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "subespecie_tb")
@NamedQueries({
    @NamedQuery(name = "SubespecieTb.findAll", query = "SELECT s FROM SubespecieTb s"),
    @NamedQuery(name = "SubespecieTb.findByEIdsubespecie", query = "SELECT s FROM SubespecieTb s WHERE s.eIdsubespecie = :eIdsubespecie"),
    @NamedQuery(name = "SubespecieTb.findByCNombre", query = "SELECT s FROM SubespecieTb s WHERE s.cNombre = :cNombre"),
    @NamedQuery(name = "SubespecieTb.findByCEstado", query = "SELECT s FROM SubespecieTb s WHERE s.cEstado = :cEstado"),
    @NamedQuery(name = "SubespecieTb.findByEIdespecie", query = "SELECT s FROM SubespecieTb s WHERE s.eIdespecie = :eIdespecie")})
public class SubespecieTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idsubespecie")
    private Integer eIdsubespecie;
    @Size(max = 30)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 20)
    @Column(name = "c_estado")
    private String cEstado;
    @Column(name = "e_idespecie")
    private Integer eIdespecie;
    @OneToMany(mappedBy = "eIdsubespecie")
    private List<EjemplarTb> ejemplarTbList;

    public SubespecieTb() {
    }

    public SubespecieTb(Integer eIdsubespecie) {
        this.eIdsubespecie = eIdsubespecie;
    }

    public Integer getEIdsubespecie() {
        return eIdsubespecie;
    }

    public void setEIdsubespecie(Integer eIdsubespecie) {
        this.eIdsubespecie = eIdsubespecie;
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

    public Integer getEIdespecie() {
        return eIdespecie;
    }

    public void setEIdespecie(Integer eIdespecie) {
        this.eIdespecie = eIdespecie;
    }

    public List<EjemplarTb> getEjemplarTbList() {
        return ejemplarTbList;
    }

    public void setEjemplarTbList(List<EjemplarTb> ejemplarTbList) {
        this.ejemplarTbList = ejemplarTbList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdsubespecie != null ? eIdsubespecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubespecieTb)) {
            return false;
        }
        SubespecieTb other = (SubespecieTb) object;
        if ((this.eIdsubespecie == null && other.eIdsubespecie != null) || (this.eIdsubespecie != null && !this.eIdsubespecie.equals(other.eIdsubespecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.SubespecieTb[ eIdsubespecie=" + eIdsubespecie + " ]";
    }
    
}
