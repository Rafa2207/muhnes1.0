package controlador;

import modelo.EjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.EjemplarTbFacade;

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
import modelo.AgenteIdentificaEjemplarTb;
import modelo.AgenteIdentificaEjemplarTbPK;
import modelo.AgenteTb;
import modelo.EspecieTb;

@Named("ejemplarTbController")
@ViewScoped
public class EjemplarTbController implements Serializable {

    @EJB
    private servicio.EjemplarTbFacade ejbFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    private List<EjemplarTb> items = null, filtro;
    private EjemplarTb selected;
    private AgenteTb agente;
    private List<AgenteTb> listaAgenteR, listaAgenteI;
    private AgenteIdentificaEjemplarTb agenteIdentifica;
    private Integer tipoTaxon;
    private String cod;
    private boolean familia;

    public EjemplarTbController() {
    }

    public String getCod() {
        return cod;
    }

    public boolean isFamilia() {
        return familia;
    }

    public void setFamilia(boolean familia) {
        this.familia = familia;
    }

    public List<AgenteTb> getListaAgenteI() {
        return listaAgenteI;
    }

    public void setListaAgenteI(List<AgenteTb> listaAgenteI) {
        this.listaAgenteI = listaAgenteI;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getTipoTaxon() {
        return tipoTaxon;
    }

    public void setTipoTaxon(Integer tipoTaxon) {
        this.tipoTaxon = tipoTaxon;
    }

    public AgenteIdentificaEjemplarTb getAgenteIdentifica() {
        return agenteIdentifica;
    }

    public void setAgenteIdentifica(AgenteIdentificaEjemplarTb agenteIdentifica) {
        this.agenteIdentifica = agenteIdentifica;
    }

    public List<AgenteTb> getListaAgenteR() {
        return listaAgenteR;
    }

    public void setListaAgenteR(List<AgenteTb> listaAgenteR) {
        this.listaAgenteR = listaAgenteR;
    }

    public AgenteTb getAgente() {
        return agente;
    }

    public void setAgente(AgenteTb agente) {
        this.agente = agente;
    }

    public List<EjemplarTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<EjemplarTb> filtro) {
        this.filtro = filtro;
    }

    public EjemplarTb getSelected() {
        return selected;
    }

