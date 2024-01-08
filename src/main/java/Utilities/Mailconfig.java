package Utilities;

import Base.AppiumTestSetup;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static Base.AppiumTestSetup.props;
import static Listeners.FrameX_Listeners.fileName;
import static Utilities.Constants.*;
import static Utilities.Utils.generatedateandtime;
import static Utilities.Utils.screenshotName;


public class Mailconfig {

    public static void sendMailReport() throws MessagingException, FileNotFoundException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host",props.get("Host"));
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", props.get("Port"));
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.ciphers", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.ciphers", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(props.get("Sendermail"), props.get("Senderpassword"));

                    }
                });
        session.setDebug(false);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.get("Sendermail")));
            String[] recipients = {
                    props.get("Reciptents1"),
                    props.get("Reciptents2"),
            };
            InternetAddress[] recipientAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                recipientAddresses[i] = new InternetAddress(recipients[i]);
            }
            message.setRecipients(Message.RecipientType.TO,recipientAddresses);
            message.setSubject( props.get("Subject"));
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(body);

            // Attach first file
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(props.get("TestReportspath")+fileName);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("Automation Test Report"+generatedateandtime()+".html");

            // Attach second file
            MimeBodyPart messageBodyPart3 = new MimeBodyPart();
            DataSource source2 = new FileDataSource(props.get("Screenshotpath") + screenshotName);
            messageBodyPart3.setDataHandler(new DataHandler(source2));
            messageBodyPart3.setFileName("Screenshot "+generatedateandtime()+".jpg");


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart2);
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart3);
            message.setContent(multipart);
            Transport.send(message);
            AppiumTestSetup.log.info("Email sent");

        } catch (MessagingException e) {
            AppiumTestSetup.log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


}

