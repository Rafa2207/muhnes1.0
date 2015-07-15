/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Frank Martinez
 */
@Named(value = "restoeDB")
@RequestScoped
public class RestorePostgresqlDatabase {
private String lblMessage;
    ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String basePath = ctx.getRealPath("/");
    private String txtPath = basePath + "paginas/configuracion/backups/";
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    EntityManager em;

    public void upload(FileUploadEvent event) throws FileNotFoundException, IOException {
        if (event.getFile().getFileName().equals("")) {
            lblMessage = "Please Select file to restore!";
            System.out.println(lblMessage);
        } else {
            String filename = FilenameUtils.getName(event.getFile().getFileName());
            InputStream input = event.getFile().getInputstream();
            OutputStream output = new FileOutputStream(new File(txtPath, filename));

            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
            restoreDB("root", "root", txtPath + event.getFile().getFileName());
        }
    }

    public boolean restoreDB(String dbUserName, String dbPassword, String source) {

        String executeCmd = "dropdb --host localhost --port 5432 --username \"postgres\" --no-password bd_muhnes && createdb --host localhost --port 5432 --username \"postgres\" bd_muhnes && /usr/bin/pg_restore --host localhost --port 5432 --username \"postgres\" --dbname \"bd_muhnes\" --role \"muhnes\" --no-password  --verbose \"" + source+ "\"";
        System.out.println(executeCmd);
        Process runtimeProcess;
        try {
            String[] cmdarray = {"/bin/sh", "-c", executeCmd};
            runtimeProcess = Runtime.getRuntime().exec(cmdarray);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                em.clear();
                lblMessage = "Se ha restaurado el respaldo con exito.";
                JsfUtil.addSuccessMessage(lblMessage);
                return true;
            } else {
                lblMessage = "Error, el archivo no se ah podido restaurar.";
                JsfUtil.addErrorMessage(lblMessage);
            }
        } catch (IOException | InterruptedException ex) {
        }

        return false;
    }    
}
