/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.util.JsfUtil;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Frank Martinez
 */
@Entity
@Table(name = "usuario_tb")
@NamedQueries({
    @NamedQuery(name = "UsuarioTb.findAll", query = "SELECT u FROM UsuarioTb u"),
    @NamedQuery(name = "UsuarioTb.findByEIdusuario", query = "SELECT u FROM UsuarioTb u WHERE u.eIdusuario = :eIdusuario"),
    @NamedQuery(name = "UsuarioTb.findByCNombre", query = "SELECT u FROM UsuarioTb u WHERE u.cNombre = :cNombre"),
    @NamedQuery(name = "UsuarioTb.findByCApellido", query = "SELECT u FROM UsuarioTb u WHERE u.cApellido = :cApellido"),
    @NamedQuery(name = "UsuarioTb.findByCTelefono", query = "SELECT u FROM UsuarioTb u WHERE u.cTelefono = :cTelefono"),
    @NamedQuery(name = "UsuarioTb.findByCNick", query = "SELECT u FROM UsuarioTb u WHERE u.cNick = :cNick"),
    @NamedQuery(name = "UsuarioTb.findByMPassword", query = "SELECT u FROM UsuarioTb u WHERE u.mPassword = :mPassword"),
    @NamedQuery(name = "UsuarioTb.findByCDui", query = "SELECT u FROM UsuarioTb u WHERE u.cDui = :cDui"),
    @NamedQuery(name = "UsuarioTb.findByCTipo", query = "SELECT u FROM UsuarioTb u WHERE u.cTipo = :cTipo")})
public class UsuarioTb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idusuario")
    private Integer eIdusuario;
    @Size(max = 50)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 50)
    @Column(name = "c_apellido")
    private String cApellido;
    @Size(max = 10)
    @Column(name = "c_telefono")
    private String cTelefono;
    @Size(max = 15)
    @Column(name = "c_nick")
    private String cNick;
    @Size(max = 2147483647)
    @Column(name = "m_password")
    private String mPassword;
    @Size(max = 10)
    @Column(name = "c_dui")
    private String cDui;
    @Size(max = 15)
    @Column(name = "c_tipo")
    private String cTipo;

    public UsuarioTb() {
    }

    public UsuarioTb(Integer eIdusuario) {
        this.eIdusuario = eIdusuario;
    }

    public Integer getEIdusuario() {
        return eIdusuario;
    }

    public void setEIdusuario(Integer eIdusuario) {
        this.eIdusuario = eIdusuario;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCApellido() {
        return cApellido;
    }

    public void setCApellido(String cApellido) {
        this.cApellido = cApellido;
    }

    public String getCTelefono() {
        return cTelefono;
    }

    public void setCTelefono(String cTelefono) {
        this.cTelefono = cTelefono;
    }

    public String getCNick() {
        return cNick;
    }

    public void setCNick(String cNick) {
        this.cNick = cNick;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(String mPassword) {
        try {
            this.mPassword = JsfUtil.cifrar(mPassword);
        } catch (NoSuchAlgorithmException ex) {
            this.mPassword = mPassword;
        }

    }

    public String getCDui() {
        return cDui;
    }

    public void setCDui(String cDui) {
        this.cDui = cDui;
    }

    public String getCTipo() {
        return cTipo;
    }

    public void setCTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdusuario != null ? eIdusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioTb)) {
            return false;
        }
        UsuarioTb other = (UsuarioTb) object;
        if ((this.eIdusuario == null && other.eIdusuario != null) || (this.eIdusuario != null && !this.eIdusuario.equals(other.eIdusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UsuarioTb[ eIdusuario=" + eIdusuario + " ]";
    }

}
