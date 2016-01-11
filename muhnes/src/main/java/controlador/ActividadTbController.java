package controlador;

import modelo.ActividadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ActividadTbFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import modelo.InsumoTb;
import modelo.ProrrogaProyectoTb;
import modelo.ProyectoTb;

@Named("actividadTbController")
@ViewScoped
public class ActividadTbController implements Serializable {

    @EJB
    private servicio.ActividadTbFacade ejbFacade;
    @EJB
    private servicio.ProrrogaProyectoTbFacade FacadeProrroga;
    private List<ActividadTb> items = null, filtro, itemsNotificacion = null;
    private ActividadTb selected;
    private ProyectoTb proyectos;
    private Date fechatemporal, fechaActual = new Date();
    private double cantidad, costo;
    private String nombre = null, tiempo;
    private boolean tActividad = false;
    private InsumoTb insumo;
    int NumeroDeNotificaciones = 0;

    public InsumoTb getInsumo() {
        return insumo;
    }

    public void setInsumo(InsumoTb insumo) {
        this.insumo = insumo;
    }

    public boolean istActividad() {
        return tActividad;
    }

    public void settActividad(boolean tActividad) {
        this.tActividad = tActividad;
    }

    public int getNumeroDeNotificaciones() {
        return NumeroDeNotificaciones;
    }

