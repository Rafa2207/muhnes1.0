package controlador;

import modelo.DonacionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.DonacionTbFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import modelo.EjemplarDonacionTb;
import modelo.EjemplarTb;
import modelo.InstitucionTb;
import modelo.TaxonomiaTb;

@Named("donacionTbController")
@ViewScoped
public class DonacionTbController implements Serializable {

    @EJB
    private servicio.DonacionTbFacade ejbFacade;
    @EJB
    private servicio.EjemplarTbFacade ejemplarFacade;
    @EJB
    private servicio.EjemplarDonacionTbFacade ejemplarDonacionFacade;
    private List<DonacionTb> items = null, filtro;
    private DonacionTb selected;
    private String tipoTaxon;
    private TaxonomiaTb taxonomia;
    private EjemplarTb ejemplar;
    private List<EjemplarTb> listaEjemplares;
    private Integer cantidad;
    private EjemplarDonacionTb ejemplarDonacion;
    //private List<EjemplarDonacionTb> eliminados = new ArrayList<EjemplarDonacionTb>();
    private List<EjemplarDonacionTb> listaEjemplaresDonados, listaEjemplaresDonadosEntregados;

    ;

    public DonacionTbController() {
    }

    public List<EjemplarDonacionTb> getListaEjemplaresDonados() {
        return listaEjemplaresDonados;
    }

    public void setListaEjemplaresDonados(List<EjemplarDonacionTb> listaEjemplaresDonados) {
        this.listaEjemplaresDonados = listaEjemplaresDonados;
    }

    public EjemplarDonacionTb getEjemplarDonacion() {
        return ejemplarDonacion;
    }

    public void setEjemplarDonacion(EjemplarDonacionTb ejemplarDonacion) {
        this.ejemplarDonacion = ejemplarDonacion;
    }

    public List<EjemplarDonacionTb> getListaEjemplaresDonadosEntregados() {
        return listaEjemplaresDonadosEntregados;
    }

    public void setListaEjemplaresDonadosEntregados(List<EjemplarDonacionTb> listaEjemplaresDonadosEntregados) {
        this.listaEjemplaresDonadosEntregados = listaEjemplaresDonadosEntregados;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<EjemplarTb> getListaEjemplares() {
        return listaEjemplares;
    }

    public void setListaEjemplares(List<EjemplarTb> listaEjemplares) {
        this.listaEjemplares = listaEjemplares;
    }

    public TaxonomiaTb getTaxonomia() {
        return taxonomia;
    }

    public EjemplarTb getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarTb ejemplar) {
        this.ejemplar = ejemplar;
    }

    public void setTaxonomia(TaxonomiaTb taxonomia) {
        this.taxonomia = taxonomia;
    }

    public String getTipoTaxon() {
        return tipoTaxon;
    }

    public void setTipoTaxon(String tipoTaxon) {
        this.tipoTaxon = tipoTaxon;
    }

    public List<DonacionTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DonacionTb> filtro) {
        this.filtro = filtro;
    }

    public DonacionTb getSelected() {
        return selected;
    }

    public void setSelected(DonacionTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DonacionTbFacade getFacade() {
        return ejbFacade;
    }

    public DonacionTb prepareCreate() {
        selected = new DonacionTb();
       // selected.setEjemplarDonacionTbList(new ArrayList<EjemplarDonacionTb>());
        initializeEmbeddableKey();
        return selected;
    }
    public DonacionTb prepareView() {
       // selected = new DonacionTb();
       // selected.setEjemplarDonacionTbList(new ArrayList<EjemplarDonacionTb>());
        listaEjemplaresDonadosEntregados = ejbFacade.listaEjemplaresEntregados(selected.getEIddonacion());
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        for (EjemplarDonacionTb i: listaEjemplaresDonados){
            i.setEIddonacion(ejbFacade.siguienteId());
            ejemplarDonacionFacade.edit(i);
        }
        /*if (selected.getEjemplarDonacionTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar ejemplares para donaciÃ³n");
            //oncomplete = "";
        } else {
            try {
                for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
                    ejemplarFacade.edit(ee.getEjemplarTb());
                }
            } catch (Exception e) {
            }*/
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        //}
    }

    public void update() {
        /*if (selected.getEjemplarDonacionTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar ejemplares para donaciÃ³n");
            //oncomplete = "";
        } else {
            try {
                for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
                    ejemplarFacade.edit(ee.getEjemplarTb());
                }
            } catch (Exception e) {
            }
            try {
                for (EjemplarDonacionTb aa : eliminados) {
                    ejemplarFacade.edit(aa.getEjemplarTb());
                }
            } catch (Exception e) {
            }*/
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbUpdated"));
        //}
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DonacionTb> getItems() {
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

    public DonacionTb getDonacionTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DonacionTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DonacionTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DonacionTb.class)
    public static class DonacionTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DonacionTbController controller = (DonacionTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "donacionTbController");
            return controller.getDonacionTb(getKey(value));
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
            if (object instanceof DonacionTb) {
                DonacionTb o = (DonacionTb) object;
                return getStringKey(o.getEIddonacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DonacionTb.class.getName()});
                return null;
            }
        }

    }

    public String calculaInsitucion(int b) {
        InstitucionTb institucion;
        String nombre;
        institucion = getFacade().BuscarInsitucion(b);
        nombre = institucion.getCNombre();
        return nombre;
    }
    
    public void llenarEjemplaresDonados(){
        setListaEjemplaresDonados(getFacade().listaEjemplares(selected.getEIdinstitucion()));
    }
    
    public void remover() {
        listaEjemplaresDonados.remove(ejemplarDonacion);
    }

/*    public void ejemplares(TaxonomiaTb taxon) {
        listaEjemplares = getFacade().BuscarEjemplares(taxon);
        for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
            for (EjemplarTb aa : listaEjemplares) {
                if ((ee.getEjemplarTb().getEIdejemplar()) == aa.getEIdejemplar()) {
                    listaEjemplares.remove(ee.getEjemplarTb());
                }
            }
        }
    }

    public void anadir() {
        EjemplarDonacionTb nuevo = new EjemplarDonacionTb();
        ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - cantidad);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setECantidad(cantidad);
        nuevo.setDonacionTb(selected);

        EjemplarDonacionTbPK exhibicionpk = new EjemplarDonacionTbPK();

        exhibicionpk.setEIddonacion(getFacade().siguienteId());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarDonacionTbPK(exhibicionpk);

        selected.getEjemplarDonacionTbList().add(nuevo);
        listaEjemplares.remove(ejemplar);

    }

    public void anadirM() {
        EjemplarDonacionTb nuevo = new EjemplarDonacionTb();
        ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - cantidad);
        nuevo.setEjemplarTb(ejemplar);
        nuevo.setECantidad(cantidad);
        nuevo.setDonacionTb(selected);

        EjemplarDonacionTbPK exhibicionpk = new EjemplarDonacionTbPK();

        exhibicionpk.setEIddonacion(selected.getEIddonacion());
        exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

        nuevo.setEjemplarDonacionTbPK(exhibicionpk);

        selected.getEjemplarDonacionTbList().add(nuevo);
        listaEjemplares.remove(ejemplar);

    }
    
    public void removerM() {
        ejemplarDonacion.getEjemplarTb().setECantDuplicado(ejemplarDonacion.getEjemplarTb().getECantDuplicado() + ejemplarDonacion.getECantidad());
        eliminados.add(ejemplarDonacion);
        selected.getEjemplarDonacionTbList().remove(ejemplarDonacion);
    }
*/
}
