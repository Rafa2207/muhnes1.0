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
import modelo.ActividadTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.ActividadTbFacade;

import java.io.Serializable;
import java.text.DecimalFormat;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.BitacoraTb;
import modelo.InsumoTb;
import modelo.ProrrogaProyectoTb;
import modelo.ProyectoTb;
import modelo.UsuarioTb;

@Named("actividadTbController")
@ViewScoped
public class ActividadTbController implements Serializable {

    @EJB
    private servicio.ActividadTbFacade ejbFacade;
    @EJB
    private servicio.ProrrogaProyectoTbFacade FacadeProrroga;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<ActividadTb> items = null, filtro, itemsNotificacion = null;
    private ActividadTb selected;
    private ProyectoTb proyectos;
    private Date fechatemporal, fechaActual = new Date();
    private double cantidad, costo;
    private String nombre = null, tiempo;
    private boolean tActividad = false;
    private InsumoTb insumo;
    int NumeroDeNotificaciones = 0;

    public InsumoTb getInsumo() {
        return insumo;
    }

    public void setInsumo(InsumoTb insumo) {
        this.insumo = insumo;
    }

    public boolean istActividad() {
        return tActividad;
    }

    public void settActividad(boolean tActividad) {
        this.tActividad = tActividad;
    }

    public int getNumeroDeNotificaciones() {
        return NumeroDeNotificaciones;
    }

    public void setNumeroDeNotificaciones(int NumeroDeNotificaciones) {
        this.NumeroDeNotificaciones = NumeroDeNotificaciones;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Date getFechatemporal() {
        return fechatemporal;
    }

    public void setFechatemporal(Date fechatemporal) {
        this.fechatemporal = fechatemporal;
    }

    public List<ActividadTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<ActividadTb> filtro) {
        this.filtro = filtro;
    }

    public ProyectoTb getProyectos() {
        return proyectos;
    }

    public void setProyectos(ProyectoTb proyectos) {
        this.proyectos = proyectos;
    }

    public ActividadTbController() {
    }

    public ActividadTb getSelected() {
        return selected;
    }

