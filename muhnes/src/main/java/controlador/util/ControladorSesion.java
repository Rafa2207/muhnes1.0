/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import modelo.DepartamentoTb;
import modelo.EjemplarVivoTb;
import modelo.MunicipioTb;
import modelo.PaisTb;
import modelo.ProyectoTb;
import modelo.TaxonomiaTb;

/**
 *
 * @author Endy
 */
@Named(value = "controladorSesion")
@SessionScoped

public class ControladorSesion implements Serializable {

    private ProyectoTb proyecto;
    private PaisTb pais;
    private DepartamentoTb departamento;
    private MunicipioTb municipio;
    private TaxonomiaTb taxonomia;
    private TaxonomiaTb familia;
    private TaxonomiaTb genero;
    private TaxonomiaTb especie;
    private EjemplarVivoTb ejemplarVivo;
    private String ayuda=null;
    //private TaxonomiaTb taxonomia;

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public EjemplarVivoTb getEjemplarVivo() {
        return ejemplarVivo;
    }

    public void setEjemplarVivo(EjemplarVivoTb ejemplarVivo) {
        this.ejemplarVivo = ejemplarVivo;
    }

    public TaxonomiaTb getFamilia() {
        return familia;
    }

    public void setFamilia(TaxonomiaTb familia) {
        this.familia = familia;
    }

    public TaxonomiaTb getGenero() {
        return genero;
    }

    public void setGenero(TaxonomiaTb genero) {
        this.genero = genero;
    }

    public TaxonomiaTb getEspecie() {
        return especie;
    }

    public void setEspecie(TaxonomiaTb especie) {
        this.especie = especie;
    }

    public TaxonomiaTb getTaxonomia() {
        return taxonomia;
    }

    public void setTaxonomia(TaxonomiaTb taxonomia) {
        this.taxonomia = taxonomia;
    }

    /*    public GeneroTb getGenero() {
     return genero;
     }

     public void setGenero(GeneroTb genero) {
     this.genero = genero;
     }
     */
    public MunicipioTb getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioTb municipio) {
        this.municipio = municipio;
    }

    public DepartamentoTb getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoTb departamento) {
        this.departamento = departamento;
    }

    public PaisTb getPais() {
        return pais;
    }

    public void setPais(PaisTb pais) {
        this.pais = pais;
    }
    /*
     public FamiliaTb getFamilia() {
     return familia;
     }

     public void setFamilia(FamiliaTb familia) {
     this.familia = familia;
     }
     */

    public ProyectoTb getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoTb proyecto) {
        this.proyecto = proyecto;
    }

    public String mostrarAyuda() {
        String ayudaComparacion=ayuda;
        
        String tipoAyuda = "inicio";

        if (ayudaComparacion != "inicio") {
            tipoAyuda = "../../pdf/"+ayudaComparacion+".pdf";
            ayudaComparacion="inicio";
            return tipoAyuda;
        } 
        else {
            return "../pdf/inicio.pdf";
        }
    }

    /**
     * Creates a new instance of ControladorSesion
     */
    public ControladorSesion() {
    }

}
