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
import controlador.util.ControladorSesion;
import modelo.NotapreliminarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.NotapreliminarTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.ProrrogaProyectoTb;
import modelo.ProyectoTb;
import modelo.UsuarioTb;
import servicio.ProrrogaProyectoTbFacade;
import servicio.UsuarioTbFacade;

@Named("notapreliminarTbController")
@SessionScoped
public class NotapreliminarTbController implements Serializable {

    @EJB
    private servicio.NotapreliminarTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.ProrrogaProyectoTbFacade FacadeProrroga;
    private List<NotapreliminarTb> items = null, filtro, NotasAll = null, Notas = null;
    private NotapreliminarTb selected;
    private UsuarioTb user = null;
    @Inject
    ProyectoTbController controladorProyecto;

    public List<NotapreliminarTb> getNotas(ProyectoTb proyecto) {
        Notas = getFacade().buscarPorProyecto(proyecto);
        return Notas;
    }

    public void setNotas(List<NotapreliminarTb> Notas) {
        this.Notas = Notas;
    }

    public List<NotapreliminarTb> getNotasAll() {
        NotasAll = getFacade().buscarNotasAll();
        return NotasAll;
    }

    public void setNotasAll(List<NotapreliminarTb> NotasAll) {
        this.NotasAll = NotasAll;
    }

    public UsuarioTb getUser() {
        return user;
    }

    public void setUser(UsuarioTb user) {
        this.user = user;
    }

