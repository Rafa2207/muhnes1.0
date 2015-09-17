package controlador;

import modelo.EspecieTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.EspecieTbFacade;

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
import modelo.AgenteEspecieTb;
import modelo.AgenteEspecieTbPK;
import modelo.AgenteTb;
import modelo.GeneroTb;
import modelo.NombrecomunTb;
import modelo.PaisTb;

@Named("especieTbController")
@ViewScoped
public class EspecieTbController implements Serializable {

    @EJB
    private servicio.EspecieTbFacade ejbFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    @EJB
    private servicio.PaisTbFacade paisFacade;
    private List<EspecieTb> items = null, filtro;
    private EspecieTb selected;
    private boolean autor;
    private String conector, nombreComun, idioma;
    private AgenteTb agente;
    private AgenteEspecieTb agenteAutor;
    private NombrecomunTb nc;
    private List<AgenteTb> listaAutores;
    private List<PaisTb> listaIdiomas;
    private List<String> idiomas;
    int secuencia = 0;

    public List<EspecieTb> getFiltro() {
        return filtro;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public NombrecomunTb getNc() {
        return nc;
    }

    public void setNc(NombrecomunTb nc) {
        this.nc = nc;
    }

    public AgenteEspecieTb getAgenteAutor() {
        return agenteAutor;
    }

    public void setAgenteAutor(AgenteEspecieTb agenteAutor) {
        this.agenteAutor = agenteAutor;
    }

    public List<PaisTb> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaIdiomas(List<PaisTb> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }

    public List<AgenteTb> getListaAutores() {
        return listaAutores;
    }

    public AgenteTb getAgente() {
        return agente;
    }

    public void setAgente(AgenteTb agente) {
        this.agente = agente;
    }

    public void setListaAutores(List<AgenteTb> listaAutores) {
        this.listaAutores = listaAutores;
    }

    public String getConector() {
        return conector;
    }

    public void setConector(String conector) {
        this.conector = conector;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }

    public void setFiltro(List<EspecieTb> filtro) {
        this.filtro = filtro;
    }

    public EspecieTbController() {
    }

    public EspecieTb getSelected() {
        return selected;
    }

    public void setSelected(EspecieTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EspecieTbFacade getFacade() {
        return ejbFacade;
    }

    public EspecieTb prepareCreate(GeneroTb genero) {
        selected = new EspecieTb();
        initializeEmbeddableKey();
        selected.setEIdgenero(genero.getEIdgenero());
        selected.setAgenteEspecieTbList(new ArrayList<AgenteEspecieTb>());
        selected.setNombrecomunTbList(new ArrayList<NombrecomunTb>());
        listaAutores = agenteFacade.agentesAutores();
        listaIdiomas = paisFacade.idiomas();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EspecieTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EspecieTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EspecieTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EspecieTb> getItems(Integer gen) {
        /*if (items == null) {
         items = getFacade().findAll();
         }*/
        items = getFacade().buscarGeneroAsc(gen);
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

    public EspecieTb getEspecieTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EspecieTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EspecieTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EspecieTb.class)
    public static class EspecieTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EspecieTbController controller = (EspecieTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "especieTbController");
            return controller.getEspecieTb(getKey(value));
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
            if (object instanceof EspecieTb) {
                EspecieTb o = (EspecieTb) object;
                return getStringKey(o.getEIdespecie());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EspecieTb.class.getName()});
                return null;
            }
        }

    }

    public String autorBasionimio(boolean ab) {
        String cadena;
        if (ab == true) {
            cadena = "Sí";
        } else {
            cadena = "No";
        }
        return cadena;
    }

    public void anadirAutor() {
        int sec = 0;
        AgenteEspecieTb nuevo = new AgenteEspecieTb();
        nuevo.setAgenteTb(agente);
        nuevo.setBAutorBasionimio(autor);
        nuevo.setCConector(conector);
        nuevo.setEspecieTb(selected);
        for (AgenteEspecieTb i : selected.getAgenteEspecieTbList()) {
            sec = sec + 1;
        }
        nuevo.setESecuencia(sec + 1);

        AgenteEspecieTbPK agenteEspeciepk = new AgenteEspecieTbPK();

        agenteEspeciepk.setEIdagente(agente.getEIdagente());
        agenteEspeciepk.setEIdespecie(getFacade().siguienteId());

        nuevo.setAgenteEspecieTbPK(agenteEspeciepk);

        selected.getAgenteEspecieTbList().add(nuevo);

        listaAutores.remove(agente);
        autor = false;
        conector = "";
    }

    public void removerAutor() {
        int sec = 1;
        selected.getAgenteEspecieTbList().remove(agenteAutor);
        listaAutores.add(agenteAutor.getAgenteTb());
        for (AgenteEspecieTb i : selected.getAgenteEspecieTbList()) {
            i.setESecuencia(sec);
            sec++;
        }
    }
    
    public void anadirNombreComun() {
        NombrecomunTb nuevo = new NombrecomunTb();
        nuevo.setCNombre(nombreComun);
        nuevo.setCIdioma(idioma);
        nuevo.setEIdespecie(selected);
        selected.getNombrecomunTbList().add(nuevo);
        nombreComun="";
        idioma="";

    }

    public void removerNombreComun() {
        selected.getNombrecomunTbList().remove(nc);
    }

}
