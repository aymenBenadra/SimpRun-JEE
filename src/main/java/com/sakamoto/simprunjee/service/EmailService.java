package com.sakamoto.simprunjee.service;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Pattern;

public class EmailService {
    private static EmailService instance = null;
    private final Dotenv dotenv;

    private EmailService() {
        dotenv = Dotenv.load();
    }

    public static EmailService getInstance() {
        if (instance == null) {
            instance = new EmailService();
        }
        return instance;
    }
    public boolean sendEmail(String to, String subject, String body) {
        // Get system properties
        Properties properties = System.getProperties();

        // Check if the email is valid
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if (!to.matches(pattern.pattern())) {
            return false;
        }

        // Setup mail server
        properties.put("mail.smtp.host", dotenv.get("EMAIL_HOST"));
        properties.put("mail.smtp.port", dotenv.get("EMAIL_PORT"));
        properties.put("mail.smtp.ssl.enable", dotenv.get("EMAIL_SSL"));
        properties.put("mail.smtp.auth", dotenv.get("EMAIL_AUTH"));
        properties.put("mail.debug", dotenv.get("EMAIL_DEBUG").equals("true"));


        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(dotenv.get("EMAIL_USERNAME"), dotenv.get("EMAIL_PASSWORD"));
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(dotenv.get("EMAIL_USERNAME")));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully!");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
