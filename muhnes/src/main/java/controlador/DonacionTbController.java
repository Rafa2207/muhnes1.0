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
import modelo.DonacionTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeaderVertical;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import servicio.DonacionTbFacade;

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
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.BitacoraTb;
import modelo.EjemplarDonacionTb;
import modelo.EjemplarNombrecomunTb;
import modelo.EjemplarTb;
import modelo.InstitucionTb;
import modelo.PedidoTb;
import modelo.TaxonomiaTb;
import modelo.UsuarioTb;

@Named("donacionTbController")
@ViewScoped
public class DonacionTbController implements Serializable {

    @EJB
    private servicio.DonacionTbFacade ejbFacade;
    @EJB
    private servicio.EjemplarTbFacade ejemplarFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    @EJB
    private servicio.EjemplarDonacionTbFacade ejemplarDonacionFacade;
    @EJB
    private servicio.InstitucionTbFacade institucionFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<DonacionTb> items = null, filtro;
    private DonacionTb selected;
    private String tipoTaxon;
    private TaxonomiaTb taxonomia;
    private EjemplarTb ejemplar;
    private List<EjemplarTb> listaEjemplares;
    private Integer cantidad;
    private EjemplarDonacionTb ejemplarDonacion;
    //private List<EjemplarDonacionTb> eliminados = new ArrayList<EjemplarDonacionTb>();
    private List<EjemplarDonacionTb> listaEjemplaresDonados, listaEjemplaresDonadosEntregados;
    @Inject
    LocalidadTbController localidadControl;
    @Inject
    EjemplarTbController ejemplarControl;

    public DonacionTbController() {
    }

    public List<EjemplarDonacionTb> getListaEjemplaresDonados() {
        return listaEjemplaresDonados;
    }

    public void setListaEjemplaresDonados(List<EjemplarDonacionTb> listaEjemplaresDonados) {
        this.listaEjemplaresDonados = listaEjemplaresDonados;
    }

    public EjemplarDonacionTb getEjemplarDonacion() {
        return ejemplarDonacion;
    }

    public void setEjemplarDonacion(EjemplarDonacionTb ejemplarDonacion) {
        this.ejemplarDonacion = ejemplarDonacion;
    }

    public List<EjemplarDonacionTb> getListaEjemplaresDonadosEntregados() {
        return listaEjemplaresDonadosEntregados;
    }

    public void setListaEjemplaresDonadosEntregados(List<EjemplarDonacionTb> listaEjemplaresDonadosEntregados) {
        this.listaEjemplaresDonadosEntregados = listaEjemplaresDonadosEntregados;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<EjemplarTb> getListaEjemplares() {
        return listaEjemplares;
    }

    public void setListaEjemplares(List<EjemplarTb> listaEjemplares) {
        this.listaEjemplares = listaEjemplares;
    }

    public TaxonomiaTb getTaxonomia() {
        return taxonomia;
    }

    public EjemplarTb getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarTb ejemplar) {
        this.ejemplar = ejemplar;
    }

    public void setTaxonomia(TaxonomiaTb taxonomia) {
        this.taxonomia = taxonomia;
    }

    public String getTipoTaxon() {
        return tipoTaxon;
    }

    public void setTipoTaxon(String tipoTaxon) {
        this.tipoTaxon = tipoTaxon;
    }

