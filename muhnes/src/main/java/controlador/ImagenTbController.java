package controlador;

import modelo.ImagenTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import controlador.util.UtilPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import servicio.ImagenTbFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("imagenTbController")
@ViewScoped
public class ImagenTbController implements Serializable {

    @EJB
    private servicio.ImagenTbFacade ejbFacade;
    private List<ImagenTb> items = null;
    private ImagenTb selected;
    private Part photo;
    private List<String> images = new ArrayList<String>();

    public ImagenTbController() {
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ImagenTb getSelected() {
        return selected;
    }

    public void setSelected(ImagenTb selected) {
        this.selected = selected;
    }

    public Part getPhoto() {
        return photo;
    }

    public void setPhoto(Part photo) {
        this.photo = photo;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ImagenTbFacade getFacade() {
        return ejbFacade;
    }

    public ImagenTb prepareCreate() {
        selected = new ImagenTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() throws IOException {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ImagenTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ImagenTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ImagenTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ImagenTb> getItems() {
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

    public ImagenTb getImagenTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ImagenTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ImagenTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ImagenTb.class)
    public static class ImagenTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ImagenTbController controller = (ImagenTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "imagenTbController");
            return controller.getImagenTb(getKey(value));
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
            if (object instanceof ImagenTb) {
                ImagenTb o = (ImagenTb) object;
                return getStringKey(o.getEIdimagen());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ImagenTb.class.getName()});
                return null;
            }
        }

    }

    //metodo para subir imagenes a una carpeta y almacenar ruta relativa en bd
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
        FacesMessage exito = new FacesMessage("imagen subida correctamente");
        FacesContext.getCurrentInstance().addMessage(null, exito);
    }

    

    public List<String> imagenes() {
        List<String> images1 = new ArrayList<String>();
        for (int i = 1; i <= 7; i++) {
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //String realPath = UtilPath.getPathDefinida(ec.getRealPath("/"));
            //String pathDefinition = realPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images" + File.separator;
            images1.add("nature" + i + ".jpg");
            System.out.println("nature" + i + ".jpg");
            
        }
        return images1;
    }

    //metodo para validar subida de imagenes a la base de datos

    public void validatePhoto(FacesContext ctx, UIComponent comp, Object value) {
// List of possible validation errors
        List<FacesMessage> msgs = new ArrayList<>();
// Retrieve the uploaded file from passed value object
        Part imagen = (Part) value;
        int num = 5;
// Ensure that the file is an image
        if (!imagen.getContentType().startsWith("image/")) {
            msgs.add(new FacesMessage("El archivo tiene que ser una imagen"));
        }
// Ensure that the file is less than 2 MB
        long size = imagen.getSize();
        if (size > 5242880) {
            msgs.add(new FacesMessage("La imagen debe ser menor a 5MB"));
        }
// Determine if a validation exception should be thrown
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
}
