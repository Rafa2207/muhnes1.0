package controlador;

import modelo.ProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProyectoTbFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.ActividadTb;
import modelo.AgenteTb;
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
    private List<ProyectoTb> items = null, filtro, ListaProyecto = null, itemsProyecto = null;
    private ProyectoTb selected;
    private Date fechatemporal;
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
        initializeEmbeddableKey();
        return selected;
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
            if(a.getEIdproyecto().getEIdproyecto()==proy.getEIdproyecto()) {
                presupuesto = presupuesto + a.getDTotal();
            }
        }
        return presupuesto;
    }

    public int progresoProyecto(ProyectoTb proy) {
        int TotalActividades = 0, TotalEntero = 0, proyecto = proy.getEIdproyecto();
        double cadaActividad = 0, total = 0;
        ListaProyecto = getFacade().ProyectoGeneral();
        ListaActividad = getFacadeActividad().findAll();

        for (ProyectoTb p : ListaProyecto) {
            if (p.getEIdproyecto() == proyecto) {
                proy = p;
            }
        }

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
                }
            }
        }
        TotalEntero = (int) total;
        return TotalEntero;

    }

}
