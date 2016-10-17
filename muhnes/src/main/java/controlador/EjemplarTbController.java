package controlador;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modelo.EjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.UtilPath;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.AgenteIdentificaEjemplarTbPK;
import modelo.AgenteTb;
import modelo.BitacoraTb;
import modelo.EjemplarDonacionTb;
import modelo.EjemplarNombrecomunTb;
import modelo.InstitucionTb;
import modelo.NombrecomunTb;
import modelo.ProyectoTb;
import modelo.TaxonomiaTb;
import modelo.UsuarioTb;
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
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
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
    private boolean familia, booleanoInstitucion, booleanoReporte, booleanFecha, booleanEtiqueta, booleanCodigo, booleanResponsable, booleanIdentidicador, booleanRecolector;
    private InstitucionTb ins;
    private Date f1, f2;
    @Inject
    LocalidadTbController localidadControl;

    public EjemplarTbController() {
    }

    public boolean isBooleanoInstitucion() {
        return booleanoInstitucion;
    }

    public void setBooleanoInstitucion(boolean booleanoInstitucion) {
        this.booleanoInstitucion = booleanoInstitucion;
    }

    public boolean isBooleanEtiqueta() {
        return booleanEtiqueta;
    }

    public void setBooleanEtiqueta(boolean booleanEtiqueta) {
        this.booleanEtiqueta = booleanEtiqueta;
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
        booleanoInstitucion = false;
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
        if (selected.getEIdinstitucion() != null) {
            booleanoInstitucion = true;
        } else {
            booleanoInstitucion = false;
        }
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
                selected.setEEstado(2); //ejemplar que se recibiÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Â ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â³ donado
            } else {
                selected.setEEstado(1); //ejemplar que se recolectÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Â ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â³
            }
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Creado Ejemplar: '" + selected.getCCodigoentrada() + "' en el módulo: Ejemplar");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado Ejemplar: '" + selected.getCCodigoentrada() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbUpdated"));
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado Ejemplar: '" + selected.getCCodigoentrada() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarTb> getItems() {
        if (items == null) {
            items = getFacade().EjemplarOrdenAsc();
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

    public void comprobarCorrelativo(Integer a, Integer c) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (EjemplarTb e : getFacade().Correlativo(a)) {
            if (e.getECorrelativo() == c) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El correlativo ya existe en los ejemplares.", "advertencia"));
                selected.setECorrelativo(null);
            }
        }
    }

    public void comprobarCodigo(String c) {
        FacesContext context = FacesContext.getCurrentInstance();
        int codigo = Integer.parseInt(c.trim().substring(0, 2));
        int correlativo = Integer.parseInt(c.trim().substring(3));
        if (codigo < 10 || codigo > 17) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El codigo de entrada esta fuera del rango.", "advertencia"));
            selected.setCCodigoentrada("");
        } else if (correlativo > 8000) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El codigo es mayor al limite.", "advertencia"));
            selected.setCCodigoentrada("");
        } else {
            for (EjemplarTb e : getFacade().findAll()) {
                if (e.getCCodigoentrada().equals(c)) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El correlativo ya existe en los ejemplares.", "advertencia"));
                    selected.setCCodigoentrada("");
                }
            }
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

    public String calcularTaxonomia(TaxonomiaTb tax) {
        if (tax.getERango() == 1) {
            return "-";
        }
        if (tax.getERango() == 2) {
            String genero = tax.getCNombre();
            return genero;
        }
        if (tax.getERango() == 3) {
            String genero = tax.getEIdniveltaxonomia().getCNombre();
            String especie = tax.getCNombre();
            return genero + " " + especie.toLowerCase();
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Subespecie")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String subespecie = tax.getCNombre();
            return genero + " " + especie.toLowerCase() + " subsp. " + subespecie.toLowerCase();
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Variedad")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String variedad = tax.getCNombre();
            return genero + " " + especie.toLowerCase() + " var. " + variedad.toLowerCase();
        }
        return "";
    }

    public String calcularTaxonomia(TaxonomiaTb tax, int n) {
        if (tax.getERango() == 1) {
            String familia = tax.getCNombre();
            return familia;
        }
        if (tax.getERango() == 2) {
            String genero = tax.getCNombre();
            return genero;
        }
        if (tax.getERango() == 3) {
            String genero = tax.getEIdniveltaxonomia().getCNombre();
            String especie = tax.getCNombre();
            return genero + " " + especie.toLowerCase();
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Subespecie")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String subespecie = tax.getCNombre();
            return genero + " " + especie.toLowerCase() + " subsp. " + subespecie.toLowerCase();
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Variedad")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String variedad = tax.getCNombre();
            return genero + " " + especie.toLowerCase() + " var. " + variedad.toLowerCase();
        }
        return "";
    }

    public String calcularFamilia(TaxonomiaTb tax) {
        if (tax.getERango() == 1) {
            String fam = tax.getCNombre();
            return fam;
        }
        if (tax.getERango() == 2) {
            String fam = tax.getEIdniveltaxonomia().getCNombre();
            return fam;
        }
        if (tax.getERango() == 3) {
            String fam = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            return fam;
        }
        if (tax.getERango() == 4) {
            String fam = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            return fam;
        }
        return "";
    }

    public void prepareReporte() {
        booleanFecha = false;
        booleanCodigo = false;
        booleanoReporte = true;
        booleanRecolector = false;
        booleanIdentidicador = false;
        booleanResponsable = false;
        booleanEtiqueta = false;
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
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("recoleccion") || tipoReporte.equals("identificacion")) {
            booleanFecha = true;
            booleanCodigo = false;
            booleanoReporte = false;
            booleanResponsable = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("entrada")) {
            booleanCodigo = true;
            booleanFecha = false;
            booleanoReporte = false;
            booleanResponsable = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("responsable")) {
            booleanResponsable = true;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanRecolector = false;
            booleanIdentidicador = false;
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("identificador")) {
            booleanResponsable = false;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanRecolector = false;
            booleanIdentidicador = true;
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("recolector")) {
            booleanResponsable = false;
            booleanCodigo = false;
            booleanFecha = false;
            booleanoReporte = false;
            booleanIdentidicador = false;
            booleanRecolector = true;
            booleanEtiqueta = false;
        } else if (tipoReporte.equals("etiqueta")) {
            booleanResponsable = false;
            booleanCodigo = true;
            booleanFecha = false;
            booleanoReporte = false;
            booleanIdentidicador = false;
            booleanRecolector = false;
            booleanEtiqueta = true;
        }
    }

    public void reporteAll() {
        if (booleanEtiqueta == false) {
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
                    //Indico tamaÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â±o del logo
                    logo.scaleToFit(80, 80);
                    //aÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â±ado el primer logo a la celda
                    cell1.addElement(logo);
                    //Celda sin borde borde
                    cell1.setBorder(Rectangle.NO_BORDER);
                    //aÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â±ado celda a la tabla
                    encabezado.addCell(cell1);
                    //celdas se alineen al centro
                    encabezado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    encabezado.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                    //Siguientes celdas no tengan borde
                    encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    //nueva celda con los datos del MUHNES
                    encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                    encabezado.addCell("");
                    document.add(encabezado);

                    Paragraph titulo = new Paragraph("Reporte General de Ejemplares", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo.setAlignment(Element.ALIGN_CENTER);

                    titulo.setSpacingBefore(5);
                    document.add(titulo);
                    //fecha de generacion entre los reportes
                    if (booleanFecha == true && tipoReporte.equals("recoleccion")) {
                        Paragraph titulo2 = new Paragraph("Fecha de Recolección: " + new SimpleDateFormat("dd/MM/yyyy").format(f1) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    } else if (booleanFecha == true && tipoReporte.equals("identificacion")) {
                        Paragraph titulo2 = new Paragraph("Fecha de Identificación: " + new SimpleDateFormat("dd/MM/yyyy").format(f1) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    } else if (booleanCodigo == true) {
                        Paragraph titulo2 = new Paragraph("Código de Entrada: Desde " + getCodigo1() + " Hasta " + getCodigo2(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    } else if (booleanResponsable == true) {
                        Paragraph titulo2 = new Paragraph("Responsable: " + getFacade().nombreResposable(responsable), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    } else if (booleanIdentidicador == true) {
                        Paragraph titulo2 = new Paragraph("Identificador: " + getFacade().nombreResposable(identificador), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    } else if (booleanRecolector == true) {
                        Paragraph titulo2 = new Paragraph("Recolector: " + getFacade().nombreResposable(recolector), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                        titulo2.setAlignment(Element.ALIGN_CENTER);
                        titulo2.setSpacingAfter(5);
                        titulo2.setSpacingBefore(2);
                        document.add(titulo2);
                    }

                    Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                            FontFactory.getFont(FontFactory.TIMES, 10));
                    fecha.setAlignment(Element.ALIGN_CENTER);
                    //fecha.setSpacingAfter(10);
                    document.add(fecha);

                    String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                    UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                    Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                            FontFactory.getFont(FontFactory.TIMES, 10));
                    usuarioSis.setAlignment(Element.ALIGN_CENTER);
                    usuarioSis.setSpacingAfter(10);
                    document.add(usuarioSis);

                    if (booleanResponsable == true) {
                        columnas = 6;
                    } else {
                        columnas = 7;
                    }
                    PdfPTable ejemplares = new PdfPTable(columnas);
                    ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                    int headerwidths[] = {8, 18, 9, 10, 24, 17, 14};
                    try {
                        ejemplares.setWidths(headerwidths);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    ejemplares.setWidthPercentage(100);
                    ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(new Phrase("Código de Entrada", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    if (booleanResponsable == false) {
                        ejemplares.addCell(new Phrase("Responsable", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    }
                    ejemplares.addCell(new Phrase("Correlativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Familia", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Informacón Taxonómica", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    //ejemplares.addCell(new Phrase("Calificativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    //ejemplares.addCell(new Phrase("Duplicados", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
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
                        if (booleanResponsable == false) {
                            PdfPCell c2 = new PdfPCell(new Phrase(calculaAgenteReporte(ejemplar.getEResponsable()), FontFactory.getFont(FontFactory.TIMES, 11)));
                            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
                            ejemplares.addCell(c2);
                        }
                        PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(ejemplar.getECorrelativo()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c3);

                        PdfPCell c4 = new PdfPCell(new Phrase(calcularFamilia(ejemplar.getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c4.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ejemplares.addCell(c4);

                        PdfPCell c5 = new PdfPCell(new Phrase(calcularTaxonomia(ejemplar.getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c5.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ejemplares.addCell(c5);

                        PdfPCell c6 = new PdfPCell(new Phrase(ejemplar.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c6.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ejemplares.addCell(c6);

                        PdfPCell c7 = new PdfPCell(new Phrase(ejemplar.getEIdlocalidad().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c7.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ejemplares.addCell(c7);

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
                    //Bitacora inicio
                    BitacoraTb bitacora = new BitacoraTb();
                    bitacora.setMDescripcion("Creado reporte general de ejemplares en el módulo: Ejemplar");
                    //String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                    //UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                    bitacora.setEIdusuario(usuario);
                    Date fecha1 = new Date();
                    bitacora.setTFecha(fecha1);
                    bitacoraFacade.create(bitacora);
                    //Bitacora fin
                }
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        } /**
         * *******************************Reporte de
         * etiquetas******************************
         */
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                Object response = context.getExternalContext().getResponse();
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse hsr = (HttpServletResponse) response;
                    hsr.setContentType("application/pdf");
                    ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

                    // Inicia reporte
                    Document document = new Document(PageSize.LEGAL);
                    PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                    document.setMargins(57, 57, 15, 15);
                    document.open();
                    //inicio de etiquetas
                    List<EjemplarTb> ejemplarListaEtiqueta = new ArrayList<EjemplarTb>();
                    ejemplarListaEtiqueta = getFacade().ejemplarGeneralEntrada(codigo1, codigo2);
                    if (!ejemplarListaEtiqueta.isEmpty()) {
                        int pag = 0;
                        int resto;
                        for (EjemplarTb ej : ejemplarListaEtiqueta) {
                            //////////
                            PdfPTable etiqueta = new PdfPTable(1);
                            etiqueta.setWidthPercentage(80);
                            etiqueta.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                            //PdfPTable Ejemplar = new PdfPTable(1);
                            ej.getAgenteIdentificaEjemplarTbIDList().clear();
                            ej.getAgenteIdentificaEjemplarTbList().clear();
                            ej.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ej.getEIdejemplar(), "Identificador"));
                            ej.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ej.getEIdejemplar(), "Recolector"));

                            PdfPCell ti = new PdfPCell(new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 1)));
                            ti.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
                            ti.setHorizontalAlignment(Element.ALIGN_CENTER);
                            etiqueta.addCell(ti);
                            PdfPTable cod = new PdfPTable(3);
                            cod.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            cod.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            int headerwidths2[] = {38, 24, 38};
                            try {
                                cod.setWidths(headerwidths2);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            //cod.setTotalWidth(new float[] { 100,10});
                            //CODIGO DE BARRAS
                            //cb sirve para obtener cÃƒÂ³digo de barras
                            cod.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                            PdfContentByte cb = writer.getDirectContent();
                            Barcode128 codigo = new Barcode128();
                            codigo.setBarHeight(22f); // great! but what about width???
                            codigo.setX(0.9f);
                            //codigo.setX(0.1f);
                            //codigo.setN(0.30f);
                            codigo.setSize(10f);
                            //codigo.setTextAlignment(Element.ALIGN_CENTER);
                            codigo.setBaseline(8f);
                            //codigo.setBarHeight(4f);
                            codigo.setCode(ej.getCCodigoentrada());
                            PdfPCell cell0 = new PdfPCell();
                            cell0.addElement(codigo.createImageWithBarcode(cb, null, null));
                            cell0.setBorder(Rectangle.NO_BORDER);
                            //cell0.setMinimumHeight(20f);
                            cod.addCell(cell0);
                            cod.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 5)));
                            etiqueta.addCell(cod);
                            /////////////////////////
                            PdfPCell titulo1 = new PdfPCell(new Paragraph("FLORA DE EL SALVADOR", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                            titulo1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                            titulo1.setHorizontalAlignment(Element.ALIGN_CENTER);
                            //titulo1.setSpacingBefore(5);
                            //document.add(titulo1);
                            etiqueta.addCell(titulo1);
                            ///////////
                            PdfPTable familia1 = new PdfPTable(2);
                            familia1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            familia1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            int headerwidths3[] = {60, 40};
                            try {
                                familia1.setWidths(headerwidths3);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            familia1.setWidthPercentage(100);
                            PdfPCell c1 = new PdfPCell(new Phrase(calcularFamilia(ej.getEIdtaxonomia()).toUpperCase(), FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            c1.setBorder(Rectangle.NO_BORDER);
                            familia1.addCell(c1);

                            PdfPCell c2 = new PdfPCell(new Phrase("Dup. = " + String.valueOf(ej.getECantDuplicado()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
                            c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            c2.setBorder(Rectangle.NO_BORDER);
                            familia1.addCell(c2);
                            etiqueta.addCell(familia1);
                            //document.add(familia1);
                            ////////////
                            PdfPTable taxonomia1 = new PdfPTable(1);
                            taxonomia1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            taxonomia1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            taxonomia1.setWidthPercentage(100);
                            PdfPCell cc1 = new PdfPCell(new Phrase(calcularTaxonomia(ej.getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 10)));
                            cc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cc1.setBorder(Rectangle.NO_BORDER);
                            taxonomia1.addCell(cc1);
                            etiqueta.addCell(taxonomia1);
                            //document.add(taxonomia1);
                            ///////////////
                            String nombres = "", coma = "";
                            if (!ej.getAgenteIdentificaEjemplarTbIDList().isEmpty()) {
                                for (AgenteIdentificaEjemplarTb i : ej.getAgenteIdentificaEjemplarTbIDList()) {
                                    nombres = nombres + coma + i.getAgenteTb().getCIniciales();
                                    coma = ", ";
                                }
                                nombres = nombres + ".";
                            } else {
                                nombres = "";
                            }
                            PdfPTable ident = new PdfPTable(1);
                            ident.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            ident.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            ident.setWidthPercentage(100);
                            PdfPCell ccc1 = new PdfPCell(new Phrase("Det. " + nombres + " " + new SimpleDateFormat("dd/MM/yyyy").format(ej.getFFechaFinIdent()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            ccc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            ccc1.setBorder(Rectangle.NO_BORDER);
                            ident.addCell(ccc1);
                            etiqueta.addCell(ident);
                            //document.add(ident);
                            /////////////////////////////////////////
                            String area = "";
                            if (ej.getEIdlocalidad().getEIdarea() == null) {
                                area = "Sin area protegida, " + ej.getEIdlocalidad().getEIdcanton().getCNombre() + ", " + ej.getEIdlocalidad().getEIdcanton().getEIdmunicipio().getCNombre() + ", " + ej.getEIdlocalidad().getEIdcanton().getEIdmunicipio().getEIddepto().getCNombre() + ".";
                            } else {
                                area = "Area Protegida: " + ej.getEIdlocalidad().getEIdarea().getCNombre() + ", " + ej.getEIdlocalidad().getEIdarea().getEIdmunicipio().getCNombre() + ", " + ej.getEIdlocalidad().getEIdarea().getEIdmunicipio().getEIddepto().getCNombre() + ".";
                            }
                            PdfPTable localidad = new PdfPTable(1);
                            localidad.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            localidad.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            localidad.setWidthPercentage(100);
                            PdfPCell cccc1 = new PdfPCell(new Phrase(ej.getEIdlocalidad().getCNombre() + ", " + ej.getEIdlocalidad().getMDescripcion() + ". " + area, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            cccc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cccc1.setBorder(Rectangle.NO_BORDER);
                            localidad.addCell(cccc1);
                            etiqueta.addCell(localidad);
                            //document.add(localidad);
                            ///////////////////////////////////////
                            PdfPTable coordenadas = new PdfPTable(1);
                            coordenadas.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            coordenadas.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            coordenadas.setWidthPercentage(100);
                            PdfPCell ccor1 = new PdfPCell(new Phrase(localidadControl.latitudList(ej.getEIdlocalidad()) + " " + localidadControl.longitudList(ej.getEIdlocalidad()) + ". " + ej.getEIdlocalidad().getEAltitudMax() + " m.s.n.m.\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            ccor1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            ccor1.setBorder(Rectangle.NO_BORDER);
                            coordenadas.addCell(ccor1);
                            etiqueta.addCell(coordenadas);
                            //document.add(coordenadas);
                            ///////////////////////////////////////
                            PdfPTable descripcion = new PdfPTable(1);
                            descripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            descripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            descripcion.setWidthPercentage(100);
                            PdfPCell desc1 = new PdfPCell(new Phrase(ej.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            desc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            desc1.setBorder(Rectangle.NO_BORDER);
                            coordenadas.addCell(desc1);
                            etiqueta.addCell(descripcion);
                            ///////////////////////////////////////
                            String nombres2 = "", coma2 = "";
                            if (!ej.getEjemplarNombrecomunTbList().isEmpty()) {
                                for (EjemplarNombrecomunTb i : ej.getEjemplarNombrecomunTbList()) {
                                    nombres2 = nombres2 + coma2 + i.getCNombrecomun();
                                    coma2 = ", ";
                                }
                                nombres2 = nombres2 + ".";
                            } else {
                                nombres2 = "";
                            }
                            PdfPTable nombresc = new PdfPTable(1);
                            nombresc.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            nombresc.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            nombresc.setWidthPercentage(100);
                            PdfPCell cnom1 = new PdfPCell(new Phrase(nombres2, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            cnom1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cnom1.setBorder(Rectangle.NO_BORDER);
                            nombresc.addCell(cnom1);
                            etiqueta.addCell(nombresc);
                            //document.add(nombresc);
                            ///////////////////////////////////////
                            String nombres3 = "", coma3 = "";
                            if (!ej.getEjemplarDonacionTbList().isEmpty()) {
                                for (EjemplarDonacionTb i : ej.getEjemplarDonacionTbList()) {
                                    nombres3 = nombres3 + coma3 + institucionFacade.find(i.getEIdinstitucion()).getCAcronimo();
                                    coma3 = ", ";
                                }
                                nombres3 = nombres3 + ".";
                            } else {
                                nombres3 = "";
                            }
                            PdfPTable donacion = new PdfPTable(1);
                            donacion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            donacion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            donacion.setWidthPercentage(100);
                            PdfPCell cdon1 = new PdfPCell(new Phrase(nombres3, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            cdon1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cdon1.setBorder(Rectangle.NO_BORDER);
                            donacion.addCell(cdon1);
                            etiqueta.addCell(donacion);
                            //document.add(donacion);
                            ///////////////////////////////////////
                            PdfPTable respon = new PdfPTable(2);
                            respon.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            respon.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            respon.setWidthPercentage(100);
                            PdfPCell cres1 = new PdfPCell(new Phrase(agenteFacade.agentePorId(ej.getEResponsable()).getCIniciales() + " " + ej.getECorrelativo(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                            cres1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cres1.setBorder(Rectangle.NO_BORDER);
                            respon.addCell(cres1);
                            PdfPCell cres2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(ej.getFFechaInicioIdent()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
                            cres2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cres2.setBorder(Rectangle.NO_BORDER);
                            respon.addCell(cres2);
                            etiqueta.addCell(respon);
                            //document.add(respon);
                            //////////////////////////////////
                            String nombres1 = "", coma1 = "";
                            if (!ej.getAgenteIdentificaEjemplarTbList().isEmpty()) {
                                for (AgenteIdentificaEjemplarTb i : ej.getAgenteIdentificaEjemplarTbList()) {
                                    nombres1 = nombres1 + coma1 + i.getAgenteTb().getCIniciales();
                                    coma1 = ", ";
                                }
                                nombres1 = nombres1 + ".";
                            } else {
                                nombres1 = "";
                            }
                            PdfPTable recol = new PdfPTable(1);
                            recol.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            recol.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            recol.setWidthPercentage(100);
                            PdfPCell crecol1 = new PdfPCell(new Phrase(nombres1, FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
                            crecol1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            crecol1.setBorder(Rectangle.NO_BORDER);
                            recol.addCell(crecol1);
                            etiqueta.addCell(recol);
                            //document.add(recol);
                            ///////////////////////////////////////
                            PdfPTable info = new PdfPTable(1);
                            info.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            info.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            info.setWidthPercentage(100);
                            PdfPCell cinfo1 = new PdfPCell(new Phrase("Herbario del Museo de Historia Natural de El Salvador \n MHES", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                            cinfo1.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cinfo1.setBorder(Rectangle.NO_BORDER);
                            info.addCell(cinfo1);
                            etiqueta.addCell(info);
                            ///////////////////////celda en blanco
                            PdfPCell titulo2 = new PdfPCell(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 5)));
                            titulo2.setBorder(Rectangle.TOP);
                            titulo2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            //titulo1.setSpacingBefore(5);
                            //document.add(titulo1);
                            etiqueta.addCell(titulo2);
                            resto = pag % 3;
                            if (resto == 0) {
                                document.newPage();
                            }
                            pag++;
                            //document.add(info);
                            //document.newPage();

                            document.add(etiqueta);

                        }
                    }
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
                    //Bitacora inicio
                    BitacoraTb bitacora = new BitacoraTb();
                    bitacora.setMDescripcion("Creado reporte de etiquetas de ejemplares en el módulo: Ejemplar");
                    String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                    UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                    bitacora.setEIdusuario(usuario);
                    Date fecha1 = new Date();
                    bitacora.setTFecha(fecha1);
                    bitacoraFacade.create(bitacora);
                    //Bitacora fin
                }
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}
