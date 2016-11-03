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
import modelo.BitacoraTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.BitacoraTbFacade;

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
import modelo.PedidoTb;
import modelo.UsuarioTb;

@Named("bitacoraTbController")
@ViewScoped
public class BitacoraTbController implements Serializable {

    @EJB
    private servicio.BitacoraTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<BitacoraTb> items = null, filtro;
    private BitacoraTb selected;
    private boolean booleanFecha, booleanUsuario, booleanGeneral;
    private Date f1, f2;
    private String tipoReporte;
    private Integer columnas;
    private UsuarioTb usuario;
    private List<UsuarioTb> listaUsuarios;

    public BitacoraTbController() {
    }

    public List<UsuarioTb> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioTb> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public UsuarioTb getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTb usuario) {
        this.usuario = usuario;
    }

    public boolean isBooleanFecha() {
        return booleanFecha;
    }

    public void setBooleanFecha(boolean booleanFecha) {
        this.booleanFecha = booleanFecha;
    }

    public boolean isBooleanUsuario() {
        return booleanUsuario;
    }

    public void setBooleanUsuario(boolean booleanUsuario) {
        this.booleanUsuario = booleanUsuario;
    }

    public boolean isBooleanGeneral() {
        return booleanGeneral;
    }

    public void setBooleanGeneral(boolean booleanGeneral) {
        this.booleanGeneral = booleanGeneral;
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

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<BitacoraTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<BitacoraTb> filtro) {
        this.filtro = filtro;
    }

    public BitacoraTb getSelected() {
        return selected;
    }

    public void setSelected(BitacoraTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BitacoraTbFacade getFacade() {
        return ejbFacade;
    }

    public BitacoraTb prepareCreate() {
        selected = new BitacoraTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BitacoraTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BitacoraTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BitacoraTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BitacoraTb> getItems() {
        if (items == null) {
            items = getFacade().BitacoraOrdenFecha();
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

    public BitacoraTb getBitacoraTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<BitacoraTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BitacoraTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BitacoraTb.class)
    public static class BitacoraTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BitacoraTbController controller = (BitacoraTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bitacoraTbController");
            return controller.getBitacoraTb(getKey(value));
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
            if (object instanceof BitacoraTb) {
                BitacoraTb o = (BitacoraTb) object;
                return getStringKey(o.getEIdbitacora());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BitacoraTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareReporte() {
        booleanFecha = false;
        booleanUsuario = false;
        booleanGeneral = true;
        listaUsuarios = getFacade().listaUsuarios();
        f1 = null;
        f2 = null;
    }

    public void actualizarVista() {
        if (tipoReporte.equals("general")) {
            booleanFecha = false;
            booleanUsuario = false;
            booleanGeneral = true;
        } else if (tipoReporte.equals("usuario")) {
            booleanFecha = false;
            booleanUsuario = true;
            booleanGeneral = false;
        } else if (tipoReporte.equals("fecha")) {
            booleanFecha = true;
            booleanUsuario = false;
            booleanGeneral = false;
        } else if (tipoReporte.equals("fechaYusuario")) {
            booleanFecha = true;
            booleanUsuario = true;
            booleanGeneral = false;
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
                Document document = new Document(PageSize.LETTER);
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

                Paragraph titulo = new Paragraph("REPORTE DE BITACORA", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes

                if (booleanUsuario == true) {
                    Paragraph titulo2 = new Paragraph("Usuario: " + usuario.getCNombre() + " " + usuario.getCApellido(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }
                if (booleanFecha == true) {
                    Paragraph titulo3 = new Paragraph("Fecha : " + new SimpleDateFormat("dd/MM/yyyy").format(f1) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo3.setAlignment(Element.ALIGN_CENTER);
                    titulo3.setSpacingAfter(5);
                    titulo3.setSpacingBefore(2);
                    document.add(titulo3);
                }

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
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
                //incializando tabla donde estara el contenido
                if (booleanUsuario == true) columnas = 3;
                else columnas =4;
                PdfPTable ejemplares = new PdfPTable(columnas);
                //indicando el ancho de las columnas
                int headerwidths[] = {40, 22, 12, 26};
                    int headerwidths2[] = {66, 22, 12};
                    if (booleanUsuario == true) {
                        try {
                            ejemplares.setWidths(headerwidths2);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        try {
                            ejemplares.setWidths(headerwidths);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                //Lista que llenara la tabla
                List<BitacoraTb> bitacoraListaReporte = new ArrayList<BitacoraTb>();
                if (booleanGeneral == true) {
                    bitacoraListaReporte = getFacade().findAll();
                } else if (booleanUsuario == true && booleanFecha == true) {
                    bitacoraListaReporte = getFacade().bitacoraFechaUsuario(usuario, f1, f2);
                } else if (booleanFecha == true) {
                    bitacoraListaReporte = getFacade().bitacoraFecha(f1, f2);
                } else if (booleanUsuario == true) {
                    bitacoraListaReporte = getFacade().bitacoraUsuario(usuario);
                }
                //cabeceras de las columnas
                if (!bitacoraListaReporte.isEmpty()) {
                    ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.setWidthPercentage(90);
                    ejemplares.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    ejemplares.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Fecha", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    ejemplares.addCell(new Phrase("Hora", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    if (booleanUsuario == false) {
                        ejemplares.addCell(new Phrase("Usuario", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                    }
                    //llenado de la tabla con la informacion
                    for (BitacoraTb bitacora : bitacoraListaReporte) {

                        PdfPCell c1 = new PdfPCell(new Phrase(bitacora.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        ejemplares.addCell(c1);

                        PdfPCell c3 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(bitacora.getTFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c3);

                        PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("hh:mm a").format(bitacora.getTFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ejemplares.addCell(c4);

                        if (booleanUsuario == false) {
                            PdfPCell c2 = new PdfPCell(new Phrase(bitacora.getEIdusuario().getCNombre() + " " + bitacora.getEIdusuario().getCApellido(), FontFactory.getFont(FontFactory.TIMES, 11)));
                            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            ejemplares.addCell(c2);
                        }
                    }
                    document.add(ejemplares);
                } else {
                    Paragraph titulo3 = new Paragraph("No se encontraron datos.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11));
                    titulo3.setAlignment(Element.ALIGN_CENTER);
                    titulo3.setSpacingAfter(5);
                    titulo3.setSpacingBefore(2);
                    document.add(titulo3);
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
                bitacora.setMDescripcion("Creado reporte de bitacora en el módulo: Seguridad");
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
