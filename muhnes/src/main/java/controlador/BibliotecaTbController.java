package controlador;

import modelo.BibliotecaTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.BibliotecaTbFacade;

import java.io.Serializable;
import java.util.Date;
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
import modelo.BitacoraTb;
import modelo.UsuarioTb;

@Named("bibliotecaTbController")
@SessionScoped
public class BibliotecaTbController implements Serializable {

    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    @EJB
    private servicio.BibliotecaTbFacade ejbFacade;
    private List<BibliotecaTb> items = null;
    private BibliotecaTb selected;

    public BibliotecaTbController() {
    }

    public BibliotecaTb getSelected() {
        return selected;
    }

    public void setSelected(BibliotecaTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BibliotecaTbFacade getFacade() {
        return ejbFacade;
    }

    public BibliotecaTb prepareCreate() {
        selected = new BibliotecaTb();
        initializeEmbeddableKey();
        return selected;
    }

    public BibliotecaTb prepareDatos() {
        selected = new BibliotecaTb();
        initializeEmbeddableKey();
        try {
            selected = getFacade().find(1);
        } catch (Exception e) {
        }

        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle_biblioteca").getString("BibliotecaTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update(String txt) {
        selected.setEIdbiblioteca(1);
        persist(PersistAction.UPDATE, "Se modificó "+txt+" a la biblioteca virtual.");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado : '" + txt + "' a la biblioteca virtual");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuarios = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuarios);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle_biblioteca").getString("BibliotecaTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BibliotecaTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle_biblioteca").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle_biblioteca").getString("PersistenceErrorOccured"));
            }
        }
    }

    public BibliotecaTb getBibliotecaTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<BibliotecaTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BibliotecaTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BibliotecaTb.class)
    public static class BibliotecaTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BibliotecaTbController controller = (BibliotecaTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bibliotecaTbController");
            return controller.getBibliotecaTb(getKey(value));
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
            if (object instanceof BibliotecaTb) {
                BibliotecaTb o = (BibliotecaTb) object;
                return getStringKey(o.getEIdbiblioteca());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BibliotecaTb.class.getName()});
                return null;
            }
        }

    }

}
