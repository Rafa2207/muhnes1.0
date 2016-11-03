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
import modelo.ProrrogaProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.ProrrogaProyectoTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.NotapreliminarTb;
import modelo.ProyectoTb;
import modelo.UsuarioTb;

@Named("prorrogaProyectoTbController")
@ViewScoped
public class ProrrogaProyectoTbController implements Serializable {

    @EJB
    private servicio.ProrrogaProyectoTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<ProrrogaProyectoTb> items = null, filtro;
    private ProrrogaProyectoTb selected;
    private ProyectoTb proyectos;
    private Date fechaMinimaTemporal, fechaMinimaTemporalEdit, fechaMaximaTemporalEdit;
    private Date FechaActual = new Date();
    @Inject
    ProyectoTbController controladorProyecto;

    public Date getFechaActual() {
        return FechaActual;
    }

    public void setFechaActual(Date FechaActual) {
        this.FechaActual = FechaActual;
    }

    public Date getFechaMaximaTemporalEdit() {
        return fechaMaximaTemporalEdit;
    }

    public void setFechaMaximaTemporalEdit(Date fechaMaximaTemporalEdit) {
        this.fechaMaximaTemporalEdit = fechaMaximaTemporalEdit;
    }

    public Date getFechaMinimaTemporalEdit() {
        return fechaMinimaTemporalEdit;
    }

    public void setFechaMinimaTemporalEdit(Date fechaMinimaTemporalEdit) {
        this.fechaMinimaTemporalEdit = fechaMinimaTemporalEdit;
    }

    public Date getFechaMinimaTemporal() {
        return fechaMinimaTemporal;
    }

    public void setFechaMinimaTemporal(Date fechaMinimaTemporal) {
        this.fechaMinimaTemporal = fechaMinimaTemporal;
    }

