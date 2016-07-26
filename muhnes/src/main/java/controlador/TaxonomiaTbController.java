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
import modelo.TaxonomiaTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.TableHeader;
import controlador.util.TableHeaderVertical;
import controlador.util.UtilPath;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import servicio.TaxonomiaTbFacade;

import java.io.Serializable;
import java.lang.reflect.Array;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.AgenteTaxonomiaTb;
import modelo.AgenteTaxonomiaTbPK;
import modelo.AgenteTb;
import modelo.BitacoraTb;
import modelo.ImagenTb;
import modelo.NombrecomunTb;
import modelo.PaisTb;
import modelo.ProyectoTb;
import modelo.UsuarioTb;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("taxonomiaTbController")
@ViewScoped
public class TaxonomiaTbController implements Serializable {

    @EJB
    private servicio.TaxonomiaTbFacade ejbFacade;
    @EJB
    private servicio.AgenteTbFacade agenteFacade;
    @EJB
    private servicio.PaisTbFacade paisFacade;
    @EJB
    private servicio.UsuarioTbFacade usuarioFacade;
    @EJB
    private servicio.BitacoraTbFacade bitacoraFacade;
    private List<TaxonomiaTb> items = null, items2 = null, items3 = null, filtro, itemsTaxonomia = null;
    private TaxonomiaTb selected;
    private boolean autor;
    private String conector, nombreComun, idioma, autores;
    private AgenteTb agente;
    private AgenteTaxonomiaTb agenteAutor;
    private NombrecomunTb nc;
    private List<AgenteTb> listaAutores;
    private List<PaisTb> listaIdiomas;
    private List<String> idiomas;
    private Part photo;
    private ImagenTb img;
    private Integer columnas, modificar;
    @Inject
    EjemplarTbController ejemplarControl;
    //int matriz[] = new int[];

    public TaxonomiaTbController() {
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }

    public ImagenTb getImg() {
        return img;
    }

    public void setImg(ImagenTb img) {
        this.img = img;
    }

    public Part getPhoto() {
        return photo;
    }

    public void setPhoto(Part photo) {
        this.photo = photo;
    }

    public List<TaxonomiaTb> getItemsTaxonomia() {
        return itemsTaxonomia;
    }

    public void setItemsTaxonomia(List<TaxonomiaTb> itemsTaxonomia) {
        this.itemsTaxonomia = itemsTaxonomia;
    }

    public String getConector() {
        return conector;
    }

