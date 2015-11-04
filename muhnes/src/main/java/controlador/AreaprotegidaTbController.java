package controlador;

import modelo.AreaprotegidaTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.AreaprotegidaTbFacade;

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

@Named("areaprotegidaTbController")
@ViewScoped
public class AreaprotegidaTbController implements Serializable {

    @EJB
    private servicio.AreaprotegidaTbFacade ejbFacade;
    private List<AreaprotegidaTb> items = null, filtro, itemsAreaOrdenNombreAsc=null;
    private AreaprotegidaTb selected;

    public List<AreaprotegidaTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<AreaprotegidaTb> filtro) {
        this.filtro = filtro;
    }

    public AreaprotegidaTbController() {
    }

    public AreaprotegidaTb getSelected() {
        return selected;
    }

    public void setSelected(AreaprotegidaTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AreaprotegidaTbFacade getFacade() {
        return ejbFacade;
    }

    public List<AreaprotegidaTb> getItemsAreaOrdenNombreAsc() {
        itemsAreaOrdenNombreAsc=getFacade().AreaProtegidaOrdenNombreAsc();
        return itemsAreaOrdenNombreAsc;
    }

    public void setItemsAreaOrdenNombreAsc(List<AreaprotegidaTb> itemsAreaOrdenNombreAsc) {
        this.itemsAreaOrdenNombreAsc = itemsAreaOrdenNombreAsc;
    } 

    public AreaprotegidaTb prepareCreate() {
        selected = new AreaprotegidaTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AreaprotegidaTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AreaprotegidaTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AreaprotegidaTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AreaprotegidaTb> getItems() {
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

    public AreaprotegidaTb getAreaprotegidaTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AreaprotegidaTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AreaprotegidaTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AreaprotegidaTb.class)
    public static class AreaprotegidaTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaprotegidaTbController controller = (AreaprotegidaTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaprotegidaTbController");
            return controller.getAreaprotegidaTb(getKey(value));
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
            if (object instanceof AreaprotegidaTb) {
                AreaprotegidaTb o = (AreaprotegidaTb) object;
                return getStringKey(o.getEIdarea());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AreaprotegidaTb.class.getName()});
                return null;
            }
        }

    }

    public void latitudDecimal(int grado, int minuto, double segundo) {
        if (grado < 0) {

            double resultado, min, seg, gra;
            gra = grado * -1;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = (gra + min + seg) * -1;

            selected.setDLatituddecimal(resultado);
        } else {
            double resultado, min, seg, gra;
            gra = grado;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = gra + min + seg;

            selected.setDLatituddecimal(resultado);
        }

    }

    public void longitudDecimal(int grado, int minuto, double segundo) {
        if (grado < 0) {
            double resultado, min, seg, gra;
            gra = grado * -1;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = (gra + min + seg) * -1;

            selected.setDLongituddecimal(resultado);
        } else {
            double resultado, min, seg, gra;
            gra = grado;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = gra + min + seg;

            selected.setDLongituddecimal(resultado);
        }

    }
    
    public String latitudList(AreaprotegidaTb la) {
        String latitud;
        if (la.getELatitudgrados() < 0) {
            latitud = la.getELatitudgrados() + "°" + la.getELatitudminutos() + "'" + la.getDLatitudsegundos() + "''" + " S";
            return latitud;
        } else {
            latitud = la.getELatitudgrados() + "°" + la.getELatitudminutos() + "'" + la.getDLatitudsegundos() + "''" + " N";
            return latitud;
        }
    }

    public String longitudList(AreaprotegidaTb lo) {
        String longitud;
        if (lo.getELongitudgrados() < 0) {
            longitud = lo.getELongitudgrados()+ "°" + lo.getELongitudminutos() + "'" + lo.getDLongitudsegundos() + "''" + " W";
            return longitud;
        } else {
            longitud = lo.getELongitudgrados() + "°" + lo.getELongitudminutos() + "'" + lo.getDLongitudsegundos() + "''" + " E";
            return longitud;
        }
    }

}
