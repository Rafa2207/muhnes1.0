package controlador;

import modelo.ProrrogaProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ProrrogaProyectoTbFacade;

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

@Named("prorrogaProyectoTbController")
@ViewScoped
public class ProrrogaProyectoTbController implements Serializable {

    @EJB
    private servicio.ProrrogaProyectoTbFacade ejbFacade;
    private List<ProrrogaProyectoTb> items = null, filtro;
    private ProrrogaProyectoTb selected;
    private ProyectoTb proyectos;
    private Date fechaMinimaTemporal, fechaMinimaTemporalEdit, fechaMaximaTemporalEdit;
    private Date FechaActual = new Date();

    public Date getFechaActual() {
        return FechaActual;
    }

    public void setFechaActual(Date FechaActual) {
        this.FechaActual = FechaActual;
    }

    public Date getFechaMaximaTemporalEdit() {
        return fechaMaximaTemporalEdit;
    }

    public void setFechaMaximaTemporalEdit(Date fechaMaximaTemporalEdit) {
        this.fechaMaximaTemporalEdit = fechaMaximaTemporalEdit;
    }

    public Date getFechaMinimaTemporalEdit() {
        return fechaMinimaTemporalEdit;
    }

    public void setFechaMinimaTemporalEdit(Date fechaMinimaTemporalEdit) {
        this.fechaMinimaTemporalEdit = fechaMinimaTemporalEdit;
    }

    public Date getFechaMinimaTemporal() {
        return fechaMinimaTemporal;
    }

    public void setFechaMinimaTemporal(Date fechaMinimaTemporal) {
        this.fechaMinimaTemporal = fechaMinimaTemporal;
    }

    public List<ProrrogaProyectoTb> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<ProrrogaProyectoTb> items) {
        this.items = items;
    }

    public List<ProrrogaProyectoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ProrrogaProyectoTb> filtro) {
        this.filtro = filtro;
    }

    public ProrrogaProyectoTbController() {
    }

    public ProrrogaProyectoTb getSelected() {
        return selected;
    }

    public void setSelected(ProrrogaProyectoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProrrogaProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public ProrrogaProyectoTb prepareCreate(List<ProrrogaProyectoTb> p, ProyectoTb proyecto) {

        selected = new ProrrogaProyectoTb();
        proyectos = proyecto;
        selected.setEIdproyecto(proyectos);
        calcularNumeroProrroga(p, proyecto);
        calcularFechaMinima(p, proyecto);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProrrogaProyectoTb> getItems(ProyectoTb proyecto) {
        items = getFacade().buscarProrroga(proyecto);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProrrogaProyectoTb getProrrogaProyectoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProrrogaProyectoTb.class)
    public static class ProrrogaProyectoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProrrogaProyectoTbController controller = (ProrrogaProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prorrogaProyectoTbController");
            return controller.getProrrogaProyectoTb(getKey(value));
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
            if (object instanceof ProrrogaProyectoTb) {
                ProrrogaProyectoTb o = (ProrrogaProyectoTb) object;
                return getStringKey(o.getEIdprorroga());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProrrogaProyectoTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit(List<ProrrogaProyectoTb> p, ProyectoTb proyecto, int numero) {
        calcularFechaMinimaEdit(p, proyecto, numero);

    }

    public void calcularNumeroProrroga(List<ProrrogaProyectoTb> p, ProyectoTb proy) {
        int i = 1;
        selected.setENumprorroga(i);
        for (ProrrogaProyectoTb proProy : p) {

            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                selected.setENumprorroga(i);
            }
        }

    }

    public void calcularFechaMinima(List<ProrrogaProyectoTb> p, ProyectoTb proy) {
        int i = 1, numeroProrroga = selected.getENumprorroga();
        fechaMinimaTemporal = proy.getFFechaFin();
        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                if (numeroProrroga == i) {
                    fechaMinimaTemporal = proProy.getFFechaFin();
                }
            }
        }
        //calcular siguiente dia
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaMinimaTemporal);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date fecha=calendar.getTime();
        //fin calculo de siguiente dia
        selected.setFFechaInicio(fecha);

    }
    
    public Date fechaMinima(Date fecha){
        Date fechafin;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fechafin=calendar.getTime();
        return fechafin;
    }

    public void calcularFechaMinimaEdit(List<ProrrogaProyectoTb> p, ProyectoTb proy, int numeroProrroga) {
        int i = 1;
        fechaMinimaTemporalEdit = proy.getFFechaFin();

        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                if (numeroProrroga == i) {
                    fechaMinimaTemporalEdit = proProy.getFFechaFin();
                }

            }
        }

    }

    public void eliminarProrroga(List<ProrrogaProyectoTb> p, ProyectoTb proy, int pro, Date fechaPro) {
        int i = 0;

        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
            }
        }

        //&& fechaPro.after(FechaActual)
        if (pro >= i && fechaPro.after(FechaActual)) {
            destroy();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            if (fechaPro.after(FechaActual)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, elimine primero la última prórroga ", "ERROR"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prórroga está en ejecución o ya fue finalizada", "ERROR"));

            }
        }

    }

}
