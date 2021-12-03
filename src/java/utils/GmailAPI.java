/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dtos.Booking;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author SE140018
 */
public class GmailAPI {

    final String fromEmail = "software.testing.develope@gmail.com";
    final String password = "nvwddloapyoctegg";
    final String subject = "Booking Hotel";

    public boolean sendEmailWhenCreatUserSuccess(String toEmail, String code) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);
            message.setSubject("Booking Hotel : Code Discount");
            String htmlContent = "<h1>Thank You for create account in Booking Hotel</h1>"
                    + "Your code :" + code;
            message.setContent(htmlContent, "text/html");
            Transport.send(message);
        } catch (Exception e) {          
        }
        return true;
    }

    public boolean sendEmailWhenBookingSuccess(String toEmail, Booking booking) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);
            message.setSubject("Booking Hotel : Booking success");
            String htmlContent = "<h1>Booking Success</h1>"
                    + "Hi " + booking.getUser().getUserName() + "<br>"
                    + "Thank you for choosing us to booking hotel<br>"
                    + "Your booking :<br>"
                    + "Booking ID : " + booking.getBookingID() + "<br>"
                    + "Use this ID to check your booking detail in website";
            message.setContent(htmlContent, "text/html");
            Transport.send(message);
        } catch (Exception e) {
        }
        return true;
    }
}