    public List<ProrrogaProyectoTb> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<ProrrogaProyectoTb> items) {
        this.items = items;
    }

    public List<ProrrogaProyectoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ProrrogaProyectoTb> filtro) {
        this.filtro = filtro;
    }

    public ProrrogaProyectoTbController() {
    }

    public ProrrogaProyectoTb getSelected() {
        return selected;
    }

    public void setSelected(ProrrogaProyectoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProrrogaProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public ProrrogaProyectoTb prepareCreate(List<ProrrogaProyectoTb> p, ProyectoTb proyecto) {

        selected = new ProrrogaProyectoTb();
        proyectos = proyecto;
        selected.setEIdproyecto(proyectos);
        calcularNumeroProrroga(p, proyecto);
        calcularFechaMinima(p, proyecto);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada nueva prórroga: '" + selected.getMJustificacion() + "' del proyecto: '" + selected.getEIdproyecto().getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbUpdated"));

        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificada prórroga: '" + selected.getMJustificacion() + "' del proyecto: '" + selected.getEIdproyecto().getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleProrrogaProy").getString("ProrrogaProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }

    }

    public List<ProrrogaProyectoTb> getItems(ProyectoTb proyecto) {
        items = getFacade().buscarProrroga(proyecto);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleProrrogaProy").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProrrogaProyectoTb getProrrogaProyectoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProrrogaProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProrrogaProyectoTb.class)
    public static class ProrrogaProyectoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProrrogaProyectoTbController controller = (ProrrogaProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prorrogaProyectoTbController");
            return controller.getProrrogaProyectoTb(getKey(value));
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
            if (object instanceof ProrrogaProyectoTb) {
                ProrrogaProyectoTb o = (ProrrogaProyectoTb) object;
                return getStringKey(o.getEIdprorroga());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProrrogaProyectoTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit(List<ProrrogaProyectoTb> p, ProyectoTb proyecto, int numero) {
        calcularFechaMinimaEdit(p, proyecto, numero);

    }

    public void calcularNumeroProrroga(List<ProrrogaProyectoTb> p, ProyectoTb proy) {
        int i = 1;
        selected.setENumprorroga(i);
        for (ProrrogaProyectoTb proProy : p) {

            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                selected.setENumprorroga(i);
            }
        }

    }

    public void calcularFechaMinima(List<ProrrogaProyectoTb> p, ProyectoTb proy) {
        int i = 1, numeroProrroga = selected.getENumprorroga();
        fechaMinimaTemporal = proy.getFFechaFin();
        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                if (numeroProrroga == i) {
                    fechaMinimaTemporal = proProy.getFFechaFin();
                }
            }
        }
        //calcular siguiente dia
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaMinimaTemporal);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date fecha = calendar.getTime();
        //fin calculo de siguiente dia
        selected.setFFechaInicio(fecha);

    }

    public Date fechaMinima(Date fecha) {
        Date fechafin;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fechafin = calendar.getTime();
        return fechafin;
    }

    public void calcularFechaMinimaEdit(List<ProrrogaProyectoTb> p, ProyectoTb proy, int numeroProrroga) {
        int i = 1;
        fechaMinimaTemporalEdit = proy.getFFechaFin();

        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
                if (numeroProrroga == i) {
                    fechaMinimaTemporalEdit = proProy.getFFechaFin();
                }

            }
        }

    }

    public void eliminarProrroga(List<ProrrogaProyectoTb> p, ProyectoTb proy, int pro, Date fechaPro) {
        int i = 0;

        for (ProrrogaProyectoTb proProy : p) {
            if (proProy.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                i++;
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        //&& fechaPro.after(FechaActual)
        if (pro >= i && fechaPro.after(FechaActual)) {
            String nombreprorroga = selected.getMJustificacion();
            String nombreproyecto = selected.getEIdproyecto().getMNombre();
            getFacade().remove(selected);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Prórroga eliminada correctamente.", "INFO"));
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Eliminada prórroga: '" + nombreprorroga + "' del proyecto: '" + nombreproyecto + "' en el módulo: Proyectos");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
        } else {

            if (fechaPro.after(FechaActual)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, elimine primero la última prórroga ", "ERROR"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prórroga está en ejecución o ya fue finalizada", "ERROR"));

            }
        }

    }

    public void reporteAll(ProyectoTb proyecto) {
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
                //Indico tamaÃ±o del logo
                logo.scaleToFit(80, 80);
                //aÃ±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃ±ado celda a la tabla
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

                Paragraph titulo = new Paragraph("REPORTE DE PRÓRROGAS DE PROYECTOS", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd /MM/ yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                document.add(fecha);
                
                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                
                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() +" "+usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(10);
                document.add(usuarioSis);

                int columnas[] = {25, 75};

                PdfPTable TablaNombre = new PdfPTable(2);
                TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombre.setWidths(columnas);
                TablaNombre.setWidthPercentage(100);
                TablaNombre.setSpacingAfter(5);
                TablaNombre.setSpacingBefore(5);
                TablaNombre.addCell(new Phrase("Proyecto: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombre.addCell(new Phrase(new Phrase(proyecto.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombre);

                PdfPTable TablaDescripcion = new PdfPTable(2);
                TablaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaDescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaDescripcion.setWidths(columnas);
                TablaDescripcion.setWidthPercentage(100);
                TablaDescripcion.setSpacingAfter(5);
                TablaDescripcion.setSpacingBefore(5);
                TablaDescripcion.addCell(new Phrase("Descripción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(proyecto.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(TablaDescripcion);

                proyecto.getProrrogaProyectoTbList().clear();
                proyecto.setProrrogaProyectoTbList(getFacade().buscarProrroga(proyecto));
                int numeroProrroga = 1;
                ProrrogaProyectoTb prorr = new ProrrogaProyectoTb();
                for (ProrrogaProyectoTb p : proyecto.getProrrogaProyectoTbList()) {
                    if (p.getENumprorroga() >= numeroProrroga) {
                        prorr = p;
                    }
                }

                if (proyecto.getProrrogaProyectoTbList().isEmpty()) {
                    PdfPTable TablaFecha = new PdfPTable(2);
                    TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    TablaFecha.setWidths(columnas);
                    TablaFecha.setWidthPercentage(100);
                    TablaFecha.setSpacingAfter(5);
                    TablaFecha.setSpacingBefore(5);
                    TablaFecha.addCell(new Phrase("Tiempo de duración: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaInicio()) + " - "
                            + new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaFin()), FontFactory.getFont(FontFactory.TIMES, 12))));
                    document.add(TablaFecha);
                } else {
                    if (proyecto.getEEstado() == 3) {
                        PdfPTable TablaFecha = new PdfPTable(2);
                        TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        TablaFecha.setWidths(columnas);
                        TablaFecha.setWidthPercentage(100);
                        TablaFecha.setSpacingAfter(5);
                        TablaFecha.setSpacingBefore(5);
                        TablaFecha.addCell(new Phrase("Tiempo de duración: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaInicio()) + " - "
                                + new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaFin()) + " (Cancelado: "
                                + new SimpleDateFormat("dd /MM/ yyyy").format(prorr.getFFechaInicio()) + ") ", FontFactory.getFont(FontFactory.TIMES, 12))));
                        document.add(TablaFecha);

                    } else {
                        PdfPTable TablaFecha = new PdfPTable(2);
                        TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        TablaFecha.setWidths(columnas);
                        TablaFecha.setWidthPercentage(100);
                        TablaFecha.setSpacingAfter(5);
                        TablaFecha.setSpacingBefore(5);
                        TablaFecha.addCell(new Phrase("Tiempo de duración: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaInicio()) + " - "
                                + new SimpleDateFormat("dd /MM/ yyyy").format(proyecto.getFFechaFin()) + " (con prórroga al "
                                + new SimpleDateFormat("dd /MM/ yyyy").format(prorr.getFFechaFin()) + ") ", FontFactory.getFont(FontFactory.TIMES, 12))));
                        document.add(TablaFecha);
                    }
                }

                PdfPTable TablaResponsable = new PdfPTable(2);
                TablaResponsable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaResponsable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaResponsable.setWidths(columnas);
                TablaResponsable.setWidthPercentage(100);
                TablaResponsable.setSpacingAfter(5);
                TablaResponsable.setSpacingBefore(5);
                TablaResponsable.addCell(new Phrase("Responsable: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaResponsable.addCell(new Phrase(new Phrase(controladorProyecto.calculaAgenteReporte(proyecto.getEResponsable()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaResponsable);

                
                
                //espacio
                Paragraph espacio = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                espacio.setAlignment(Element.ALIGN_CENTER);
                espacio.setSpacingBefore(5);
                espacio.setSpacingAfter(5);
                document.add(espacio);

                proyecto.getProrrogaProyectoTbList().clear();
                proyecto.setProrrogaProyectoTbList(getFacade().buscarProrroga(proyecto));

                if (!proyecto.getProrrogaProyectoTbList().isEmpty()) {

                    int tamano[] = {10, 50, 40};
                    PdfPTable TablaProrrogaTitulo = new PdfPTable(3);
                    TablaProrrogaTitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaProrrogaTitulo.setWidths(tamano);
                    TablaProrrogaTitulo.setWidthPercentage(100);

                    PdfPCell c0t = new PdfPCell(new Phrase("Número", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    c0t.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaProrrogaTitulo.addCell(c0t);

                    PdfPCell c1t = new PdfPCell(new Phrase("Motivo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    c1t.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaProrrogaTitulo.addCell(c1t);

                    PdfPCell c2t = new PdfPCell(new Phrase("Fecha de ejecución", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    c2t.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaProrrogaTitulo.addCell(c2t);

                    document.add(TablaProrrogaTitulo);

                    for (ProrrogaProyectoTb prorroga : proyecto.getProrrogaProyectoTbList()) {

                        if (prorroga.getFFechaFin() != null) {

                            PdfPTable TablaProrroga = new PdfPTable(3);
                            TablaProrroga.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            TablaProrroga.setWidths(tamano);
                            TablaProrroga.setWidthPercentage(100);

                            PdfPCell c0 = new PdfPCell(new Phrase(String.valueOf(prorroga.getENumprorroga()), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c0.setHorizontalAlignment(Element.ALIGN_CENTER);
                            TablaProrroga.addCell(c0);

                            PdfPCell c1 = new PdfPCell(new Phrase(prorroga.getMJustificacion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            TablaProrroga.addCell(c1);

                            PdfPCell c2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(prorroga.getFFechaInicio()) + " - "
                                    + new SimpleDateFormat("dd /MM/ yyyy").format(prorroga.getFFechaFin()), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            TablaProrroga.addCell(c2);

                            document.add(TablaProrroga);
                        }
                    }

                } else {
                    Paragraph tituloNotas = new Paragraph("No se encontraron prórrogas en este proyecto", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNotas.setAlignment(Element.ALIGN_CENTER);
                    tituloNotas.setSpacingAfter(5);
                    tituloNotas.setSpacingBefore(5);
                    document.add(tituloNotas);
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
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
