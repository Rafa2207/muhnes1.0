package controlador;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modelo.EjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.EjemplarTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.AgenteIdentificaEjemplarTbPK;
import modelo.AgenteTb;
import modelo.EjemplarDonacionTb;
import modelo.EjemplarNombrecomunTb;
import modelo.InstitucionTb;
import modelo.NombrecomunTb;
import modelo.ProyectoTb;
//import modelo.EspecieTb;

@Named("ejemplarTbController")
@ViewScoped
public class EjemplarTbController implements Serializable {

    @EJB
    private servicio.EjemplarTbFacade ejbFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    @EJB
    private servicio.InstitucionTbFacade institucionFacade;
    private List<EjemplarTb> items = null, filtro;
    private EjemplarTb selected;
    private AgenteTb agente;
    private NombrecomunTb nc;
    private List<AgenteTb> listaAgenteR, listaAgenteI, listaResponsables, listaIdenficadores, listaRecolectores;
    private List<NombrecomunTb> listaNombreComun;
    private List<InstitucionTb> listaInstitucion;
    private AgenteIdentificaEjemplarTb agenteIdentifica;
    private EjemplarNombrecomunTb ejemplarnc;
    private EjemplarDonacionTb ejemplarIns;
    private String tipoTaxon, tipoReporte, codigo1, codigo2;
    private String cod;
    private Integer responsable, identificador, recolector, columnas;
    private boolean familia, booleanoReporte, booleanFecha, booleanCodigo, booleanResponsable, booleanIdentidicador, booleanRecolector;
    private InstitucionTb ins;
    private Date f1, f2;

    public EjemplarTbController() {
    }

    public List<AgenteTb> getListaRecolectores() {
        return listaRecolectores;
    }

    public void setListaRecolectores(List<AgenteTb> listaRecolectores) {
        this.listaRecolectores = listaRecolectores;
    }

    public Integer getRecolector() {
        return recolector;
    }

    public void setRecolector(Integer recolector) {
        this.recolector = recolector;
    }

    public boolean isBooleanRecolector() {
        return booleanRecolector;
    }