    public UsuarioTbFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioTbFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public List<NotapreliminarTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<NotapreliminarTb> filtro) {
        this.filtro = filtro;
    }

    public NotapreliminarTbController() {
    }

    public NotapreliminarTb getSelected() {
        return selected;
    }

    public void setSelected(NotapreliminarTb selected) {
        this.selected = selected;
    }

    public ProrrogaProyectoTbFacade getFacadeProrroga() {
        return FacadeProrroga;
    }

    public void setFacadeProrroga(ProrrogaProyectoTbFacade FacadeProrroga) {
        this.FacadeProrroga = FacadeProrroga;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NotapreliminarTbFacade getFacade() {
        return ejbFacade;
    }

    public NotapreliminarTb prepareCreate(String usuario, ProyectoTb proyecto) {
        selected = new NotapreliminarTb();
        initializeEmbeddableKey();
        selected.setEIdproyecto(proyecto);
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleNotas").getString("NotapreliminarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NotapreliminarTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotas").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotas").getString("PersistenceErrorOccured"));
            }
        }
    }

    public NotapreliminarTb getNotapreliminarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<NotapreliminarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NotapreliminarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = NotapreliminarTb.class)
    public static class NotapreliminarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NotapreliminarTbController controller = (NotapreliminarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "notapreliminarTbController");
            return controller.getNotapreliminarTb(getKey(value));
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
            if (object instanceof NotapreliminarTb) {
                NotapreliminarTb o = (NotapreliminarTb) object;
                return getStringKey(o.getEIdnotapreliminar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NotapreliminarTb.class.getName()});
                return null;
            }
        }

    }

    public String NombreUsuario(int idUser) {
        UsuarioTb user = null;
        user = getFacade().UsuarioParaVista(idUser);
        return user.getCNombre() + " " + user.getCApellido();
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

                Paragraph titulo = new Paragraph("Reporte de notas de proyecto", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(15);
                document.add(fecha);

                int columnas[] = {25, 75};

                PdfPTable TablaNombre = new PdfPTable(2);
                TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombre.setWidths(columnas);
                TablaNombre.setWidthPercentage(100);
                TablaNombre.setSpacingAfter(5);
                TablaNombre.setSpacingBefore(5);
                TablaNombre.addCell(new Phrase("Proyecto: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombre.addCell(new Phrase(new Phrase(proyecto.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombre);

                PdfPTable TablaDescripcion = new PdfPTable(2);
                TablaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaDescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaDescripcion.setWidths(columnas);
                TablaDescripcion.setWidthPercentage(100);
                TablaDescripcion.setSpacingAfter(5);
                TablaDescripcion.setSpacingBefore(5);
                TablaDescripcion.addCell(new Phrase("Descripción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(proyecto.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(TablaDescripcion);

                proyecto.getProrrogaProyectoTbList().clear();
                proyecto.setProrrogaProyectoTbList(getFacadeProrroga().buscarProrroga(proyecto));
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
                    TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaInicio()) + " - "
                            + new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaFin()), FontFactory.getFont(FontFactory.TIMES, 12))));
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
                        TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaInicio()) + " - "
                                + new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaFin()) + " (Cancelado: "
                                + new SimpleDateFormat("dd MMMM yyyy").format(prorr.getFFechaInicio()) + ") ", FontFactory.getFont(FontFactory.TIMES, 12))));
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
                        TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaInicio()) + " - "
                                + new SimpleDateFormat("dd MMMM yyyy").format(proyecto.getFFechaFin()) + " (con prórroga al "
                                + new SimpleDateFormat("dd MMMM yyyy").format(prorr.getFFechaFin()) + ") ", FontFactory.getFont(FontFactory.TIMES, 12))));
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

                //Titulo de prórrogas
                Paragraph tituloNota = new Paragraph("NOTAS DE PROYECTO", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloNota.setAlignment(Element.ALIGN_CENTER);
                tituloNota.setSpacingAfter(5);
                tituloNota.setSpacingBefore(5);
                document.add(tituloNota);

                if (!proyecto.getNotapreliminarTbList().isEmpty()) {
                    for (NotapreliminarTb nota : proyecto.getNotapreliminarTbList()) {

                        //Espacio
                        Paragraph espacio = new Paragraph("", FontFactory.getFont(FontFactory.TIMES, 12));
                        espacio.setAlignment(Element.ALIGN_CENTER);
                        espacio.setSpacingAfter(5);
                        espacio.setSpacingBefore(5);
                        document.add(espacio);
                        //Nombre
                        PdfPTable TablatituloNota = new PdfPTable(1);
                        TablatituloNota.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        int tamanotitulo[] = {100};
                        TablatituloNota.setWidths(tamanotitulo);
                        TablatituloNota.setWidthPercentage(80);

                        PdfPCell c0 = new PdfPCell(new Phrase(nota.getCNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablatituloNota.addCell(c0);
                        document.add(TablatituloNota);

                        int tamanocuerpo[] = {25, 75};
                        //Descripcion
                        PdfPTable NotaDescripcion = new PdfPTable(2);
                        NotaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        NotaDescripcion.setWidths(tamanocuerpo);
                        NotaDescripcion.setWidthPercentage(80);

                        PdfPCell c1 = new PdfPCell(new Phrase("Descripción:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        c1.setBorder(Rectangle.LEFT);
                        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaDescripcion.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(nota.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setBorder(Rectangle.RIGHT);
                        c2.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaDescripcion.addCell(c2);
                        document.add(NotaDescripcion);

                        //Fecha
                        PdfPTable NotaFecha = new PdfPTable(2);
                        NotaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        NotaFecha.setWidths(tamanocuerpo);
                        NotaFecha.setWidthPercentage(80);

                        PdfPCell c11 = new PdfPCell(new Phrase("Fecha:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        c11.setBorder(Rectangle.LEFT);
                        c11.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaFecha.addCell(c11);

                        PdfPCell c22 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(nota.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c22.setBorder(Rectangle.RIGHT);
                        c22.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaFecha.addCell(c22);
                        document.add(NotaFecha);

                        //Acompañantes
                        PdfPTable NotaAcompañantes = new PdfPTable(2);
                        NotaAcompañantes.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        NotaAcompañantes.setWidths(tamanocuerpo);
                        NotaAcompañantes.setWidthPercentage(80);

                        PdfPCell c111 = new PdfPCell(new Phrase("Acompañantes:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        c111.setBorder(Rectangle.LEFT);
                        c111.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaAcompañantes.addCell(c111);

                        PdfPCell c222 = new PdfPCell(new Phrase(nota.getMAcompanantes(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c222.setBorder(Rectangle.RIGHT);
                        c222.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaAcompañantes.addCell(c222);
                        document.add(NotaAcompañantes);

                        //Ubicación
                        PdfPTable NotaUbicacion = new PdfPTable(2);
                        NotaUbicacion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        NotaUbicacion.setWidths(tamanocuerpo);
                        NotaUbicacion.setWidthPercentage(80);

                        PdfPCell c1111 = new PdfPCell(new Phrase("Ubicación:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        c1111.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                        c1111.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaUbicacion.addCell(c1111);

                        PdfPCell c2222 = new PdfPCell(new Phrase(nota.getCUbicacion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2222.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                        c2222.setHorizontalAlignment(Element.ALIGN_LEFT);
                        NotaUbicacion.addCell(c2222);
                        document.add(NotaUbicacion);
                    }

                } else {
                    Paragraph tituloNotas = new Paragraph("No se encontraron notas en este proyecto", FontFactory.getFont(FontFactory.TIMES, 12));
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
