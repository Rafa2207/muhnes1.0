package controlador;

import modelo.MaterialProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.MaterialProyectoTbFacade;

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

@Named("materialProyectoTbController")
@SessionScoped
public class MaterialProyectoTbController implements Serializable {

    @EJB
    private servicio.MaterialProyectoTbFacade ejbFacade;
    private List<MaterialProyectoTb> items = null;
    private MaterialProyectoTb selected;

    public MaterialProyectoTbController() {
    }

    public MaterialProyectoTb getSelected() {
        return selected;
    }

    public void setSelected(MaterialProyectoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getMaterialProyectoTbPK().setEIdmaterial(selected.getMaterialTb().getEIdmaterial());
        selected.getMaterialProyectoTbPK().setEIdproyecto(selected.getProyectoTb().getEIdproyecto());
    }

    protected void initializeEmbeddableKey() {
        selected.setMaterialProyectoTbPK(new modelo.MaterialProyectoTbPK());
    }

    private MaterialProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public MaterialProyectoTb prepareCreate() {
        selected = new MaterialProyectoTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlematerialproyecto").getString("MaterialProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlematerialproyecto").getString("MaterialProyectoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlematerialproyecto").getString("MaterialProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MaterialProyectoTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlematerialproyecto").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlematerialproyecto").getString("PersistenceErrorOccured"));
            }
        }
    }

    public MaterialProyectoTb getMaterialProyectoTb(modelo.MaterialProyectoTbPK id) {
        return getFacade().find(id);
    }

    public List<MaterialProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MaterialProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MaterialProyectoTb.class)
    public static class MaterialProyectoTbControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialProyectoTbController controller = (MaterialProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialProyectoTbController");
            return controller.getMaterialProyectoTb(getKey(value));
        }

        modelo.MaterialProyectoTbPK getKey(String value) {
            modelo.MaterialProyectoTbPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.MaterialProyectoTbPK();
            key.setEIdmaterial(Integer.parseInt(values[0]));
            key.setEIdproyecto(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.MaterialProyectoTbPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getEIdmaterial());
            sb.append(SEPARATOR);
            sb.append(value.getEIdproyecto());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MaterialProyectoTb) {
                MaterialProyectoTb o = (MaterialProyectoTb) object;
                return getStringKey(o.getMaterialProyectoTbPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MaterialProyectoTb.class.getName()});
                return null;
            }
        }

    }

}
