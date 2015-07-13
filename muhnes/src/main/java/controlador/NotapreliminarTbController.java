package controlador;

import modelo.NotapreliminarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.NotapreliminarTbFacade;

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

@Named("notapreliminarTbController")
@SessionScoped
public class NotapreliminarTbController implements Serializable {

    @EJB
    private servicio.NotapreliminarTbFacade ejbFacade;
    private List<NotapreliminarTb> items = null;
    private NotapreliminarTb selected;

    public NotapreliminarTbController() {
    }

    public NotapreliminarTb getSelected() {
        return selected;
    }

    public void setSelected(NotapreliminarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NotapreliminarTbFacade getFacade() {
        return ejbFacade;
    }

    public NotapreliminarTb prepareCreate() {
        selected = new NotapreliminarTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NotapreliminarTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotas").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotas").getString("PersistenceErrorOccured"));
            }
        }
    }

    public NotapreliminarTb getNotapreliminarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NotapreliminarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NotapreliminarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = NotapreliminarTb.class)
    public static class NotapreliminarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NotapreliminarTbController controller = (NotapreliminarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "notapreliminarTbController");
            return controller.getNotapreliminarTb(getKey(value));
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
            if (object instanceof NotapreliminarTb) {
                NotapreliminarTb o = (NotapreliminarTb) object;
                return getStringKey(o.getEIdnotapreliminar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NotapreliminarTb.class.getName()});
                return null;
            }
        }

    }

}
