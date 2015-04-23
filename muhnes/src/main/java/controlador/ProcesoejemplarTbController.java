package controlador;

import modelo.ProcesoejemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProcesoejemplarTbFacade;

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

@Named("procesoejemplarTbController")
@ViewScoped
public class ProcesoejemplarTbController implements Serializable {

    @EJB
    private servicio.ProcesoejemplarTbFacade ejbFacade;
    private List<ProcesoejemplarTb> items = null;
    private ProcesoejemplarTb selected;
    private ProyectoTb proyectos;

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }
    
    

    public ProcesoejemplarTbController() {
    }

    public ProcesoejemplarTb getSelected() {
        return selected;
    }

    public void setSelected(ProcesoejemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProcesoejemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public ProcesoejemplarTb prepareCreate() {
        selected = new ProcesoejemplarTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProcesoejemplarTb> getItems(ProyectoTb proyecto) {
        
            items = getFacade().buscarProcesoAsc(proyecto);
        
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

    public ProcesoejemplarTb getProcesoejemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProcesoejemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProcesoejemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProcesoejemplarTb.class)
    public static class ProcesoejemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProcesoejemplarTbController controller = (ProcesoejemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "procesoejemplarTbController");
            return controller.getProcesoejemplarTb(getKey(value));
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
            if (object instanceof ProcesoejemplarTb) {
                ProcesoejemplarTb o = (ProcesoejemplarTb) object;
                return getStringKey(o.getEIdproceso());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProcesoejemplarTb.class.getName()});
                return null;
            }
        }

    }

}
