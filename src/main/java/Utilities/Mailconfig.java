package Utilities;

import Base.AppiumTestSetup;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static Utilities.Constants.*;

public class Mailconfig {

    public static void sendMailReport() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.ciphers", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.ciphers", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendermail, senderpassword);
                    }
                });
        session.setDebug(false);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendermail));
            String[] recipients = {
                    "Ezhilraj@fieldlytics.in"
            };
            InternetAddress[] recipientAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                recipientAddresses[i] = new InternetAddress(recipients[i]);
            }
            message.setRecipients(Message.RecipientType.TO,recipientAddresses);
            message.setSubject(subject);
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(body);
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(ReportPath);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(ReportPath);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart2);
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);
            Transport.send(message);
            AppiumTestSetup.log.info("Email sent");
            System.out.println("Email Sent");

        } catch (MessagingException e) {
            AppiumTestSetup.log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


}