    public void setBooleanRecolector(boolean booleanRecolector) {
        this.booleanRecolector = booleanRecolector;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public List<AgenteTb> getListaIdenficadores() {
        return listaIdenficadores;
    }

    public void setListaIdenficadores(List<AgenteTb> listaIdenficadores) {
        this.listaIdenficadores = listaIdenficadores;
    }

    public boolean isBooleanIdentidicador() {
        return booleanIdentidicador;
    }

    public void setBooleanIdentidicador(boolean booleanIdentidicador) {
        this.booleanIdentidicador = booleanIdentidicador;
    }

    public List<AgenteTb> getListaResponsables() {
        return listaResponsables;
    }

    public void setListaResponsables(List<AgenteTb> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

    public Integer getResponsable() {
        return responsable;
    }

    public void setResponsable(Integer responsable) {
        this.responsable = responsable;
    }

    public boolean isBooleanResponsable() {
        return booleanResponsable;
    }

    public void setBooleanResponsable(boolean booleanResponsable) {
        this.booleanResponsable = booleanResponsable;
    }

    public String getCodigo1() {
        return codigo1;
    }

    public void setCodigo1(String codigo1) {
        this.codigo1 = codigo1;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public boolean isBooleanCodigo() {
        return booleanCodigo;
    }

    public void setBooleanCodigo(boolean booleanCodigo) {
        this.booleanCodigo = booleanCodigo;
    }

    public boolean isBooleanFecha() {
        return booleanFecha;
    }

    public void setBooleanFecha(boolean booleanFecha) {
        this.booleanFecha = booleanFecha;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public boolean isBooleanoReporte() {
        return booleanoReporte;
    }

    public void setBooleanoReporte(boolean booleanoReporte) {
        this.booleanoReporte = booleanoReporte;
    }

    public Date getF1() {
        return f1;
    }

    public void setF1(Date f1) {
        this.f1 = f1;
    }

    public Date getF2() {
        return f2;
    }

    public void setF2(Date f2) {
        this.f2 = f2;
    }

    public EjemplarDonacionTb getEjemplarIns() {
        return ejemplarIns;
    }

    public void setEjemplarIns(EjemplarDonacionTb ejemplarIns) {
        this.ejemplarIns = ejemplarIns;
    }

    public InstitucionTb getIns() {
        return ins;
    }

    public void setIns(InstitucionTb ins) {
        this.ins = ins;
    }

    public List<NombrecomunTb> getListaNombreComun() {
        return listaNombreComun;
    }

    public void setListaNombreComun(List<NombrecomunTb> listaNombreComun) {
        this.listaNombreComun = listaNombreComun;
    }

    public List<InstitucionTb> getListaInstitucion() {
        return listaInstitucion;
    }

    public void setListaInstitucion(List<InstitucionTb> listaInstitucion) {
        this.listaInstitucion = listaInstitucion;
    }

    public EjemplarNombrecomunTb getEjemplarnc() {
        return ejemplarnc;
    }

    public void setEjemplarnc(EjemplarNombrecomunTb ejemplarnc) {
        this.ejemplarnc = ejemplarnc;
    }

    public NombrecomunTb getNc() {
        return nc;
    }

    public void setNc(NombrecomunTb nc) {
        this.nc = nc;
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

    public String getTipoTaxon() {
        return tipoTaxon;
    }

    public void setTipoTaxon(String tipoTaxon) {
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
        selected.setEjemplarNombrecomunTbList(new ArrayList<EjemplarNombrecomunTb>());
        selected.setEjemplarDonacionTbList(new ArrayList<EjemplarDonacionTb>()); //iniciar la lista para poder agregar informacion
        initializeEmbeddableKey();
        listaInstitucion = institucionFacade.findAll();
        listaAgenteR = agenteFacade.agentesRecolectores();
        listaAgenteI = agenteFacade.agentesIdentificadores();
        return selected;
    }

    public EjemplarTb prepareView(EjemplarTb ejemplar) {
        selected.getAgenteIdentificaEjemplarTbIDList().clear();
        selected.getAgenteIdentificaEjemplarTbList().clear();
        selected.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ejemplar.getEIdejemplar(), "Identificador"));
        selected.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ejemplar.getEIdejemplar(), "Recolector"));
        return selected;
    }

    public EjemplarTb prepareEdit(EjemplarTb ejemplar) {
        listaAgenteR = agenteFacade.agentesRecolectores();
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbList()) {
            listaAgenteR.remove(i.getAgenteTb());
        }
        listaAgenteI = agenteFacade.agentesIdentificadores();
        for (AgenteIdentificaEjemplarTb i : selected.getAgenteIdentificaEjemplarTbIDList()) {
            listaAgenteI.remove(i.getAgenteTb());
        }
        selected.getAgenteIdentificaEjemplarTbIDList().clear();
        selected.getAgenteIdentificaEjemplarTbList().clear();
        selected.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ejemplar.getEIdejemplar(), "Identificador"));
        selected.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ejemplar.getEIdejemplar(), "Recolector"));
        if (selected.getEIdtaxonomia().getCTipo().equals("Familia")) {
            tipoTaxon = "Familia";
        }
        if (selected.getEIdtaxonomia().getCTipo().equals("Genero")) {
            tipoTaxon = "Genero";
        }
        if (selected.getEIdtaxonomia().getCTipo().equals("Especie")) {
            tipoTaxon = "Especie";
        }
        if (selected.getEIdtaxonomia().getCTipo().equals("Subespecie")) {
            tipoTaxon = "Subespecie";
        }
        if (selected.getEIdtaxonomia().getCTipo().equals("Variedad")) {
            tipoTaxon = "Variedad";
        }
        //lista de nombres comunes.

        //lista de instituciones.
        listaInstitucion = institucionFacade.findAll();
        for (EjemplarDonacionTb i : selected.getEjemplarDonacionTbList()) {
            listaInstitucion.remove(institucionFacade.Institucion(i.getEIdinstitucion()));
        }
        return selected;
    }

    public void create() {
        if (selected.getAgenteIdentificaEjemplarTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar recolectores");
            // oncomplete = "";
        } else if (selected.getAgenteIdentificaEjemplarTbIDList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar identificadores");
            // oncomplete = "";
        } else {
            if (selected.getEIdinstitucion() != null) {
                selected.setEEstado(2); //ejemplar que se recibiÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³ donado
            } else {
                selected.setEEstado(1); //ejemplar que se recolectÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³
            }
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
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
        String agente = "";
        for (AgenteTb agen : a) {
            if (agen.getEIdagente() == b) {
                agente = agen.getCNombre() + " " + agen.getCApellido();
            }
        }
        return agente;
    }

    /*   public String calculaEspecie(List<EspecieTb> a, int b) {
     String especie=""; 
     for (EspecieTb esp : a) {
     if (esp.getEIdespecie()== b) {
     especie = esp.getCNombre();
     }
     }
     return especie;
     }*/
    public void llenarNombreComun() {
        if (selected.getEIdtaxonomia().getERango() >= 3) {
            setListaNombreComun(getFacade().nombresComunes(selected.getEIdtaxonomia()));
        }
    }

    public void anadirNombreComun() {
        //int sec = 0;
        EjemplarNombrecomunTb nuevo = new EjemplarNombrecomunTb();
        nuevo.setCNombrecomun(nc.getCNombre());
        nuevo.setEIdejemplar(selected);

        selected.getEjemplarNombrecomunTbList().add(nuevo);

        listaNombreComun.remove(nc);
    }

    public void removerNombrecomun() {
        selected.getEjemplarNombrecomunTbList().remove(ejemplarnc);
        //listaAgenteI.add(agenteIdentifica.getAgenteTb());
    }

    public void anadirInstitucion() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selected.getECantDuplicado() == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha ingresado cantidad de duplicados o no es una cantidad valida", "Alerta: "));
        } else {
            int a = selected.getECantDuplicado();
            int b = selected.getEjemplarDonacionTbList().size();
            if (a > b) {

                EjemplarDonacionTb nuevo = new EjemplarDonacionTb();
                nuevo.setEIdinstitucion(ins.getEIdinstitucion());
                nuevo.setBEstado(false);
                nuevo.setEIdejemplar(selected);

                selected.getEjemplarDonacionTbList().add(nuevo);

                listaInstitucion.remove(ins);
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La cantidad es mayor a los duplicados disponibles", "Alerta: "));
            }
        }
    }

    public String nombreInstitucion(Integer id) {
        return institucionFacade.nombreIns(id);
    }

    public void removerInstitucion() {
        selected.getEjemplarDonacionTbList().remove(ejemplarIns);
        listaInstitucion.add(institucionFacade.Institucion(ejemplarIns.getEIdinstitucion()));
    }

    //********************************************REPORTES********************************************************//
    public String calculaAgenteReporte(int b) {
        AgenteTb agen;
        agen = agenteFacade.agentePorId(b);
        String agente = agen.getCNombre() + " " + agen.getCApellido();
        return agente;
    }

    public void prepareReporte() {
        booleanFecha = false;
        booleanCodigo = false;
        booleanoReporte = true;
        booleanRecolector = false;
        booleanIdentidicador = false;
        booleanResponsable = false;
        listaResponsables = getFacade().responsables();
        listaIdenficadores = getFacade().identificadores();
        listaRecolectores = getFacade().recolectores();
        f1 = null;
        f2 = null;
    }

    public void actualizarVista() {
        if (tipoReporte.equals("general")) {
            booleanFecha = false;
            booleanCodigo = false;
            booleanoReporte = true;
            booleanResponsable = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
        } else if (tipoReporte.equals("recoleccion") || tipoReporte.equals("identificacion")) {
            booleanFecha = true;
            booleanCodigo = false;
            booleanoReporte = false;
            booleanResponsable = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
        } else if (tipoReporte.equals("entrada")) {
            booleanCodigo = true;
            booleanFecha = false;
            booleanoReporte = false;
            booleanResponsable = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
        } else if (tipoReporte.equals("responsable")) {
            booleanResponsable = true;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
        } else if (tipoReporte.equals("identificador")) {
            booleanResponsable = false;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanRecolector = false;
            booleanIdentidicador = true;
        } else if (tipoReporte.equals("recolector")) {
            booleanResponsable = false;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanIdentidicador = false;
            booleanRecolector = true;
        }
    }

    public void reporteAll() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

                // Inicia reporte
                Document document = new Document(PageSize.LETTER.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                TableHeader event = new TableHeader();
                writer.setPageEvent(event);
                document.open();

                //Encabezado
                //ruta del sistema
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                //Referencia al logo
                String logoPath = servletContext.getRealPath("") + File.separator + "resources"
                        + File.separator + "images"
                        + File.separator + "muhnes1.png";

                //Tabla para  el encabezado
                PdfPTable encabezado = new PdfPTable(3);
                //Ancho de la tabla
                encabezado.setWidthPercentage(100);
                //Primera celda
                PdfPCell cell1 = new PdfPCell();
                //Instancia al logo
                Image logo = Image.getInstance(logoPath);
                //Indico tamaÃƒÆ’Ã‚Â±o del logo
                logo.scaleToFit(80, 80);
                //aÃƒÆ’Ã‚Â±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃƒÆ’Ã‚Â±ado celda a la tabla
                encabezado.addCell(cell1);
                //celdas se alineen al centro
                encabezado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                encabezado.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                //Siguientes celdas no tengan borde
                encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                //nueva celda con los datos del MUHNES
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de El Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte General de Ejemplares", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes
                if (booleanFecha == true && tipoReporte.equals("recoleccion")) {
                    Paragraph titulo2 = new Paragraph("Fecha de RecolecciÃ³n: " + new SimpleDateFormat("dd MMMM yyyy").format(f1) + " - " + new SimpleDateFormat("dd MMMM yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanFecha == true && tipoReporte.equals("identificacion")) {
                    Paragraph titulo2 = new Paragraph("Fecha de IdentificaciÃ³n: " + new SimpleDateFormat("dd MMMM yyyy").format(f1) + " - " + new SimpleDateFormat("dd MMMM yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanCodigo == true) {
                    Paragraph titulo2 = new Paragraph("CÃ³digo de Entrada: Desde " + getCodigo1() + " Hasta " + getCodigo2(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanResponsable == true) {
                    Paragraph titulo2 = new Paragraph("Responsable: " + getFacade().nombreResposable(responsable), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanIdentidicador == true) {
                    Paragraph titulo2 = new Paragraph("Identificador: " + getFacade().nombreResposable(identificador), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanRecolector == true) {
                    Paragraph titulo2 = new Paragraph("Recolector: " + getFacade().nombreResposable(recolector), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }

                Paragraph fecha = new Paragraph("Fecha de generaciÃ³n: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);
                if(booleanResponsable == true){
                    columnas = 7;
                }else {columnas = 8;}
                PdfPTable ejemplares = new PdfPTable(columnas);
                ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {8, 20, 9, 15, 9, 20, 9, 10};
                try {
                    ejemplares.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                ejemplares.setWidthPercentage(100);
                ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                ejemplares.addCell(new Phrase("CÃ³digo de Entrada", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                if(booleanResponsable == false){
                ejemplares.addCell(new Phrase("Responsable", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                }
                ejemplares.addCell(new Phrase("Correlativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                ejemplares.addCell(new Phrase("InformaciÃ³n TaxonÃ³mica", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                ejemplares.addCell(new Phrase("Calificativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                ejemplares.addCell(new Phrase("DescripciÃ³n", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                ejemplares.addCell(new Phrase("Duplicados", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                ejemplares.addCell(new Phrase("Localidad", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<EjemplarTb> ejemplarListaReporte = new ArrayList<EjemplarTb>();

                if (booleanoReporte == true) {
                    ejemplarListaReporte = getFacade().ejemplarGeneral();
                } else if (booleanFecha == true && tipoReporte.equals("recoleccion")) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralRecoleccion(f1, f2);
                } else if (booleanFecha == true && tipoReporte.equals("identificacion")) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralIdentificacion(f1, f2);
                } else if (booleanCodigo == true) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralEntrada(codigo1, codigo2);
                } else if (booleanResponsable == true) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralResponsable(responsable);
                } else if (booleanIdentidicador == true) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralIdentificador(identificador);
                } else if (booleanRecolector == true) {
                    ejemplarListaReporte = getFacade().ejemplarGeneralRecolector(recolector);
                }

                for (EjemplarTb ejemplar : ejemplarListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(ejemplar.getCCodigoentrada(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(c1);
                    if (booleanResponsable == false){
                    PdfPCell c2 = new PdfPCell(new Phrase(calculaAgenteReporte(ejemplar.getEResponsable()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    ejemplares.addCell(c2);
                    }
                    PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(ejemplar.getECorrelativo()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(ejemplar.getEIdtaxonomia().getCTipo() + ": " + ejemplar.getEIdtaxonomia().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c4.setHorizontalAlignment(Element.ALIGN_LEFT);
                    ejemplares.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(ejemplar.getCCalificativo(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase(ejemplar.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c6.setHorizontalAlignment(Element.ALIGN_LEFT);
                    ejemplares.addCell(c6);

                    PdfPCell c7 = new PdfPCell(new Phrase(String.valueOf(ejemplar.getECantDuplicado()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(c7);

                    PdfPCell c8 = new PdfPCell(new Phrase(ejemplar.getEIdlocalidad().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c8.setHorizontalAlignment(Element.ALIGN_LEFT);
                    ejemplares.addCell(c8);

                }
                document.add(ejemplares);
                document.close();
                //Termina reporte

                hsr.setHeader("Expires", "0");
                hsr.setContentType("application/pdf");
                hsr.setContentLength(pdfOutputStream.size());
                ServletOutputStream responseOutputStream = hsr.getOutputStream();
                responseOutputStream.write(pdfOutputStream.toByteArray());
                responseOutputStream.flush();
                responseOutputStream.close();
                context.responseComplete();
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
