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
import modelo.LocalidadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.LocalidadTbFacade;

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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
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
import modelo.EjemplarParticipaExhibicionTb;
import modelo.EjemplarTb;
import modelo.UsuarioTb;

@Named("localidadTbController")
@ViewScoped
public class LocalidadTbController implements Serializable {

    @EJB
    private servicio.LocalidadTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<LocalidadTb> items = null, filtro;
    private LocalidadTb selected;
    @Inject
    EjemplarTbController controladorEjemplarTb;

    public List<LocalidadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<LocalidadTb> filtro) {
        this.filtro = filtro;
    }

    public LocalidadTbController() {
    }

    public LocalidadTb getSelected() {
        return selected;
    }

    public void setSelected(LocalidadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocalidadTbFacade getFacade() {
        return ejbFacade;
    }

    public LocalidadTb prepareCreate() {
        selected = new LocalidadTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Agregado nueva localidad: '" + selected.getCNombre() + "' en el módulo: Localización");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado localidad: '" + selected.getCNombre() + "' en el módulo: Localización");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LocalidadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LocalidadTb> getItems() {
        if (items == null) {
            items = getFacade().LocalidadGeneral();
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

    public LocalidadTb getLocalidadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LocalidadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LocalidadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LocalidadTb.class)
    public static class LocalidadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocalidadTbController controller = (LocalidadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localidadTbController");
            return controller.getLocalidadTb(getKey(value));
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
            if (object instanceof LocalidadTb) {
                LocalidadTb o = (LocalidadTb) object;
                return getStringKey(o.getEIdlocalidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LocalidadTb.class.getName()});
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

            selected.setDLatitudDecimal(resultado);
        } else {
            double resultado, min, seg, gra;
            gra = grado;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = gra + min + seg;

            selected.setDLatitudDecimal(resultado);
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

            selected.setDLongitudDecimal(resultado);
        } else {
            double resultado, min, seg, gra;
            gra = grado;
            min = (double) minuto;
            min = min / 60;
            seg = segundo / 3600;
            resultado = gra + min + seg;

            selected.setDLongitudDecimal(resultado);
        }

    }

    public String latitudList(LocalidadTb la) {
        String latitud;
        if (la.getELatitudgrados() < 0) {
            latitud = la.getELatitudgrados() + "°" + la.getELatitudminutos() + "'" + la.getDLatitudsegundos() + "''" + " S";
            return latitud;
        } else {
            latitud = la.getELatitudgrados() + "°" + la.getELatitudminutos() + "'" + la.getDLatitudsegundos() + "''" + " N";
            return latitud;
        }
    }

    public String longitudList(LocalidadTb lo) {
        String longitud;
        if (lo.getELongitudgrados() < 0) {
            longitud = lo.getELongitudgrados() + "°" + lo.getELongitudminutos() + "'" + lo.getDLongitudsegundos() + "''" + " W";
            return longitud;
        } else {
            longitud = lo.getELongitudgrados() + "°" + lo.getELongitudminutos() + "'" + lo.getDLongitudsegundos() + "''" + " E";
            return longitud;
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("REPORTE GENERAL DE LOCALIDADES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
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

                List<LocalidadTb> localidadListaReporte = new ArrayList<LocalidadTb>();
                localidadListaReporte = getFacade().LocalidadGeneral();

                int columnas[] = {25, 25, 15, 15, 20};
                PdfPTable Tabla = new PdfPTable(5);
                Tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                Tabla.setWidths(columnas);
                Tabla.setWidthPercentage(100);

                Tabla.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tabla.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tabla.addCell(new Phrase("Latitud", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tabla.addCell(new Phrase("Longitud", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tabla.addCell(new Phrase("Lugar", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                for (LocalidadTb l : localidadListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(l.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    Tabla.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(l.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    Tabla.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(latitudList(l), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Tabla.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(longitudList(l), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Tabla.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(l.getEIdcanton().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c5.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Tabla.addCell(c5);
                }

                document.add(Tabla);

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
                bitacora.setMDescripcion("Creado reporte general de localidades en el módulo: Localización");
                UsuarioTb user = usuarioFacade.BuscarUsuario(nick);
                bitacora.setEIdusuario(user);
                Date fecha1 = new Date();
                bitacora.setTFecha(fecha1);
                bitacoraFacade.create(bitacora);
                //Bitacora fin                
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
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

                Paragraph titulo = new Paragraph("REPORTE DE LOCALIDAD", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
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

                Paragraph espacio = new Paragraph("");
                espacio.setSpacingAfter(15);

                int columnas[] = {25, 75};

                PdfPTable TablaNombre = new PdfPTable(2);
                TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombre.setWidths(columnas);
                TablaNombre.setWidthPercentage(100);
                TablaNombre.setSpacingAfter(5);
                TablaNombre.setSpacingBefore(5);
                TablaNombre.addCell(new Phrase("Nombre: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombre.addCell(new Phrase(new Phrase(selected.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombre);

                PdfPTable Tabladescripcion = new PdfPTable(2);
                Tabladescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                Tabladescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                Tabladescripcion.setWidths(columnas);
                Tabladescripcion.setWidthPercentage(100);
                Tabladescripcion.setSpacingAfter(5);
                Tabladescripcion.setSpacingBefore(5);
                Tabladescripcion.addCell(new Phrase("Descripción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tabladescripcion.addCell(new Phrase(new Phrase(selected.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(Tabladescripcion);

                PdfPTable Tablacanton = new PdfPTable(2);
                Tablacanton.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Tablacanton.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                Tablacanton.setWidths(columnas);
                Tablacanton.setWidthPercentage(100);
                Tablacanton.setSpacingAfter(5);
                Tablacanton.setSpacingBefore(5);
                Tablacanton.addCell(new Phrase("Unidad política-adiministrativa:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tablacanton.addCell(new Phrase(new Phrase(selected.getEIdcanton().getEIdmunicipio().getEIddepto().getEIdpais().getCNombre()
                        + ", " + selected.getEIdcanton().getEIdmunicipio().getEIddepto().getCNombre()
                        + ", " + selected.getEIdcanton().getEIdmunicipio().getCNombre()
                        + ", " + selected.getEIdcanton().getCNombre() + ".", FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(Tablacanton);

                PdfPTable Tablaubicacion = new PdfPTable(2);
                Tablaubicacion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                Tablaubicacion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                Tablaubicacion.setWidths(columnas);
                Tablaubicacion.setWidthPercentage(100);
                Tablaubicacion.setSpacingAfter(5);
                Tablaubicacion.setSpacingBefore(5);
                Tablaubicacion.addCell(new Phrase("Ubicación geográfica: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                Tablaubicacion.addCell(new Phrase(new Phrase(latitudList(selected) + ", " + longitudList(selected) + ".", FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(Tablaubicacion);

                Paragraph tituloejemplares = new Paragraph("EJEMPLARES RECOLECTADOS EN ESTA LOCALIDAD", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloejemplares.setAlignment(Element.ALIGN_CENTER);
                tituloejemplares.setSpacingAfter(5);
                tituloejemplares.setSpacingBefore(5);
                document.add(tituloejemplares);
                
                document.add(espacio);

                List<EjemplarTb> ejemplarListaReporte = new ArrayList<EjemplarTb>();
                ejemplarListaReporte = getFacade().EjemplaresPorLocalidad(selected);

                if (!ejemplarListaReporte.isEmpty()) {
                    int columnasa[] = {10, 15, 10, 10, 15, 25, 15};
                    PdfPTable ejemplares = new PdfPTable(7);
                    ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.setWidthPercentage(100);
                    ejemplares.setWidths(columnasa);

                    ejemplares.addCell(new Phrase("Código de Entrada", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Responsable", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Correlativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                    ejemplares.addCell(new Phrase("Familia", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Información Taxonómica", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Fecha recolección", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                    for (EjemplarTb ejemplar : ejemplarListaReporte) {

                        PdfPCell c1 = new PdfPCell(new Phrase(ejemplar.getCCodigoentrada(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(controladorEjemplarTb.calculaAgenteReporte(ejemplar.getEResponsable()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c2.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ejemplares.addCell(c2);

                        PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(ejemplar.getECorrelativo()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c3);

                        PdfPCell c4 = new PdfPCell(new Phrase(controladorEjemplarTb.calcularFamilia(ejemplar.getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c4);

                        PdfPCell c5 = new PdfPCell(new Phrase(controladorEjemplarTb.calcularTaxonomia(ejemplar.getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c5);

                        PdfPCell c6 = new PdfPCell(new Phrase(ejemplar.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c6);
                        
                        PdfPCell c7 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(ejemplar.getFFechaInicioIdent()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c7);
                    }
                    document.add(ejemplares);
                } else {
                    Paragraph tituloNo = new Paragraph("No se encontraron ejemplares en esta localidad.", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNo.setAlignment(Element.ALIGN_CENTER);
                    tituloNo.setSpacingAfter(5);
                    tituloNo.setSpacingBefore(5);
                    document.add(tituloNo);
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
                bitacora.setMDescripcion("Creado reporte individual de la localidad: "+selected.getCNombre()+", en el módulo: Localización");
                UsuarioTb user = usuarioFacade.BuscarUsuario(nick);
                bitacora.setEIdusuario(user);
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
