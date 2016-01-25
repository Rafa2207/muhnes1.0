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
import modelo.ProyectoTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.ProyectoTbFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.ActividadTb;
import modelo.AgenteTb;
import modelo.BitacoraTb;
import modelo.ProrrogaProyectoTb;
import modelo.UsuarioTb;
import org.primefaces.context.RequestContext;
import servicio.ActividadTbFacade;
import servicio.ProrrogaProyectoTbFacade;

@Named("proyectoTbController")
@ViewScoped
public class ProyectoTbController implements Serializable {

    @EJB
    private servicio.ProyectoTbFacade ejbFacade;
    @EJB
    private servicio.ActividadTbFacade FacadeActividad;
    @EJB
    private servicio.ProrrogaProyectoTbFacade FacadeProrroga;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    @EJB
    private servicio.AgenteTbFacade FacadeAgente;
    private List<ActividadTb> ListaActividad = null;
    private List<ProyectoTb> items = null, filtro, ListaProyecto = null, itemsProyecto = null, itemsNotificacion = null, listaNotificacion = null;
    private ProyectoTb selected;
    private ProrrogaProyectoTb prorroga;
    private Date fechatemporal, fechaActual = new Date(), FechaMaxima, f1, f2;
    int NumeroDeNotificaciones = 0;
    private boolean booleanoReporte;
    String agente;
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    EntityManager em;

    public List<ProyectoTb> getItemsProyecto() {
        itemsProyecto = getFacade().ProyectoGeneral();
        return itemsProyecto;
    }

    public ActividadTbFacade getFacadeActividad() {
        return FacadeActividad;
    }

    public void setFacadeActividad(ActividadTbFacade FacadeActividad) {
        this.FacadeActividad = FacadeActividad;
    }

    public List<ActividadTb> getListaActividad() {
        ListaActividad = getFacadeActividad().findAll();
        return ListaActividad;
    }

    public void setListaActividad(List<ActividadTb> ListaActividad) {
        this.ListaActividad = ListaActividad;
    }

    public Date getFechatemporal() {
        return fechatemporal;
    }

    public void setFechatemporal(Date fechatemporal) {
        this.fechatemporal = fechatemporal;
    }

