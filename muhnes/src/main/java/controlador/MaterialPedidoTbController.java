package controlador;

import modelo.MaterialPedidoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.MaterialPedidoTbFacade;

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

@Named("materialPedidoTbController")
@SessionScoped
public class MaterialPedidoTbController implements Serializable {

    @EJB
    private servicio.MaterialPedidoTbFacade ejbFacade;
    private List<MaterialPedidoTb> items = null, filtro;
    private MaterialPedidoTb selected;
    private Double entrada;

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public List<MaterialPedidoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<MaterialPedidoTb> filtro) {
        this.filtro = filtro;
    }

    public MaterialPedidoTbController() {
    }

    public MaterialPedidoTb getSelected() {
        return selected;
    }

    public void setSelected(MaterialPedidoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getMaterialPedidoTbPK().setEIdmaterial(selected.getMaterialTb().getEIdmaterial());
        selected.getMaterialPedidoTbPK().setEIdpedido(selected.getPedidoTb().getEIdpedido());
    }

    protected void initializeEmbeddableKey() {
        selected.setMaterialPedidoTbPK(new modelo.MaterialPedidoTbPK());
    }

    private MaterialPedidoTbFacade getFacade() {
        return ejbFacade;
    }

    public MaterialPedidoTb prepareCreate() {
        selected = new MaterialPedidoTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleMateriales").getString("MaterialPedidoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleMateriales").getString("MaterialPedidoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleMateriales").getString("MaterialPedidoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MaterialPedidoTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleMateriales").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleMateriales").getString("PersistenceErrorOccured"));
            }
        }
    }

    public MaterialPedidoTb getMaterialPedidoTb(modelo.MaterialPedidoTbPK id) {
        return getFacade().find(id);
    }

    public List<MaterialPedidoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MaterialPedidoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MaterialPedidoTb.class)
    public static class MaterialPedidoTbControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialPedidoTbController controller = (MaterialPedidoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialPedidoTbController");
            return controller.getMaterialPedidoTb(getKey(value));
        }

        modelo.MaterialPedidoTbPK getKey(String value) {
            modelo.MaterialPedidoTbPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.MaterialPedidoTbPK();
            key.setEIdmaterial(Integer.parseInt(values[0]));
            key.setEIdpedido(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.MaterialPedidoTbPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getEIdmaterial());
            sb.append(SEPARATOR);
            sb.append(value.getEIdpedido());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MaterialPedidoTb) {
                MaterialPedidoTb o = (MaterialPedidoTb) object;
                return getStringKey(o.getMaterialPedidoTbPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MaterialPedidoTb.class.getName()});
                return null;
            }
        }

    }

}
