package controlador;

import modelo.EjemplarParticipaExhibicionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.EjemplarParticipaExhibicionTbFacade;

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

@Named("ejemplarParticipaExhibicionTbController")
@ViewScoped
public class EjemplarParticipaExhibicionTbController implements Serializable {

    @EJB
    private servicio.EjemplarParticipaExhibicionTbFacade ejbFacade;
    private List<EjemplarParticipaExhibicionTb> items = null;
    private EjemplarParticipaExhibicionTb selected;

    public EjemplarParticipaExhibicionTbController() {
    }

    public EjemplarParticipaExhibicionTb getSelected() {
        return selected;
    }

    public void setSelected(EjemplarParticipaExhibicionTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getEjemplarParticipaExhibicionTbPK().setEIdexhibicion(selected.getExhibicionTb().getEIdexhibicion());
        selected.getEjemplarParticipaExhibicionTbPK().setEIdejemplar(selected.getEjemplarTb().getEIdejemplar());
    }

    protected void initializeEmbeddableKey() {
        selected.setEjemplarParticipaExhibicionTbPK(new modelo.EjemplarParticipaExhibicionTbPK());
    }

    private EjemplarParticipaExhibicionTbFacade getFacade() {
        return ejbFacade;
    }

    public EjemplarParticipaExhibicionTb prepareCreate() {
        selected = new EjemplarParticipaExhibicionTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarParticipaExhibicionTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarParticipaExhibicionTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EjemplarParticipaExhibicionTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarParticipaExhibicionTb> getItems() {
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

    public EjemplarParticipaExhibicionTb getEjemplarParticipaExhibicionTb(modelo.EjemplarParticipaExhibicionTbPK id) {
        return getFacade().find(id);
    }

    public List<EjemplarParticipaExhibicionTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EjemplarParticipaExhibicionTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EjemplarParticipaExhibicionTb.class)
    public static class EjemplarParticipaExhibicionTbControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EjemplarParticipaExhibicionTbController controller = (EjemplarParticipaExhibicionTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ejemplarParticipaExhibicionTbController");
            return controller.getEjemplarParticipaExhibicionTb(getKey(value));
        }

        modelo.EjemplarParticipaExhibicionTbPK getKey(String value) {
            modelo.EjemplarParticipaExhibicionTbPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.EjemplarParticipaExhibicionTbPK();
            key.setEIdejemplar(Integer.parseInt(values[0]));
            key.setEIdexhibicion(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.EjemplarParticipaExhibicionTbPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getEIdejemplar());
            sb.append(SEPARATOR);
            sb.append(value.getEIdexhibicion());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EjemplarParticipaExhibicionTb) {
                EjemplarParticipaExhibicionTb o = (EjemplarParticipaExhibicionTb) object;
                return getStringKey(o.getEjemplarParticipaExhibicionTbPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EjemplarParticipaExhibicionTb.class.getName()});
                return null;
            }
        }

    }

}
