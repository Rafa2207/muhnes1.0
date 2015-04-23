package controlador;

import modelo.GeneroTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.GeneroTbFacade;

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
import modelo.FamiliaTb;

@Named("generoTbController")
@ViewScoped
public class GeneroTbController implements Serializable {

    @EJB
    private servicio.GeneroTbFacade ejbFacade;
    private List<GeneroTb> items = null, filtro;
    private GeneroTb selected;
    private FamiliaTb familia;

    public List<GeneroTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<GeneroTb> filtro) {
        this.filtro = filtro;
    }

    public FamiliaTb getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaTb familia) {
        this.familia = familia;
    }

    public GeneroTbController() {
    }

    public GeneroTb getSelected() {
        return selected;
    }

    public void setSelected(GeneroTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private GeneroTbFacade getFacade() {
        return ejbFacade;
    }

    public GeneroTb prepareCreate(FamiliaTb fam) {
        selected = new GeneroTb();
        //familia=fam;
        selected.setEIdfamilia(fam.getEIdfamilia());
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GeneroTb> getItems(Integer fam) {
        //if (items == null) {
        //items = getFacade().findAll();
       //familia = familias;
        
        items = getFacade().buscarFamiliaAsc(fam);
        //}
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

    public GeneroTb getGeneroTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<GeneroTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GeneroTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = GeneroTb.class)
    public static class GeneroTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GeneroTbController controller = (GeneroTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "generoTbController");
            return controller.getGeneroTb(getKey(value));
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
            if (object instanceof GeneroTb) {
                GeneroTb o = (GeneroTb) object;
                return getStringKey(o.getEIdgenero());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GeneroTb.class.getName()});
                return null;
            }
        }

    }

}
