/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import modelo.FamiliaTb;
import modelo.PaisTb;
import modelo.PresupuestoTb;
import modelo.ProyectoTb;

/**
 *
 * @author Endy
 */
@Named(value = "controladorSesion")
@SessionScoped

public class ControladorSesion implements Serializable {

    private ProyectoTb proyecto;
    private FamiliaTb familia;
    private PaisTb pais;

    public PaisTb getPais() {
        return pais;
    }

    public void setPais(PaisTb pais) {
        this.pais = pais;
    }
    
    

    public FamiliaTb getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaTb familia) {
        this.familia = familia;
    }
    

    public ProyectoTb getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoTb proyecto) {
        this.proyecto = proyecto;
    }
    
    /**
     * Creates a new instance of ControladorSesion
     */
    public ControladorSesion() {
    }
    
}
