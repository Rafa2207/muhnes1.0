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
import modelo.ExhibicionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.ExhibicionTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
    private Date fechaActual = new Date(), f1, f2;
    private boolean booleanoReporte;
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

    public boolean isBooleanoReporte() {
        return booleanoReporte;
    }

    public void setBooleanoReporte(boolean booleanoReporte) {
        this.booleanoReporte = booleanoReporte;
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

    public void prepareReporte() {
        booleanoReporte = true;
        f1 = null;
        f2 = null;
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
                //Indico tamaÃƒÂ±o del logo
                logo.scaleToFit(80, 80);
                //aÃƒÂ±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃƒÂ±ado celda a la tabla
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

                Paragraph titulo = new Paragraph("Reporte General de exhibiciones", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);

                if (booleanoReporte == false) {
                    Paragraph titulo2 = new Paragraph(new SimpleDateFormat("dd MMMM yyyy").format(f1) + " - " + new SimpleDateFormat("dd MMMM yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                PdfPTable proyectos = new PdfPTable(6);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {30, 15, 15, 10, 15, 15};
                try {
                    proyectos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                proyectos.setWidthPercentage(100);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                proyectos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Responsable", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Destino", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Fecha préstamo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Fecha reingreso", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<ExhibicionTb> exhibicionListaReporte = new ArrayList<ExhibicionTb>();

                if (booleanoReporte == true) {
                    exhibicionListaReporte = getFacade().ExhibicionGeneral();
                } else {
                    exhibicionListaReporte = getFacade().ExhibicionesReporteAll(f1, f2);
                }

                for (ExhibicionTb proy : exhibicionListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(proy.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    proyectos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(calculaAgente(proy.getEIdResponsable()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(proy.getMDestino(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(EstadoList(proy.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proy.getFFechaPrestamo()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proy.getFFechaRecibido()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c6);

                }
                document.add(proyectos);
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
    
    public void reportePrestamo() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

                // Inicia reporte
                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                TableHeaderVertical event = new TableHeaderVertical();
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
                //Indico tamaÃƒÂ±o del logo
                logo.scaleToFit(80, 80);
                //aÃƒÂ±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃƒÂ±ado celda a la tabla
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

                Paragraph titulo = new Paragraph("Salida de ejemplares", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);
                
                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);
                
                Paragraph espacio=new Paragraph("");
                espacio.setSpacingAfter(15);
                document.add(espacio);

                
                PdfPTable descripcionTabla = new PdfPTable(1);
                descripcionTabla.setWidthPercentage(80);
                descripcionTabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                descripcionTabla.getRowspanHeight(2,4);
                espacio.add(new Phrase("holaaaa"));
                espacio.add(new Phrase("holaaaa   kjdiohe   "));

                descripcionTabla.addCell(new Paragraph("Fecha de Salida: "
                        + new SimpleDateFormat("dd MMMM yyyy").format(selected.getFFechaPrestamo())
                        +". Hora:"
                        + new SimpleDateFormat("hh:mm a").format(selected.getHHoraPrestamo())
                        + ". Solicitante de salida: "
                        + selected.getMSolicitante()
                        + " probando por ahi", FontFactory.getFont(FontFactory.TIMES, 13)));
                
                document.add(descripcionTabla);
                
                

                
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
    
    public void reporteReingreso() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

                // Inicia reporte
                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                TableHeaderVertical event = new TableHeaderVertical();
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
                //Indico tamaÃƒÂ±o del logo
                logo.scaleToFit(80, 80);
                //aÃƒÂ±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃƒÂ±ado celda a la tabla
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

                Paragraph titulo = new Paragraph("Reingreso de ejemplares", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);


                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                
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
