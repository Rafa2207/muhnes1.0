package controlador;

import modelo.LocalidadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.LocalidadTbFacade;

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

@Named("localidadTbController")
@ViewScoped
public class LocalidadTbController implements Serializable {

    @EJB
    private servicio.LocalidadTbFacade ejbFacade;
    private List<LocalidadTb> items = null, filtro;
    private LocalidadTb selected;

    public List<LocalidadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<LocalidadTb> filtro) {
        this.filtro = filtro;
    }

    public LocalidadTbController() {
    }

    public LocalidadTb getSelected() {
        return selected;
    }

    public void setSelected(LocalidadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocalidadTbFacade getFacade() {
        return ejbFacade;
    }

    public LocalidadTb prepareCreate() {
        selected = new LocalidadTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LocalidadTb> getItems() {
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

    public LocalidadTb getLocalidadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LocalidadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LocalidadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LocalidadTb.class)
    public static class LocalidadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocalidadTbController controller = (LocalidadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localidadTbController");
            return controller.getLocalidadTb(getKey(value));
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
            if (object instanceof LocalidadTb) {
                LocalidadTb o = (LocalidadTb) object;
                return getStringKey(o.getEIdlocalidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LocalidadTb.class.getName()});
                return null;
            }
        }

    }
    public void latitudDecimal(int grado, int minuto, double segundo){
        double resultado,min,seg,gra;
        gra=grado;
        min= minuto/60;
        seg= segundo/360;
        resultado= gra + min + seg ;
        
        selected.setDLatitudDecimal(resultado);
        
    }
    
    public void longitudDecimal(int grado, int minuto, double segundo){
        double resultado,min,seg,gra;
        gra=grado;
        min= minuto/60;
        seg= segundo/360;
        resultado= gra + min + seg;
        
        selected.setDLongitudDecimal(resultado);
        
    }

}
