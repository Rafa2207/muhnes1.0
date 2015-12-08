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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modelo.InstitucionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.InstitucionTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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


@Named("institucionTbController")
@ViewScoped
public class InstitucionTbController implements Serializable {

    @EJB
    private servicio.InstitucionTbFacade ejbFacade;
    private List<InstitucionTb> items = null, filtrar = null, filtro;
    private InstitucionTb selected;

    public List<InstitucionTb> getFiltrar() {
        return filtrar;
    }

    public void setFiltrar(List<InstitucionTb> filtrar) {
        this.filtrar = filtrar;
    }


    public List<InstitucionTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<InstitucionTb> filtro) {
        this.filtro = filtro;
    }

    public InstitucionTbController() {
    }

    public InstitucionTb getSelected() {
        return selected;
    }

    public void setSelected(InstitucionTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InstitucionTbFacade getFacade() {
        return ejbFacade;
    }

    public InstitucionTb prepareCreate() {
        selected = new InstitucionTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InstitucionTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InstitucionTb> getItems() {
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

    public InstitucionTb getInstitucionTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<InstitucionTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InstitucionTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InstitucionTb.class)
    public static class InstitucionTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InstitucionTbController controller = (InstitucionTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "institucionTbController");
            return controller.getInstitucionTb(getKey(value));
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
            if (object instanceof InstitucionTb) {
                InstitucionTb o = (InstitucionTb) object;
                return getStringKey(o.getEIdinstitucion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InstitucionTb.class.getName()});
                return null;
            }
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
                //Indico tamaño del logo
                logo.scaleToFit(80, 80);
                //añado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //añado celda a la tabla
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

                Paragraph titulo = new Paragraph("Reporte de Instituciones", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    titulo.setSpacingAfter(5);
                    titulo.setSpacingBefore(10);
                    document.add(titulo);

                    Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                            FontFactory.getFont(FontFactory.TIMES, 10));
                    fecha.setAlignment(Element.ALIGN_CENTER);
                    fecha.setSpacingAfter(10);
                    document.add(fecha);

                    PdfPTable ins = new PdfPTable(4);
                    ins.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    ins.addCell(new Phrase("Acrónimo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    ins.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    ins.addCell(new Phrase("Télefono", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    ins.addCell(new Phrase("Pais", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                    List<InstitucionTb> lista;
                    if (filtrar != null) {
                        lista = filtrar;
                    } else {
                        lista = this.items;
                    }

                    for (InstitucionTb l : lista) {
                        PdfPCell cell = new PdfPCell(new Phrase(l.getCAcronimo(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        ins.addCell(cell);

                        PdfPCell cell2 = new PdfPCell(new Phrase(l.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ins.addCell(cell2);

                        PdfPCell cell3 = new PdfPCell(new Phrase(l.getCTelefono(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ins.addCell(cell3);

                        PdfPCell cell4 = new PdfPCell(new Phrase(l.getEIdpais().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ins.addCell(cell4);
                    }
                    document.add(ins);
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