    public void setConector(String conector) {
        this.conector = conector;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public AgenteTb getAgente() {
        return agente;
    }

    public void setAgente(AgenteTb agente) {
        this.agente = agente;
    }

    public AgenteTaxonomiaTb getAgenteAutor() {
        return agenteAutor;
    }

    public void setAgenteAutor(AgenteTaxonomiaTb agenteAutor) {
        this.agenteAutor = agenteAutor;
    }

    public NombrecomunTb getNc() {
        return nc;
    }

    public void setNc(NombrecomunTb nc) {
        this.nc = nc;
    }

    public List<AgenteTb> getListaAutores() {
        return listaAutores;
    }

    public void setListaAutores(List<AgenteTb> listaAutores) {
        this.listaAutores = listaAutores;
    }

    public List<PaisTb> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaIdiomas(List<PaisTb> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<TaxonomiaTb> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<TaxonomiaTb> filtro) {
        this.filtro = filtro;
    }

    public TaxonomiaTb getSelected() {
        return selected;
    }

    public void setSelected(TaxonomiaTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxonomiaTbFacade getFacade() {
        return ejbFacade;
    }

    public TaxonomiaTb prepareCreate() {
        selected = new TaxonomiaTb();
        selected.setCTipo("Familia");
        initializeEmbeddableKey();
        return selected;
    }

    public TaxonomiaTb prepareCreate(TaxonomiaTb taxonomia) {
        selected = new TaxonomiaTb();
        initializeEmbeddableKey();
        selected.setEIdniveltaxonomia(taxonomia);
        selected.setAgenteTaxonomiaTbList(new ArrayList<AgenteTaxonomiaTb>());
        selected.setNombrecomunTbList(new ArrayList<NombrecomunTb>());
        selected.setImagenTbList(new ArrayList<ImagenTb>());
        listaAutores = agenteFacade.agentesAutores();
        listaIdiomas = paisFacade.idiomas();
        modificar = 0;
        autores = "";
        return selected;
    }

    public TaxonomiaTb prepareEdit(TaxonomiaTb especie) {
        String cadena1 = "", cadena2 = "";
        listaAutores = agenteFacade.agentesAutores();
        for (AgenteTaxonomiaTb i : getFacade().buscarEspecieSecuencia(especie.getEIdtaxonomia())) {
            listaAutores.remove(i.getAgenteTb());
            if (i.getBAutorbasionimio() == true) {
                cadena1 = cadena1 + i.getAgenteTb().getCIniciales() + " " + i.getCConector() + " ";
            }
            if (i.getBAutorbasionimio() == false) {
                cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
            }
        }
        selected.getAgenteTaxonomiaTbList().clear();
        selected.setAgenteTaxonomiaTbList(getFacade().buscarEspecieSecuencia(especie.getEIdtaxonomia()));
        autores = "(" + cadena1 + ") " + cadena2;
        listaIdiomas = paisFacade.idiomas();
        modificar = 1;
        initializeEmbeddableKey();
        return selected;
    }

    public void createFamilia() {
        selected.setERango(1);
        selected.setCTipo("Familia");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FamiliaTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createGenero(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(2);
        selected.setCTipo("Genero");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creado " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createEspecie(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(3);
        selected.setCTipo("Especie");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EspecieTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createSubEspecie(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(4);
        selected.setCTipo("Subespecie");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SubespecieTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createVariedad(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(4);
        selected.setCTipo("Variedad");
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Creada " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VariedadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Modificado " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbUpdated"));
    }

    public void destroy() {
        //Bitacora inicio
        BitacoraTb bitacora = new BitacoraTb();
        bitacora.setMDescripcion("Eliminado " + selected.getCTipo() + "  '" + selected.getCNombre() + "' en el módulo: Información Taxonómica");
        String nick = JsfUtil.getRequest().getUserPrincipal().getName();
        UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);
        bitacora.setEIdusuario(usuario);
        Date fecha = new Date();
        bitacora.setTFecha(fecha);
        bitacoraFacade.create(bitacora);
        //Bitacora fin
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleTaxonomia").getString("TaxonomiaTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TaxonomiaTb> taxonomia(String tipo) {

        items = getFacade().buscarTaxonomiaAsc(tipo);

        return items;
    }

    public List<TaxonomiaTb> getItemsFamilia() {
        if (items == null) {
            items = getFacade().buscarFamiliaAsc();
        }
        return items;
    }

    public List<TaxonomiaTb> getItemsGenero(Integer id) {
        //if (items2 == null) {
        try {
            items2 = getFacade().buscarGeneroAsc(id);
        } catch (Exception e) {
        }
        //}
        return items2;
    }

    public List<TaxonomiaTb> getItemsEspecie(Integer especie) {
        //if (items3 == null) {
        try {
            items3 = getFacade().buscarEspecieAsc(especie);
        } catch (Exception e) {
        }

        //}
        return items3;
    }

    public List<TaxonomiaTb> getItemsSubespecie(Integer subespecie) {
        try {
            items3 = getFacade().buscarSubEspecieAsc(subespecie);
        } catch (Exception e) {
        }
        return items3;
    }

    public List<TaxonomiaTb> getItemsVariedad(Integer variedad) {
        try {
            items3 = getFacade().buscarVariedadAsc(variedad);
        } catch (Exception e) {
        }
        return items3;
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

    public TaxonomiaTb getTaxonomiaTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TaxonomiaTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TaxonomiaTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TaxonomiaTb.class)
    public static class TaxonomiaTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxonomiaTbController controller = (TaxonomiaTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxonomiaTbController");
            return controller.getTaxonomiaTb(getKey(value));
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
            if (object instanceof TaxonomiaTb) {
                TaxonomiaTb o = (TaxonomiaTb) object;
                return getStringKey(o.getEIdtaxonomia());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxonomiaTb.class.getName()});
                return null;
            }
        }

    }

    /*public List<TaxonomiaTb> rangos(TaxonomiaTb tx) {
     List<TaxonomiaTb> lista;
     // int id;
     // if(tx.getERango()==2){
     lista = getFacade().buscarGeneroAsc(tx.getEIdtaxonomia());
     return lista;
     /*}
     if(tx.getERango()==3){
     id=tx.getEIdtaxonomia();
     return getFacade().buscarEspecieAsc(id);
     }
     else {
     return null;
     }
        
     }*/
    public String autorBasionimio(boolean ab) {
        String cadena;
        if (ab == true) {
            cadena = "Si";
        } else {
            cadena = "No";
        }
        return cadena;
    }

    public void anadirAutor() {
        int sec = 0;
        AgenteTaxonomiaTb nuevo = new AgenteTaxonomiaTb();
        nuevo.setAgenteTb(agente);
        nuevo.setBAutorbasionimio(autor);
        nuevo.setCConector(conector);
        nuevo.setTaxonomiaTb(selected);
        for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
            sec = sec + 1;
        }
        nuevo.setESecuecia(sec + 1);

        AgenteTaxonomiaTbPK agenteTaxonomiapk = new AgenteTaxonomiaTbPK();

        agenteTaxonomiapk.setEIdagente(agente.getEIdagente());
        agenteTaxonomiapk.setEIdtaxonomia(getFacade().siguienteId());

        nuevo.setAgenteTaxonomiaTbPK(agenteTaxonomiapk);

        selected.getAgenteTaxonomiaTbList().add(nuevo);

        listaAutores.remove(agente);
        autor = false;
        conector = "";
        //llenar el acronimo de los autores taxonomicos.
        String cadena1 = "", cadena2 = "";
        for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
            if (i.getBAutorbasionimio() == true) {
                cadena1 = cadena1 + i.getAgenteTb().getCIniciales() + " " + i.getCConector() + " ";
            }
            if (i.getBAutorbasionimio() == false) {
                cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
            }
        }
        if (cadena2.equals("")) {
            autores = cadena1;
        } else {
            autores = "(" + cadena1 + ") " + cadena2;
        }
    }

