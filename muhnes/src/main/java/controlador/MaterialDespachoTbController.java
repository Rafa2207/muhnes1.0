package controlador;

import modelo.MaterialDespachoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.MaterialDespachoTbFacade;

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

@Named("materialDespachoTbController")
@SessionScoped
public class MaterialDespachoTbController implements Serializable {

    @EJB
    private servicio.MaterialDespachoTbFacade ejbFacade;
    private List<MaterialDespachoTb> items = null;
    private MaterialDespachoTb selected;

    public MaterialDespachoTbController() {
    }

    public MaterialDespachoTb getSelected() {
        return selected;
    }

    public void setSelected(MaterialDespachoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getMaterialDespachoTbPK().setEIdmaterial(selected.getMaterialTb().getEIdmaterial());
        selected.getMaterialDespachoTbPK().setEIddespacho(selected.getDespachoTb().getEIddespacho());
    }

    protected void initializeEmbeddableKey() {
        selected.setMaterialDespachoTbPK(new modelo.MaterialDespachoTbPK());
    }

    private MaterialDespachoTbFacade getFacade() {
        return ejbFacade;
    }

    public MaterialDespachoTb prepareCreate() {
        selected = new MaterialDespachoTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleDespacho").getString("MaterialDespachoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleDespacho").getString("MaterialDespachoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleDespacho").getString("MaterialDespachoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MaterialDespachoTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
            }
        }
    }

    public MaterialDespachoTb getMaterialDespachoTb(modelo.MaterialDespachoTbPK id) {
        return getFacade().find(id);
    }

    public List<MaterialDespachoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MaterialDespachoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MaterialDespachoTb.class)
    public static class MaterialDespachoTbControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialDespachoTbController controller = (MaterialDespachoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialDespachoTbController");
            return controller.getMaterialDespachoTb(getKey(value));
        }

        modelo.MaterialDespachoTbPK getKey(String value) {
            modelo.MaterialDespachoTbPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.MaterialDespachoTbPK();
            key.setEIdmaterial(Integer.parseInt(values[0]));
            key.setEIddespacho(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.MaterialDespachoTbPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getEIdmaterial());
            sb.append(SEPARATOR);
            sb.append(value.getEIddespacho());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MaterialDespachoTb) {
                MaterialDespachoTb o = (MaterialDespachoTb) object;
                return getStringKey(o.getMaterialDespachoTbPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MaterialDespachoTb.class.getName()});
                return null;
            }
        }

    }

}