    public List<DonacionTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<DonacionTb> filtro) {
        this.filtro = filtro;
    }

    public DonacionTb getSelected() {
        return selected;
    }

    public void setSelected(DonacionTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DonacionTbFacade getFacade() {
        return ejbFacade;
    }

    public DonacionTb prepareCreate() {
        selected = new DonacionTb();
        // selected.setEjemplarDonacionTbList(new ArrayList<EjemplarDonacionTb>());
        initializeEmbeddableKey();
        return selected;
    }

    public DonacionTb prepareView() {
        // selected = new DonacionTb();
        // selected.setEjemplarDonacionTbList(new ArrayList<EjemplarDonacionTb>());
        listaEjemplaresDonadosEntregados = ejbFacade.listaEjemplaresEntregados(selected.getEIddonacion());
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        for (EjemplarDonacionTb i : listaEjemplaresDonados) {
            i.setEIddonacion(ejbFacade.siguienteId());
            ejemplarDonacionFacade.edit(i);
        }
        /*if (selected.getEjemplarDonacionTbList().isEmpty()) {
         JsfUtil.addErrorMessage("Debe agregar ejemplares para donaciÃ³n");
         //oncomplete = "";
         } else {
         try {
         for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
         ejemplarFacade.edit(ee.getEjemplarTb());
         }
         } catch (Exception e) {
         }*/
        //inicio bitacora
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada Donación para insitución: '" + selected.getEIdinstitucion().getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        //}
    }

    public void update() {
        /*if (selected.getEjemplarDonacionTbList().isEmpty()) {
         JsfUtil.addErrorMessage("Debe agregar ejemplares para donaciÃ³n");
         //oncomplete = "";
         } else {
         try {
         for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
         ejemplarFacade.edit(ee.getEjemplarTb());
         }
         } catch (Exception e) {
         }
         try {
         for (EjemplarDonacionTb aa : eliminados) {
         ejemplarFacade.edit(aa.getEjemplarTb());
         }
         } catch (Exception e) {
         }*/
        //inicio bitacora
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificada Donación para institución: '" + selected.getEIdinstitucion().getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbUpdated"));
        //}
    }

    public void destroy() {
        //inicio bitacora
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminada Donación para institución: '" + selected.getEIdinstitucion().getCNombre() + "' en el módulo: Ejemplar");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DonacionTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DonacionTb> getItems() {
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

    public DonacionTb getDonacionTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DonacionTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DonacionTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DonacionTb.class)
    public static class DonacionTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DonacionTbController controller = (DonacionTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "donacionTbController");
            return controller.getDonacionTb(getKey(value));
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
            if (object instanceof DonacionTb) {
                DonacionTb o = (DonacionTb) object;
                return getStringKey(o.getEIddonacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DonacionTb.class.getName()});
                return null;
            }
        }

    }

    public String calculaInsitucion(int b) {
        InstitucionTb institucion;
        String nombre;
        institucion = getFacade().BuscarInsitucion(b);
        nombre = institucion.getCNombre();
        return nombre;
    }

    public void llenarEjemplaresDonados() {
        setListaEjemplaresDonados(getFacade().listaEjemplares(selected.getEIdinstitucion()));
    }

    public void remover() {
        listaEjemplaresDonados.remove(ejemplarDonacion);
    }

    /*    public void ejemplares(TaxonomiaTb taxon) {
     listaEjemplares = getFacade().BuscarEjemplares(taxon);
     for (EjemplarDonacionTb ee : selected.getEjemplarDonacionTbList()) {
     for (EjemplarTb aa : listaEjemplares) {
     if ((ee.getEjemplarTb().getEIdejemplar()) == aa.getEIdejemplar()) {
     listaEjemplares.remove(ee.getEjemplarTb());
     }
     }
     }
     }

     public void anadir() {
     EjemplarDonacionTb nuevo = new EjemplarDonacionTb();
     ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - cantidad);
     nuevo.setEjemplarTb(ejemplar);
     nuevo.setECantidad(cantidad);
     nuevo.setDonacionTb(selected);

     EjemplarDonacionTbPK exhibicionpk = new EjemplarDonacionTbPK();

     exhibicionpk.setEIddonacion(getFacade().siguienteId());
     exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

     nuevo.setEjemplarDonacionTbPK(exhibicionpk);

     selected.getEjemplarDonacionTbList().add(nuevo);
     listaEjemplares.remove(ejemplar);

     }

     public void anadirM() {
     EjemplarDonacionTb nuevo = new EjemplarDonacionTb();
     ejemplar.setECantDuplicado(ejemplar.getECantDuplicado() - cantidad);
     nuevo.setEjemplarTb(ejemplar);
     nuevo.setECantidad(cantidad);
     nuevo.setDonacionTb(selected);

     EjemplarDonacionTbPK exhibicionpk = new EjemplarDonacionTbPK();

     exhibicionpk.setEIddonacion(selected.getEIddonacion());
     exhibicionpk.setEIdejemplar(ejemplar.getEIdejemplar());

     nuevo.setEjemplarDonacionTbPK(exhibicionpk);

     selected.getEjemplarDonacionTbList().add(nuevo);
     listaEjemplares.remove(ejemplar);

     }
    
     public void removerM() {
     ejemplarDonacion.getEjemplarTb().setECantDuplicado(ejemplarDonacion.getEjemplarTb().getECantDuplicado() + ejemplarDonacion.getECantidad());
     eliminados.add(ejemplarDonacion);
     selected.getEjemplarDonacionTbList().remove(ejemplarDonacion);
     }
     */
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

                Paragraph titulo = new Paragraph("Reporte General de Donaciones", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);
                //fecha de generacion entre los reportes

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(10);
                document.add(fecha);

                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(10);
                document.add(usuarioSis);
                
                PdfPTable donaciones = new PdfPTable(3);
                donaciones.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {40, 25, 35};
                try {
                    donaciones.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                donaciones.setWidthPercentage(80);
                donaciones.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                donaciones.addCell(new Phrase("Descrpción", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                donaciones.addCell(new Phrase("Fecha", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));
                donaciones.addCell(new Phrase("Institución", FontFactory.getFont(FontFactory.TIMES_BOLD, 11)));

                List<DonacionTb> donacionListaReporte = new ArrayList<DonacionTb>();

                donacionListaReporte = getFacade().donacionesPorFecha();

                for (DonacionTb donacion : donacionListaReporte) {

                    PdfPCell c1 = new PdfPCell(new Phrase(donacion.getMDescripcion(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    donaciones.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(donacion.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    donaciones.addCell(c2);

                    PdfPCell c3 = new PdfPCell(new Phrase(donacion.getEIdinstitucion().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 11)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    donaciones.addCell(c3);

                }
                document.add(donaciones);
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
                bitacora.setMDescripcion("Creado Reporte general de donaciones en el módulo: Ejemplares");
                //String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                //UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
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

                Paragraph titulo = new Paragraph("Reporte de Donación", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd MMMM yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(15);
                document.add(fecha);

                int columnas[] = {25, 75};

                PdfPTable TablaNombre = new PdfPTable(2);
                TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombre.setWidths(columnas);
                TablaNombre.setWidthPercentage(100);
                TablaNombre.setSpacingAfter(5);
                TablaNombre.setSpacingBefore(5);
                TablaNombre.addCell(new Phrase("Descrpción: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
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
                TablaDescripcion.addCell(new Phrase(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(selected.getFFecha()), FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaDescripcion);

                PdfPTable TablaFecha = new PdfPTable(2);
                TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaFecha.setWidths(columnas);
                TablaFecha.setWidthPercentage(100);
                TablaFecha.setSpacingAfter(5);
                TablaFecha.setSpacingBefore(5);
                TablaFecha.addCell(new Phrase("Institución: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaFecha.addCell(new Phrase(new Phrase(selected.getEIdinstitucion().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
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
                Paragraph tituloActividades = new Paragraph("Ejemplares", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloActividades.setAlignment(Element.ALIGN_CENTER);
                tituloActividades.setSpacingAfter(5);
                tituloActividades.setSpacingBefore(5);
                document.add(tituloActividades);

                if (!getListaEjemplaresDonadosEntregados().isEmpty()) {
                    //Encabezado
                    PdfPTable TablaInsumo1 = new PdfPTable(2);
                    int numero[] = {50, 50};
                    TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo1.setWidths(numero);
                    TablaInsumo1.setWidthPercentage(70);
                    TablaInsumo1.addCell(new Phrase("Taxonómia", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Ejemplar", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    //TablaInsumo1.addCell(new Phrase("Cantidad Solicitada", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    //TablaInsumo1.addCell(new Phrase("Cantidad Recibida", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    //TablaInsumo1.addCell(new Phrase("Fecha Recibido", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    document.add(TablaInsumo1);

                    for (EjemplarDonacionTb i : listaEjemplaresDonadosEntregados) {
                        PdfPTable TablaInsumo = new PdfPTable(2);
                        TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.setWidths(numero);
                        TablaInsumo.setWidthPercentage(70);

                        PdfPCell c0 = new PdfPCell(new Phrase(i.getEIdejemplar().getEIdtaxonomia().getCTipo() + ": " + i.getEIdejemplar().getEIdtaxonomia().getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaInsumo.addCell(c0);

                        PdfPCell c1 = new PdfPCell(new Phrase(i.getEIdejemplar().getCCodigoentrada(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c1);

                        /*PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getDEntrada()) + " " + i.getMaterialTb().getEIdunidad().getCAbreviatura(), FontFactory.getFont(FontFactory.TIMES, 12)));
                         c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                         TablaInsumo.addCell(c3);

                         PdfPCell c4 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(i.getFFechaRecibido()), FontFactory.getFont(FontFactory.TIMES, 12)));
                         c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                         TablaInsumo.addCell(c4);*/
                        document.add(TablaInsumo);
                    }
                } else {
                    Paragraph tituloNoActividades = new Paragraph("No se encontraron Ejemplares", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNoActividades.setAlignment(Element.ALIGN_CENTER);
                    tituloNoActividades.setSpacingAfter(5);
                    tituloNoActividades.setSpacingBefore(5);
                    document.add(tituloNoActividades);
                }
                document.newPage();
                //Etiquetas de las donaciones//
                PdfPTable etiqueta = new PdfPTable(1);
                etiqueta.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                if (!listaEjemplaresDonadosEntregados.isEmpty()) {
                    for (EjemplarDonacionTb ej : listaEjemplaresDonadosEntregados) {
                        ej.getEIdejemplar().getAgenteIdentificaEjemplarTbIDList().clear();
                        ej.getEIdejemplar().getAgenteIdentificaEjemplarTbList().clear();
                        ej.getEIdejemplar().setAgenteIdentificaEjemplarTbIDList(ejemplarFacade.ejemplarIdentificador(ej.getEIdejemplar().getEIdejemplar(), "Identificador"));
                        ej.getEIdejemplar().setAgenteIdentificaEjemplarTbList(ejemplarFacade.ejemplarRecolector(ej.getEIdejemplar().getEIdejemplar(), "Recolector"));
                        PdfPTable Ejemplar = new PdfPTable(1); 
                        /////////////////////////
                        PdfPCell titulo1 = new PdfPCell (new Paragraph("FLORA DE EL SALVADOR\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                        titulo1.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
                        titulo1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //titulo1.setSpacingBefore(5);
                        //document.add(titulo1);
                        etiqueta.addCell(titulo1);
                        ///////////
                        PdfPTable familia1 = new PdfPTable(2);
                        familia1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        familia1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        int headerwidths3[] = {60, 40};
                        try {
                            familia1.setWidths(headerwidths3);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        familia1.setWidthPercentage(100);
                        PdfPCell c1 = new PdfPCell(new Phrase(ejemplarControl.calcularFamilia(ej.getEIdejemplar().getEIdtaxonomia()).toUpperCase(), FontFactory.getFont(FontFactory.TIMES_BOLD, 8)));
                        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        c1.setBorder(Rectangle.NO_BORDER);
                        familia1.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase("Dup. = " + String.valueOf(ej.getEIdejemplar().getECantDuplicado()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        c2.setBorder(Rectangle.NO_BORDER);
                        familia1.addCell(c2);
                        etiqueta.addCell(familia1);
                            //document.add(familia1);
                        ////////////
                        PdfPTable taxonomia1 = new PdfPTable(1);
                        taxonomia1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        taxonomia1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        taxonomia1.setWidthPercentage(100);
                        PdfPCell cc1 = new PdfPCell(new Phrase(ejemplarControl.calcularTaxonomia(ej.getEIdejemplar().getEIdtaxonomia()), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 8)));
                        cc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cc1.setBorder(Rectangle.NO_BORDER);
                        taxonomia1.addCell(cc1);
                        etiqueta.addCell(taxonomia1);
                            //document.add(taxonomia1);
                        ///////////////
                        String nombres = "", coma = "";
                        if (!ej.getEIdejemplar().getAgenteIdentificaEjemplarTbIDList().isEmpty()) {
                            for (AgenteIdentificaEjemplarTb i : ej.getEIdejemplar().getAgenteIdentificaEjemplarTbIDList()) {
                                nombres = nombres + coma + i.getAgenteTb().getCIniciales();
                                coma = ", ";
                            }
                            nombres = nombres + ".";
                        } else {
                            nombres = "";
                        }
                        PdfPTable ident = new PdfPTable(1);
                        ident.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        ident.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        ident.setWidthPercentage(100);
                        PdfPCell ccc1 = new PdfPCell(new Phrase("Det. " + nombres + " " + new SimpleDateFormat("dd MMMM yyyy").format(ej.getEIdejemplar().getFFechaFinIdent()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        ccc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ccc1.setBorder(Rectangle.NO_BORDER);
                        ident.addCell(ccc1);
                        etiqueta.addCell(ident);
                            //document.add(ident);
                        /////////////////////////////////////////
                        String area = "";
                        if (ej.getEIdejemplar().getEIdlocalidad().getEIdarea() == null) {
                            area = "Sin area protegida, " + ej.getEIdejemplar().getEIdlocalidad().getEIdcanton().getCNombre() + ", " + ej.getEIdejemplar().getEIdlocalidad().getEIdcanton().getEIdmunicipio().getCNombre() + ", " + ej.getEIdejemplar().getEIdlocalidad().getEIdcanton().getEIdmunicipio().getEIddepto().getCNombre() + ".";
                        } else {
                            area = "Area Protegida: " + ej.getEIdejemplar().getEIdlocalidad().getEIdarea().getCNombre() + ", " + ej.getEIdejemplar().getEIdlocalidad().getEIdarea().getEIdmunicipio().getCNombre() + ", " + ej.getEIdejemplar().getEIdlocalidad().getEIdarea().getEIdmunicipio().getEIddepto().getCNombre() + ".";
                        }
                        PdfPTable localidad = new PdfPTable(1);
                        localidad.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        localidad.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        localidad.setWidthPercentage(100);
                        PdfPCell cccc1 = new PdfPCell(new Phrase(ej.getEIdejemplar().getEIdlocalidad().getCNombre() + ", " + ej.getEIdejemplar().getEIdlocalidad().getMDescripcion() + ". " + area, FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        cccc1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cccc1.setBorder(Rectangle.NO_BORDER);
                        localidad.addCell(cccc1);
                        etiqueta.addCell(localidad);
                            //document.add(localidad);
                        ///////////////////////////////////////
                        PdfPTable coordenadas = new PdfPTable(1);
                        coordenadas.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        coordenadas.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        coordenadas.setWidthPercentage(100);
                        PdfPCell ccor1 = new PdfPCell(new Phrase(localidadControl.latitudList(ej.getEIdejemplar().getEIdlocalidad()) + " " + localidadControl.longitudList(ej.getEIdejemplar().getEIdlocalidad()) + ". " + ej.getEIdejemplar().getEIdlocalidad().getEAltitudMax() + " m.s.n.m.\n\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        ccor1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        ccor1.setBorder(Rectangle.NO_BORDER);
                        coordenadas.addCell(ccor1);
                        etiqueta.addCell(coordenadas);
                            //document.add(coordenadas);
                        ///////////////////////////////////////
                        String nombres2 = "", coma2 = "";
                        if (!ej.getEIdejemplar().getEjemplarNombrecomunTbList().isEmpty()) {
                            for (EjemplarNombrecomunTb i : ej.getEIdejemplar().getEjemplarNombrecomunTbList()) {
                                nombres2 = nombres2 + coma2 + i.getCNombrecomun();
                                coma2 = ", ";
                            }
                            nombres2 = nombres2 + ".";
                        } else {
                            nombres2 = "";
                        }
                        PdfPTable nombresc = new PdfPTable(1);
                        nombresc.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        nombresc.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        nombresc.setWidthPercentage(100);
                        PdfPCell cnom1 = new PdfPCell(new Phrase(nombres2, FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        cnom1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cnom1.setBorder(Rectangle.NO_BORDER);
                        nombresc.addCell(cnom1);
                        etiqueta.addCell(nombresc);
                            //document.add(nombresc);
                        ///////////////////////////////////////
                        String nombres3 = "", coma3 = "";
                        if (!ej.getEIdejemplar().getEjemplarDonacionTbList().isEmpty()) {
                            for (EjemplarDonacionTb i : ej.getEIdejemplar().getEjemplarDonacionTbList()) {
                                nombres3 = nombres3 + coma3 + institucionFacade.find(i.getEIdinstitucion()).getCAcronimo();
                                coma3 = ", ";
                            }
                            nombres3 = nombres3 + ".";
                        } else {
                            nombres3 = "";
                        }
                        PdfPTable donacion = new PdfPTable(1);
                        donacion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        donacion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        donacion.setWidthPercentage(100);
                        PdfPCell cdon1 = new PdfPCell(new Phrase(nombres3, FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        cdon1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cdon1.setBorder(Rectangle.NO_BORDER);
                        donacion.addCell(cdon1);
                        etiqueta.addCell(donacion);
                            //document.add(donacion);
                        ///////////////////////////////////////
                        PdfPTable respon = new PdfPTable(2);
                        respon.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        respon.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        respon.setWidthPercentage(100);
                        PdfPCell cres1 = new PdfPCell(new Phrase(agenteFacade.agentePorId(ej.getEIdejemplar().getEResponsable()).getCIniciales() + " " + ej.getEIdejemplar().getECorrelativo(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        cres1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cres1.setBorder(Rectangle.NO_BORDER);
                        respon.addCell(cres1);
                        PdfPCell cres2 = new PdfPCell(new Phrase(new SimpleDateFormat("dd MMMM yyyy").format(ej.getEIdejemplar().getFFechaInicioIdent()), FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        cres2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cres2.setBorder(Rectangle.NO_BORDER);
                        respon.addCell(cres2);
                        etiqueta.addCell(respon);
                            //document.add(respon);
                        //////////////////////////////////
                        String nombres1 = "", coma1 = "";
                        if (!ej.getEIdejemplar().getAgenteIdentificaEjemplarTbList().isEmpty()) {
                            for (AgenteIdentificaEjemplarTb i : ej.getEIdejemplar().getAgenteIdentificaEjemplarTbList()) {
                                nombres1 = nombres1 + coma1 + i.getAgenteTb().getCIniciales();
                                coma1 = ", ";
                            }
                            nombres1 = nombres1 + ".";
                        } else {
                            nombres1 = "";
                        }
                        PdfPTable recol = new PdfPTable(1);
                        recol.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        recol.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        recol.setWidthPercentage(100);
                        PdfPCell crecol1 = new PdfPCell(new Phrase(nombres1, FontFactory.getFont(FontFactory.TIMES_ROMAN, 8)));
                        crecol1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        crecol1.setBorder(Rectangle.NO_BORDER);
                        recol.addCell(crecol1);
                        etiqueta.addCell(recol);
                            //document.add(recol);
                        ///////////////////////////////////////
                        PdfPTable info = new PdfPTable(1);
                        info.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        info.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        info.setWidthPercentage(100);
                        PdfPCell cinfo1 = new PdfPCell(new Phrase("Herbario del Museo de Historia Natural de El Salvador \n MHES", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)));
                        cinfo1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cinfo1.setBorder(Rectangle.BOTTOM);
                        info.addCell(cinfo1);
                        etiqueta.addCell(info);
                            //document.add(info);
                        //document.newPage();

                    }
                }
                document.add(etiqueta);
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
