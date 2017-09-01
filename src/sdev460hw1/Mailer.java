/*
 * Christian Lepson
 */

package sdev460hw1;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mailer.java is responsible for sending Multi-factor
 * authorization e-mails to users.
 */
public class Mailer {

    /**
     * Gets a new multi-factor authorization code and
     * sends it via e-mail on a background thread
     */
    public static void sendNewMultiFactorAuthCode() {
        int multiFactorAuthCode = MultiFactorAuthenticator.generateMultiFactorAuthCode();
        Thread sendMailThread = new Thread(() -> {
            sendEmail(multiFactorAuthCode);
        });
        sendMailThread.start();     
    }
    
    /**
     * Sends a message containing a multi-factor authorization
     * code.
     * @param multiFactorAuthCode authorization code to be sent via e-mail
     */
    private static void sendEmail(int multiFactorAuthCode) {
        try {
            Message message = createEmail(multiFactorAuthCode);
            if (message != null) {
                Transport.send(message);
            }
        } catch (MessagingException e) {
            System.out.println("Unable to send email.");
            e.printStackTrace();
        }
    }
    
    /**
     * Creates the e-mail containing a multi-factor authorization
     * code.
     * @param multiFactorAuthCode authorization code to be sent via e-mail
     * @return 
     */
    private static Message createEmail(int multiFactorAuthCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        // TODO (1) Enter Gmail username and password for multi-factor auth sender
                        /**
                         * Note: you must enable 'less secure app' login
                         * for your Gmail account. This can be done at
                         * https://myaccount.google.com/lesssecureapps
                         */
                        return new PasswordAuthentication(
                                "Sender's Gmail Username Email Here",
                                "Sender's Gmail Password Here"
                        );
                    }
                });

        try {

            Message message = new MimeMessage(session);
            // TODO (2) Enter sender address
            message.setFrom(new InternetAddress("Sender's Gmail Address Here"));
            // TODO (3) Enter receiver address
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("Recipient's Email Address Here"));
            message.setSubject("SDEV460 Login Code");
            message.setText("The code you requested is: \n" +
                    multiFactorAuthCode);
            return message;
        } catch (AddressException e) {
            e.printStackTrace();
            return null;
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }
        
}
