package controlador;

import modelo.TaxonomiaTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.UtilPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import servicio.TaxonomiaTbFacade;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
import javax.mail.MessagingException;
import javax.mail.Part;
import modelo.AgenteTaxonomiaTb;
import modelo.AgenteTaxonomiaTbPK;
import modelo.AgenteTb;
import modelo.ImagenTb;
import modelo.NombrecomunTb;
import modelo.PaisTb;
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
        initializeEmbeddableKey();
        return selected;
    }

    public void createFamilia() {
        selected.setERango(1);
        selected.setCTipo("Familia");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FamiliaTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createGenero(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(2);
        selected.setCTipo("Genero");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createEspecie(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(3);
        selected.setCTipo("Especie");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EspecieTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createSubEspecie(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(4);
        selected.setCTipo("Subespecie");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SubespecieTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createVariedad(TaxonomiaTb tx) {
        selected.setEIdniveltaxonomia(tx);
        selected.setERango(4);
        selected.setCTipo("Variedad");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VariedadTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GeneroTbUpdated"));
    }

    public void destroy() {
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
        autores = "(" + cadena1 + ") " + cadena2;
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
        nuevo.setCNombre(nombreComun);
        nuevo.setCIdioma(idioma);
        nuevo.setEIdtaxonomia(selected);
        selected.getNombrecomunTbList().add(nuevo);
        nombreComun = "";
        idioma = "";
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String realPath = UtilPath.getPathDefinida(ec.getRealPath("/"));
        String pathDefinition = realPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images" + File.separator + file.getFileName();
        String ruta = File.separator + "images" + File.separator + file.getFileName();
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
        nuevo.setCRuta(ruta);
        nuevo.setEIdtaxonomia(selected);
        selected.getImagenTbList().add(nuevo);
        FacesMessage exito = new FacesMessage("imagen subida correctamente");
        FacesContext.getCurrentInstance().addMessage(null, exito);
    }

    public void borrarImagen() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String realPath = UtilPath.getPathDefinida(ec.getRealPath("/"));
        String pathDefinition = realPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images" + File.separator + img.getCNombre();
        try {
            File imagen = new File(pathDefinition);
            if (imagen.exists()) {
                imagen.delete();
                FacesMessage exito = new FacesMessage("imagen eliminada correctamente");
                FacesContext.getCurrentInstance().addMessage(null, exito);
                selected.getImagenTbList().remove(img);
            }
        } catch (Exception e) {
            FacesMessage error = new FacesMessage("No se puede subir la imagen");
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
                cadena1 = cadena1 + i.getAgenteTb().getCIniciales() + " " + i.getCConector() + " ";
            }
            if (i.getBAutorbasionimio() == false) {
                cadena2 = cadena2 + i.getAgenteTb().getCIniciales() + " ";
            }
        }
        //autores = "("+ cadena1 +") " + cadena2;
        return "(" + cadena1 + ") " + cadena2;
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
            autores = "(" + cadena1 + ") " + cadena2;
        } catch (Exception e) {
            return null;
        }
        return autores;
    }

}
