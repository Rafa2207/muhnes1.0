package controlador;

import modelo.UsuarioTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.UsuarioTbFacade;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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

@Named("usuarioTbController")
@ViewScoped
public class UsuarioTbController implements Serializable {

    @EJB
    private servicio.UsuarioTbFacade ejbFacade;
    private List<UsuarioTb> items = null, filtro;
    private UsuarioTb selected;
    private String pass1;
    private String anterior;
    private String respaldo;
    
    public UsuarioTbController() {
    }

    public UsuarioTb getSelected() {
        return selected;
    }

    public void setSelected(UsuarioTb selected) {
        this.selected = selected;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public List<UsuarioTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<UsuarioTb> filtro) {
        this.filtro = filtro;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }
    
    private UsuarioTbFacade getFacade() {
        return ejbFacade;
    }

    public UsuarioTb prepareCreate() {
        selected = new UsuarioTb();
        initializeEmbeddableKey();
        return selected;
    }
    
        public void prepareCambiarContra(String usuario){
        selected=getFacade().BuscarUsuario(usuario);
        respaldo=selected.getMPassword();
    }
        
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

        public void cambiarContra() {
        try{
            if(JsfUtil.cifrar(anterior).equals(respaldo)){
                //busca si existe un usuario que puso el mismo email
                UsuarioTb usuario = ejbFacade.usuarioByCorreo(selected.getMEmail());
                //Comprueba que no sea el mismo usuario
                if(usuario!=null && usuario.equals(selected)) usuario = null;
                if(usuario==null){
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbUpdated"));
                }else{
                    JsfUtil.addErrorMessage("El email ingresado ya se encuentra en uso por otro usuario");
                }
                
            }
            else{
                JsfUtil.addErrorMessage("La contraseña anterior es incorrecta");
            }
        }
        catch(NoSuchAlgorithmException e){
            JsfUtil.addErrorMessage("No se pudo cifrar la nueva contraseña. Intentelo más tarde");
        }
    }
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UsuarioTb> getItems() {
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

    public UsuarioTb getUsuarioTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UsuarioTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UsuarioTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UsuarioTb.class)
    public static class UsuarioTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioTbController controller = (UsuarioTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioTbController");
            return controller.getUsuarioTb(getKey(value));
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
            if (object instanceof UsuarioTb) {
                UsuarioTb o = (UsuarioTb) object;
                return getStringKey(o.getEIdusuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsuarioTb.class.getName()});
                return null;
            }
        }

    }

}
