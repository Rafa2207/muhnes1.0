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
import modelo.DespachoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.DespachoTbFacade;

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
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.MaterialDespachoTb;
import modelo.MaterialDespachoTbPK;
import modelo.MaterialTb;
import modelo.PedidoTb;
import modelo.UsuarioTb;

@Named("despachoTbController")
@ViewScoped
public class DespachoTbController implements Serializable {

    @EJB
    private servicio.MaterialTbFacade materialFacade;
    @EJB
    private servicio.DespachoTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<DespachoTb> items = null, filtro;
    private DespachoTb selected;
    private MaterialTb material;
    private List<MaterialTb> materialDisponible;
    private double cantidad, salida;
    private MaterialDespachoTb materialMD;
    private boolean bandera;
    private List<MaterialDespachoTb> eliminados = new ArrayList<MaterialDespachoTb>();

    public DespachoTbController() {
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public List<MaterialTb> getMaterialDisponible() {
        return materialDisponible;
    }

    public void setMaterialDisponible(List<MaterialTb> materialDisponible) {
        this.materialDisponible = materialDisponible;
    }

    public MaterialDespachoTb getMaterialMD() {
        return materialMD;
    }

    public void setMaterialMD(MaterialDespachoTb materialMD) {
        this.materialMD = materialMD;
    }

    public MaterialTb getMaterial() {
        return material;
    }

    public void setMaterial(MaterialTb material) {
        this.material = material;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public List<DespachoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DespachoTb> filtro) {
        this.filtro = filtro;
    }

    public DespachoTb getSelected() {
        return selected;
    }

    public void setSelected(DespachoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DespachoTbFacade getFacade() {
        return ejbFacade;
    }

    public DespachoTb prepareCreate() {
        selected = new DespachoTb();
        initializeEmbeddableKey();
        selected.setMaterialDespachoTbList(new ArrayList<MaterialDespachoTb>());
        materialDisponible = getFacade().materialDisponible();
        return selected;
    }

    public DespachoTb prepareEdit() {
        materialDisponible = getFacade().materialDisponible();
        for (MaterialDespachoTb b : selected.getMaterialDespachoTbList()) {
            materialDisponible.remove(b.getMaterialTb());
        }
        //para saber si ya se hizo reingreso
        int a = 0;
        for (MaterialDespachoTb i : selected.getMaterialDespachoTbList()) {
            if (i.getDRegreso() > 0.0) {
                a++;
            }
        }
        if (a > 0) {
            bandera = false;
        } else {
            bandera = true;
        }
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected.getMaterialDespachoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar materiales");
        } else {
            for (MaterialDespachoTb i : selected.getMaterialDespachoTbList()) {
                i.getMaterialTb().setDCantidad(i.getMaterialTb().getDCantidad() - i.getDCantidad());
                materialFacade.edit(i.getMaterialTb());
            }
            selected.setEEstado(1); //cuando se realizo un despacho de materiales
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Creado Despacho '" + selected.getMDescripcion() + "' en el módulo: Materiales");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        if (selected.getMaterialDespachoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar materiales");
        } else {
            try {
                for (MaterialDespachoTb aa : eliminados) {
                    materialFacade.edit(aa.getMaterialTb());
                }
            } catch (Exception e) {
            }
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Modificado Despacho '" + selected.getMDescripcion() + "' en el módulo: Materiales");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbUpdated"));
        }
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado Despacho '" + selected.getMDescripcion() + "' en el módulo: Materiales");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DespachoTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleDespacho").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DespachoTb getDespachoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DespachoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DespachoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DespachoTb.class)
    public static class DespachoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DespachoTbController controller = (DespachoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "despachoTbController");
            return controller.getDespachoTb(getKey(value));
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
            if (object instanceof DespachoTb) {
                DespachoTb o = (DespachoTb) object;
                return getStringKey(o.getEIddespacho());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DespachoTb.class.getName()});
                return null;
            }
        }

    }

    public String estadoDespacho(Integer estado) {
        if (estado == 1) {
            return "No";
        }
        if (estado == 2) {
            return "Sí";
        }
        return "";
    }

    public void agregar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (material == null || cantidad == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione material e ingrese cantidad", "Alerta: "));
        } else if (cantidad > material.getDCantidad()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "la cantidad no debe ser mayor a " + material.getDCantidad(), "Alerta: "));
        } else {
            MaterialDespachoTb nuevo = new MaterialDespachoTb();
            nuevo.setDCantidad(cantidad);
            nuevo.setDRegreso(0.0);
            nuevo.setMaterialTb(material);
            nuevo.setDespachoTb(selected);

            MaterialDespachoTbPK mppk = new MaterialDespachoTbPK();
            mppk.setEIdmaterial(material.getEIdmaterial());
            mppk.setEIddespacho(getFacade().siguienteId());

            nuevo.setMaterialDespachoTbPK(mppk);

            selected.getMaterialDespachoTbList().add(nuevo);

            materialDisponible.remove(material);
            //material.setDCantidad(material.getDCantidad()-cantidad);

            cantidad = 0.0;
            material = null;
        }

    }

    public void agregarM() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (material == null || cantidad == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione material e ingrese cantidad", "Alerta: "));
        } else if (cantidad > material.getDCantidad()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "la cantidad no debe ser mayor a " + material.getDCantidad(), "Alerta: "));
        } else {
            MaterialDespachoTb nuevo = new MaterialDespachoTb();
            nuevo.setDCantidad(cantidad);
            nuevo.setDRegreso(0.0);
            nuevo.setMaterialTb(material);
            nuevo.setDespachoTb(selected);

            MaterialDespachoTbPK mppk = new MaterialDespachoTbPK();
            mppk.setEIdmaterial(material.getEIdmaterial());
            mppk.setEIddespacho(selected.getEIddespacho());

            nuevo.setMaterialDespachoTbPK(mppk);

            selected.getMaterialDespachoTbList().add(nuevo);

            materialDisponible.remove(material);
            //material.setDCantidad(material.getDCantidad()-cantidad);

            cantidad = 0.0;
            material = null;
        }

    }

    public void remover() {
        selected.getMaterialDespachoTbList().remove(materialMD);
        materialDisponible.add(materialMD.getMaterialTb());
        material = null;
    }

    public void removerM() {
        materialMD.getMaterialTb().setDCantidad(materialMD.getMaterialTb().getDCantidad() + materialMD.getDCantidad());
        eliminados.add(materialMD);
        selected.getMaterialDespachoTbList().remove(materialMD);
        materialDisponible.add(materialMD.getMaterialTb());
        material = null;
    }

    public void reingresoMaterial() {
        for (MaterialDespachoTb i : selected.getMaterialDespachoTbList()) {
            i.getMaterialTb().setDCantidad(i.getMaterialTb().getDCantidad() + i.getDRegreso());
            materialFacade.edit(i.getMaterialTb());
        }
        selected.setEEstado(2); //cuando el se realizo el reingreso de material
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Reingreso de materiales en Despacho '" + selected.getMDescripcion() + "' en el módulo: Materiales");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleDespacho").getString("DespachoTbReingreso"));
    }

    //**************************************REPORTES****************************************************//
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

                Paragraph titulo = new Paragraph("Reporte General de Despachos", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                PdfPTable pedidos = new PdfPTable(4);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {35, 18, 20, 27};
                try {
                    pedidos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                pedidos.setWidthPercentage(100);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                pedidos.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Fecha de despacho", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Proyecto", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Reingreso", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<DespachoTb> despachoListaReporte = new ArrayList<DespachoTb>();

                despachoListaReporte = getFacade().findAll();

                for (DespachoTb despacho : despachoListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(despacho.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pedidos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(despacho.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(despacho.getEIdproyecto().getMNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(estadoDespacho(despacho.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c4);

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
                bitacora.setMDescripcion("Creado Reporte general de pedidos en el módulo: Materiales");
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

    public void reporteIndividual() {
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

                Paragraph titulo = new Paragraph("Reporte de Despacho", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(15);
                document.add(fecha);

                int columnas[] = {30, 70};

                PdfPTable TablaNombre = new PdfPTable(2);
                TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombre.setWidths(columnas);
                TablaNombre.setWidthPercentage(100);
                TablaNombre.setSpacingAfter(5);
                TablaNombre.setSpacingBefore(5);
                TablaNombre.addCell(new Phrase("Descripción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombre.addCell(new Phrase(new Phrase(selected.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombre);

                PdfPTable TablaDescripcion = new PdfPTable(2);
                TablaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaDescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaDescripcion.setWidths(columnas);
                TablaDescripcion.setWidthPercentage(100);
                TablaDescripcion.setSpacingAfter(5);
                TablaDescripcion.setSpacingBefore(5);
                TablaDescripcion.addCell(new Phrase("Fecha de despacho: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(selected.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaDescripcion);

                PdfPTable TablaFecha = new PdfPTable(2);
                TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaFecha.setWidths(columnas);
                TablaFecha.setWidthPercentage(100);
                TablaFecha.setSpacingAfter(5);
                TablaFecha.setSpacingBefore(5);
                TablaFecha.addCell(new Phrase("Proyecto: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaFecha.addCell(new Phrase(new Phrase(selected.getEIdproyecto().getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaFecha);

                PdfPTable TablaResponsable = new PdfPTable(2);
                TablaResponsable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaResponsable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaResponsable.setWidths(columnas);
                TablaResponsable.setWidthPercentage(100);
                TablaResponsable.setSpacingAfter(5);
                TablaResponsable.setSpacingBefore(5);
                TablaResponsable.addCell(new Phrase("Materiales Reingresados: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaResponsable.addCell(new Phrase(new Phrase(estadoDespacho(selected.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaResponsable);

                Paragraph tituloActividades = new Paragraph("MATERIALES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloActividades.setAlignment(Element.ALIGN_CENTER);
                tituloActividades.setSpacingAfter(5);
                tituloActividades.setSpacingBefore(5);
                document.add(tituloActividades);

                if (!selected.getMaterialDespachoTbList().isEmpty()) {
                    //Encabezado
                    PdfPTable TablaInsumo1 = new PdfPTable(4);
                    int numero[] = {30, 18, 25, 27};
                    TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo1.setWidths(numero);
                    TablaInsumo1.setWidthPercentage(90);
                    TablaInsumo1.addCell(new Phrase("Material", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Marca", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Cantidad Solicitda", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Cantidad Reingresada", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    document.add(TablaInsumo1);

                    for (MaterialDespachoTb i : selected.getMaterialDespachoTbList()) {
                        PdfPTable TablaInsumo = new PdfPTable(4);
                        TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.setWidths(numero);
                        TablaInsumo.setWidthPercentage(90);

                        PdfPCell c0 = new PdfPCell(new Phrase(i.getMaterialTb().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaInsumo.addCell(c0);

                        PdfPCell c1 = new PdfPCell(new Phrase(i.getMaterialTb().getMMarca(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getDCantidad()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        TablaInsumo.addCell(c2);

                        PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getDRegreso()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        TablaInsumo.addCell(c3);

                        document.add(TablaInsumo);
                    }
                } else {
                    Paragraph tituloNoActividades = new Paragraph("No se encontraron Materiales", FontFactory.getFont(FontFactory.TIMES, 12));
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
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
