package controlador;

import modelo.ProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProyectoTbFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.ActividadTb;
import modelo.AgenteTb;
import modelo.ProrrogaProyectoTb;
import org.primefaces.context.RequestContext;
import servicio.ActividadTbFacade;

@Named("proyectoTbController")
@ViewScoped
public class ProyectoTbController implements Serializable {

    @EJB
    private servicio.ProyectoTbFacade ejbFacade;
    @EJB
    private servicio.ActividadTbFacade FacadeActividad;
    private List<ActividadTb> ListaActividad = null;
    private List<ProyectoTb> items = null, filtro, ListaProyecto = null, itemsProyecto = null, itemsNotificacion = null, listaNotificacion = null;
    private ProyectoTb selected;
    private Date fechatemporal, fechaActual = new Date();
    String agente;
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    EntityManager em;

    public List<ProyectoTb> getItemsProyecto() {
        itemsProyecto = getFacade().ProyectoGeneral();
        return itemsProyecto;
    }

    public ActividadTbFacade getFacadeActividad() {
        return FacadeActividad;
    }

    public void setFacadeActividad(ActividadTbFacade FacadeActividad) {
        this.FacadeActividad = FacadeActividad;
    }

    public List<ActividadTb> getListaActividad() {
        ListaActividad = getFacadeActividad().findAll();
        return ListaActividad;
    }

    public void setListaActividad(List<ActividadTb> ListaActividad) {
        this.ListaActividad = ListaActividad;
    }

    public Date getFechatemporal() {
        return fechatemporal;
    }

    public void setFechatemporal(Date fechatemporal) {
        this.fechatemporal = fechatemporal;
    }

    public List<ProyectoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ProyectoTb> filtro) {
        this.filtro = filtro;
    }

    public ProyectoTbController() {
    }

    public ProyectoTb getSelected() {
        return selected;
    }

    public void setSelected(ProyectoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public ProyectoTb prepareCreate() {
        selected = new ProyectoTb();
        selected.setEEstado(0);
        initializeEmbeddableKey();
        return selected;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public void setItemsNotificacion(List<ProyectoTb> itemsNotificacion) {
        this.itemsNotificacion = itemsNotificacion;
    }

    public List<ProyectoTb> getItemsNotificacion() {
        List<ProyectoTb> quitarFinalizados = new ArrayList<ProyectoTb>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 15);  // numero de dias que se restan o suman
        Date fecha = calendar.getTime(); //mandamos la fecha a una variable Date */
        itemsNotificacion = getFacade().ProyectoNotificaciones(fechaActual, fecha);

        for (ProyectoTb p : itemsNotificacion) {
            if (p.getEEstado() == 2) {
                quitarFinalizados.add(p);
            }
            else if (p.getFFechaFin().after(fecha)){
                quitarFinalizados.add(p);
            }
            
        }
        itemsNotificacion.removeAll(quitarFinalizados);
        return itemsNotificacion;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setFFechaFin(fechatemporal);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProyectoTb> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProyectoTb getProyectoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProyectoTb.class)
    public static class ProyectoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoTbController controller = (ProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoTbController");
            return controller.getProyectoTb(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProyectoTb) {
                ProyectoTb o = (ProyectoTb) object;
                return getStringKey(o.getEIdproyecto());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProyectoTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit() {
        fechatemporal = selected.getFFechaFin();

    }

    public void limpiarFecha() {
        fechatemporal = null;
    }

    public String calculaAgente(List<AgenteTb> a, int b) {
        for (AgenteTb agen : a) {
            if (agen.getEIdagente() == b) {
                agente = agen.getCNombre() + " " + agen.getCApellido();
            }
        }
        return agente;
    }

    public double presupuesto(ProyectoTb proy) {
        double presupuesto = 0;
        for (ActividadTb a : getListaActividad()) {
            if (a.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                if (a.getDGastoAdicional() == null) {
                    a.setDGastoAdicional(0.0);
                }
                presupuesto = presupuesto + a.getDTotal() + a.getDGastoAdicional();
            }
        }
        return presupuesto;
    }

    public int progresoProyecto(ProyectoTb proy) {
        int TotalActividades = 0, TotalEntero = 0, proyecto = proy.getEIdproyecto(), i = 0;
        double cadaActividad = 0, total = 0;
        ListaProyecto = getFacade().ProyectoGeneral();
        ListaActividad = getFacadeActividad().findAll();

        for (ActividadTb a : ListaActividad) {
            if (a.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                TotalActividades++;
            }
        }
        try {
            cadaActividad = 100 / (float) TotalActividades;
        } catch (Exception e) {
            cadaActividad = 0;
        }
        for (ActividadTb a : ListaActividad) {
            if (a.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                if (a.geteEstado() == 1) {
                    total = total + (cadaActividad * 0);
                }
                if (a.geteEstado() == 2) {
                    total = total + (cadaActividad / 2);
                }
                if (a.geteEstado() == 3) {
                    total = total + (cadaActividad);
                    i++;
                }
            }
        }
        TotalEntero = (int) total;
        //probando..
        if (i == TotalActividades && i != 0) {
            TotalEntero = 100;
        }
        return TotalEntero;
    }

    public String EstadoProyecto(ProyectoTb proyecto) {
        String estado = null;
        if (proyecto.getEEstado() == 0) {
            estado = "No iniciado";
        }
        if (proyecto.getEEstado() == 1) {
            estado = "En Proceso";
        }
        if (proyecto.getEEstado() == 2) {
            estado = "Finalizado";
        }
        return estado;

    }

    public void FinalizarProyecto() {
        FacesContext context = FacesContext.getCurrentInstance();
        selected.setEEstado(2);
        getFacade().edit(selected);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proyecto Finalizado", "INFO"));
    }

    public String NombreNotificacion(ProyectoTb p) {
        String nombre = null;
        int cadena=0;
        try {
            cadena=p.getMNombre().length();
            if(cadena>=200){
                nombre = p.getMNombre().substring(0, 200);
                nombre = nombre+"...";
            }
            else{
                nombre=p.getMNombre().substring(0,cadena);
            }   
        } catch (Exception e) {
            nombre = null;
        }
        return nombre;
    }

}
