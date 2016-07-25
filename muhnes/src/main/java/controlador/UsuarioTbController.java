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
import modelo.UsuarioTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.UsuarioTbFacade;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
import modelo.AgenteTb;
import modelo.BitacoraTb;

@Named("usuarioTbController")
@ViewScoped
public class UsuarioTbController implements Serializable {

    @EJB
    private servicio.UsuarioTbFacade ejbFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    @EJB
    private servicio.AgenteTbFacade FacadeAgente;
    private List<UsuarioTb> items = null, filtro;
    private UsuarioTb selected;
    private String pass1;
    private String anterior;
    private String respaldo;
    private Integer columnas;

    public UsuarioTbController() {
    }

    public UsuarioTb getSelected() {
        return selected;
    }

    public void setSelected(UsuarioTb selected) {
        this.selected = selected;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public List<UsuarioTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<UsuarioTb> filtro) {
        this.filtro = filtro;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    private UsuarioTbFacade getFacade() {
        return ejbFacade;
    }

    public UsuarioTb prepareCreate() {
        selected = new UsuarioTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareCambiarContra(String usuario) {
        selected = getFacade().BuscarUsuario(usuario);
        respaldo = selected.getMPassword();

    }

    public void create() {
        selected.setMEmail(selected.getMEmail().toLowerCase());
        selected.setBEstado(Boolean.TRUE);
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creado nuevo usuario: '" + selected.getCNick() + "' en el módulo: Usuarios");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void cambiarContra() {
        selected.setMEmail(selected.getMEmail().toLowerCase());
        try {
            if (JsfUtil.cifrar(anterior).equals(respaldo)) {
                //busca si existe un usuario que puso el mismo email
                UsuarioTb usuario = ejbFacade.usuarioByCorreo(selected.getMEmail());
                //Comprueba que no sea el mismo usuario
                if (usuario != null && usuario.equals(selected)) {
                    usuario = null;
                }
                if (usuario == null) {
                    persist(PersistAction.UPDATE, "El usuario '" + selected.getCNick() + "'  ha modificado su información");
                    //Bitacora inicio
                    BitacoraTb bitacora = new BitacoraTb();
                    bitacora.setMDescripcion("El usuario : '" + selected.getCNick() + "' modificó su información");
                    String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                    UsuarioTb usuarios = usuarioFacade.BuscarUsuario(nick);
                    bitacora.setEIdusuario(usuarios);
                    Date fecha = new Date();
                    bitacora.setTFecha(fecha);
                    bitacoraFacade.create(bitacora);
                    //Bitacora fin
                } else {
                    JsfUtil.addErrorMessage("El email ingresado ya se encuentra en uso por otro usuario");
                }

            } else {
                JsfUtil.addErrorMessage("La contraseña anterior es incorrecta");
            }
        } catch (NoSuchAlgorithmException e) {
            JsfUtil.addErrorMessage("No se pudo cifrar la nueva contraseña. Intentelo más tarde");
        }
    }

    public void cambioMinusculaEmail() {
        selected.setMEmail(selected.getMEmail().toLowerCase());
    }

    public void update() {
        selected.setMEmail(selected.getMEmail().toLowerCase());
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado usuario: '" + selected.getCNick() + "' en el módulo: Usuarios");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UsuarioTb> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<UsuarioTb> getActivos() {
        items = null;
        items = getFacade().buscarActivos();
        return items;
    }

    public List<UsuarioTb> getInactivos() {
        items = null;
        items = getFacade().buscarInactivos();
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

    public UsuarioTb getUsuarioTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UsuarioTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UsuarioTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UsuarioTb.class)
    public static class UsuarioTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioTbController controller = (UsuarioTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioTbController");
            return controller.getUsuarioTb(getKey(value));
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
            if (object instanceof UsuarioTb) {
                UsuarioTb o = (UsuarioTb) object;
                return getStringKey(o.getEIdusuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsuarioTb.class.getName()});
                return null;
            }
        }

    }

    public void compararEmail(String c) {
        selected.setMEmail(selected.getMEmail().toLowerCase());
        FacesContext context = FacesContext.getCurrentInstance();
        for (UsuarioTb u : items) {
            if (u.getMEmail().equals(c)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El correo ya se encuentra utilizado por otro usuario.", "advertencia"));
                selected.setMEmail(null);
            }
        }
    }

