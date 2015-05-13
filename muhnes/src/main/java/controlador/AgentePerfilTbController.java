package controlador;

import modelo.AgentePerfilTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.AgentePerfilTbFacade;

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

@Named("agentePerfilTbController")
@SessionScoped
public class AgentePerfilTbController implements Serializable {

    @EJB
    private servicio.AgentePerfilTbFacade ejbFacade;
    private List<AgentePerfilTb> items = null;
    private AgentePerfilTb selected;

    public AgentePerfilTbController() {
    }

    public AgentePerfilTb getSelected() {
        return selected;
    }

    public void setSelected(AgentePerfilTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getAgentePerfilTbPK().setEIdperfil(selected.getPerfilTb().getEIdperfil());
        selected.getAgentePerfilTbPK().setEIdagente(selected.getAgenteTb().getEIdagente());
    }

    protected void initializeEmbeddableKey() {
        selected.setAgentePerfilTbPK(new modelo.AgentePerfilTbPK());
    }

    private AgentePerfilTbFacade getFacade() {
        return ejbFacade;
    }

    public AgentePerfilTb prepareCreate() {
        selected = new AgentePerfilTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundlePerfilAgente").getString("AgentePerfilTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundlePerfilAgente").getString("AgentePerfilTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundlePerfilAgente").getString("AgentePerfilTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AgentePerfilTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePerfilAgente").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePerfilAgente").getString("PersistenceErrorOccured"));
            }
        }
    }

    public AgentePerfilTb getAgentePerfilTb(modelo.AgentePerfilTbPK id) {
        return getFacade().find(id);
    }

    public List<AgentePerfilTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AgentePerfilTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AgentePerfilTb.class)
    public static class AgentePerfilTbControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgentePerfilTbController controller = (AgentePerfilTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agentePerfilTbController");
            return controller.getAgentePerfilTb(getKey(value));
        }

        modelo.AgentePerfilTbPK getKey(String value) {
            modelo.AgentePerfilTbPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.AgentePerfilTbPK();
            key.setEIdagente(Integer.parseInt(values[0]));
            key.setEIdperfil(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.AgentePerfilTbPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getEIdagente());
            sb.append(SEPARATOR);
            sb.append(value.getEIdperfil());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AgentePerfilTb) {
                AgentePerfilTb o = (AgentePerfilTb) object;
                return getStringKey(o.getAgentePerfilTbPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AgentePerfilTb.class.getName()});
                return null;
            }
        }

    }

}
