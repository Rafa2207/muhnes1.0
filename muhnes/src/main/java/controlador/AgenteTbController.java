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
    private List<AgenteTb> items = null, filtrado = null, filtro;
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

                // Empieza reporte
                Document document = new Document(PageSize.LETTER);
                PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
                TableHeaderVertical event = new TableHeaderVertical();
                writer.setPageEvent(event);
                document.open();

                //este cb sirve para sacar el codigo de barra
                PdfContentByte cb = writer.getDirectContent();

                //Encabezado
                //esto es para obtener la ruta del sistema
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                //Hago referencia a los logo
                String logoPath = servletContext.getRealPath("") + File.separator + "resources"
                        + File.separator + "images"
                        + File.separator + "muhnes1.png";
                //String logoMined = servletContext.getRealPath("") + File.separator + "resources"
                //      + File.separator + "images"
                //    + File.separator + "secultura.png";
                //Creo una tabla para poner el encabezado
                PdfPTable encabezado = new PdfPTable(3);
                //indico que el ancho de la tabla es del 100%
                encabezado.setWidthPercentage(100);
                //creo la primer celda
                PdfPCell cell1 = new PdfPCell();
                //Instancio los logos
                Image logo = Image.getInstance(logoPath);
                //Image minedLogo = Image.getInstance(logoMined);
                //Indico los tamaños de los logos
                logo.scaleToFit(80, 80);
                //minedLogo.scaleToFit(130, 130);
                //añado el primer logo a la celda
                cell1.addElement(logo);
                //indico que la celda no tenga borde
                cell1.setBorder(Rectangle.NO_BORDER);
                //añado la celda a la tabla
                encabezado.addCell(cell1);
                //indico que las siguientes celdas se alineen al centro
                encabezado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                encabezado.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                //indico que las siguientes celdas no tengan borde
                encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                //añado una nueva celda con los datos del instituto
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de El Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));
                //PdfPCell cell2 = new PdfPCell();
                //cell2.setBorder(Rectangle.NO_BORDER);
                encabezado.addCell("");
                document.add(encabezado);
                //creo una nueva celda para la otra imagen

                //Ajusto los parametros de la celda igual que la anterior
                //minedLogo.setAlignment(Element.ALIGN_RIGHT);
                //cell2.addElement(minedLogo);
                //cell2.setBorder(Rectangle.NO_BORDER);
                //encabezado.addCell(cell2);
                //document.add(encabezado);
                Paragraph titulo = new Paragraph("Reporte de Agentes", FontFactory.getFont(FontFactory.TIMES_BOLD, 11));
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
                agentes.addCell(new Phrase("Iniciales", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                agentes.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                agentes.addCell(new Phrase("Apellido", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                agentes.addCell(new Phrase("Ocupación", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                agentes.addCell(new Phrase("Institución", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                agentes.addCell(new Phrase("Perfiles", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<AgenteTb> lista;
                if (filtrado != null) {
                    lista = filtrado;
                } else {
                    lista = this.items;
                }
                List<AgentePerfilTb> lista1;
                if (perfilAgente != null) {
                    lista1 = perfil1;
                } else {
                    lista1 = this.items1;
                }
                
                for (AgenteTb l : lista) {
                    PdfPCell cell = new PdfPCell(new Phrase(l.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 10)));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell);

                    PdfPCell cell2 = new PdfPCell(new Phrase(l.getCApellido(), FontFactory.getFont(FontFactory.TIMES, 10)));
                    cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell2);

                    agentes.addCell(new Phrase(l.getCIniciales(), FontFactory.getFont(FontFactory.TIMES, 10)));

                    PdfPCell cell3 = new PdfPCell(new Phrase(l.getCOcupacion(), FontFactory.getFont(FontFactory.TIMES, 10)));
                    cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell3);

                    PdfPCell cell4 = new PdfPCell(new Phrase(l.getEIdinstitucion().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 10)));
                    cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                    agentes.addCell(cell4);

                    PdfPCell cell5 = new PdfPCell(new Phrase(l.getCTelefono(), FontFactory.getFont(FontFactory.TIMES, 10)));
                        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell5);
                    /*for (AgentePerfilTb p : lista1) {
                        PdfPCell cell5 = new PdfPCell(new Phrase(p., FontFactory.getFont(FontFactory.TIMES, 10)));
                        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
                        agentes.addCell(cell5);
                    }*/
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

}
