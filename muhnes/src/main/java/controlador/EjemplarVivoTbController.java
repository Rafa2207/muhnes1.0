package controlador;

import modelo.EjemplarVivoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.EjemplarVivoTbFacade;

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
import javax.faces.view.ViewScoped;
import modelo.BitacoraTb;
import modelo.UsuarioTb;

@Named("ejemplarVivoTbController")
@ViewScoped
public class EjemplarVivoTbController implements Serializable {

    @EJB
    private servicio.EjemplarVivoTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<EjemplarVivoTb> items = null, items2 = null, filtro;
    private EjemplarVivoTb selected;

    public EjemplarVivoTbController() {
    }

    public List<EjemplarVivoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<EjemplarVivoTb> filtro) {
        this.filtro = filtro;
    }

    public EjemplarVivoTb getSelected() {
        return selected;
    }

    public void setSelected(EjemplarVivoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EjemplarVivoTbFacade getFacade() {
        return ejbFacade;
    }

    public EjemplarVivoTb prepareCreate() {
        selected = new EjemplarVivoTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbUpdated"));
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarVivoTb> getItems() {
        if (items == null) {
            items = getFacade().buscarEjemplarConExistencia();
        }
        return items;
    }

    public List<EjemplarVivoTb> getItems2() {
        if (items2 == null) {
            items2 = getFacade().buscarEjemplarSinExistencia();
        }
        return items2;
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

    public EjemplarVivoTb getEjemplarVivoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EjemplarVivoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EjemplarVivoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EjemplarVivoTb.class)
    public static class EjemplarVivoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EjemplarVivoTbController controller = (EjemplarVivoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ejemplarVivoTbController");
            return controller.getEjemplarVivoTb(getKey(value));
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
            if (object instanceof EjemplarVivoTb) {
                EjemplarVivoTb o = (EjemplarVivoTb) object;
                return getStringKey(o.getEIdejemplarVivo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EjemplarVivoTb.class.getName()});
                return null;
            }
        }

    }

}
