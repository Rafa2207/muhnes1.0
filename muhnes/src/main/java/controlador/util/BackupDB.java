/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
/**
 *
 * @author Frank Martinez
 */
@Named(value = "backupDB")
@RequestScoped
public class BackupDB {
ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String basePath = ctx.getRealPath("/");
    private String txtPath = basePath + "paginas/configuracion/backups/";
    private String lblMessage;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String backup() {
        if (txtPath.equals("")) {
            lblMessage = ("Please choose path to save!");
        } else {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            String strFilename = "bd_muhnes"+dateFormat.format(now);
            System.out.println(strFilename);
            String command = "/usr/bin/pg_dump --host localhost --port 5432 --username \"postgres\" --role \"muhnes\" --no-password  --format custom --blobs --verbose \"bd_muhnes\" > " + txtPath + strFilename + ".sql";
            System.out.println(command);
            Process p = null;
            try {
                Runtime runtime = Runtime.getRuntime();
                String[] cmdarray = {"/bin/sh", "-c", command};
                p = runtime.exec(cmdarray);
                int processComplete = p.waitFor();
                if (processComplete == 0) {
                    lblMessage = "El respaldo se ha generado exitosamente.";
                    JsfUtil.addSuccessMessage(lblMessage);
                    url = "/paginas/configuracion/backups/"+strFilename+".sql";
                } else {
                    lblMessage = "Error, el respaldo no se ah podido generar.";
                    JsfUtil.addErrorMessage(lblMessage);
                }
            } catch (IOException | InterruptedException e) {

            }
        }
        return null;
    }    
}