    public List<ProyectoTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ProyectoTb> filtro) {
        this.filtro = filtro;
    }

    public ProyectoTbController() {
    }

    public ProyectoTb getSelected() {
        return selected;
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

    public boolean isBooleanoReporte() {
        return booleanoReporte;
    }

    public void setBooleanoReporte(boolean booleanoReporte) {
        this.booleanoReporte = booleanoReporte;
    }

    public void setSelected(ProyectoTb selected) {
        this.selected = selected;
    }

    public ProrrogaProyectoTb getProrroga() {
        return prorroga;
    }

    public void setProrroga(ProrrogaProyectoTb prorroga) {
        this.prorroga = prorroga;
    }

    public ProrrogaProyectoTbFacade getFacadeProrroga() {
        return FacadeProrroga;
    }

    public void setFacadeProrroga(ProrrogaProyectoTbFacade FacadeProrroga) {
        this.FacadeProrroga = FacadeProrroga;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProyectoTbFacade getFacade() {
        return ejbFacade;
    }

    public ProyectoTb prepareCreate() {
        selected = new ProyectoTb();
        selected.setEEstado(0);
        initializeEmbeddableKey();
        return selected;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public int getNumeroDeNotificaciones() {
        return NumeroDeNotificaciones;
    }

    public void setNumeroDeNotificaciones(int NumeroDeNotificaciones) {
        this.NumeroDeNotificaciones = NumeroDeNotificaciones;
    }

    public void setItemsNotificacion(List<ProyectoTb> itemsNotificacion) {
        this.itemsNotificacion = itemsNotificacion;
    }

    public Date getFechaMaxima() {
        return FechaMaxima;
    }

    public void setFechaMaxima(Date FechaMaxima) {
        this.FechaMaxima = FechaMaxima;
    }

    public List<ProyectoTb> getItemsNotificacion() {
        List<ProyectoTb> quitarFinalizados = new ArrayList<ProyectoTb>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 15);  // numero de dias que se restan o suman
        Date fecha = calendar.getTime(); //mandamos la fecha a una variable Date */
        itemsNotificacion = getFacade().ProyectoNotificaciones(fechaActual, fecha);
        try {
            for (ProyectoTb p : itemsNotificacion) {
                if (p.getEEstado() == 2 || p.getEEstado() == 3) {
                    quitarFinalizados.add(p);
                } else if (p.getFFechaFin().after(fecha)) {
                    quitarFinalizados.add(p);
                }

            }

            itemsNotificacion.removeAll(quitarFinalizados);
            NumeroDeNotificaciones = itemsNotificacion.size();

        } catch (Exception e) {
        }
        return itemsNotificacion;
    }

    public void create() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creado nuevo proyecto: '" + selected.getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setFFechaFin(fechatemporal);
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado proyecto: '" + selected.getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProyectoTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProyectoTb> getItems() {
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

    public ProyectoTb getProyectoTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ProyectoTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProyectoTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProyectoTb.class)
    public static class ProyectoTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoTbController controller = (ProyectoTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoTbController");
            return controller.getProyectoTb(getKey(value));
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
            if (object instanceof ProyectoTb) {
                ProyectoTb o = (ProyectoTb) object;
                return getStringKey(o.getEIdproyecto());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProyectoTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit() {
        fechatemporal = selected.getFFechaFin();

    }

    public void limpiarFecha() {
        fechatemporal = null;
    }

    public String calculaAgente(List<AgenteTb> a, int b) {
        for (AgenteTb agen : a) {
            if (agen.getEIdagente() == b) {
                agente = agen.getCNombre() + " " + agen.getCApellido();
            }
        }
        return agente;
    }

    public String calculaAgenteReporte(int b) {
        AgenteTb agen;
        agen = FacadeAgente.agentePorId(b);
        agente = agen.getCNombre() + " " + agen.getCApellido();
        return agente;
    }

    public double presupuesto(ProyectoTb proy) {
        double presupuesto = 0;
        for (ActividadTb a : getListaActividad()) {
            if (a.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                if (a.getDGastoAdicional() == null) {
                    a.setDGastoAdicional(0.0);
                }
                if (a.getEEstado() != 0) {
                    presupuesto = presupuesto + a.getDTotal() + a.getDGastoAdicional();
                }
            }
        }
        return presupuesto;
    }

    public int progresoProyecto(ProyectoTb proy) {
        int TotalActividades = 0, TotalEntero = 0, proyecto = proy.getEIdproyecto(), i = 0;
        double cadaActividad = 0, total = 0;
        ListaProyecto = getFacade().ProyectoGeneral();
        ListaActividad = getFacadeActividad().buscarAsc(proy);

        for (ActividadTb a : ListaActividad) {
            if (a.getEEstado() == 0) {
            } else {
                TotalActividades++;
            }

        }
        try {
            cadaActividad = 100 / (float) TotalActividades;
        } catch (Exception e) {
            cadaActividad = 0;
        }
        for (ActividadTb a : ListaActividad) {
            if (a.getEEstado() == 1) {
                total = total + (cadaActividad * 0);
            }
            if (a.getEEstado() == 2) {
                total = total + (cadaActividad / 2);
            }
            if (a.getEEstado() == 3) {
                total = total + (cadaActividad);
                i++;
            }
        }
        TotalEntero = (int) total;
        //probando..
        if (i == TotalActividades && i != 0) {
            TotalEntero = 100;
        }
        return TotalEntero;
    }

    public String EstadoProyecto(ProyectoTb proyecto) {
        String estado = null;
        if (proyecto.getEEstado() == 0) {
            estado = "No iniciado";
        }
        if (proyecto.getEEstado() == 1) {
            estado = "En Proceso";
        }
        if (proyecto.getEEstado() == 2) {
            estado = "Finalizado";
        }
        if (proyecto.getEEstado() == 3) {
            estado = "Cancelado";
        }
        return estado;

    }

    public void prepareFinalizar() {
        prorroga = new ProrrogaProyectoTb();
        //Añadiendo
        prorroga.setEIdproyecto(selected);
        prorroga.setENumprorroga(1);
        selected.getProrrogaProyectoTbList().clear();
        selected.setProrrogaProyectoTbList(getFacadeProrroga().buscarProrroga(selected));
        FechaMaxima = selected.getFFechaInicio();
        for (ProrrogaProyectoTb p : selected.getProrrogaProyectoTbList()) {
            try {
                if (p.getFFechaFin().after(FechaMaxima)) {
                    FechaMaxima = p.getFFechaFin();
                }
            } catch (Exception e) {
            }
        }
        initializeEmbeddableKey();
    }

    public void prepareActivar() {
        int i = 0;
        prorroga = new ProrrogaProyectoTb();
        selected.getProrrogaProyectoTbList().clear();
        selected.setProrrogaProyectoTbList(getFacadeProrroga().buscarProrroga(selected));
        for (ProrrogaProyectoTb proProy : selected.getProrrogaProyectoTbList()) {
            if (proProy.getENumprorroga() > i) {
                i = proProy.getENumprorroga();
                prorroga = proProy;
            }
        }
    }

    public void finalizarProyecto() {
        int i = 1;
        FacesContext context = FacesContext.getCurrentInstance();
        selected.getActividadTbList().clear();
        selected.setActividadTbList(getFacadeActividad().buscarAsc(selected));

        for (ProrrogaProyectoTb p : selected.getProrrogaProyectoTbList()) {
            i++;
            prorroga.setENumprorroga(i);
        }
        prorroga.setMJustificacion(selected.getMInformacionAdicional());
        selected.setEEstado(3);

        getFacadeProrroga().create(prorroga);
        getFacade().edit(selected);
        for (ActividadTb ac : selected.getActividadTbList()) {
            ac.setEEstado(0);
            getFacadeActividad().edit(ac);
        }
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Desactivado proyecto: '" + selected.getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proyecto Cancelado", "INFO"));
    }

    public void activarProyecto() {
        FacesContext context = FacesContext.getCurrentInstance();

        selected.getActividadTbList().clear();
        selected.setActividadTbList(getFacadeActividad().buscarAsc(selected));

        selected.setEEstado(1);
        getFacadeProrroga().edit(prorroga);
        getFacade().edit(selected);
        for (ActividadTb ac : selected.getActividadTbList()) {
            ac.setEEstado(ac.getEEstadoPermanente());
            getFacadeActividad().edit(ac);
        }
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Reactivado proyecto: '" + selected.getMNombre() + "' en el módulo: Proyectos");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proyecto Activado", "INFO"));
    }

    public String NombreNotificacion(String p, int n) {
        String nombre = null;
        int cadena = 0;
        try {
            cadena = p.length();
            if (cadena >= n) {
                nombre = p.substring(0, n);
                nombre = nombre + "...";
            } else {
                nombre = p.substring(0, cadena);
            }
        } catch (Exception e) {
            nombre = null;
        }
        return nombre;
    }

    public void prepareReporte() {
        booleanoReporte = true;
        f1 = null;
        f2 = null;
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

                Paragraph titulo = new Paragraph("Reporte General de Proyectos", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                
                titulo.setSpacingBefore(5);
                document.add(titulo);

                if (booleanoReporte == false) {
                    Paragraph titulo2 = new Paragraph(new SimpleDateFormat("dd MMMM yyyy").format(f1) + " - " + new SimpleDateFormat("dd MMMM yyyy").format(f2), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                } 

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                PdfPTable proyectos = new PdfPTable(6);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {35, 15, 15, 15, 10, 10};
                try {
                    proyectos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                proyectos.setWidthPercentage(100);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                proyectos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Responsable", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Fecha Inicio", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Fecha Fin", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Presupuesto", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<ProyectoTb> proyectoListaReporte = new ArrayList<ProyectoTb>();

                if (booleanoReporte == true) {
                    proyectoListaReporte = getFacade().ProyectoGeneral();
                } else {
                    proyectoListaReporte = getFacade().ProyectoReportesEntreFechas(f1, f2);
                }

                for (ProyectoTb proy : proyectoListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(proy.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    proyectos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(calculaAgenteReporte(proy.getEResponsable()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proy.getFFechaInicio()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(proy.getFFechaFin()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(EstadoProyecto(proy), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase("$ " + String.valueOf(presupuesto(proy)), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    proyectos.addCell(c6);

                }
                document.add(proyectos);
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
