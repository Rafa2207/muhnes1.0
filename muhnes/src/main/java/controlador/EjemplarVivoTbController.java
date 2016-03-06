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
import modelo.EjemplarVivoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.EjemplarVivoTbFacade;

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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.DespachoTb;
import modelo.UsuarioTb;

@Named("ejemplarVivoTbController")
@ViewScoped
public class EjemplarVivoTbController implements Serializable {

    @EJB
    private servicio.EjemplarVivoTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<EjemplarVivoTb> items = null, items2 = null, filtro;
    private EjemplarVivoTb selected;

    public EjemplarVivoTbController() {
    }

    public List<EjemplarVivoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<EjemplarVivoTb> filtro) {
        this.filtro = filtro;
    }

    public EjemplarVivoTb getSelected() {
        return selected;
    }

    public void setSelected(EjemplarVivoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EjemplarVivoTbFacade getFacade() {
        return ejbFacade;
    }

    public EjemplarVivoTb prepareCreate() {
        selected = new EjemplarVivoTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbUpdated"));
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado Ejemplar Vivo: '" + selected.getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleEjemplarVivo").getString("EjemplarVivoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarVivoTb> getItems() {
        if (items == null) {
            items = getFacade().buscarEjemplarConExistencia();
        }
        return items;
    }

    public List<EjemplarVivoTb> getItems2() {
        if (items2 == null) {
            items2 = getFacade().buscarEjemplarSinExistencia();
        }
        return items2;
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

    public EjemplarVivoTb getEjemplarVivoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EjemplarVivoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EjemplarVivoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EjemplarVivoTb.class)
    public static class EjemplarVivoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EjemplarVivoTbController controller = (EjemplarVivoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ejemplarVivoTbController");
            return controller.getEjemplarVivoTb(getKey(value));
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
            if (object instanceof EjemplarVivoTb) {
                EjemplarVivoTb o = (EjemplarVivoTb) object;
                return getStringKey(o.getEIdejemplarVivo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EjemplarVivoTb.class.getName()});
                return null;
            }
        }

    }

    public void reporteAll(int n) {
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
                if(n==1){
                Paragraph titulo = new Paragraph("Reporte General de Ejemplares Vivos", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);
                } if(n==2){
                Paragraph titulo = new Paragraph("Reporte General de Ejemplares Vivos sin Existencias", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingBefore(5);
                document.add(titulo);
                }
                
                //fecha de generacion entre los reportes

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);
                
                List<EjemplarVivoTb> ejemplarVivoListaReporte = new ArrayList<EjemplarVivoTb>();
                if (n == 1) {
                    ejemplarVivoListaReporte = getFacade().findAll();
                }
                if (n == 2) {
                    ejemplarVivoListaReporte = getFacade().buscarEjemplarSinExistencia();
                }
                if(!ejemplarVivoListaReporte.isEmpty()){
                PdfPTable pedidos = new PdfPTable(6);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {14, 25, 15, 8, 20, 18};
                try {
                    pedidos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                pedidos.setWidthPercentage(90);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                pedidos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Fecha", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Taxonomia", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Jardín", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                
                for (EjemplarVivoTb ejem : ejemplarVivoListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(ejem.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pedidos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(ejem.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(ejem.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase("" + ejem.getECantidad() + "", FontFactory.getFont(FontFactory.TIMES, 11)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(ejem.getEIdtaxonomia().getCTipo() + ": " + ejem.getEIdtaxonomia().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase(ejem.getCJardin(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c6);

                }
                document.add(pedidos);
                }else{
                    Paragraph tituloNoActividades = new Paragraph("No se encontraron Ejemplares vivos sin existencias", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNoActividades.setAlignment(Element.ALIGN_CENTER);
                    tituloNoActividades.setSpacingAfter(5);
                    tituloNoActividades.setSpacingBefore(5);
                    document.add(tituloNoActividades);
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
                bitacora.setMDescripcion("Creado Reporte general de ejemplares vivos en el módulo: Ejemplares");
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
