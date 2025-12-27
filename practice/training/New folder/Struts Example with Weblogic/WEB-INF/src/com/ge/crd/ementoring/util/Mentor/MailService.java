package com.ge.crd.ementoring.util.Mentor;

import java.util.Properties;
import java.util.Vector;

import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailService {

    private Vector vCcList;
    private Vector vToList;
    private String mailHost;
    private String mailFrom;
    private String mailSubject;
    private String mailBody;

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public Vector getMailTo() {
        return this.vToList;
    }

    public void setMailTo(String mailTo) {
        if ( this.vToList == null ) {
            this.vToList = new Vector();
        }

        this.vToList.add(mailTo);
    }

    public Vector getMailCc() {
        return this.vCcList;
    }

    public void setMailCc(String mailCc) {
        if ( this.vCcList == null ) {
            this.vCcList = new Vector();
        }

        this.vCcList.add(mailCc);
    }

    /*
    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailCc() {
        return mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }
    */

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public void doSendMail(String sHost, String sFrom, String sTo, String sSubject, String sText) throws MessagingException {

        try {

             this.setMailHost(sHost);
             this.setMailFrom(sFrom);
             this.setMailTo(sTo);
             this.setMailSubject(sSubject);
             this.setMailBody(sText);

             this.doSendMail();

        } catch(Exception ex) {
            ex.printStackTrace();            
        }
    }

    public void doSendMail() throws MessagingException {

         //String sSmtpHost = "3.159.213.25";  //Local
         //String sSmtpHost="3.159.213.47";  //Production

         Properties props = System.getProperties();

         props.put("mail.smtp.host", mailHost);

         Session session = Session.getInstance(props, null);

         MimeMessage message = new MimeMessage(session);

         message.setFrom(new InternetAddress(mailFrom));

         /*
         InternetAddress address[] = new InternetAddress[1];
         address[0] = new InternetAddress(mailTo);
         message.setRecipients(Message.RecipientType.TO, address);

         if ( mailCc != null ) {
            InternetAddress addressCC[] = new InternetAddress[1];
            addressCC[0] = new InternetAddress(mailCc);
            message.setRecipients(Message.RecipientType.CC, addressCC);
         }
         */

        if ( vToList != null && vToList.size() > 0 ) {
            String toAddressList = vToList.toString();
            InternetAddress[] toAddress = InternetAddress.parse(toAddressList.substring(1, (toAddressList.length() - 1)));
            message.setRecipients(Message.RecipientType.TO, toAddress);
        }

        if ( vCcList != null && vCcList.size() > 0 ) {
            String toAddressList = vCcList.toString();
            InternetAddress[] toAddress = InternetAddress.parse(toAddressList.substring(1, (toAddressList.length() - 1)));
            message.setRecipients(Message.RecipientType.CC, toAddress);
        }

         message.setSubject(mailSubject);

         MimeBodyPart messageBodyPart = new MimeBodyPart();

         messageBodyPart.setContent(mailBody, "text/html");

         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);

         message.setContent(multipart);

         Transport.send(message);
    }

}