    public void setNumeroDeNotificaciones(int NumeroDeNotificaciones) {
        this.NumeroDeNotificaciones = NumeroDeNotificaciones;
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

    public Date getFechatemporal() {
        return fechatemporal;
    }

    public void setFechatemporal(Date fechatemporal) {
        this.fechatemporal = fechatemporal;
    }

    public List<ActividadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ActividadTb> filtro) {
        this.filtro = filtro;
    }

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }

    public ActividadTbController() {
    }

    public ActividadTb getSelected() {
        return selected;
    }

    public void setSelected(ActividadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActividadTbFacade getFacade() {
        return ejbFacade;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<ActividadTb> getItemsNotificacion() {
        List<ActividadTb> quitarFinalizados = new ArrayList<ActividadTb>();
        //Notificación de 7 dias 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 7);  // numero de dias que se restan o suman
        Date fechaDeSemana = calendar.getTime(); //mandamos la fecha a una variable Date

        itemsNotificacion = getFacade().ActividadNotificaciones(fechaActual, fechaDeSemana);
        try {
            for (ActividadTb a : itemsNotificacion) {
                if (a.getEEstado() == 3 || a.getEEstado() == 0) {
                    quitarFinalizados.add(a);
                }
                if (a.getEEstado() == 1) {
                    quitarFinalizados.add(a);
                }
                if (a.getEEstado() == 2) {
                    if (a.getFFechaFinReal().after(fechaDeSemana)) {
                        quitarFinalizados.add(a);
                    } else if (a.getEIdproyecto().getEEstado() == 3) {
                        quitarFinalizados.add(a);
                    }
                }

            }
            itemsNotificacion.removeAll(quitarFinalizados);
            NumeroDeNotificaciones = itemsNotificacion.size();

        } catch (Exception e) {
        }
        return itemsNotificacion;
    }

    public void setItemsNotificacion(List<ActividadTb> itemsNotificacion) {
        this.itemsNotificacion = itemsNotificacion;
    }

    public ActividadTb prepareCreate(ProyectoTb proyecto) {
        selected = new ActividadTb();
        proyectos = proyecto;
        selected.setEIdproyecto(proyectos);
        selected.setEEstado(1);
        selected.setDGastoAdicional(0.0);
        selected.setInsumoTbList(new ArrayList<InsumoTb>());
        tActividad = false;
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected.getInsumoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar insumos");
        } else {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        if (selected.getInsumoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar insumos");
        } else {
            selected.setFFechafin(fechatemporal);
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbUpdated"));
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ActividadTb> getItems(ProyectoTb proyecto) { //modificando para que funcione por proyectos

        items = getFacade().buscarAsc(proyecto);

        return items;
    }

    public List<ActividadTb> getItems() { //modificando para que funcione por proyectos
        items = getFacade().findAll();

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

    public ActividadTb getActividadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ActividadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ActividadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ActividadTb.class)
    public static class ActividadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadTbController controller = (ActividadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadTbController");
            return controller.getActividadTb(getKey(value));
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
            if (object instanceof ActividadTb) {
                ActividadTb o = (ActividadTb) object;
                return getStringKey(o.getEIdactividad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ActividadTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit() {
        fechatemporal = selected.getFFechafin();
    }

    public void limpiarFecha() {
        fechatemporal = null;
    }

    public void agregar() {
        FacesContext context = FacesContext.getCurrentInstance();
        InsumoTb ins = new InsumoTb();
        //presupuesto = new PresupuestoTb();
        //presupuesto.setEIdpresupuesto(getFacade().siguienteId());
        if (nombre.isEmpty() || tiempo == null || cantidad == 0.0 || costo == 0.0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Llene los campos insumo, cantidad, costo y el tipo de unidad.", "INFO"));
        } else {
            ins.setMNombre(nombre);
            ins.setDCantidad(cantidad);
            ins.setDGasto(costo);
            ins.setMTiempo(tiempo);
            //presupuesto.setEIdpresupuesto();
            ins.setEIdactividad(selected);
            selected.getInsumoTbList().add(ins);

            cantidad = 0;
            costo = 0;
            nombre = "";
            tiempo = "";
            tActividad = true;

        }

    }

    public Double costoTotal() {
        Double tot = 0.0;

        for (InsumoTb i : selected.getInsumoTbList()) {
            tot = tot + (i.getDCantidad() * i.getDGasto());
        }
        selected.setDTotal(tot);
        return tot;
    }

    public double totalProyecto(ProyectoTb proy) {
        double totalProy = 0.0;

        for (ActividadTb act : getItems()) {
            if (act.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                if (act.getEEstado() != 0) {
                    totalProy = totalProy + act.getDTotal() + act.getDGastoAdicional();
                }
            }
        }
        return totalProy;
    }

    public void removerInsumo() {
        selected.getInsumoTbList().remove(insumo);
    }

    public Double TotalViewAct(ActividadTb a) {
        try {
            Double total = 0.0;
            total = a.getDTotal() + a.getDGastoAdicional();
            return total;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public String NombreNotificacion(String a, int n) {
        String nombre = null;
        int cadena = 0;
        try {
            cadena = a.length();
            if (cadena >= n) {
                nombre = a.substring(0, n);
                nombre = nombre + "...";
            } else {
                nombre = a.substring(0, cadena);
            }
        } catch (Exception e) {
            nombre = null;
        }
        return nombre;
    }

    public Date calcularFechaMaxima() {
        Date FechaMaxima = selected.getEIdproyecto().getFFechaFin();
        selected.getEIdproyecto().setProrrogaProyectoTbList(FacadeProrroga.buscarProrroga(selected.getEIdproyecto()));
        for (ProrrogaProyectoTb p : selected.getEIdproyecto().getProrrogaProyectoTbList()) {
            try {
                if (p.getFFechaFin().after(FechaMaxima)) {
                    FechaMaxima = p.getFFechaFin();
                }
            } catch (Exception e) {
            }
        }
        return FechaMaxima;
    }

    public String estadoNombresList(int estadoList) {
        String mensaje = null;
        if (estadoList == 0) {
            mensaje = "Cancelado";
        }
        if (estadoList == 1) {
            mensaje = "No iniciado";
        }
        if (estadoList == 2) {
            mensaje = "En proceso";
        }
        if (estadoList == 3) {
            mensaje = "Finalizado";
        }

        return mensaje;
    }

    public void desactivarActividad() {
        FacesContext context = FacesContext.getCurrentInstance();
        selected.setEEstado(0);

        getFacade().edit(selected);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad Cancelada", "INFO"));

    }

    public double totalMasGastoAd(ActividadTb a) {
        double gastoTotal = 0;
        gastoTotal = a.getDTotal() + a.getDGastoAdicional();
        return gastoTotal;
    }
}