    public void compararUsuario(String c) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (UsuarioTb u : items) {
            if (u.getCNick().equals(c)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El nombre de usuario ya existe en el Sistema.", "advertencia"));
                selected.setCNick(null);
            }
        }
    }

    public boolean renderedDarBaja(String c) {
        boolean a = false;
        try {
            if (c.equals(selected.getCNick())) {
                a = true;
            }
        } catch (Exception e) {
        }

        return a;
    }

    public Date fechaActual() {
        return new Date();
    }

    public void cambio() {
        selected.setBEstado(false);
        selected.setMPassword(String.valueOf(fechaActual()));
        getFacade().edit(selected);
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("El usuario: '" + selected.getCNick() + "' a sido dado de baja en el módulo: Usuarios");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.        
        }
    }

    public void cambioAct() {
        selected.setBEstado(true);
        getFacade().edit(selected);
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("El usuario: '" + selected.getCNick() + "' a sido dado de alta en el módulo: Usuarios");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        items = null;
    }

    //////////////////////////////////////////////REPORTE//////////////////////////////////////////////////////
    public void reporteAll(Integer n) {
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
                if (n == 1) {
                    Paragraph titulo = new Paragraph("Reporte General de Usuarios", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    titulo.setSpacingBefore(5);
                    document.add(titulo);
                }
                if (n == 2) {
                    Paragraph titulo = new Paragraph("Reporte General de Usuarios Inactivos", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    titulo.setSpacingBefore(5);
                    document.add(titulo);
                }
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

                columnas = 5;
                PdfPTable usuarios = new PdfPTable(columnas);
                //indicando el ancho de las columnas
                int headerwidths[] = {25, 32, 15, 13, 15};
                try {
                    usuarios.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //Lista que llenara la tabla
                List<UsuarioTb> usuarioListaReporte = new ArrayList<UsuarioTb>();
                if (n == 1) {
                    usuarioListaReporte = getFacade().buscarActivos();
                }
                if (n == 2) {
                    usuarioListaReporte = getFacade().buscarInactivos();
                }

                //cabeceras de las columnas
                if (!usuarioListaReporte.isEmpty()) {
                    usuarios.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    usuarios.setWidthPercentage(100);
                    usuarios.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    usuarios.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    usuarios.addCell(new Phrase("Correo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    usuarios.addCell(new Phrase("Usuario", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    usuarios.addCell(new Phrase("DUI", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    usuarios.addCell(new Phrase("Tipo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                    //llenado de la tabla con la informacion
                    for (UsuarioTb u : usuarioListaReporte) {

                        PdfPCell c1 = new PdfPCell(new Phrase(u.getCNombre() + " " + u.getCApellido(), FontFactory.getFont(FontFactory.TIMES, 11)));
                        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        usuarios.addCell(c1);

                        PdfPCell c3 = new PdfPCell(new Phrase(u.getMEmail(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        usuarios.addCell(c3);

                        PdfPCell c4 = new PdfPCell(new Phrase(u.getCNick(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                        usuarios.addCell(c4);

                        if (u.getCDui().equals("")) {
                            PdfPCell cell3 = new PdfPCell(new Phrase("Sin DUI", FontFactory.getFont(FontFactory.TIMES, 12)));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            usuarios.addCell(cell3);
                        } else {
                            PdfPCell c5 = new PdfPCell(new Phrase(u.getCDui(), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                            usuarios.addCell(c5);
                        }

                        PdfPCell c6 = new PdfPCell(new Phrase(u.getCTipo(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        usuarios.addCell(c6);

                    }
                    document.add(usuarios);
                } else {
                    Paragraph titulo3 = new Paragraph("No se encontraron usuarios.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11));
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
                bitacora.setMDescripcion("Creado Reporte general de Usuarios en el módulo: Seguridad");
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

    public String calculaAgente(int b) {
        String agente;
        AgenteTb agen;
        agen = FacadeAgente.find(b);
        if (agen == null) {
            agente = "Sin Agente";
        } else {
            agente = agen.getCNombre() + " " + agen.getCApellido();
        }
        return agente;
    }

}
