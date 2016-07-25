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
import modelo.DisminuirEjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.DisminuirEjemplarTbFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.EjemplarVivoTb;
import modelo.UsuarioTb;

@Named("disminuirEjemplarTbController")
@ViewScoped
public class DisminuirEjemplarTbController implements Serializable {

    @EJB
    private servicio.DisminuirEjemplarTbFacade ejbFacade;
    @EJB
    private servicio.EjemplarVivoTbFacade ejemplarVivoFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<DisminuirEjemplarTb> items = null, filtro;
    private DisminuirEjemplarTb selected;
    private Date fechaActual = new Date();

    public DisminuirEjemplarTbController() {
    }

    public List<DisminuirEjemplarTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DisminuirEjemplarTb> filtro) {
        this.filtro = filtro;
    }

    public DisminuirEjemplarTb getSelected() {
        return selected;
    }

    public void setSelected(DisminuirEjemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    private DisminuirEjemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public DisminuirEjemplarTb prepareCreate(EjemplarVivoTb ejemplarVivo) {
        selected = new DisminuirEjemplarTb();
        selected.setEIdejemplarVivo(ejemplarVivo);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        int cantidad = selected.getEIdejemplarVivo().getECantidad();
        int cantidadBaja = selected.getECantidad();
        selected.getEIdejemplarVivo().setECantidad(cantidad-cantidadBaja);
        ejemplarVivoFacade.edit(selected.getEIdejemplarVivo());
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Dado de baja ejemplar vivo: '" + selected.getEIdejemplarVivo().getCNombre() + "' la cantidad de: '"+ cantidadBaja +"' unidades en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("DisminuirEjemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DisminuirEjemplarTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DisminuirEjemplarTb getDisminuirEjemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DisminuirEjemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DisminuirEjemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DisminuirEjemplarTb.class)
    public static class DisminuirEjemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DisminuirEjemplarTbController controller = (DisminuirEjemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "disminuirEjemplarTbController");
            return controller.getDisminuirEjemplarTb(getKey(value));
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
            if (object instanceof DisminuirEjemplarTb) {
                DisminuirEjemplarTb o = (DisminuirEjemplarTb) object;
                return getStringKey(o.getEIddisminuirEjemplar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DisminuirEjemplarTb.class.getName()});
                return null;
            }
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte General de Ejemplares Vivos Dados de Baja", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);
                
                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                    UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                    Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                            FontFactory.getFont(FontFactory.TIMES, 10));
                    usuarioSis.setAlignment(Element.ALIGN_CENTER);
                    usuarioSis.setSpacingAfter(10);
                    document.add(usuarioSis);

                PdfPTable pedidos = new PdfPTable(5);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {18, 25, 12, 24, 21};
                try {
                    pedidos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                pedidos.setWidthPercentage(85);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                pedidos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Motivo", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Bajas", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Taxonomia", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Jardín", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<DisminuirEjemplarTb> ejemplarBajaListaReporte = new ArrayList<DisminuirEjemplarTb>();

                ejemplarBajaListaReporte = getFacade().findAll();

                for (DisminuirEjemplarTb ejem : ejemplarBajaListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(ejem.getEIdejemplarVivo().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pedidos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(ejem.getMMotivo(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c2);

                    PdfPCell c4 = new PdfPCell(new Phrase("" + ejem.getECantidad() + "", FontFactory.getFont(FontFactory.TIMES, 11)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(ejem.getEIdejemplarVivo().getEIdtaxonomia().getCTipo() + ": " + ejem.getEIdejemplarVivo().getEIdtaxonomia().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase(ejem.getEIdejemplarVivo().getCJardin(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c6);

                }
                document.add(pedidos);
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
                bitacora.setMDescripcion("Creado reporte general de ejemplares vivos dados de baja en el módulo: Ejemplar");
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
    }

}
