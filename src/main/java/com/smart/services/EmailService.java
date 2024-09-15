package com.smart.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class EmailService {
    public EmailService() {
    }

    public boolean sendEmail(String subject, String message, String to){

        try {
            //rest of the code..
            String from = "youremail.com";
            String password = "yourpassword";

            //Variable for gmail
            String host = "smtp.gmail.com";

            //get the system properties
            Properties properties = System.getProperties();
            System.out.println("Properties " + properties);

            //setting important information to properties object

            //host set
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            //step 1 : to get the session object ..
//        Session session = javax.mail.Session.getInstance()

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(from, password);
                }
            });

            session.setDebug(true);

            //step 2 : compose the message[text, multi media]
            MimeMessage m = new MimeMessage(session);

            //from
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subjrct to message
            m.setSubject(subject);

            //adding text to message
//            m.setText(message);
            m.setContent(message,"text/html");


            //send
            //step 3 : send the mesaage using transport class
            Transport.send(m);

            System.out.println("sent mesaage..........");
            return true;

            //
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}