package controlador;

import modelo.ProrrogaProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProrrogaProyectoTbFacade;

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

@Named("prorrogaProyectoTbController")
@ViewScoped
public class ProrrogaProyectoTbController implements Serializable {

    @EJB
    private servicio.ProrrogaProyectoTbFacade ejbFacade;
    private List<ProrrogaProyectoTb> items = null, filtro;
    private ProrrogaProyectoTb selected;
    private ProyectoTb proyectos;

    public List<ProrrogaProyectoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ProrrogaProyectoTb> filtro) {
        this.filtro = filtro;
    }

    
    public ProrrogaProyectoTbController() {
    }

    public ProrrogaProyectoTb getSelected() {
        return selected;
    }

    public void setSelected(ProrrogaProyectoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProrrogaProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public ProrrogaProyectoTb prepareCreate(ProyectoTb proyecto) {
        
        selected = new ProrrogaProyectoTb();
        proyectos=proyecto;
        selected.setEIdproyecto(proyectos);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProrrogaProyectoTb> getItems(ProyectoTb proyecto) {
        items = getFacade().buscarProrroga(proyecto);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProrrogaProyectoTb getProrrogaProyectoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProrrogaProyectoTb.class)
    public static class ProrrogaProyectoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProrrogaProyectoTbController controller = (ProrrogaProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prorrogaProyectoTbController");
            return controller.getProrrogaProyectoTb(getKey(value));
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
            if (object instanceof ProrrogaProyectoTb) {
                ProrrogaProyectoTb o = (ProrrogaProyectoTb) object;
                return getStringKey(o.getEIdprorroga());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProrrogaProyectoTb.class.getName()});
                return null;
            }
        }

    }

}
