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
import modelo.AgenteTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.AgenteTbFacade;

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
import modelo.AgentePerfilTb;
import modelo.AgentePerfilTbPK;
import modelo.PerfilTb;

@Named("agenteTbController")
@ViewScoped
public class AgenteTbController implements Serializable {

    @EJB
    private servicio.AgenteTbFacade ejbFacade;
    @EJB
    private servicio.PerfilTbFacade perfilFacade;
    private List<AgenteTb> items = null, filtrado = null, filtro, AgenteCustodio;
    private AgenteTb selected;
    private PerfilTb perfil;
    private List<PerfilTb> perfiles;
    private List<AgentePerfilTb> agentePerfil, items1 = null, perfil1 = null;
    private AgentePerfilTb perfilAgente;

    public List<AgentePerfilTb> getItems1() {
        return items1;
    }

    public void setItems1(List<AgentePerfilTb> items1) {
        this.items1 = items1;
    }

    public AgentePerfilTb getPerfilAgente() {
        return perfilAgente;
    }

    public void setPerfilAgente(AgentePerfilTb perfilAgente) {
        this.perfilAgente = perfilAgente;
    }

    public List<PerfilTb> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<PerfilTb> perfiles) {
        this.perfiles = perfiles;
    }

    public PerfilTb getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilTb perfil) {
        this.perfil = perfil;
    }

    public List<AgenteTb> getFiltrado() {
        return filtrado;
    }

    public void setFiltrado(List<AgenteTb> filtrado) {
        this.filtrado = filtrado;
    }

    public List<AgenteTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<AgenteTb> filtro) {
        this.filtro = filtro;
    }

    public List<AgentePerfilTb> getAgentePerfil() {
        return agentePerfil;
    }

    public void setAgentePerfil(List<AgentePerfilTb> agentePerfil) {
        this.agentePerfil = agentePerfil;
    }

    public List<AgentePerfilTb> getPerfil1() {
        return perfil1;
    }

    public void setPerfil1(List<AgentePerfilTb> perfil1) {
        this.perfil1 = perfil1;
    }

    public AgenteTbController() {
    }

    public AgenteTb getSelected() {
        return selected;
    }

    public void setSelected(AgenteTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AgenteTbFacade getFacade() {
        return ejbFacade;
    }

    public List<AgenteTb> getAgenteCustodio() {
        AgenteCustodio = getFacade().agenteCustodio();
        return AgenteCustodio;
    }

    public void setAgenteCustodio(List<AgenteTb> AgenteCustodio) {
        this.AgenteCustodio = AgenteCustodio;
    }

    public AgenteTb prepareCreate() {
        selected = new AgenteTb();
        //Hay que inicializar la lista
        selected.setAgentePerfilTbList(new ArrayList<AgentePerfilTb>());
        perfiles = perfilFacade.buscarTodosAZ();
        initializeEmbeddableKey();
        return selected;
    }

    public AgenteTb prepareEdit() {
        perfiles = perfilFacade.buscarTodosAZ();

        for (AgentePerfilTb b : selected.getAgentePerfilTbList()) {
            perfiles.remove(b.getPerfilTb());
        }

        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgenteTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AgenteTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AgenteTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AgenteTb> getItems() {
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

    public AgenteTb getAgenteTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AgenteTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AgenteTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AgenteTb.class)
    public static class AgenteTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgenteTbController controller = (AgenteTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agenteTbController");
            return controller.getAgenteTb(getKey(value));
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
            if (object instanceof AgenteTb) {
                AgenteTb o = (AgenteTb) object;
                return getStringKey(o.getEIdagente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AgenteTb.class.getName()});
                return null;
            }
        }

    }

    public void anadir() {
        AgentePerfilTb nuevo = new AgentePerfilTb();
        nuevo.setPerfilTb(perfil);
        nuevo.setAgenteTb(selected);

        AgentePerfilTbPK agentepk = new AgentePerfilTbPK();

        agentepk.setEIdperfil(perfil.getEIdperfil());
        agentepk.setEIdagente(getFacade().siguienteId());

        nuevo.setAgentePerfilTbPK(agentepk);

        selected.getAgentePerfilTbList().add(nuevo);

        perfiles.remove(perfil);
    }

    public void anadirEdit() {
        AgentePerfilTb nuevo = new AgentePerfilTb();
        nuevo.setPerfilTb(perfil);
        nuevo.setAgenteTb(selected);

        AgentePerfilTbPK agentepk = new AgentePerfilTbPK();

        agentepk.setEIdperfil(perfil.getEIdperfil());
        agentepk.setEIdagente(selected.getEIdagente());

        nuevo.setAgentePerfilTbPK(agentepk);

        selected.getAgentePerfilTbList().add(nuevo);

        perfiles.remove(perfil);

    }

    public void remover() {
        selected.getAgentePerfilTbList().remove(perfilAgente);
        perfiles.add(perfilAgente.getPerfilTb());

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

                Paragraph titulo = new Paragraph("Reporte de Agentes", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                PdfPTable agentes = new PdfPTable(6);
                agentes.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                
                int headerwidths[] = {13, 15, 15, 10, 12, 35};
                try {
                    agentes.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                agentes.setWidthPercentage(100);
                agentes.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                agentes.addCell(new Phrase("Iniciales", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                agentes.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                agentes.addCell(new Phrase("Apellido", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                agentes.addCell(new Phrase("Ocupación", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                agentes.addCell(new Phrase("Télefono", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                agentes.addCell(new Phrase("Institución", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<AgenteTb> lista;
                if (filtrado != null) {
                    lista = filtrado;
                } else {
                    lista = this.items;
                }

                for (AgenteTb l : lista) {
           
                    PdfPCell celll = new PdfPCell(new Phrase(l.getCIniciales(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    celll.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(celll);
                    
                    PdfPCell cell = new PdfPCell(new Phrase(l.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell);

                    PdfPCell cell2 = new PdfPCell(new Phrase(l.getCApellido(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell2);

                    if (l.getCOcupacion().equals("")) {
                        PdfPCell cell3 = new PdfPCell(new Phrase("Sin ocupación", FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell3);
                    } else {
                        PdfPCell cell3 = new PdfPCell(new Phrase(l.getCOcupacion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell3);
                    }
                    
                    if (l.getCTelefono().equals("")) {
                        PdfPCell cell31 = new PdfPCell(new Phrase("Sin teléfono", FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        agentes.addCell(cell31);
                    } else {
                        PdfPCell cell31 = new PdfPCell(new Phrase(l.getCTelefono(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        agentes.addCell(cell31);
                    }
                    

                    try {
                        PdfPCell cell4 = new PdfPCell(new Phrase(l.getEIdinstitucion().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell4);
                    } catch (Exception e) {
                        PdfPCell cell4 = new PdfPCell(new Phrase("Sin institución", FontFactory.getFont(FontFactory.TIMES, 12)));
                        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell4);
                    }

                }
                document.add(agentes);
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

    public void reporteseleccionadocv() {
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

                Paragraph titulo = new Paragraph("Reporte de Agente", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(15);
                document.add(fecha);

                int i = 0;
                for (AgentePerfilTb l : selected.getAgentePerfilTbList()) {
                    i++;
                }

                PdfPTable per = new PdfPTable(1);
                for (AgentePerfilTb l : selected.getAgentePerfilTbList()) {
                    PdfPCell cell0 = new PdfPCell(new Phrase(" -" + l.getPerfilTb().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    cell0.setBorder(Rectangle.NO_BORDER);
                    cell0.setHorizontalAlignment(Element.ALIGN_LEFT);
                    per.addCell(cell0);
                }

                int j = 0;
                for (AgentePerfilTb r : selected.getAgentePerfilTbList()) {
                    if (j == 0) {
                        PdfPTable ag = new PdfPTable(2);
                        ag.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headerwidths[] = {13, 87};
                        try {
                            ag.setWidths(headerwidths);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag.setWidthPercentage(100);
                        ag.addCell(new Phrase("Nombre :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        ag.addCell(new Phrase(new Phrase(r.getAgenteTb().getCNombre() + " " + r.getAgenteTb().getCApellido(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        document.add(ag);

                        Paragraph a = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        a.setAlignment(Element.ALIGN_LEFT);
                        a.setSpacingAfter(5);
                        a.setSpacingBefore(5);
                        document.add(a);

                        PdfPTable ag1 = new PdfPTable(2);
                        ag1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headerwidth[] = {13, 87};
                        try {
                            ag1.setWidths(headerwidth);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag1.setWidthPercentage(100);
                        ag1.addCell(new Phrase("Iniciales :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getCIniciales().equals("")) {
                            ag1.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag1.addCell(new Phrase(new Phrase(r.getAgenteTb().getCIniciales(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag1);

                        Paragraph aa = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        aa.setAlignment(Element.ALIGN_LEFT);
                        aa.setSpacingAfter(5);
                        aa.setSpacingBefore(5);
                        document.add(aa);

                        PdfPTable ag2 = new PdfPTable(2);
                        ag2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int header[] = {13, 87};
                        try {
                            ag2.setWidths(header);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag2.setWidthPercentage(100);
                        ag2.addCell(new Phrase("Ocupación :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getCOcupacion().equals("")) {
                            ag2.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag2.addCell(new Phrase(new Phrase(r.getAgenteTb().getCOcupacion(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag2);

                        Paragraph aaa = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        aaa.setAlignment(Element.ALIGN_LEFT);
                        aaa.setSpacingAfter(5);
                        aaa.setSpacingBefore(5);
                        document.add(aaa);

                        PdfPTable ag3 = new PdfPTable(2);
                        ag3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headers[] = {13, 87};
                        try {
                            ag3.setWidths(headers);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag3.setWidthPercentage(100);
                        ag3.addCell(new Phrase("Email :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getCEmail().equals("")) {
                            ag2.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag2.addCell(new Phrase(new Phrase(r.getAgenteTb().getCEmail(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag3);

                        Paragraph o = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        o.setAlignment(Element.ALIGN_LEFT);
                        o.setSpacingAfter(5);
                        o.setSpacingBefore(5);
                        document.add(o);

                        PdfPTable ag4 = new PdfPTable(2);
                        ag4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headersw[] = {13, 87};
                        try {
                            ag4.setWidths(headersw);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag4.setWidthPercentage(100);
                        ag4.addCell(new Phrase("Postal :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getEPostal() != null) {
                            ag4.addCell(new Phrase(new Phrase(String.valueOf(r.getAgenteTb().getEPostal()), FontFactory.getFont(FontFactory.TIMES, 12))));
                        } else {
                            ag4.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        }
                        document.add(ag4);

                        Paragraph os = new Paragraph(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 11)));
                        os.setAlignment(Element.ALIGN_LEFT);
                        os.setSpacingAfter(5);
                        os.setSpacingBefore(5);
                        document.add(os);

                        PdfPTable ag5 = new PdfPTable(2);
                        ag5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int head[] = {13, 87};
                        try {
                            ag5.setWidths(head);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag5.setWidthPercentage(100);
                        ag5.addCell(new Phrase("Teléfono :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getCTelefono().equals("")) {
                            ag5.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag5.addCell(new Phrase(new Phrase(r.getAgenteTb().getCTelefono(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag5);

                        Paragraph ex = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        ex.setAlignment(Element.ALIGN_LEFT);
                        ex.setSpacingAfter(5);
                        ex.setSpacingBefore(5);
                        document.add(ex);

                        PdfPTable ag6 = new PdfPTable(2);
                        ag6.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag6.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headerss[] = {13, 87};
                        try {
                            ag6.setWidths(headerss);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag6.setWidthPercentage(100);
                        ag6.addCell(new Phrase("Fax :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getCFax().equals("")) {
                            ag6.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag6.addCell(new Phrase(new Phrase(r.getAgenteTb().getCFax(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag6);

                        Paragraph es = new Paragraph(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 11)));
                        es.setAlignment(Element.ALIGN_LEFT);
                        es.setSpacingAfter(5);
                        es.setSpacingBefore(5);
                        document.add(es);

                        PdfPTable ag7 = new PdfPTable(2);
                        ag7.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag7.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headerssw[] = {13, 87};
                        try {
                            ag7.setWidths(headerssw);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag7.setWidthPercentage(100);
                        ag7.addCell(new Phrase("Dirección :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getMDireccion().equals("")) {
                            ag2.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag7.addCell(new Phrase(new Phrase(r.getAgenteTb().getMDireccion(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        }
                        document.add(ag7);

                        Paragraph p = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        p.setAlignment(Element.ALIGN_LEFT);
                        p.setSpacingAfter(5);
                        p.setSpacingBefore(5);
                        document.add(p);

                        PdfPTable ag8 = new PdfPTable(2);
                        ag8.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int hea[] = {26, 74};
                        try {
                            ag8.setWidths(hea);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag8.setWidthPercentage(100);
                        ag8.addCell(new Phrase("Fecha de Nacimiento :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getFFechanac() != null) {
                            ag8.addCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(r.getAgenteTb().getFFechanac()), FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag8.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 14)));
                        }
                        document.add(ag8);

                        Paragraph ps = new Paragraph(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 11)));
                        ps.setAlignment(Element.ALIGN_LEFT);
                        ps.setSpacingAfter(5);
                        ps.setSpacingBefore(5);
                        document.add(ps);

                        PdfPTable ag9 = new PdfPTable(2);
                        ag9.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag9.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int heaw[] = {26, 74};
                        try {
                            ag9.setWidths(heaw);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag9.setWidthPercentage(100);
                        ag9.addCell(new Phrase("Fecha de Muerte :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        if (r.getAgenteTb().getFFecham() != null) {
                            ag9.addCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(r.getAgenteTb().getFFecham()), FontFactory.getFont(FontFactory.TIMES, 12)));
                        } else {
                            ag9.addCell(new Phrase("---------", FontFactory.getFont(FontFactory.TIMES, 12)));
                        }
                        document.add(ag9);

                        Paragraph t = new Paragraph("", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
                        t.setAlignment(Element.ALIGN_LEFT);
                        t.setSpacingAfter(5);
                        t.setSpacingBefore(5);
                        document.add(t);

                        PdfPTable ag10 = new PdfPTable(2);
                        ag10.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag10.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int heaws[] = {13, 87};
                        try {
                            ag10.setWidths(heaws);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag10.setWidthPercentage(100);
                        ag10.addCell(new Phrase("Institución :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        try {
                            ag10.addCell(new Phrase(new Phrase(r.getAgenteTb().getEIdinstitucion().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                        } catch (Exception e) {
                            ag10.addCell(new Phrase("Sin Institución", FontFactory.getFont(FontFactory.TIMES, 12)));
                        }
                        document.add(ag10);

                        Paragraph ts = new Paragraph(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 11)));
                        ts.setAlignment(Element.ALIGN_LEFT);
                        ts.setSpacingAfter(5);
                        ts.setSpacingBefore(5);
                        document.add(ts);

                        PdfPTable ag11 = new PdfPTable(2);
                        ag11.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                        ag11.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int heawsh[] = {13, 87};
                        try {
                            ag11.setWidths(heawsh);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        ag11.setWidthPercentage(100);
                        ag11.addCell(new Phrase("Perfiles :", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                        PdfPCell cellp = new PdfPCell(per);
                        cellp.setBorder(Rectangle.NO_BORDER);
                        ag11.addCell(cellp);
                        //ag11.addCell(new PdfPCell(per).setBorder(Rectangle.NO_BORDER));
                        document.add(ag11);
                    }
                    j++;
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
