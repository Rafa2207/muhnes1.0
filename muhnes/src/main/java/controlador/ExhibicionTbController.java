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
import modelo.BitacoraTb;
import modelo.EjemplarParticipaExhibicionTb;
import modelo.EjemplarParticipaExhibicionTbPK;
import modelo.EjemplarTb;
import modelo.TaxonomiaTb;
import modelo.UsuarioTb;
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
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<ExhibicionTb> items = null, lista = null, filtro, itemsNotificacion = null, itemsControlExhibiciones = null;
    private List<EjemplarTb> ejemplares, listaEjemplares;
    private List<EjemplarParticipaExhibicionTb> listaEliminados;
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

    public List<ExhibicionTb> getItemsControlExhibiciones() {
        itemsControlExhibiciones = getFacade().ExhibicionControl();
        return itemsControlExhibiciones;
    }

    public void setItemsControlExhibiciones(List<ExhibicionTb> itemsControlExhibiciones) {
        this.itemsControlExhibiciones = itemsControlExhibiciones;
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

    public List<EjemplarTb> getListaEjemplares() {
        return listaEjemplares;
    }

    public void setListaEjemplares(List<EjemplarTb> listaEjemplares) {
        this.listaEjemplares = listaEjemplares;
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
        initializeEmbeddableKey();
        return selected;
    }

    public ExhibicionTb prepareEdit() {
        listaEliminados = new ArrayList<EjemplarParticipaExhibicionTb>();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected.getEjemplarParticipaExhibicionTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar ejemplares a préstamo");
        } else {

            for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                ejemplarFacade.edit(ee.getEjemplarTb());
            }
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Realizada exhibición: '" + selected.getMNombre() + "' de tipo '" + selected.getCTipo() + "' en el módulo: Exhibición");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExhibicionTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }

    }

    public void update() {
        if (selected.getEjemplarParticipaExhibicionTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar ejemplares a préstamo");
        } else {
            try {
                for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                    ejemplarFacade.edit(ee.getEjemplarTb());
                }
            } catch (Exception e) {
            }
            try {
                for (EjemplarParticipaExhibicionTb ee : listaEliminados) {
                    ejemplarFacade.edit(ee.getEjemplarTb());
                }
            } catch (Exception e) {
            }
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Modificada exhibición: '" + selected.getMNombre() + "' en el módulo: Exhibición");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExhibicionTbUpdated"));
        }
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
        selected.setEEstado(2);
        try {
            for (EjemplarParticipaExhibicionTb ee : listaEliminados) {
                ejemplarFacade.edit(ee.getEjemplarTb());
            }
        } catch (Exception e) {
        }

        //Para determinar estado de ejemplar
        try {
            for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                if (ee.getEEstado() != 0) {
                    ee.setFFecha(selected.getFFechaRecibido());
                }
                if (ee.getEEstado() != 0 || ee.getEEstado() != 3) {
                    ee.getEjemplarTb().setEEstado(1);
                }
                ejemplarFacade.edit(ee.getEjemplarTb());
            }

        } catch (Exception e) {
        }
        //Fin determinar estado de ejemplar

        //Para determinar estado de exhibicion 
        try {
            for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                if (ee.getEEstado() == 0) {
                    selected.setEEstado(1);
                    break;
                }
            }

        } catch (Exception e) {
        }
        //Fin determinar estado de exhibicion

        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Reingreso de ejemplares de la exhibición: '" + selected.getMNombre() + "' en el módulo: Exhibición");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
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
            mensaje = "Parcialmente Recibido";
        }
        if (estado == 2) {
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
        ejemplar.setEEstado(0);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setExhibicionTb(selected);
        nuevo.setEEstado(0);

        EjemplarParticipaExhibicionTbPK exhibicionpk = new EjemplarParticipaExhibicionTbPK();

        exhibicionpk.setEIdexhibicion(getFacade().siguienteId());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarParticipaExhibicionTbPK(exhibicionpk);

        selected.getEjemplarParticipaExhibicionTbList().add(nuevo);
        listaEjemplares.remove(ejemplar);

    }

    public void anadirEdit() {
        EjemplarParticipaExhibicionTb nuevo = new EjemplarParticipaExhibicionTb();
        ejemplar.setEEstado(0);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setExhibicionTb(selected);

        EjemplarParticipaExhibicionTbPK exhibicionpk = new EjemplarParticipaExhibicionTbPK();

        exhibicionpk.setEIdexhibicion(selected.getEIdexhibicion());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarParticipaExhibicionTbPK(exhibicionpk);

        selected.getEjemplarParticipaExhibicionTbList().add(nuevo);
        listaEjemplares.remove(ejemplar);

    }

    public void llenarEjemplares(TaxonomiaTb taxon) {
        listaEjemplares = ejemplarFacade.BuscarEjemplares(taxon);
        for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
            for (EjemplarTb aa : listaEjemplares) {
                if ((ee.getEjemplarTb().getEIdejemplar()) == aa.getEIdejemplar()) {
                    listaEjemplares.remove(ee.getEjemplarTb());
                }
            }
        }
    }

    public void remover() {
        ejemplarExhibicion.getEjemplarTb().setEEstado(1);
        selected.getEjemplarParticipaExhibicionTbList().remove(ejemplarExhibicion);
        listaEjemplares.add(ejemplarExhibicion.getEjemplarTb());
    }

    public void removerEdit() {
        ejemplarExhibicion.getEjemplarTb().setEEstado(1);
        selected.getEjemplarParticipaExhibicionTbList().remove(ejemplarExhibicion);
        listaEliminados.add(ejemplarExhibicion);
        listaEjemplares.add(ejemplarExhibicion.getEjemplarTb());
    }

    public void prepareRecibir() {
        selected.setEEstado(0);
    }

    public void finExhibicionUpdate() {
        FacesContext context = FacesContext.getCurrentInstance();
        selected.setEEstado(2);
        try {
            for (EjemplarParticipaExhibicionTb ee : listaEliminados) {
                ejemplarFacade.edit(ee.getEjemplarTb());
            }
        } catch (Exception e) {
        }

        //Para determinar estado de ejemplar
        try {
            for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                if (ee.getEEstado() != 0 || ee.getEEstado() != 3) {
                    ee.getEjemplarTb().setEEstado(1);
                }
                ejemplarFacade.edit(ee.getEjemplarTb());
            }

        } catch (Exception e) {
        }
        //Fin determinar estado de ejemplar

        //Para determinar estado de exhibicion 
        try {
            for (EjemplarParticipaExhibicionTb ee : selected.getEjemplarParticipaExhibicionTbList()) {
                if (ee.getEEstado() == 0) {
                    selected.setEEstado(1);
                    break;
                }
            }

        } catch (Exception e) {
        }
        //Fin determinar estado de exhibicion

        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Reingreso de ejemplares de la exhibición: '" + selected.getMNombre() + "' en el módulo: Exhibición");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        getFacade().edit(selected);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recibido", "INFO"));
    }

}
