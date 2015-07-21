package controlador;

import modelo.DespachoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.DespachoTbFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import modelo.MaterialDespachoTb;
import modelo.MaterialDespachoTbPK;
import modelo.MaterialTb;

@Named("despachoTbController")
@ViewScoped
public class DespachoTbController implements Serializable {

    @EJB
    private servicio.MaterialTbFacade materialFacade;
    @EJB
    private servicio.DespachoTbFacade ejbFacade;
    private List<DespachoTb> items = null, filtro;
    private DespachoTb selected;
    private MaterialTb material;
    private List<MaterialTb> materialDisponible;
    private double cantidad, salida;
    private MaterialDespachoTb materialMD;

    public DespachoTbController() {
    }

    public List<MaterialTb> getMaterialDisponible() {
        return materialDisponible;
    }

    public void setMaterialDisponible(List<MaterialTb> materialDisponible) {
        this.materialDisponible = materialDisponible;
    }

    public MaterialDespachoTb getMaterialMD() {
        return materialMD;
    }

    public void setMaterialMD(MaterialDespachoTb materialMD) {
        this.materialMD = materialMD;
    }

    public MaterialTb getMaterial() {
        return material;
    }

    public void setMaterial(MaterialTb material) {
        this.material = material;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public List<DespachoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DespachoTb> filtro) {
        this.filtro = filtro;
    }

    public DespachoTb getSelected() {
        return selected;
    }

    public void setSelected(DespachoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DespachoTbFacade getFacade() {
        return ejbFacade;
    }

    public DespachoTb prepareCreate() {
        selected = new DespachoTb();
        initializeEmbeddableKey();
        selected.setMaterialDespachoTbList(new ArrayList<MaterialDespachoTb>());
        materialDisponible = materialFacade.buscarTodosAZ();
        return selected;
    }
    
    public DespachoTb prepareEdit() {
        materialDisponible = materialFacade.buscarTodosAZ();
        for (MaterialDespachoTb b : selected.getMaterialDespachoTbList()) {
            materialDisponible.remove(b.getMaterialTb());
        }
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        for (MaterialDespachoTb i: selected.getMaterialDespachoTbList()){
            i.getMaterialTb().setDCantidad(i.getMaterialTb().getDCantidad() - i.getDCantidad());
            materialFacade.edit(i.getMaterialTb());
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DespachoTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DespachoTb getDespachoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DespachoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DespachoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DespachoTb.class)
    public static class DespachoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DespachoTbController controller = (DespachoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "despachoTbController");
            return controller.getDespachoTb(getKey(value));
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
            if (object instanceof DespachoTb) {
                DespachoTb o = (DespachoTb) object;
                return getStringKey(o.getEIddespacho());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DespachoTb.class.getName()});
                return null;
            }
        }

    }

    public void agregar() {
        MaterialDespachoTb nuevo = new MaterialDespachoTb();
        nuevo.setDCantidad(cantidad);
        nuevo.setMaterialTb(material);
        nuevo.setDespachoTb(selected);

        MaterialDespachoTbPK mppk = new MaterialDespachoTbPK();
        mppk.setEIdmaterial(material.getEIdmaterial());
        mppk.setEIddespacho(getFacade().siguienteId());

        nuevo.setMaterialDespachoTbPK(mppk);

        selected.getMaterialDespachoTbList().add(nuevo);

        materialDisponible.remove(material);
        //material.setDCantidad(material.getDCantidad()-cantidad);

        cantidad = 0.0;

    }

    public void remover() {
        selected.getMaterialDespachoTbList().remove(materialMD);
        materialDisponible.add(materialMD.getMaterialTb());
        material = null;
    }
}
