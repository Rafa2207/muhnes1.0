/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import controlador.util.ControladorSesion;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.ActividadTb;
import modelo.ProyectoTb;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import servicio.ActividadTbFacade;
import servicio.ProyectoTbFacade;

/**
 *
 * @author Endy
 */

@ManagedBean
@ViewScoped
public class controlProyectos {
    @EJB
    private servicio.ProyectoTbFacade FacadeProyecto;
    @EJB
    private servicio.ActividadTbFacade FacadeActividad;
    private List<ProyectoTb> listaProyecto = null;
    private List<ActividadTb> listaActividad = null;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private ProyectoTb proyecto;
  
    @PostConstruct
    public void init() {
        listaProyecto = getFacadeProyecto().findAll();
        listaActividad = getFacadeActividad().findAll();
        
    }
    
    public ScheduleModel eventoActividad(ProyectoTb proy) {
        eventModel = new DefaultScheduleModel(); 
        for (ActividadTb a : proy.getActividadTbList()) {
            if (a.geteEstado()==1){
            eventModel.addEvent(new DefaultScheduleEvent("No iniciado: "+a.getMNombre(), a.getFFecha(), a.getFFechafin(), "NoIniciado"));    
            }
            if(a.geteEstado()==2){
            eventModel.addEvent(new DefaultScheduleEvent("En proceso: "+a.getMNombre(), a.getFFechaInicioReal(), a.getFFechafin(), "EnProceso"));
            }
            if (a.geteEstado()==3){
                eventModel.addEvent(new DefaultScheduleEvent("Finalizado: "+a.getMNombre(), a.getFFechaInicioReal(), a.getFFechaFinReal(), "Finalizado"));                
            }
            }
        return eventModel;
    }

    public ProyectoTb getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoTb proyecto) {
        this.proyecto = proyecto;
    }
    
    public ProyectoTbFacade getFacadeProyecto() {
        return FacadeProyecto;
    }

    public ActividadTbFacade getFacadeActividad() {
        return FacadeActividad;
    }

    public List<ProyectoTb> getListaProyecto() {
        return listaProyecto;
    }

    public void setListaProyecto(List<ProyectoTb> listaProyecto) {
        this.listaProyecto = listaProyecto;
    }

    public List<ActividadTb> getListaActividad() {
        return listaActividad;
    }

    public void setListaActividad(List<ActividadTb> listaActividad) {
        this.listaActividad = listaActividad;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
}
