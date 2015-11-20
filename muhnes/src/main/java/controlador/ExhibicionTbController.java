package controlador;

import modelo.ExhibicionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.ExhibicionTbFacade;

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
import modelo.AgenteTb;
import modelo.EjemplarParticipaExhibicionTb;
import modelo.EjemplarParticipaExhibicionTbPK;
import modelo.EjemplarTb;
import modelo.TaxonomiaTb;
import servicio.AgenteTbFacade;

@Named("exhibicionTbController")
@ViewScoped
public class ExhibicionTbController implements Serializable {

    @EJB
    private servicio.ExhibicionTbFacade ejbFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    @EJB
    private servicio.EjemplarTbFacade ejemplarFacade;
    private List<ExhibicionTb> items = null, lista = null, filtro, itemsNotificacion = null;
    private List<EjemplarTb> ejemplares;
    private ExhibicionTb selected;
    private Date fechaActual = new Date();
    private int NumeroDeNotificaciones = 0, CantidadEjemplar = 1;
    private String tipoTaxon = null;
    private TaxonomiaTb taxonomia;
    private EjemplarTb ejemplar;
    private AgenteTb agente;
    private EjemplarParticipaExhibicionTb ejemplarExhibicion;

    public ExhibicionTbController() {
    }

    public List<ExhibicionTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ExhibicionTb> filtro) {
        this.filtro = filtro;
    }

    public ExhibicionTb getSelected() {
        return selected;
    }

    public void setSelected(ExhibicionTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public int getNumeroDeNotificaciones() {
        return NumeroDeNotificaciones;
    }

    public void setNumeroDeNotificaciones(int NumeroDeNotificaciones) {
        this.NumeroDeNotificaciones = NumeroDeNotificaciones;
    }

    public String getTipoTaxon() {
        return tipoTaxon;
    }

    public void setTipoTaxon(String tipoTaxon) {
        this.tipoTaxon = tipoTaxon;
    }

    public TaxonomiaTb getTaxonomia() {
        return taxonomia;
    }

    public void setTaxonomia(TaxonomiaTb taxonomia) {
        this.taxonomia = taxonomia;
    }

    public int getCantidadEjemplar() {
        return CantidadEjemplar;
    }

    public void setCantidadEjemplar(int CantidadEjemplar) {
        this.CantidadEjemplar = CantidadEjemplar;
    }

    public EjemplarTb getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarTb ejemplar) {
        this.ejemplar = ejemplar;
    }

    private ExhibicionTbFacade getFacade() {
        return ejbFacade;
    }

    public AgenteTbFacade getAgenteFacade() {
        return agenteFacade;
    }

    public void setAgenteFacade(AgenteTbFacade agenteFacade) {
        this.agenteFacade = agenteFacade;
    }

    public List<EjemplarTb> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<EjemplarTb> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public EjemplarParticipaExhibicionTb getEjemplarExhibicion() {
        return ejemplarExhibicion;
    }

    public void setEjemplarExhibicion(EjemplarParticipaExhibicionTb ejemplarExhibicion) {
        this.ejemplarExhibicion = ejemplarExhibicion;
    }

    public List<ExhibicionTb> getItemsNotificacion() {
        List<ExhibicionTb> quitarFinalizados = new ArrayList<ExhibicionTb>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 4);  // numero de dias que se restan o suman
        Date fecha = calendar.getTime(); //mandamos la fecha a una variable Date */
        itemsNotificacion = getFacade().ExhibicionesNotificaciones(fechaActual, fecha);
        try {
            for (ExhibicionTb e : itemsNotificacion) {
                if (e.getEEstado() == 1) {
                    quitarFinalizados.add(e);
                }
                if (e.getEEstado() == 0) {
                    if (e.getFFechaRecibido().after(fecha)) {
                        quitarFinalizados.add(e);
                    }
                }
            }
            itemsNotificacion.removeAll(quitarFinalizados);
            NumeroDeNotificaciones = itemsNotificacion.size();

        } catch (Exception e) {
        }
        return itemsNotificacion;
    }

    public void setItemsNotificacion(List<ExhibicionTb> itemsNotificacion) {
        this.itemsNotificacion = itemsNotificacion;
    }

    public ExhibicionTb prepareCreate() {
        selected = new ExhibicionTb();
        selected.setEEstado(0);
        selected.setEjemplarParticipaExhibicionTbList(new ArrayList<EjemplarParticipaExhibicionTb>());
        ejemplares = ejemplarFacade.EjemplarOrdenAsc();
        initializeEmbeddableKey();
        return selected;
    }

    public ExhibicionTb prepareEdit() {
        ejemplares = ejemplarFacade.EjemplarOrdenAsc();
        for (EjemplarParticipaExhibicionTb b : selected.getEjemplarParticipaExhibicionTbList()) {
            ejemplares.remove(b.getEjemplarTb());
        }
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
            ejemplarFacade.edit(ee.getEjemplarTb());
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExhibicionTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
            ejemplarFacade.edit(ee.getEjemplarTb());
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExhibicionTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExhibicionTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void finExhibicion() {
        FacesContext context = FacesContext.getCurrentInstance();
        selected.setEEstado(1);
        for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
            ee.getEjemplarTb().setECantDuplicado(ee.getEjemplarTb().getECantDuplicado()+1);
            ejemplarFacade.edit(ee.getEjemplarTb());
        }
        getFacade().edit(selected);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recibido", "INFO"));
    }

    public List<ExhibicionTb> getItems() {
        if (items == null) {
            items = getFacade().ExhibicionGeneral();
        }
        return items;
    }

    public List<ExhibicionTb> getLista() {
        lista = getFacade().findAll();

        return lista;
    }

    public void setLista(List<ExhibicionTb> lista) {
        this.lista = lista;
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

    public ExhibicionTb getExhibicionTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ExhibicionTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ExhibicionTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ExhibicionTb.class)
    public static class ExhibicionTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExhibicionTbController controller = (ExhibicionTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "exhibicionTbController");
            return controller.getExhibicionTb(getKey(value));
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
            if (object instanceof ExhibicionTb) {
                ExhibicionTb o = (ExhibicionTb) object;
                return getStringKey(o.getEIdexhibicion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ExhibicionTb.class.getName()});
                return null;
            }
        }

    }

    public String NombreNotificacion(ExhibicionTb a) {
        String nombre = null;
        int cadena = 0;
        try {
            cadena = a.getMNombre().length();
            if (cadena >= 200) {
                nombre = a.getMNombre().substring(0, 200);
                nombre = nombre + "...";
            } else {
                nombre = a.getMNombre().substring(0, cadena);
            }
        } catch (Exception e) {
            nombre = null;
        }
        return nombre;
    }

    public String EstadoList(int estado) {
        String mensaje = null;
        if (estado == 0) {
            mensaje = "En préstamo";
        }
        if (estado == 1) {
            mensaje = "Recibido";
        }
        return mensaje;
    }

    public String calculaAgente(int a) {
        try {
            agente = getAgenteFacade().agentePorId(a);
            return agente.getCNombre() + " " + agente.getCApellido();
        } catch (Exception e) {
            return null;
        }
    }

    public void anadir() {
        EjemplarParticipaExhibicionTb nuevo = new EjemplarParticipaExhibicionTb();
        ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - 1);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setExhibicionTb(selected);

        EjemplarParticipaExhibicionTbPK exhibicionpk = new EjemplarParticipaExhibicionTbPK();

        exhibicionpk.setEIdexhibicion(getFacade().siguienteId());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarParticipaExhibicionTbPK(exhibicionpk);

        selected.getEjemplarParticipaExhibicionTbList().add(nuevo);
        ejemplares.remove(ejemplar);

    }

    public void anadirEdit() {
        EjemplarParticipaExhibicionTb nuevo = new EjemplarParticipaExhibicionTb();
        ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - 1);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setExhibicionTb(selected);

        EjemplarParticipaExhibicionTbPK exhibicionpk = new EjemplarParticipaExhibicionTbPK();

        exhibicionpk.setEIdexhibicion(selected.getEIdexhibicion());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarParticipaExhibicionTbPK(exhibicionpk);

        selected.getEjemplarParticipaExhibicionTbList().add(nuevo);
        ejemplares.remove(ejemplar);

    }

    public void remover() {
        ejemplarExhibicion.getEjemplarTb().setECantDuplicado(ejemplarExhibicion.getEjemplarTb().getECantDuplicado() + 1);
        selected.getEjemplarParticipaExhibicionTbList().remove(ejemplarExhibicion);
        ejemplares.add(ejemplarExhibicion.getEjemplarTb());
    }

}