    public void setSelected(ActividadTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActividadTbFacade getFacade() {
        return ejbFacade;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<ActividadTb> getItemsNotificacion() {
        List<ActividadTb> quitarFinalizados = new ArrayList<ActividadTb>();
        //Notificación de 7 dias 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 7);  // numero de dias que se restan o suman
        Date fechaDeSemana = calendar.getTime(); //mandamos la fecha a una variable Date

        itemsNotificacion = getFacade().ActividadNotificaciones(fechaActual, fechaDeSemana);
        try {
            for (ActividadTb a : itemsNotificacion) {
                if (a.getEEstado() == 3 || a.getEEstado() == 0) {
                    quitarFinalizados.add(a);
                }
                if (a.getEEstado() == 1) {
                    quitarFinalizados.add(a);
                }
                if (a.getEEstado() == 2) {
                    if (a.getFFechaFinReal().after(fechaDeSemana)) {
                        quitarFinalizados.add(a);
                    } else if (a.getEIdproyecto().getEEstado() == 3) {
                        quitarFinalizados.add(a);
                    }
                }

            }
            itemsNotificacion.removeAll(quitarFinalizados);
            NumeroDeNotificaciones = itemsNotificacion.size();

        } catch (Exception e) {
        }
        return itemsNotificacion;
    }

    public void setItemsNotificacion(List<ActividadTb> itemsNotificacion) {
        this.itemsNotificacion = itemsNotificacion;
    }

    public ActividadTb prepareCreate(ProyectoTb proyecto) {
        selected = new ActividadTb();
        proyectos = proyecto;
        selected.setEIdproyecto(proyectos);
        selected.setEEstado(1);
        selected.setEEstadoPermanente(selected.getEEstado());
        selected.setDGastoAdicional(0.0);
        selected.setInsumoTbList(new ArrayList<InsumoTb>());
        tActividad = false;
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected.getInsumoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar insumos");
        } else {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Creada nueva actividad: '" + selected.getMNombre() + "' del proyecto: '"+ selected.getEIdproyecto().getMNombre() +"' en el módulo: Proyectos");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
        }
    }

    public void update() {
        if (selected.getInsumoTbList().isEmpty()) {
            JsfUtil.addErrorMessage("Debe agregar insumos");
        } else {
            selected.setFFechafin(fechatemporal);
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbUpdated"));
            
            //Bitacora inicio
            BitacoraTb bitacora = new BitacoraTb();
            bitacora.setMDescripcion("Modificada actividad: '" + selected.getMNombre() + "' del proyecto: '"+ selected.getEIdproyecto().getMNombre() +"' en el módulo: Proyectos");
            String nick = JsfUtil.getRequest().getUserPrincipal().getName();
            UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
            bitacora.setEIdusuario(usuario);
            Date fecha = new Date();
            bitacora.setTFecha(fecha);
            bitacoraFacade.create(bitacora);
            //Bitacora fin
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ActividadTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ActividadTb> getItems(ProyectoTb proyecto) { //modificando para que funcione por proyectos

        items = getFacade().buscarAsc(proyecto);

        return items;
    }

    public List<ActividadTb> getItems() { //modificando para que funcione por proyectos
        items = getFacade().findAll();

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

    public ActividadTb getActividadTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ActividadTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ActividadTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ActividadTb.class)
    public static class ActividadTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadTbController controller = (ActividadTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadTbController");
            return controller.getActividadTb(getKey(value));
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
            if (object instanceof ActividadTb) {
                ActividadTb o = (ActividadTb) object;
                return getStringKey(o.getEIdactividad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ActividadTb.class.getName()});
                return null;
            }
        }

    }

    public void prepareEdit() {
        fechatemporal = selected.getFFechafin();
    }

    public void limpiarFecha() {
        fechatemporal = null;
    }

    public void agregar() {
        FacesContext context = FacesContext.getCurrentInstance();
        InsumoTb ins = new InsumoTb();
        //presupuesto = new PresupuestoTb();
        //presupuesto.setEIdpresupuesto(getFacade().siguienteId());
        if (nombre.isEmpty() || tiempo == null || cantidad == 0.0 || costo == 0.0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Llene los campos insumo, cantidad, costo y el tipo de unidad.", "INFO"));
        } else {
            ins.setMNombre(nombre);
            ins.setDCantidad(dosDecimales(cantidad));
            ins.setDGasto(dosDecimales(costo));
            ins.setMTiempo(tiempo);
            //presupuesto.setEIdpresupuesto();
            ins.setEIdactividad(selected);
            selected.getInsumoTbList().add(ins);

            cantidad = 0.00;
            costo = 0.00;
            nombre = "";
            tiempo = "";
            tActividad = true;

        }

    }

    public Double costoTotal() {
        Double tot = 0.00;

        for (InsumoTb i : selected.getInsumoTbList()) {
            tot = tot + (i.getDCantidad() * i.getDGasto());
        }
        tot=dosDecimales(tot);
        selected.setDTotal(tot);
        return tot;
    }

    public double totalProyecto(ProyectoTb proy) {
        double totalProy = 0.00;

        for (ActividadTb act : getItems()) {
            if (act.getEIdproyecto().getEIdproyecto() == proy.getEIdproyecto()) {
                if (act.getEEstado() != 0) {
                    totalProy = totalProy + act.getDTotal() + act.getDGastoAdicional();
                }
            }
        }
        totalProy=dosDecimales(totalProy);
        return totalProy;
    }

    public void removerInsumo() {
        selected.getInsumoTbList().remove(insumo);
    }

    public Double TotalViewAct(ActividadTb a) {
        try {
            Double total = 0.0;
            total = a.getDTotal() + a.getDGastoAdicional();
            return total;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public String NombreNotificacion(String a, int n) {
        String nombre = null;
        int cadena = 0;
        try {
            cadena = a.length();
            if (cadena >= n) {
                nombre = a.substring(0, n);
                nombre = nombre + "...";
            } else {
                nombre = a.substring(0, cadena);
            }
        } catch (Exception e) {
            nombre = null;
        }
        return nombre;
    }

    public Date calcularFechaMaxima() {
        Date FechaMaxima = selected.getEIdproyecto().getFFechaFin();
        selected.getEIdproyecto().setProrrogaProyectoTbList(FacadeProrroga.buscarProrroga(selected.getEIdproyecto()));
        for (ProrrogaProyectoTb p : selected.getEIdproyecto().getProrrogaProyectoTbList()) {
            try {
                if (p.getFFechaFin().after(FechaMaxima)) {
                    FechaMaxima = p.getFFechaFin();
                }
            } catch (Exception e) {
            }
        }
        return FechaMaxima;
    }

    public String estadoNombresList(int estadoList) {
        String mensaje = null;
        if (estadoList == 0) {
            mensaje = "Cancelado";
        }
        if (estadoList == 1) {
            mensaje = "No iniciado";
        }
        if (estadoList == 2) {
            mensaje = "En proceso";
        }
        if (estadoList == 3) {
            mensaje = "Finalizado";
        }

        return mensaje;
    }

    public void desactivarActividad() {
        FacesContext context = FacesContext.getCurrentInstance();
        selected.setEEstado(0);

        getFacade().edit(selected);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad Cancelada", "INFO"));

    }

    public double totalMasGastoAd(ActividadTb a) {
        double gastoTotal = 0;
        gastoTotal = a.getDTotal() + a.getDGastoAdicional();
        return gastoTotal;
    }

    public void reporteAll(ProyectoTb proyectoReporte) {
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("REPORTE GENERAL DE ACTIVIDADES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                document.add(fecha);
                
                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                
                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() +" "+usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(10);
                document.add(usuarioSis);

                Paragraph titulo1 = new Paragraph("Proyecto: " + proyectoReporte.getMNombre(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo1.setAlignment(Element.ALIGN_CENTER);
                titulo1.setSpacingAfter(10);
                titulo1.setSpacingBefore(5);
                document.add(titulo1);

                PdfPTable actividades = new PdfPTable(5);
                actividades.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {50, 15, 15, 10, 10};
                try {
                    actividades.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                actividades.setWidthPercentage(100);
                actividades.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                actividades.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                actividades.addCell(new Phrase("Fecha Inicio", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                actividades.addCell(new Phrase("Fecha Fin", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                actividades.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                actividades.addCell(new Phrase("Presupuesto", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<ActividadTb> actividadListaReporte = new ArrayList<ActividadTb>();
                actividadListaReporte = getFacade().buscarAsc(proyectoReporte);
                double total = 0.0;
                for (ActividadTb act : actividadListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(act.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    actividades.addCell(c1);

                    PdfPCell c3 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(act.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    actividades.addCell(c3);

                    PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(act.getFFechafin()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    actividades.addCell(c4);

                    PdfPCell c5 = new PdfPCell(new Phrase(estadoNombresList(act.getEEstado()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    actividades.addCell(c5);

                    PdfPCell c6 = new PdfPCell(new Phrase("$ " + String.valueOf(act.getDTotal()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    actividades.addCell(c6);
                    total = total + act.getDTotal();

                }

                PdfPCell c1 = new PdfPCell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
                actividades.addCell(c1);

                PdfPCell c3 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 12)));
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                c3.setBorder(Rectangle.BOTTOM);
                actividades.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 12)));
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                c4.setBorder(Rectangle.BOTTOM);
                actividades.addCell(c4);

                PdfPCell c5 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 12)));
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                c5.setBorder(Rectangle.BOTTOM);
                actividades.addCell(c5);

                PdfPCell c6 = new PdfPCell(new Phrase("$ " + String.valueOf(total), FontFactory.getFont(FontFactory.TIMES, 12)));
                c6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                actividades.addCell(c6);

                document.add(actividades);

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

                Paragraph titulo = new Paragraph("REPORTE DE ACTIVIDAD", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha y hora: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                document.add(fecha);
                
                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
                
                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() +" "+usuario.getCApellido(),
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
                TablaNombre.addCell(new Phrase("Actividad: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombre.addCell(new Phrase(new Phrase(selected.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombre);

                PdfPTable TablaProyecto = new PdfPTable(2);
                TablaProyecto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaProyecto.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaProyecto.setWidths(columnas);
                TablaProyecto.setWidthPercentage(100);
                TablaProyecto.setSpacingAfter(5);
                TablaProyecto.setSpacingBefore(5);
                TablaProyecto.addCell(new Phrase("Proyecto: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaProyecto.addCell(new Phrase(new Phrase(selected.getEIdproyecto().getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaProyecto);

                PdfPTable TablaDescripcion = new PdfPTable(2);
                TablaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaDescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaDescripcion.setWidths(columnas);
                TablaDescripcion.setWidthPercentage(100);
                TablaDescripcion.setSpacingAfter(5);
                TablaDescripcion.setSpacingBefore(5);
                TablaDescripcion.addCell(new Phrase("Descripción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(selected.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(TablaDescripcion);

                PdfPTable FechaEjecucion = new PdfPTable(2);
                FechaEjecucion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                FechaEjecucion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                FechaEjecucion.setWidths(columnas);
                FechaEjecucion.setWidthPercentage(100);
                FechaEjecucion.setSpacingAfter(5);
                FechaEjecucion.setSpacingBefore(5);
                FechaEjecucion.addCell(new Phrase("Fecha duración planificada: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                FechaEjecucion.addCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(selected.getFFecha()) + " - "
                        + new SimpleDateFormat("dd/MM/yyyy").format(selected.getFFechafin()), FontFactory.getFont(FontFactory.TIMES, 12)));
                document.add(FechaEjecucion);

                if (selected.getEEstado() != 0 && selected.getEEstado() != 1) {
                    PdfPTable FechaEjecucion1 = new PdfPTable(2);
                    FechaEjecucion1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    FechaEjecucion1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    FechaEjecucion1.setWidths(columnas);
                    FechaEjecucion1.setWidthPercentage(100);
                    FechaEjecucion1.setSpacingAfter(5);
                    FechaEjecucion1.setSpacingBefore(5);
                    FechaEjecucion1.addCell(new Phrase("Fecha de ejecución: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    FechaEjecucion1.addCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(selected.getFFechaInicioReal()) + " - "
                            + new SimpleDateFormat("dd/MM/yyyy").format(selected.getFFechaFinReal()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    document.add(FechaEjecucion1);
                }

                Paragraph tituloActividades = new Paragraph("INSUMO", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloActividades.setAlignment(Element.ALIGN_CENTER);
                tituloActividades.setSpacingAfter(5);
                tituloActividades.setSpacingBefore(5);
                document.add(tituloActividades);

                //Encabezado
                PdfPTable TablaInsumo1 = new PdfPTable(4);
                int numero[] = {40, 20, 20, 20};
                TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                TablaInsumo1.setWidths(numero);
                TablaInsumo1.setWidthPercentage(80);
                TablaInsumo1.addCell(new Phrase("Insumo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaInsumo1.addCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaInsumo1.addCell(new Phrase("Costo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaInsumo1.addCell(new Phrase("Sub-Total", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                document.add(TablaInsumo1);

                for (InsumoTb i : selected.getInsumoTbList()) {

                    PdfPTable TablaInsumo = new PdfPTable(4);
                    TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo.setWidths(numero);
                    TablaInsumo.setWidthPercentage(80);

                    PdfPCell c0 = new PdfPCell(new Phrase(i.getMNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                    TablaInsumo.addCell(c0);

                    PdfPCell c1 = new PdfPCell(new Phrase(String.valueOf(i.getDCantidad()) + " " + i.getMTiempo(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase("$ " + String.valueOf(i.getDGasto()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    TablaInsumo.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase("$ " + String.valueOf(i.getDGasto() * i.getDCantidad()), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    TablaInsumo.addCell(c3);

                    document.add(TablaInsumo);
                }
                //Si hay gasto adicional
                if (selected.getMJustificacion() != null) {
                    PdfPTable TablaJustificacion = new PdfPTable(4);
                    TablaJustificacion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaJustificacion.setWidths(numero);
                    TablaJustificacion.setWidthPercentage(80);
                    TablaJustificacion.addCell(new Phrase(selected.getMJustificacion(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    TablaJustificacion.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaJustificacion.addCell(new Phrase("Gasto Adicional", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                    PdfPCell c3 = new PdfPCell(new Phrase("$ " + String.valueOf(selected.getDGastoAdicional()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    TablaJustificacion.addCell(c3);

                    document.add(TablaJustificacion);

                }
                //Totalizando
                PdfPTable TablaInsumo2 = new PdfPTable(4);
                TablaInsumo2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                TablaInsumo2.setWidths(numero);
                TablaInsumo2.setWidthPercentage(80);
                TablaInsumo2.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaInsumo2.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaInsumo2.addCell(new Phrase("Total", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                PdfPCell c3 = new PdfPCell(new Phrase("$ " + String.valueOf(selected.getDTotal() + selected.getDGastoAdicional()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                TablaInsumo2.addCell(c3);

                document.add(TablaInsumo2);

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
    
    public void compararNombreActividad(ProyectoTb proyecto,String c) {
        FacesContext context = FacesContext.getCurrentInstance();
        List<ActividadTb> actividadesLista=null;
        actividadesLista=getFacade().buscarAsc(proyecto);
        for (ActividadTb a : actividadesLista) {
            if (c.equals(a.getMNombre())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el mismo nombre de actividad en este proyecto.", "advertencia"));
                selected.setMNombre(null);
                break;
            }
        }
    }
    public double dosDecimales(double a){
            DecimalFormat df = new DecimalFormat("#.##");
            a=Double.parseDouble(df.format(a));
            return a;
    }
    
}
