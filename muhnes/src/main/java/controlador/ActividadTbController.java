package controlador;

import modelo.ActividadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ActividadTbFacade;

import java.io.Serializable;
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
import modelo.ProyectoTb;

@Named("actividadTbController")
@ViewScoped
public class ActividadTbController implements Serializable {

    @EJB
    private servicio.ActividadTbFacade ejbFacade;
    private List<ActividadTb> items = null,filtro;
    private ActividadTb selected;
    private ProyectoTb proyectos;

    public List<ActividadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ActividadTb> filtro) {
        this.filtro = filtro;
    }
    
    

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }
    
    
    public ActividadTbController() {
    }

    public ActividadTb getSelected() {
        return selected;
    }

    public void setSelected(ActividadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActividadTbFacade getFacade() {
        return ejbFacade;
    }

    public ActividadTb prepareCreate(ProyectoTb proyecto) {
        
        selected = new ActividadTb();
        proyectos= proyecto;
        selected.setEIdproyecto(proyectos);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ActividadTb> getItems(ProyectoTb proyecto) { //modificando para que funcione por proyectos
        
            items = getFacade().buscarAsc(proyecto);
        
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

    public ActividadTb getActividadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ActividadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ActividadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ActividadTb.class)
    public static class ActividadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadTbController controller = (ActividadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadTbController");
            return controller.getActividadTb(getKey(value));
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
            if (object instanceof ActividadTb) {
                ActividadTb o = (ActividadTb) object;
                return getStringKey(o.getEIdactividad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ActividadTb.class.getName()});
                return null;
            }
        }

    }

}
