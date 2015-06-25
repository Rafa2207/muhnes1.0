package controlador;

import modelo.ProcesoejemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProcesoejemplarTbFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import modelo.ProyectoTb;

@Named("procesoejemplarTbController")
@ViewScoped
public class ProcesoejemplarTbController implements Serializable {

    @EJB
    private servicio.ProcesoejemplarTbFacade ejbFacade;
    private List<ProcesoejemplarTb> items = null, lista = null;
    private ProcesoejemplarTb selected;
    private ProyectoTb proyectos;
    private int cantidadSiguiente;
    private Date fechaSiguiente = new Date();
    private Date fechaInicioSiguiente = new Date();
    boolean valor, control;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public List<ProcesoejemplarTb> getLista() {
        lista = getFacade().findAll();
        return lista;
    }

    public Date getFechaInicioSiguiente() {
        return fechaInicioSiguiente;
    }

    public void setFechaInicioSiguiente(Date fechaInicioSiguiente) {
        this.fechaInicioSiguiente = fechaInicioSiguiente;
    }

    public int getCantidadSiguiente() {
        return cantidadSiguiente;
    }

    public void setCantidadSiguiente(int cantidadSiguiente) {
        this.cantidadSiguiente = cantidadSiguiente;
    }

    public Date getFechaSiguiente() {
        return fechaSiguiente;
    }

    public void setFechaSiguiente(Date fechaSiguiente) {
        this.fechaSiguiente = fechaSiguiente;
    }

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }

    public ProcesoejemplarTbController() {
    }

    public ProcesoejemplarTb getSelected() {
        return selected;
    }

    public void setSelected(ProcesoejemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProcesoejemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public ProcesoejemplarTb prepareCreate(ProyectoTb proyecto) {
        selected = new ProcesoejemplarTb();
        proyectos = proyecto;
        selected.setCTipo("Secado");
        selected.setEIdproyecto(proyectos);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProcesoejemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProcesoejemplarTb> getItems(ProyectoTb proyecto) {

        items = getFacade().buscarProcesoAsc(proyecto);
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

    public ProcesoejemplarTb getProcesoejemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProcesoejemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProcesoejemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProcesoejemplarTb.class)
    public static class ProcesoejemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProcesoejemplarTbController controller = (ProcesoejemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "procesoejemplarTbController");
            return controller.getProcesoejemplarTb(getKey(value));
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
            if (object instanceof ProcesoejemplarTb) {
                ProcesoejemplarTb o = (ProcesoejemplarTb) object;
                return getStringKey(o.getEIdproceso());

            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProcesoejemplarTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareSiguiente(ProyectoTb proyecto, ProcesoejemplarTb pe) {
        selected = pe;
        nombre=pe.getMNombre();
        int id = pe.getEIdproceso();
        cantidadSiguiente = pe.getECantidad();
        fechaSiguiente = pe.getFFechafin();

        selected = new ProcesoejemplarTb();
        selected.setCTipo("Cuarentena");
        proyectos = proyecto;
        selected.setEIdproyecto(proyectos);
        selected.setERelacion(id);

    }

    public void prepareViewSiguiente(ProyectoTb proyecto, ProcesoejemplarTb pe) {
        getLista();
        nombre = pe.getMNombre();
        for (ProcesoejemplarTb pro : lista) {
            if (pro.getEIdproyecto().getEIdproyecto() == proyecto.getEIdproyecto()) {
                if (pro.getERelacion() == pe.getEIdproceso()) {
                    selected = pro;
                    break;
                }
            }
        }
        control = true;
        fechaSiguiente = selected.getFFechafin();
        cantidadSiguiente = selected.getECantidad();

    }

    //para controlar el siguiente proceso ejemplar y no deje ponerlo nuevamente
    public boolean corroborar(ProyectoTb proyecto, ProcesoejemplarTb selec) {
        getLista();

        valor = false;
        for (ProcesoejemplarTb pro : lista) {

            if (pro.getEIdproyecto().getEIdproyecto() == proyecto.getEIdproyecto()) {
                if (pro.getERelacion() == selec.getEIdproceso()) {
                    valor = true;
                }
            }

        }
        return valor;
    }

    public void calcularFechaFinCuarentena(Date fechainicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechainicio); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 3);  // numero de dias que se restan o suman
        Date fecha = calendar.getTime(); //mandamos la fecha a una variable Date
        selected.setFFechafin(fecha);
    }

    public void controlmodificar() {
            control = false;        
    }

}
