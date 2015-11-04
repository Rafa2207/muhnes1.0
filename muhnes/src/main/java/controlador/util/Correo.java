/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;


import java.io.Serializable;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import modelo.UsuarioTb;
import org.apache.commons.lang3.RandomStringUtils;
import servicio.UsuarioTbFacade;



/**
 *
 * @author Frank Martinez
 */
@Named("correo")
@ViewScoped

public class Correo implements Serializable{
    
    @EJB
    private UsuarioTbFacade usuarioFacade;
    private UsuarioTb usuario;
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void mandarcorreo(){
    // email del destinatario
        String to = this.correo;
        
        //remitente
        String from = "botanica.muhnes@gmail.com";
        
        //servidor
        String host = "smtp.gmail.com";
        
        //login
        final String user = "botanica.muhnes";
        final String pass = "Muhnes.2015";   
        
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Asunto del mensaje
            message.setSubject("Recuperación de usuario y contraseña para el Sistema BOTANICA_MUHNES");
            //AAsignar el password

            this.usuario = usuarioFacade.usuarioByCorreo(this.correo);

            // Mensaje
              if (this.usuario != null && this.usuario.getBEstado()) {//si encontrÃ³ algo manda el correo
                String passnew = RandomStringUtils.randomAlphanumeric(8);
                this.usuario.setMPassword(passnew);
                message.setText("Su usuario es: " + this.usuario.getCNick()
                        + "\nSu nueva contraseña es: " + passnew
                        + "\nPor favor dirijase al sistema, inicie sesión y cambie su contraseña");
                // Send message
                Transport.send(message);
                System.out.println("Mensaje enviado correctamente.");
                JsfUtil.addSuccessMessage("El correo fue enviado. Verifique su bandeja de entrada.");
                this.usuarioFacade.edit(usuario);
            } else {
                JsfUtil.addErrorMessage("Correo no encontrado en la base de datos del Sistema, porfavor contactarse con el administrador del sistema si es necesario");
            }

            } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        }
    }
