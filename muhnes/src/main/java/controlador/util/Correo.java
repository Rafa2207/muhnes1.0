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
            message.setSubject("Recuperacion de credenciales BOTANICA_MUHNES");
            //ACA LA MAGIA PARA ASIGNAR EL PASSWORD

            this.usuario = usuarioFacade.usuarioByCorreo(this.correo);

            // Mensaje
            if (this.usuario != null) {//si encontró algo manda el correo
                String passnew = RandomStringUtils.randomAlphanumeric(8);
                this.usuario.setMPassword(passnew);
                message.setText("Su usuario es: " + this.usuario.getCNick()
                        + "\nSu nueva contraseña es: " + passnew
                        + "\nFavor ir al sistema, inicie sesión y cambie la contraseña");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
                JsfUtil.addSuccessMessage("El correo fue enviado. Revise su bandeja de entrada.");
                this.usuarioFacade.edit(usuario);
            } else {
                JsfUtil.addErrorMessage("Correo no encontrado en la base de datos del Sistema");
            }

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    
    }
}
