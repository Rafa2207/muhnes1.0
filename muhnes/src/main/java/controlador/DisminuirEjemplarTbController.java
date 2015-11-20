package controlador;

import modelo.DisminuirEjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.DisminuirEjemplarTbFacade;

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
import modelo.EjemplarVivoTb;

@Named("disminuirEjemplarTbController")
@SessionScoped
public class DisminuirEjemplarTbController implements Serializable {

    @EJB
    private servicio.DisminuirEjemplarTbFacade ejbFacade;
    @EJB
    private servicio.EjemplarVivoTbFacade ejemplarVivoFacade;
    private List<DisminuirEjemplarTb> items = null, filtro;
    private DisminuirEjemplarTb selected;

    public DisminuirEjemplarTbController() {
    }

    public List<DisminuirEjemplarTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DisminuirEjemplarTb> filtro) {
        this.filtro = filtro;
    }

    public DisminuirEjemplarTb getSelected() {
        return selected;
    }

    public void setSelected(DisminuirEjemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DisminuirEjemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public DisminuirEjemplarTb prepareCreate(EjemplarVivoTb ejemplarVivo) {
        selected = new DisminuirEjemplarTb();
        selected.setEIdejemplarVivo(ejemplarVivo);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        int cantidad = selected.getEIdejemplarVivo().getECantidad();
        int cantidadBaja = selected.getECantidad();
        selected.getEIdejemplarVivo().setECantidad(cantidad-cantidadBaja);
        ejemplarVivoFacade.edit(selected.getEIdejemplarVivo());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DisminuirEjemplarTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DisminuirEjemplarTb getDisminuirEjemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DisminuirEjemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DisminuirEjemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DisminuirEjemplarTb.class)
    public static class DisminuirEjemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DisminuirEjemplarTbController controller = (DisminuirEjemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "disminuirEjemplarTbController");
            return controller.getDisminuirEjemplarTb(getKey(value));
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
            if (object instanceof DisminuirEjemplarTb) {
                DisminuirEjemplarTb o = (DisminuirEjemplarTb) object;
                return getStringKey(o.getEIddisminuirEjemplar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DisminuirEjemplarTb.class.getName()});
                return null;
            }
        }

    }

}
