/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import modelo.ActividadTb;
import modelo.AgenteTb;
import modelo.ProyectoTb;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import servicio.ActividadTbFacade;
import servicio.ProyectoTbFacade;

@ManagedBean
@ViewScoped
public class agenda implements Serializable {

    @EJB
    private servicio.ProyectoTbFacade FacadeProyecto;
    @EJB
    private servicio.ActividadTbFacade FacadeActividad;
    private List<ProyectoTb> listaProyecto = null;
    private List<ActividadTb> listaActividad = null;
    private ProyectoTb proyectoView;
    private ActividadTb actividadView;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String comparador;

    @PostConstruct
    public void init() {
        listaProyecto = getFacadeProyecto().findAll();
        listaActividad = getFacadeActividad().findAll();
        eventModel = new DefaultScheduleModel();
        eventoProyecto(listaProyecto);
        eventoActividad(listaActividad);

    }

    public String getComparador() {
        return comparador;
    }

    public List<ActividadTb> getListaActividad() {
        return listaActividad;
    }

    public ProyectoTb getProyectoView() {
        return proyectoView;
    }

    public ActividadTb getActividadView() {
        return actividadView;
    }

    public ActividadTbFacade getFacadeActividad() {
        return FacadeActividad;
    }

    public void setFacadeActividad(ActividadTbFacade FacadeActividad) {
        this.FacadeActividad = FacadeActividad;
    }

    public ProyectoTbFacade getFacadeProyecto() {
        return FacadeProyecto;
    }

    public void setFacadeProyecto(ProyectoTbFacade FacadeProyecto) {
        this.FacadeProyecto = FacadeProyecto;
    }

    public List<ProyectoTb> getListaProyecto() {
        return listaProyecto;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    //Encuentra todos los proyectos y los agrega a la calendarizacion
    public ScheduleModel eventoProyecto(List<ProyectoTb> proy) {
        for (ProyectoTb p : proy) {
            eventModel.addEvent(new DefaultScheduleEvent("Proyecto: " + p.getMNombre(), p.getFFechaInicio(), p.getFFechaFin(), "proyecto"));
            
        }
        return eventModel;
    }

    public ScheduleModel eventoActividad(List<ActividadTb> proy) {
        for (ActividadTb a : proy) {
            eventModel.addEvent(new DefaultScheduleEvent("Actividad: " + a.getMNombre(), a.getFFecha(), a.getFFechafin(), "actividad"));
        }
        return eventModel;
    }

    //Preparar el evento seleccionado y muestra una visualizacion de ese evento
    public void seleccionEvento(SelectEvent eventoSeleccionado) {
        event = (ScheduleEvent) eventoSeleccionado.getObject();
        String[] cadena = event.getTitle().split(": ", 2);
        comparador = cadena[0];
        String titulo = cadena[1];
        
        if (comparador.equals("Proyecto")) {
            proyectoView = getFacadeProyecto().BuscarProyecto(titulo, event.getStartDate(), event.getEndDate());
        }
        if (comparador.equals("Actividad")) {
            actividadView= getFacadeActividad().BuscarActividades(titulo, event.getStartDate(), event.getEndDate());
        }
    }
}
