package controlador;

import modelo.PresupuestoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.PresupuestoTbFacade;

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
import modelo.InsumoTb;
import modelo.ProyectoTb;

@Named("presupuestoTbController")
@ViewScoped
public class PresupuestoTbController implements Serializable {

    @EJB
    private servicio.PresupuestoTbFacade ejbFacade;
    private List<PresupuestoTb> items = null, filtro;
    private PresupuestoTb selected, presupuesto;
    private ProyectoTb proyectos;
    private double cantidad, costo;
    private String nombre, tiempo;

    public PresupuestoTb getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoTb presupuesto) {
        this.presupuesto = presupuesto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    

    public List<PresupuestoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<PresupuestoTb> filtro) {
        this.filtro = filtro;
    }
    
    

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }
    
    public PresupuestoTbController() {
    }

    public PresupuestoTb getSelected() {
        return selected;
    }

    public void setSelected(PresupuestoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PresupuestoTbFacade getFacade() {
        return ejbFacade;
    }

    public PresupuestoTb prepareCreate(ProyectoTb proyecto) {
        selected = new PresupuestoTb();
        proyectos= proyecto;
        selected.setEIdproyecto(proyectos);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PresupuestoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PresupuestoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PresupuestoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PresupuestoTb> getItems(ProyectoTb proyecto) {
        
            items = getFacade().buscarPresupuestoAsc(proyecto);
        
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

    public PresupuestoTb getPresupuestoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PresupuestoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PresupuestoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PresupuestoTb.class)
    public static class PresupuestoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PresupuestoTbController controller = (PresupuestoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "presupuestoTbController");
            return controller.getPresupuestoTb(getKey(value));
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
            if (object instanceof PresupuestoTb) {
                PresupuestoTb o = (PresupuestoTb) object;
                return getStringKey(o.getEIdpresupuesto());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PresupuestoTb.class.getName()});
                return null;
            }
        }

    }
    public void agregar(){
        InsumoTb insumo = new InsumoTb();
        presupuesto = new PresupuestoTb();
        //presupuesto.setEIdpresupuesto(getFacade().siguienteId());
        insumo.setMNombre(nombre);
        insumo.setMCantidad(cantidad);
        
        selected.getInsumoTbList().add(insumo);
        
        
    }

}
