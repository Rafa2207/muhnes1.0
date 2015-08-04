/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import controlador.util.ControladorSesion;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import modelo.ActividadTb;
import modelo.ProyectoTb;
import org.primefaces.event.SelectEvent;
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
    private ActividadTb actividadView;
    private ScheduleModel eventModel;
    private ScheduleEvent event;
    private ProyectoTb proyecto;
    private String comparador;
    private Date fechaActual = new Date();
    @Inject
    ControladorSesion sesion;

    @PostConstruct
    public void init() {
        int pro=sesion.getProyecto().getEIdproyecto();
        eventModel = new DefaultScheduleModel();
        listaProyecto = getFacadeProyecto().findAll();
        

        for (ProyectoTb p : listaProyecto) {
            if (p.getEIdproyecto() == pro) {
               proyecto=p;
            }
        }
        
        try {
            eventoActividad(proyecto);
        } catch (Exception e) {
        }

    }

    public ScheduleModel eventoActividad(ProyectoTb proy) {
        

        for (ActividadTb a : proy.getActividadTbList()) {
            if (a.geteEstado() == 1) {
                eventModel.addEvent(new DefaultScheduleEvent("No iniciado: " + a.getMNombre(), a.getFFecha(), a.getFFechafin(), "NoIniciado"));
            }
            if (a.geteEstado() == 2) {
                eventModel.addEvent(new DefaultScheduleEvent("En proceso: " + a.getMNombre(), a.getFFechaInicioReal(), fechaActual, "EnProceso"));
            }
            if (a.geteEstado() == 3) {
                eventModel.addEvent(new DefaultScheduleEvent("Finalizado: " + a.getMNombre(), a.getFFechaInicioReal(), a.getFFechaFinReal(), "Finalizado"));
            }
        }
        return eventModel;
    }

    public void seleccionEvento(SelectEvent eventoSeleccionado) {

        event = (ScheduleEvent) eventoSeleccionado.getObject();
        String[] cadena = event.getTitle().split(": ", 2);
        comparador = cadena[0];
        String titulo = cadena[1];

        if (comparador.equals("No iniciado")) {
            actividadView = getFacadeActividad().BuscarActividadNoIniciada(titulo, event.getStartDate(), event.getEndDate());
        }
        if (comparador.equals("En proceso")) {
            actividadView = getFacadeActividad().BuscarActividadEnProceso(titulo, event.getStartDate());
        }
        if (comparador.equals("Finalizado")) {
            actividadView = getFacadeActividad().BuscarActividadFinalizada(titulo, event.getStartDate(), event.getEndDate());
        }
    }

    public ProyectoTbFacade getFacadeProyecto() {
        return FacadeProyecto;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getComparador() {
        return comparador;
    }

    public void setComparador(String comparador) {
        this.comparador = comparador;
    }

    public ActividadTb getActividadView() {
        return actividadView;
    }

    public void setActividadView(ActividadTb actividadView) {
        this.actividadView = actividadView;
    }

    public ProyectoTb getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoTb proyecto) {
        this.proyecto = proyecto;
    }

    public void setFacadeActividad(ActividadTbFacade FacadeActividad) {
        this.FacadeActividad = FacadeActividad;
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

    public void iniciarActividad() {
        FacesContext context = FacesContext.getCurrentInstance();
        eventModel.clear();
        actividadView.seteEstado(2);
        actividadView.setFFechaInicioReal(fechaActual);
        try {
            getFacadeActividad().edit(actividadView);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad iniciada ", "INFO"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, no se puede iniciar actividad ", "INFO"));
        }
        init();
    }

    public void finalizarActividad() {
        FacesContext context = FacesContext.getCurrentInstance();
        eventModel.clear();
        actividadView.setFFechaFinReal(fechaActual);
        actividadView.seteEstado(3);
        try {
            getFacadeActividad().edit(actividadView);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad finalizada", "INFO"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, no se puede finalizar actividad", "INFO"));
        }
        init();
    }

}