    public void setSelected(EjemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EjemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public EjemplarTb prepareCreate() {
        selected = new EjemplarTb();
        selected.setAgenteIdentificaEjemplarTbList(new ArrayList<AgenteIdentificaEjemplarTb>());
        selected.setAgenteIdentificaEjemplarTbIDList(new ArrayList<AgenteIdentificaEjemplarTb>());
        initializeEmbeddableKey();
        listaAgenteR = agenteFacade.agentesRecolectores();
        listaAgenteI = agenteFacade.agentesIdentificadores();
        return selected;
    }
    public EjemplarTb prepareView(EjemplarTb ejemplar) {
        selected.getAgenteIdentificaEjemplarTbIDList().clear();
        selected.getAgenteIdentificaEjemplarTbList().clear();
        selected.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ejemplar.getEIdejemplar(),"Identificador"));
        selected.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ejemplar.getEIdejemplar(),"Recolector"));
        return selected;
    }
    public EjemplarTb prepareEdit(EjemplarTb ejemplar) {
        listaAgenteR = agenteFacade.agentesRecolectores();
        for(AgenteIdentificaEjemplarTb i: selected.getAgenteIdentificaEjemplarTbList()){
            listaAgenteR.remove(i.getAgenteTb());
        }
        listaAgenteI = agenteFacade.agentesIdentificadores();
        for(AgenteIdentificaEjemplarTb i: selected.getAgenteIdentificaEjemplarTbIDList()){
            listaAgenteI.remove(i.getAgenteTb());
        }
        selected.getAgenteIdentificaEjemplarTbIDList().clear();
        selected.getAgenteIdentificaEjemplarTbList().clear();
        selected.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ejemplar.getEIdejemplar(),"Identificador"));
        selected.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ejemplar.getEIdejemplar(),"Recolector"));
        
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarTb> getItems() {
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

    public EjemplarTb getEjemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EjemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EjemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EjemplarTb.class)
    public static class EjemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EjemplarTbController controller = (EjemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ejemplarTbController");
            return controller.getEjemplarTb(getKey(value));
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
            if (object instanceof EjemplarTb) {
                EjemplarTb o = (EjemplarTb) object;
                return getStringKey(o.getEIdejemplar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EjemplarTb.class.getName()});
                return null;
            }
        }

    }

    public void anadirRecolector() {
        int sec = 0;
        AgenteIdentificaEjemplarTb nuevo = new AgenteIdentificaEjemplarTb();
        nuevo.setAgenteTb(agente);
        nuevo.setEjemplarTb(selected);
        //nuevo.setCTipo("Recolector");
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbList()) {
            sec = sec + 1;
        }
        nuevo.setESecuencia(sec + 1);
        AgenteIdentificaEjemplarTbPK agenteIdentificapk = new AgenteIdentificaEjemplarTbPK();

        agenteIdentificapk.setEIdagente(agente.getEIdagente());
        agenteIdentificapk.setEIdejemplar(getFacade().siguienteId());
        agenteIdentificapk.setCTipo("Recolector");

        nuevo.setAgenteIdentificaEjemplarTbPK(agenteIdentificapk);

        selected.getAgenteIdentificaEjemplarTbList().add(nuevo);

        listaAgenteR.remove(agente);
    }
    public void anadirRecolectorM() {
        int sec = 0;
        AgenteIdentificaEjemplarTb nuevo = new AgenteIdentificaEjemplarTb();
        nuevo.setAgenteTb(agente);
        nuevo.setEjemplarTb(selected);
        //nuevo.setCTipo("Recolector");
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbList()) {
            sec = sec + 1;
        }
        nuevo.setESecuencia(sec + 1);
        AgenteIdentificaEjemplarTbPK agenteIdentificapk = new AgenteIdentificaEjemplarTbPK();

        agenteIdentificapk.setEIdagente(agente.getEIdagente());
        agenteIdentificapk.setEIdejemplar(selected.getEIdejemplar());
        agenteIdentificapk.setCTipo("Recolector");

        nuevo.setAgenteIdentificaEjemplarTbPK(agenteIdentificapk);

        selected.getAgenteIdentificaEjemplarTbList().add(nuevo);

        listaAgenteR.remove(agente);
    }

    public void removerRecolector() {
        int sec = 1;
        selected.getAgenteIdentificaEjemplarTbList().remove(agenteIdentifica);
        listaAgenteR.add(agenteIdentifica.getAgenteTb());
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbList()) {
            i.setESecuencia(sec);
            sec++;
        }

    }

    public void anadirIndentificador() {
        int sec = 0;
        AgenteIdentificaEjemplarTb nuevo = new AgenteIdentificaEjemplarTb();
        nuevo.setAgenteTb(agente);
        nuevo.setEjemplarTb(selected);
        //nuevo.setCTipo("Identificador");
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbIDList()) {
            sec = sec + 1;
        }
        nuevo.setESecuencia(sec + 1);
        AgenteIdentificaEjemplarTbPK agenteIdentificapk = new AgenteIdentificaEjemplarTbPK();

        agenteIdentificapk.setEIdagente(agente.getEIdagente());
        agenteIdentificapk.setEIdejemplar(getFacade().siguienteId());
        agenteIdentificapk.setCTipo("Identificador");

        nuevo.setAgenteIdentificaEjemplarTbPK(agenteIdentificapk);

        selected.getAgenteIdentificaEjemplarTbIDList().add(nuevo);

        listaAgenteI.remove(agente);
    }
    
    public void anadirIndentificadorM() {
        int sec = 0;
        AgenteIdentificaEjemplarTb nuevo = new AgenteIdentificaEjemplarTb();
        nuevo.setAgenteTb(agente);
        nuevo.setEjemplarTb(selected);
        //nuevo.setCTipo("Identificador");
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbIDList()) {
            sec = sec + 1;
        }
        nuevo.setESecuencia(sec + 1);
        AgenteIdentificaEjemplarTbPK agenteIdentificapk = new AgenteIdentificaEjemplarTbPK();

        agenteIdentificapk.setEIdagente(agente.getEIdagente());
        agenteIdentificapk.setEIdejemplar(selected.getEIdejemplar());
        agenteIdentificapk.setCTipo("Identificador");

        nuevo.setAgenteIdentificaEjemplarTbPK(agenteIdentificapk);

        selected.getAgenteIdentificaEjemplarTbIDList().add(nuevo);

        listaAgenteI.remove(agente);
    }

    public void removerIdentificador() {
        int sec = 1;
        selected.getAgenteIdentificaEjemplarTbIDList().remove(agenteIdentifica);
        listaAgenteI.add(agenteIdentifica.getAgenteTb());
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbIDList()) {
            i.setESecuencia(sec);
            sec++;
        }

    }

    public void generarCodigoEntrada() {
        String codigo = cod;
        String correlativo = getFacade().obtenerCorrelativo(codigo);
        selected.setCCodigoentrada(codigo + "-" + correlativo);
    }
    
   public String calculaAgente(List<AgenteTb> a, int b) {
       String agente=""; 
       for (AgenteTb agen : a) {
            if (agen.getEIdagente() == b) {
                agente = agen.getCNombre() + " " + agen.getCApellido();
            }
        }
        return agente;
   }
   
   public String calculaEspecie(List<EspecieTb> a, int b) {
       String especie=""; 
       for (EspecieTb esp : a) {
            if (esp.getEIdespecie()== b) {
                especie = esp.getCNombre();
            }
        }
        return especie;
   }
}
