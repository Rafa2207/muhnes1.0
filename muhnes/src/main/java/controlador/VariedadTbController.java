package controlador;

import modelo.VariedadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.VariedadTbFacade;

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
import modelo.SubespecieTb;

@Named("variedadTbController")
@ViewScoped
public class VariedadTbController implements Serializable {

    @EJB
    private servicio.VariedadTbFacade ejbFacade;
    private List<VariedadTb> items = null, filtro;
    private VariedadTb selected;

    public List<VariedadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<VariedadTb> filtro) {
        this.filtro = filtro;
    }

    public VariedadTbController() {
    }

    public VariedadTb getSelected() {
        return selected;
    }

    public void setSelected(VariedadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VariedadTbFacade getFacade() {
        return ejbFacade;
    }

    public VariedadTb prepareCreate(SubespecieTb subespecie) {
        selected = new VariedadTb();
        initializeEmbeddableKey();
        selected.setEIdespecie(subespecie.getEIdespecie());
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VariedadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VariedadTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VariedadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<VariedadTb> getItems(Integer subesp) {
        /*if (items == null) {
            items = getFacade().findAll();
        }*/
        items = getFacade().buscarSubEspecieAsc(subesp);
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

    public VariedadTb getVariedadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<VariedadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VariedadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = VariedadTb.class)
    public static class VariedadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VariedadTbController controller = (VariedadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "variedadTbController");
            return controller.getVariedadTb(getKey(value));
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
            if (object instanceof VariedadTb) {
                VariedadTb o = (VariedadTb) object;
                return getStringKey(o.getEIdvariedad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VariedadTb.class.getName()});
                return null;
            }
        }

    }

}
