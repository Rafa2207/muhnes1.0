package controlador;

import modelo.UnidadesTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.UnidadesTbFacade;

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


@Named("unidadesTbController")
@SessionScoped
public class UnidadesTbController implements Serializable {

    @EJB
    private servicio.UnidadesTbFacade ejbFacade;
    private List<UnidadesTb> items = null, filtro;
    private UnidadesTb selected;

    public UnidadesTbController() {
    }

    public List<UnidadesTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<UnidadesTb> filtro) {
        this.filtro = filtro;
    }

    public UnidadesTb getSelected() {
        return selected;
    }

    public void setSelected(UnidadesTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UnidadesTbFacade getFacade() {
        return ejbFacade;
    }

    public UnidadesTb prepareCreate() {
        selected = new UnidadesTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleUnidades").getString("UnidadesTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleUnidades").getString("UnidadesTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleUnidades").getString("UnidadesTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UnidadesTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleUnidades").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleUnidades").getString("PersistenceErrorOccured"));
            }
        }
    }

    public UnidadesTb getUnidadesTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UnidadesTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UnidadesTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    public List<UnidadesTb> getItemsMateriales() {
        return getFacade().obtenerUnidadesMateriales();
    }

    @FacesConverter(forClass = UnidadesTb.class)
    public static class UnidadesTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UnidadesTbController controller = (UnidadesTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "unidadesTbController");
            return controller.getUnidadesTb(getKey(value));
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
            if (object instanceof UnidadesTb) {
                UnidadesTb o = (UnidadesTb) object;
                return getStringKey(o.getEIdunidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UnidadesTb.class.getName()});
                return null;
            }
        }

    }
    public List<UnidadesTb> unidades(String pre){
        
        items = getFacade().obtenerUnidades(pre);
        
        return items;
    }
    public String abreviatura(){
        String abrev;
        if(selected==null){
            abrev="";
        }
        else{abrev=selected.getCAbreviatura();}
        return abrev;
    }

}
