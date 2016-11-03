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
import modelo.PedidoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.PedidoTbFacade;

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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.EjemplarTb;
import modelo.MaterialPedidoTb;
import modelo.MaterialPedidoTbPK;
import modelo.MaterialTb;
import modelo.UsuarioTb;

@Named("pedidoTbController")
@ViewScoped
public class PedidoTbController implements Serializable {

    @EJB
    private servicio.MaterialTbFacade materialFacade;
    @EJB
    private servicio.PedidoTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<PedidoTb> items = null, filtro;
    private MaterialTb material;
    private List<MaterialTb> materialDisponible;
    private List<MaterialPedidoTb> mp;
    private double cantidad, entrada;
    private MaterialPedidoTb materialEL;
    private Integer estado;
    private boolean bandera;
    private String oncomplete, codigoBarras;

    public boolean isBandera() {
        return bandera;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getOncomplete() {
        return oncomplete;
    }

    public void setOncomplete(String oncomplete) {
        this.oncomplete = oncomplete;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public List<MaterialPedidoTb> getMp() {
        return mp;
    }

    public void setMp(List<MaterialPedidoTb> mp) {
        this.mp = mp;
    }

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public MaterialPedidoTb getMaterialEL() {
        return materialEL;
    }

    public void setMaterialEL(MaterialPedidoTb materialEL) {
        this.materialEL = materialEL;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public List<MaterialTb> getMaterialDisponible() {
        return materialDisponible;
    }

    public void setMaterialDisponible(List<MaterialTb> materialDisponible) {
        this.materialDisponible = materialDisponible;
    }

    public MaterialTb getMaterial() {
        return material;
    }

    public void setMaterial(MaterialTb material) {
        this.material = material;
    }

    public List<PedidoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<PedidoTb> filtro) {
        this.filtro = filtro;
    }
    private PedidoTb selected;

    public PedidoTbController() {
    }

    public PedidoTb getSelected() {
        return selected;
    }

    public void setSelected(PedidoTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PedidoTbFacade getFacade() {
        return ejbFacade;
    }

    public PedidoTb prepareCreate() {
        selected = new PedidoTb();
        selected.setMaterialPedidoTbList(new ArrayList<MaterialPedidoTb>());
        initializeEmbeddableKey();
        materialDisponible = materialFacade.ordenarMenosStock();
        return selected;
    }

    public PedidoTb prepareEdit() {
        materialDisponible = materialFacade.buscarTodosAZ();
        for (MaterialPedidoTb b : selected.getMaterialPedidoTbList()) {
            materialDisponible.remove(b.getMaterialTb());
        }
        if (selected.getEEstado() == 3) {
            bandera = true;
        } else {
            bandera = false;
        }
        initializeEmbeddableKey();
        return selected;
    }

    public PedidoTb prepareActualizar() {
        if (selected.getEEstado() == 3) {
            bandera = true;
        } else {
            bandera = false;
        }
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected.getMaterialPedidoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar materiales");
            //oncomplete = "";
        } else {
            //oncomplete = "handleSubmit(args,'PedidoTbCreateDialog');";
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Creado pedido '" + selected.getMDescripcion() + "' en el módulo: Materiales");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PedidoTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        if (selected.getMaterialPedidoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar materiales");
            oncomplete = "";
        } else {
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Modificado pedido '" + selected.getMDescripcion() + "' en el módulo: Materiales");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidoTbUpdated"));
        }
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado pedido '" + selected.getMDescripcion() + "' en el módulo: Materiales");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PedidoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PedidoTb> getItems() {
        if (items == null) {
            items = getFacade().ordenarPedidoFecha();
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

    public PedidoTb getPedidoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PedidoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PedidoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PedidoTb.class)
    public static class PedidoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidoTbController controller = (PedidoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidoTbController");
            return controller.getPedidoTb(getKey(value));
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
            if (object instanceof PedidoTb) {
                PedidoTb o = (PedidoTb) object;
                return getStringKey(o.getEIdpedido());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PedidoTb.class.getName()});
                return null;
            }
        }

    }

        public void cargarMaterial() {
        material = materialFacade.obtenerPorCodigo(codigoBarras);
    }
    public void agregar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (material == null || cantidad == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione material e ingrese cantidad", "Alerta: "));
        } else {
            MaterialPedidoTb nuevo = new MaterialPedidoTb();
            nuevo.setDCantidad(cantidad);
            nuevo.setDEntrada(0.0);
            nuevo.setMaterialTb(material);
            nuevo.setPedidoTb(selected);

            MaterialPedidoTbPK mppk = new MaterialPedidoTbPK();
            mppk.setEIdmaterial(material.getEIdmaterial());
            mppk.setEIdpedido(getFacade().siguienteId());

            nuevo.setMaterialPedidoTbPK(mppk);

            selected.getMaterialPedidoTbList().add(nuevo);

            materialDisponible.remove(material);

            cantidad = 0.0;
            codigoBarras="";
        }

    }

    public void agregarModificar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (material == null || cantidad == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione material e ingrese cantidad", "Alerta: "));
        } else {
            MaterialPedidoTb nuevo = new MaterialPedidoTb();
            nuevo.setDCantidad(cantidad);
            nuevo.setDEntrada(0.0);
            nuevo.setMaterialTb(material);
            nuevo.setPedidoTb(selected);

            MaterialPedidoTbPK mppk = new MaterialPedidoTbPK();
            mppk.setEIdmaterial(material.getEIdmaterial());
            mppk.setEIdpedido(selected.getEIdpedido());

            nuevo.setMaterialPedidoTbPK(mppk);

            selected.getMaterialPedidoTbList().add(nuevo);

            materialDisponible.remove(material);

            cantidad = 0.0;
            codigoBarras="";
        }

    }

    public void remover() {
        selected.getMaterialPedidoTbList().remove(materialEL);
        materialDisponible.add(materialEL.getMaterialTb());
        material = null;
    }

    public String estadoPedido(Integer estado) {
        if (estado == 0) {
            return "En Proceso";
        }
        if (estado == 1) {
            return "Recibido Parcialmente";
        }
        if (estado == 2) {
            return "Recibido";
        }
        if (estado == 3) {
            return "No Recibido";
        }
        return "";
    }

    public Date fechaActual() {
        Date fechaAct = new Date();
        selected.setFFecha(fechaAct);
        selected.setEEstado(0);
        return fechaAct;
    }

    public void recibirPedido() {
        for (MaterialPedidoTb i : selected.getMaterialPedidoTbList()) {
            i.getMaterialTb().setDCantidad(i.getMaterialTb().getDCantidad() + i.getDEntrada());
            materialFacade.edit(i.getMaterialTb());
        }
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Recibido pedido '" + selected.getMDescripcion() + "' en el módulo: Materiales");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidoTbRecibido"));
    }

    public void actualizarPedido() {
        for (MaterialPedidoTb i : selected.getMaterialPedidoTbList()) {
            i.setDEntrada(i.getDEntrada() + i.getCantidad2());
            i.getMaterialTb().setDCantidad(i.getMaterialTb().getDCantidad() + i.getCantidad2());
            materialFacade.edit(i.getMaterialTb());
        }
        //selected.setEEstado(estado);
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Actualizado pedido '" + selected.getMDescripcion() + "' en el módulo: Materiales");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidoTbActualizado"));
    }

    public void desactivar() {
        if (selected.getEEstado() == 3) {
            bandera = true;
        } else {
            bandera = false;
        }
    }

    public void activarEnProceso() {
        int a = 0;
        for (MaterialPedidoTb i : selected.getMaterialPedidoTbList()) {
            if (i.getDEntrada() > 0.0) {
                a++;
            }
        }
        if (a > 0) {
            bandera = true;
            //return bandera;
        } else {
            bandera = false;
            //return bandera;
        }

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
                //Indico tamaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â±o del logo
                logo.scaleToFit(80, 80);
                //aÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â±ado el primer logo a la celda
                cell1.addElement(logo);
                //Celda sin borde borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //aÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â±ado celda a la tabla
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

                Paragraph titulo = new Paragraph("REPORTE GENERAL DE PEDIDOS", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                //fecha.setSpacingAfter(10);
                document.add(fecha);

                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(15);
                document.add(usuarioSis);

                PdfPTable pedidos = new PdfPTable(4);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {40, 20, 20, 20};
                try {
                    pedidos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                pedidos.setWidthPercentage(100);
                pedidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                pedidos.addCell(new Phrase("Descripción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Fecha de pedido", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Fecha a recibir", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                pedidos.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<PedidoTb> pedidoListaReporte = new ArrayList<PedidoTb>();

                pedidoListaReporte = getFacade().findAll();

                for (PedidoTb pedido : pedidoListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(pedido.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pedidos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(pedido.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(pedido.getFFechaPosibleRecibir()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pedidos.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(estadoPedido(pedido.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 11)));
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
                bitacora.setMDescripcion("Creado reporte general de pedidos en el módulo: Materiales");
                //String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                //UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                bitacora.setEIdusuario(usuario);
                Date fecha1 = new Date();
                bitacora.setTFecha(fecha1);
                bitacoraFacade.create(bitacora);
                //Bitacora fin 125, 142, 158-167
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

                Paragraph titulo = new Paragraph("REPORTE DE PEDIDO", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                //fecha.setSpacingAfter(15);
                document.add(fecha);

                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
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
                TablaDescripcion.addCell(new Phrase("Fecha de pedido: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(selected.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaDescripcion);

                PdfPTable TablaFecha = new PdfPTable(2);
                TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaFecha.setWidths(columnas);
                TablaFecha.setWidthPercentage(100);
                TablaFecha.setSpacingAfter(5);
                TablaFecha.setSpacingBefore(5);
                TablaFecha.addCell(new Phrase("Fecha posible recibir: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(selected.getFFechaPosibleRecibir()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaFecha);

                /*PdfPTable TablaResponsable = new PdfPTable(2);
                 TablaResponsable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                 TablaResponsable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                 TablaResponsable.setWidths(columnas);
                 TablaResponsable.setWidthPercentage(100);
                 TablaResponsable.setSpacingAfter(5);
                 TablaResponsable.setSpacingBefore(5);
                 TablaResponsable.addCell(new Phrase("Estado: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                 TablaResponsable.addCell(new Phrase(new Phrase(estadoPedido(selected.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 12))));
                 document.add(TablaResponsable);*/
                Paragraph tituloActividades = new Paragraph("MATERIALES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloActividades.setAlignment(Element.ALIGN_CENTER);
                tituloActividades.setSpacingAfter(5);
                tituloActividades.setSpacingBefore(5);
                document.add(tituloActividades);

                if (!selected.getMaterialPedidoTbList().isEmpty()) {
                    //Encabezado
                    PdfPTable TablaInsumo1 = new PdfPTable(3);
                    int numero[] = {35, 35, 30};
                    TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo1.setWidths(numero);
                    TablaInsumo1.setWidthPercentage(75);
                    TablaInsumo1.addCell(new Phrase("Material", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Marca", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Cantidad Solicitada", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    //TablaInsumo1.addCell(new Phrase("Cantidad Recibida", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    //TablaInsumo1.addCell(new Phrase("Fecha Recibido", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    document.add(TablaInsumo1);

                    for (MaterialPedidoTb i : selected.getMaterialPedidoTbList()) {
                        PdfPTable TablaInsumo = new PdfPTable(3);
                        TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.setWidths(numero);
                        TablaInsumo.setWidthPercentage(75);

                        PdfPCell c0 = new PdfPCell(new Phrase(i.getMaterialTb().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaInsumo.addCell(c0);

                        PdfPCell c1 = new PdfPCell(new Phrase(i.getMaterialTb().getMMarca(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getDCantidad()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        TablaInsumo.addCell(c2);

                        /*PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getDEntrada()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                         c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                         TablaInsumo.addCell(c3);

                         PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(i.getFFechaRecibido()), FontFactory.getFont(FontFactory.TIMES, 12)));
                         c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                         TablaInsumo.addCell(c4);*/
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
                //Bitacora inicio
                BitacoraTb bitacora = new BitacoraTb();
                bitacora.setMDescripcion("Creado reporte individual de pedido en el módulo: Materiales");
                //String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                //UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                bitacora.setEIdusuario(usuario);
                Date fecha1 = new Date();
                bitacora.setTFecha(fecha1);
                bitacoraFacade.create(bitacora);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void reporteIndividualProcesado() {
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

                Paragraph titulo = new Paragraph("REPORTE DE PEDIDO", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
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
                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
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
                TablaDescripcion.addCell(new Phrase("Fecha de Pedido: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(selected.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaDescripcion);

                PdfPTable TablaFecha = new PdfPTable(2);
                TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaFecha.setWidths(columnas);
                TablaFecha.setWidthPercentage(100);
                TablaFecha.setSpacingAfter(5);
                TablaFecha.setSpacingBefore(5);
                TablaFecha.addCell(new Phrase("Fecha posible recibir: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaFecha.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(selected.getFFechaPosibleRecibir()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaFecha);

                PdfPTable TablaResponsable = new PdfPTable(2);
                TablaResponsable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaResponsable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaResponsable.setWidths(columnas);
                TablaResponsable.setWidthPercentage(100);
                TablaResponsable.setSpacingAfter(5);
                TablaResponsable.setSpacingBefore(5);
                TablaResponsable.addCell(new Phrase("Estado: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaResponsable.addCell(new Phrase(new Phrase(estadoPedido(selected.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaResponsable);

                Paragraph tituloActividades = new Paragraph("MATERIALES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloActividades.setAlignment(Element.ALIGN_CENTER);
                tituloActividades.setSpacingAfter(5);
                tituloActividades.setSpacingBefore(5);
                document.add(tituloActividades);

                if (!selected.getMaterialPedidoTbList().isEmpty()) {
                    //Encabezado
                    PdfPTable TablaInsumo1 = new PdfPTable(5);
                    int numero[] = {25, 20, 15, 15, 25};
                    TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo1.setWidths(numero);
                    TablaInsumo1.setWidthPercentage(100);
                    TablaInsumo1.addCell(new Phrase("Material", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Marca", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Cantidad Solicitada", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Cantidad Recibida", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Fecha Recibido", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    document.add(TablaInsumo1);

                    for (MaterialPedidoTb i : selected.getMaterialPedidoTbList()) {
                        PdfPTable TablaInsumo = new PdfPTable(5);
                        TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.setWidths(numero);
                        TablaInsumo.setWidthPercentage(100);

                        PdfPCell c0 = new PdfPCell(new Phrase(i.getMaterialTb().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaInsumo.addCell(c0);

                        PdfPCell c1 = new PdfPCell(new Phrase(i.getMaterialTb().getMMarca(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getDCantidad()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        TablaInsumo.addCell(c2);

                        PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getDEntrada()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        TablaInsumo.addCell(c3);
                        if (i.getFFechaRecibido() == null) {
                            PdfPCell c4 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 12)));
                            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            TablaInsumo.addCell(c4);
                        } else {
                            PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("dd /MM/ yyyy").format(i.getFFechaRecibido()), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            TablaInsumo.addCell(c4);
                        }

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
                //Bitacora inicio
                BitacoraTb bitacora = new BitacoraTb();
                bitacora.setMDescripcion("Creado reporte individual en proceso de pedido en el módulo: Materiales");
                //String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                //UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                bitacora.setEIdusuario(usuario);
                Date fecha1 = new Date();
                bitacora.setTFecha(fecha1);
                bitacoraFacade.create(bitacora);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