    public void anadirAutorM() {
        int sec = 0;
        AgenteTaxonomiaTb nuevo = new AgenteTaxonomiaTb();
        nuevo.setAgenteTb(agente);
        nuevo.setBAutorbasionimio(autor);
        nuevo.setCConector(conector);
        nuevo.setTaxonomiaTb(selected);
        for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
            sec = sec + 1;
        }
        nuevo.setESecuecia(sec + 1);

        AgenteTaxonomiaTbPK agenteTaxonomiapk = new AgenteTaxonomiaTbPK();

        agenteTaxonomiapk.setEIdagente(agente.getEIdagente());
        agenteTaxonomiapk.setEIdtaxonomia(selected.getEIdtaxonomia());

        nuevo.setAgenteTaxonomiaTbPK(agenteTaxonomiapk);

        selected.getAgenteTaxonomiaTbList().add(nuevo);

        listaAutores.remove(agente);
        autor = false;
        conector = "";
        //llenar el acronimo de los autores taxonomicos.
        /*String cadena1="", cadena2="";
         for(AgenteEspecieTb i: selected.getAgenteEspecieTbList()){
         if(i.getBAutorBasionimio()==true){
         cadena1 = cadena1 + i.getAgenteTb().getCIniciales() + " " + i.getCConector() +" ";
         }
         if(i.getBAutorBasionimio()==false){
         cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
         }
         }
         autores = "("+ cadena1 +") " + cadena2;*/
    }

    public void removerAutor() {
        int sec = 1;
        selected.getAgenteTaxonomiaTbList().remove(agenteAutor);
        listaAutores.add(agenteAutor.getAgenteTb());
        for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
            i.setESecuecia(sec);
            sec++;
        }
    }

    public void anadirNombreComun() {
        NombrecomunTb nuevo = new NombrecomunTb();
        if (!nombreComun.equals("") && !idioma.equals("")) {
            nuevo.setCNombre(nombreComun);
            nuevo.setCIdioma(idioma);
            nuevo.setEIdtaxonomia(selected);
            selected.getNombrecomunTbList().add(nuevo);
            nombreComun = "";
            idioma = "";
        } else {
            FacesMessage error = new FacesMessage("Error al agregar nombre común");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        //ruta para debian
        //ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String basePath = ctx.getRealPath("/");
        //String txtPath = basePath + "images";
        //nueva ruta para servidor
        if (selected.getImagenTbList().size() >= 3) {
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lacantidad máxima de imágenes es 3", "ERROR");
            FacesContext.getCurrentInstance().addMessage(null, error);
        } else {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String picture_directory = ctx.getExternalContext().getInitParameter("pictures_directory_path");
            //******************************************
            UploadedFile file = event.getFile();
            String ruta = "";// = File.separator + "images" + File.separator + file.getFileName();
            //If directory exists ? do nothing : make directory
            if (modificar == 1) {
                ruta = picture_directory + selected.getEIdtaxonomia();
                File storage_folder = new File(ruta);
                if (!storage_folder.exists()) {
                    storage_folder.mkdir();
                }
            } else {
                ruta = picture_directory + getFacade().siguienteId();
                File storage_folder = new File(ruta);
                if (!storage_folder.exists()) {
                    storage_folder.mkdir();
                }
            }
            String pathDefinition = ruta + File.separator + file.getFileName();
            System.out.println("" + pathDefinition);
            try {
                FileInputStream in = (FileInputStream) file.getInputstream();
                FileOutputStream out = new FileOutputStream(pathDefinition);

                byte[] buffer = new byte[(int) file.getSize()];
                int contador = 0;

                while ((contador = in.read(buffer)) != -1) {
                    out.write(buffer, 0, contador);
                }
                in.close();
                out.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();
                FacesMessage error = new FacesMessage("No se puede subir la imagen");
                FacesContext.getCurrentInstance().addMessage(null, error);

            }
            ImagenTb nuevo = new ImagenTb();
            nuevo.setCNombre(file.getFileName());
            nuevo.setCRuta(pathDefinition);
            nuevo.setEIdtaxonomia(selected);
            selected.getImagenTbList().add(nuevo);
            FacesMessage exito = new FacesMessage("imagen subida correctamente");
            FacesContext.getCurrentInstance().addMessage(null, exito);
        }
    }

    public void borrarImagen() {
        //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        //String realPath = UtilPath.getPathDefinida(ec.getRealPath("/"));
        //////////////////////////////////
        FacesContext ctx = FacesContext.getCurrentInstance();
        // String txtPath = ctx.getExternalContext().getInitParameter("pictures_directory_path");
        String pathDefinition = ctx.getExternalContext().getInitParameter("pictures_directory_path") + img.getEIdtaxonomia() + "/" + img.getCNombre();
        try {
            File imagen = new File(img.getCRuta());
            if (imagen.exists()) {
                imagen.delete();
                FacesMessage exito = new FacesMessage("imagen eliminada correctamente");
                FacesContext.getCurrentInstance().addMessage(null, exito);
                selected.getImagenTbList().remove(img);
            }
        } catch (Exception e) {
            FacesMessage error = new FacesMessage("No se puede eliminar la imagen");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    public void removerNombreComun() {
        selected.getNombrecomunTbList().remove(nc);
    }

    public String autoresBList(TaxonomiaTb List) {
        String cadena1 = "", cadena2 = "";
        for (AgenteTaxonomiaTb i : getFacade().buscarEspecieSecuencia(List.getEIdtaxonomia())) {
            if (i.getBAutorbasionimio() == true) {
                cadena1 = cadena1 + " " + i.getAgenteTb().getCIniciales() + " " + i.getCConector();
            }
            if (i.getBAutorbasionimio() == false) {
                cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
            }
        }
        //autores = "("+ cadena1 +") " + cadena2;
        if (cadena2.equals("")) {
            return cadena1;
        } else if (cadena1.equals("")) {
            return cadena2;
        } else {
            return "(" + cadena1 + ") " + cadena2;
        }
    }

    public List<AgenteTaxonomiaTb> ordenarSecuencia(TaxonomiaTb List) {
        try {
            return getFacade().buscarEspecieSecuencia(List.getEIdtaxonomia());
        } catch (Exception e) {
            return null;
        }

    }

    public List<AgenteTaxonomiaTb> Secuencia(TaxonomiaTb List) {
        return getFacade().buscarEspecieSecuencia(List.getEIdtaxonomia());

    }

    public String cadena() {
        try {
            String cadena1 = "", cadena2 = "";
            for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
                if (i.getBAutorbasionimio() == true) {
                    cadena1 = cadena1 + i.getAgenteTb().getCIniciales() + " " + i.getCConector() + " ";
                }
                if (i.getBAutorbasionimio() == false) {
                    cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
                }
            }
            if (cadena2.equals("")) {
                autores = cadena1;
            } else if (cadena1.equals("")) {
                autores = cadena2;
            } else {
                autores = "(" + cadena1 + ") " + cadena2;
            }
        } catch (Exception e) {
            return null;
        }
        return autores;
    }

    public void pasarMinusculas() {
        selected.setCNombre(selected.getCNombre().toLowerCase());
    }

    //************************************************REPORTES****************************************************//
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte General de Información Taxonómica", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);

                Paragraph titulo2 = new Paragraph(new Phrase("Reporte de Familias", FontFactory.getFont(FontFactory.TIMES_BOLD, 13)));
                titulo2.setAlignment(Element.ALIGN_CENTER);
                titulo2.setSpacingAfter(5);
                titulo2.setSpacingBefore(2);
                document.add(titulo2);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
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

                PdfPTable proyectos = new PdfPTable(2);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                int headerwidths[] = {10, 40};
                try {
                    proyectos.setWidths(headerwidths);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                proyectos.setWidthPercentage(60);
                proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                proyectos.addCell(new Phrase("Correlativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                proyectos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                //proyectos.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                //proyectos.addCell(new Phrase("Autores", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

                List<TaxonomiaTb> taxonomiaListaReporte = new ArrayList<TaxonomiaTb>();

                taxonomiaListaReporte = getFacade().buscarFamiliaAsc();
                int i = 0;
                for (TaxonomiaTb taxon : taxonomiaListaReporte) {
                    i++;
                    PdfPCell c1 = new PdfPCell(new Phrase("" + i + "", FontFactory.getFont(FontFactory.TIMES, 12)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c1);

                    PdfPCell c2 = new PdfPCell(new Phrase(taxon.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(c2);

                    /*PdfPCell c3 = new PdfPCell(new Phrase(taxon.getCEstado(), FontFactory.getFont(FontFactory.TIMES, 12)));
                     c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                     proyectos.addCell(c3);

                     PdfPCell c4 = new PdfPCell(new Phrase(taxon.getCEstado(), FontFactory.getFont(FontFactory.TIMES, 12)));
                     c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                     proyectos.addCell(c4);*/
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
                //Bitacora inicio
                BitacoraTb bitacora = new BitacoraTb();
                bitacora.setMDescripcion("Creado Reporte general de familias en el módulo: Información Taxonómica");
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

    public void reporteAll(TaxonomiaTb tax, int n) {
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte General de Información Taxonómica", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);

                titulo.setSpacingBefore(5);
                document.add(titulo);

                if (tax.getCTipo().equals("Familia")) {
                    Paragraph titulo2 = new Paragraph(new Phrase("Reporte de Género \n Familia: " + tax.getCNombre() + "", FontFactory.getFont(FontFactory.TIMES_BOLD, 13)));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                } else if (tax.getCTipo().equals("Genero")) {
                    Paragraph titulo2 = new Paragraph(new Phrase("Reporte de Especies \n Género: " + tax.getCNombre() + "", FontFactory.getFont(FontFactory.TIMES_BOLD, 13)));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                } else if (tax.getCTipo().equals("Especie") && n == 1) {
                    Paragraph titulo2 = new Paragraph(new Phrase("Reporte de Subespecies \n Especie: " + tax.getCNombre() + "", FontFactory.getFont(FontFactory.TIMES_BOLD, 13)));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                } else if (tax.getCTipo().equals("Especie") && n == 2) {
                    Paragraph titulo2 = new Paragraph(new Phrase("Reporte de Variedades \n Especie: " + tax.getCNombre() + "", FontFactory.getFont(FontFactory.TIMES_BOLD, 13)));
                    titulo2.setAlignment(Element.ALIGN_CENTER);
                    titulo2.setSpacingAfter(5);
                    titulo2.setSpacingBefore(2);
                    document.add(titulo2);
                }

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()), FontFactory.getFont(FontFactory.TIMES, 10));
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

                if (tax.getCTipo().equals("Familia")) {
                    int headerwidths[] = {10, 40};
                    columnas = 2;
                } else {
                    columnas = 4;
                    int headerwidths[] = {10, 30, 30, 30};
                }

                List<TaxonomiaTb> taxonomiaListaReporte = new ArrayList<TaxonomiaTb>();
                if (tax.getCTipo().equals("Familia")) {
                    taxonomiaListaReporte = getFacade().buscarGeneroAsc(tax.getEIdtaxonomia());

                } else if (tax.getCTipo().equals("Genero")) {
                    taxonomiaListaReporte = getFacade().buscarEspecieAsc(tax.getEIdtaxonomia());

                } else if (tax.getCTipo().equals("Especie") && n == 1) {
                    taxonomiaListaReporte = getFacade().buscarSubEspecieAsc(tax.getEIdtaxonomia());

                } else if (tax.getCTipo().equals("Especie") && n == 2) {
                    taxonomiaListaReporte = getFacade().buscarVariedadAsc(tax.getEIdtaxonomia());

                }

                if (!taxonomiaListaReporte.isEmpty()) {
                    PdfPTable proyectos = new PdfPTable(columnas);
                    proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                    try {
                        if (tax.getCTipo().equals("Familia")) {
                            int headerwidths[] = {10, 40};
                            proyectos.setWidths(headerwidths);
                        } else {
                            int headerwidths[] = {15, 30, 20, 35};
                            proyectos.setWidths(headerwidths);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (tax.getCTipo().equals("Familia")) {
                        proyectos.setWidthPercentage(60);
                    } else {
                        proyectos.setWidthPercentage(90);
                    }
                    proyectos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    proyectos.addCell(new Phrase("Correlativo", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    proyectos.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    if (!tax.getCTipo().equals("Familia")) {
                        proyectos.addCell(new Phrase("Estado", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    }
                    if (!tax.getCTipo().equals("Familia")) {
                        proyectos.addCell(new Phrase("Autores", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    }

                    int i = 0;
                    for (TaxonomiaTb taxon : taxonomiaListaReporte) {
                        i++;
                        PdfPCell c1 = new PdfPCell(new Phrase("" + i + "", FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        proyectos.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(taxon.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        proyectos.addCell(c2);

                        if (!tax.getCTipo().equals("Familia")) {
                            PdfPCell c3 = new PdfPCell(new Phrase(taxon.getCEstado(), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            proyectos.addCell(c3);
                        }

                        if (!tax.getCTipo().equals("Familia")) {
                            PdfPCell c4 = new PdfPCell(new Phrase(autoresBList(taxon), FontFactory.getFont(FontFactory.TIMES, 12)));
                            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            proyectos.addCell(c4);
                        }
                    }
                    document.add(proyectos);
                } else {
                    Paragraph noInfo = new Paragraph("No se encontro información taxonómica", FontFactory.getFont(FontFactory.TIMES, 12));
                    noInfo.setAlignment(Element.ALIGN_CENTER);
                    noInfo.setSpacingAfter(5);
                    noInfo.setSpacingBefore(5);
                    document.add(noInfo);
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
                bitacora.setMDescripcion("Creado reporte general de " + tax.getCTipo() + " en el módulo: Información Taxonómica");
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
                encabezado.addCell(new Paragraph("\n Museo de Historia Natural de \nEl Salvador" + "\n \n Plantas de El Salvador", FontFactory.getFont(FontFactory.TIMES_BOLD, 14)));

                encabezado.addCell("");
                document.add(encabezado);

                Paragraph titulo = new Paragraph("Reporte de " + selected.getCTipo(), FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(5);
                titulo.setSpacingBefore(10);
                document.add(titulo);

                Paragraph fecha = new Paragraph("Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                fecha.setAlignment(Element.ALIGN_CENTER);
                fecha.setSpacingAfter(15);
                document.add(fecha);

                String nick = JsfUtil.getRequest().getUserPrincipal().getName();
                UsuarioTb usuario = usuarioFacade.BuscarUsuario(nick);

                Paragraph usuarioSis = new Paragraph("Generado por: " + usuario.getCNombre() + " " + usuario.getCApellido(),
                        FontFactory.getFont(FontFactory.TIMES, 10));
                usuarioSis.setAlignment(Element.ALIGN_CENTER);
                usuarioSis.setSpacingAfter(10);
                document.add(usuarioSis);

                int columnas[] = {30, 70};
                //tabla para espacio entre las imagenes
                /*PdfPTable tablaEspacio = new PdfPTable(1);
                 PdfPCell celda = new PdfPCell();
                 celda.addElement(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 20)));
                 celda.setBorder(Rectangle.NO_BORDER);
                 tablaEspacio.addCell(celda);
                 document.add(tablaEspacio);*/

                /*PdfPTable TablaNombre = new PdfPTable(2);
                 TablaNombre.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                 TablaNombre.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                 TablaNombre.setWidths(columnas);
                 TablaNombre.setWidthPercentage(100);
                 TablaNombre.setSpacingAfter(5);
                 TablaNombre.setSpacingBefore(5);
                 TablaNombre.addCell(new Phrase("Nombre: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                 TablaNombre.addCell(new Phrase(new Phrase(selected.getCNombre(), FontFactory.getFont(FontFactory.TIMES, 12))));
                 document.add(TablaNombre);*/
                PdfPTable TablaDescripcion = new PdfPTable(2);
                TablaDescripcion.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaDescripcion.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaDescripcion.setWidths(columnas);
                TablaDescripcion.setWidthPercentage(100);
                TablaDescripcion.setSpacingAfter(5);
                TablaDescripcion.setSpacingBefore(5);
                TablaDescripcion.addCell(new Phrase("Información taxonómica: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaDescripcion.addCell(new Phrase(new Phrase(ejemplarControl.calcularTaxonomia(selected) + " (" + autoresBList(selected) + ")", FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaDescripcion);

                /*PdfPTable TablaFecha = new PdfPTable(2);
                 TablaFecha.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                 TablaFecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                 TablaFecha.setWidths(columnas);
                 TablaFecha.setWidthPercentage(100);
                 TablaFecha.setSpacingAfter(5);
                 TablaFecha.setSpacingBefore(5);
                 TablaFecha.addCell(new Phrase("Estado: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                 TablaFecha.addCell(new Phrase(new Phrase(selected.getCEstado(), FontFactory.getFont(FontFactory.TIMES, 12))));
                 document.add(TablaFecha);*/

                /*PdfPTable TablaAutores = new PdfPTable(2);
                 TablaAutores.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                 TablaAutores.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                 TablaAutores.setWidths(columnas);
                 TablaAutores.setWidthPercentage(100);
                 TablaAutores.setSpacingAfter(5);
                 TablaAutores.setSpacingBefore(5);
                 TablaAutores.addCell(new Phrase("Autores: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                 TablaAutores.addCell(new Phrase(new Phrase(autoresBList(selected), FontFactory.getFont(FontFactory.TIMES, 12))));
                 document.add(TablaAutores);*/
                String nombres = "", coma = "";
                if (!selected.getNombrecomunTbList().isEmpty()) {
                    for (NombrecomunTb i : selected.getNombrecomunTbList()) {
                        nombres = nombres + coma + i.getCNombre();
                        coma = ", ";
                    }
                    nombres = nombres + ".";
                } else {
                    nombres = "No se encontraron nombres comunes.";
                }
                PdfPTable TablaNombres = new PdfPTable(2);
                TablaNombres.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                TablaNombres.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                TablaNombres.setWidths(columnas);
                TablaNombres.setWidthPercentage(100);
                TablaNombres.setSpacingAfter(5);
                TablaNombres.setSpacingBefore(5);
                TablaNombres.addCell(new Phrase("Nombres comunes: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                TablaNombres.addCell(new Phrase(new Phrase(nombres, FontFactory.getFont(FontFactory.TIMES, 12))));
                document.add(TablaNombres);

                //tabla para espacio entre las imagenes
                /*PdfPTable tablaEspacio1 = new PdfPTable(1);
                 PdfPCell celdaa = new PdfPCell();
                 celdaa.addElement(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
                 celdaa.setBorder(Rectangle.NO_BORDER);
                 tablaEspacio1.addCell(celdaa);
                 document.add(tablaEspacio1);*/
                Paragraph tituloAutores = new Paragraph("AUTORES", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
                tituloAutores.setAlignment(Element.ALIGN_CENTER);
                tituloAutores.setSpacingAfter(5);
                tituloAutores.setSpacingBefore(5);
                document.add(tituloAutores);
                //tabla con los autores
                if (!selected.getAgenteTaxonomiaTbList().isEmpty()) {
                    //Encabezado
                    PdfPTable TablaInsumo1 = new PdfPTable(3);
                    int numero[] = {40, 35, 25};
                    TablaInsumo1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    TablaInsumo1.setWidths(numero);
                    TablaInsumo1.setWidthPercentage(80);
                    TablaInsumo1.addCell(new Phrase("Autor", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Nombre corto", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    TablaInsumo1.addCell(new Phrase("Autor Basionimio", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
                    document.add(TablaInsumo1);

                    for (AgenteTaxonomiaTb i : selected.getAgenteTaxonomiaTbList()) {
                        PdfPTable TablaInsumo = new PdfPTable(3);
                        TablaInsumo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.setWidths(numero);
                        TablaInsumo.setWidthPercentage(80);

                        PdfPCell c0 = new PdfPCell(new Phrase(i.getAgenteTb().getCNombre() + " " + i.getAgenteTb().getCApellido(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c0.setHorizontalAlignment(Element.ALIGN_LEFT);
                        TablaInsumo.addCell(c0);

                        PdfPCell c1 = new PdfPCell(new Phrase(i.getAgenteTb().getCIniciales(), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c1);

                        PdfPCell c2 = new PdfPCell(new Phrase(autorBasionimio(i.getBAutorbasionimio()), FontFactory.getFont(FontFactory.TIMES, 12)));
                        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TablaInsumo.addCell(c2);

                        document.add(TablaInsumo);
                    }
                } else {
                    Paragraph tituloNoActividades = new Paragraph("No se encontraron Autores", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNoActividades.setAlignment(Element.ALIGN_CENTER);
                    tituloNoActividades.setSpacingAfter(5);
                    tituloNoActividades.setSpacingBefore(5);
                    document.add(tituloNoActividades);
                }

                //Mostrar imagenes
                document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES, 10)));
                FacesContext ctx = FacesContext.getCurrentInstance();
                String ruta = ctx.getExternalContext().getInitParameter("pictures_directory_path");
                String carpeta = selected.getEIdtaxonomia() + "/";
                if (!selected.getImagenTbList().isEmpty()) {
                    for (ImagenTb i : selected.getImagenTbList()) {
                        String Img = ruta + carpeta + i.getCNombre();

                        //Tabla para  las imagenes
                        PdfPTable tablaImg1 = new PdfPTable(1);
                        //Ancho de la tabla
                        tablaImg1.setWidthPercentage(100);
                        Image imagenes = Image.getInstance(Img);
                        //Indico tamaÃƒÂ±o del logo
                        float documentWidth = (document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / 2;
                        float documentHeight = (document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin()) / 2;
                        imagenes.scaleToFit(documentWidth, documentHeight);
                        System.out.println("" + imagenes.getWidth() + "x" + imagenes.getHeight());
                        //aÃ±adiendo la imagen a la tabla
                        tablaImg1.addCell(imagenes);
                        //aÃ±adiendo la tabla al documento
                        document.add(tablaImg1);
                        //tabla para espacio entre las imagenes
                        PdfPTable tablaImg2 = new PdfPTable(1);
                        PdfPCell celda2 = new PdfPCell();
                        celda2.addElement(new Paragraph(" ", FontFactory.getFont(FontFactory.TIMES_BOLD, 20)));
                        celda2.setBorder(Rectangle.NO_BORDER);
                        tablaImg2.addCell(celda2);
                        document.add(tablaImg2);
                    }
                } else {
                    Paragraph tituloNoImagenes = new Paragraph("No se encontraron imágenes", FontFactory.getFont(FontFactory.TIMES, 12));
                    tituloNoImagenes.setAlignment(Element.ALIGN_CENTER);
                    tituloNoImagenes.setSpacingAfter(5);
                    tituloNoImagenes.setSpacingBefore(5);
                    document.add(tituloNoImagenes);
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
                bitacora.setMDescripcion("Creado reporte individual de "+ selected.getCTipo() + " en el módulo: Información Taxonómica");
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
