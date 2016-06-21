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
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.Barcode39;
import modelo.MaterialTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.MaterialTbFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.UsuarioTb;

@Named("materialTbController")
@ViewScoped
public class MaterialTbController implements Serializable {

    @EJB
    private servicio.MaterialTbFacade ejbFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    private List<MaterialTb> items = null, filtrar = null, filtro;
    private static final String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

    public List<MaterialTb> getFiltro() {
        return filtro;
    }

    public List<MaterialTb> getFiltrar() {
        return filtrar;
    }

    public void setFiltrar(List<MaterialTb> filtrar) {
        this.filtrar = filtrar;
    }

    public void setFiltro(List<MaterialTb> filtro) {
        this.filtro = filtro;
    }
    private MaterialTb selected;

    public MaterialTbController() {
    }

    public MaterialTb getSelected() {
        return selected;
    }

    public void setSelected(MaterialTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MaterialTbFacade getFacade() {
        return ejbFacade;
    }

    public MaterialTb prepareCreate() {
        selected = new MaterialTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setDCantidad(0.0);
        /**
         * ************Guardar en bitacora*************
         */
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Se agregó un nuevo material '" + selected.getCNombre() + "' en el modulo: Material");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MaterialTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        /**
         * ************Guardar en bitacora*************
         */
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Se modificó el material '" + selected.getCNombre() + "' en el modulo: Material");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MaterialTbUpdated"));
    }

    public void destroy() {
        /**
         * ************Guardar en bitacora*************
         */
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Se eliminó el material '" + selected.getCNombre() + "' en el modulo: Material");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MaterialTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MaterialTb> getItems() {
        if (items == null) {
            items = getFacade().buscarTodosAZ();
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

    public MaterialTb getMaterialTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<MaterialTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MaterialTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MaterialTb.class)
    public static class MaterialTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialTbController controller = (MaterialTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialTbController");
            return controller.getMaterialTb(getKey(value));
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
            if (object instanceof MaterialTb) {
                MaterialTb o = (MaterialTb) object;
                return getStringKey(o.getEIdmaterial());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MaterialTb.class.getName()});
                return null;
            }
        }

    }

    public String fueraStock(MaterialTb material){
        String color="";
        if(material.getDCantidad()<=material.getDCantidadmin()){
        color = "color: #cc0000; font-weight: bold";
        }
        return color;
    }
    
    public String tipo(Integer tipo) {
        String Tipo = "";
        if (tipo == 1) {
            Tipo = "Consumibles";
        } else {
            Tipo = "Herramientas";
        }
        return Tipo;
    }

    public void generarCodigo() {
        String pre = stripAccents(selected.getCNombre()).trim().toUpperCase().substring(0, 3);
        String correlativo = getFacade().obtenerCorrelativo(pre);
        selected.setMCodigobarras(pre + correlativo);
    }

    public static String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }

    public void reporte() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

                // Inicio de reporte horizontal
                Document document = new Document(PageSize.LETTER.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                TableHeader event = new TableHeader();
                writer.setPageEvent(event);
                document.open();

                //cb sirve para obtener código de barras
                PdfContentByte cb = writer.getDirectContent();

                //Encabezado
                //Ruta del sistema
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                //Referencia al logo
                String logoPath = servletContext.getRealPath("") + File.separator + "resources"
                        + File.separator + "images"
                        + File.separator + "muhnes1.png";
                //Tabla para el encabezado
                PdfPTable encabezado = new PdfPTable(3);
                //Ancho de la tabla
                encabezado.setWidthPercentage(100);
                //Primera celda
                PdfPCell cell1 = new PdfPCell();
                //Instancia al logo
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(80, 80);
                //Agregar logo a la celda
                cell1.addElement(logo);
                //Celda sin borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //Agregar celda a la tabla
                encabezado.addCell(cell1);
                //Celdas alineadas al centro
                encabezado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                encabezado.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                //Celdas sin bordes
                encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                //Celdas con datos de institución
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));
                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte de Materiales", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                document.add(fecha);
                
                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(10);
                document.add(usuarioSis);

                PdfPTable mat = new PdfPTable(6);
                mat.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                mat.getDefaultCell().setBorder(Rectangle.BOTTOM);
                int headerwidths[] = {20, 17, 10, 10, 13, 30};
                try {
                    mat.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                mat.setWidthPercentage(100);
                mat.addCell(new Phrase("Código de Barras", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                mat.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                mat.addCell(new Phrase("Tipo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                mat.addCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                mat.addCell(new Phrase("Cantidad Min", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                mat.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<MaterialTb> lista;
                if (filtrar != null) {
                    lista = filtrar;
                } else {
                    lista = this.items;
                }

                for (MaterialTb l : lista) {
                    Barcode128 cod = new Barcode128();
                    cod.setCode(l.getMCodigobarras());
                    PdfPCell cell0 = new PdfPCell();
                    cell0.addElement(cod.createImageWithBarcode(cb, null, null));
                    cell0.setBorder(Rectangle.BOTTOM);
                    mat.addCell(cell0);

                    PdfPCell cell = new PdfPCell(new Phrase("  "+l.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(Rectangle.BOTTOM);
                    mat.addCell(cell);

                    if (l.getETipo() == 1) {
                        PdfPCell cell11 = new PdfPCell(new Phrase("Consumibles", FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell11.setBorder(Rectangle.BOTTOM);
                        mat.addCell(cell11);
                    } else {
                        PdfPCell cell11 = new PdfPCell(new Phrase("Herramientas", FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell11.setBorder(Rectangle.BOTTOM);
                        mat.addCell(cell11);
                    }

                    PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(l.getDCantidad())+" "+l.getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell3.setBorder(Rectangle.BOTTOM);
                    mat.addCell(cell3);

                    PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(l.getDCantidadmin())+" "+l.getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell4.setBorder(Rectangle.BOTTOM);
                    mat.addCell(cell4);

                    PdfPCell cell5 = new PdfPCell(new Phrase(l.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell5.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cell5.setBorder(Rectangle.BOTTOM);
                    mat.addCell(cell5);

                    mat.setSpacingBefore(10);
                    mat.setSpacingAfter(10);
                }
                document.add(mat);
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
