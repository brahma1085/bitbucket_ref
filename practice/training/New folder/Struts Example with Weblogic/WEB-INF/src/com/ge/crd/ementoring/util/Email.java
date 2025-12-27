package com.ge.crd.ementoring.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email  extends Thread  {

	private String mailHost,from,body,subj;
	private Vector to, cc;
	private Vector fileName;
	private Vector filePath;
	private boolean deleteFile;

    /*
	private static final String prop_class = "config";
	private static final ResourceBundle oProp = ResourceBundle.getBundle(prop_class);
    */

	private static final Properties props = System.getProperties();

	public boolean isDeleteFile() {
        return deleteFile;
    }

    public void setDeleteFile(boolean deleteFile) {
        this.deleteFile = deleteFile;
    }

    /**
     * @param fileDetails Expects a string array with filePath and fileName in that respective order
     */

    public void setFileDetails(String[] fileDetails) {
        if ((fileDetails != null) && (fileDetails.length == 2)) {
            if (fileName == null) {
                fileName = new Vector();
                filePath = new Vector();
            }
            filePath.addElement(fileDetails[0]);
            fileName.addElement(fileDetails[1]);
        }
    }

// set this variable to be your SMTP host

    public void setMailHost(String strServer) {
        mailHost = strServer;
    }

//returns the SMTP Server name.

    private  String getMailHost() {
        return mailHost;
    }

// set the from address of the mail

    public void setFrom(String strFrom) {
        from = strFrom;
    }

//returns the from address of the email

    private  String getFrom() {
        return from;
    }

//sets the to address of the mail.

    public void setTo(String strTo) {
        if (to == null) {
            to = new Vector();
        }
        to.addElement(strTo);
    }

//sets the to address of the mail.

    public void setTo(ArrayList vectorTo) {
        if (to == null) {
            to = new Vector();
        }
        to.addAll(vectorTo);
    }

// returns the to address  of the mail

    private Vector getTo() {
        return to;
    }

//sets the cc address of the mail.

    public void setCc(String strCc) {
        if (cc == null) {
            cc = new Vector();
        }
        cc.addElement(strCc);
    }

//sets the to address of the mail.

    public void setCc(ArrayList vectorCc) {
        if (cc == null) {
            cc = new Vector();
        }
        cc.addAll(vectorCc);
    }

// returns the cc address  of the mail

    private Vector getCc() {
        return cc;
    }

// sets the subject of the mail

    public void setSubject(String strSub) {
        subj = strSub;
    }

// returns the subject of the mail

    private  String getSubject() {
        return subj;
    }

// sets the body of the mail

    public void setBody(String strBody) {
        body = strBody;
    }

// returns the body of the mail

    private  String getBody() {
        return body;
    }

// start the thread

    public void sendMail() {
        //System.out.println("*********** Emailing Process Start ************ ");
        start();
    }

// send the mail

    public void run() {
        try {
            //Specify the desired SMTP server

            mailHost = getMailHost() ;
            props.put("mail.smtp.host", mailHost);

            Session session = Session.getInstance(props, null);
            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();

            from = getFrom() ;
            to = getTo() ;
            subj = getSubject() ;
            body = getBody() ;
            cc =  getCc() ;

            //System.out.println("cc = " + cc);

            message.setFrom(new InternetAddress(from));
            message.setSubject(subj);
            message.setContent(body, "text/html");

            // Sets the to Addresses

            if (to != null && to.size() != 0) {
                String toAddressList = to.toString();
                InternetAddress[] toAddress = InternetAddress.parse(toAddressList.substring(1, (toAddressList.length() - 1)));
                message.setRecipients(Message.RecipientType.TO, toAddress);

            } else {
                System.err.println("Email::run2 : No 'To' addresses given.");
                return;
            }

            // Sets the cc address

            if (cc != null && cc.size() != 0) {
                String ccAddressList = cc.toString();
                InternetAddress[] ccAddress = InternetAddress.parse(ccAddressList.substring(1, (ccAddressList.length() - 1)));
                message.setRecipients(Message.RecipientType.CC, ccAddress);
            }

            // Attach the text message

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment

            if ((fileName != null) && (fileName.size() != 0)) {
                for (int i = 0; i < fileName.size(); i++) {
                    String fullPath=filePath.elementAt(i) + ""+ fileName.elementAt(i);

                    //System.out.println("fullPath...."+fullPath);

                    File file = new File(fullPath);

                     //(String) fileName.elementAt(i)) ;
                     // File file = new File(filePath.elementAt(i) + ""+ fileName.elementAt(i));

                     if (file.exists()) {

                     //System.out.println("file = " + file);

                        BodyPart fileBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(fullPath);

                         //(String) fileName.elementAt(i));

                        fileBodyPart.setDataHandler(new DataHandler(source));
                        fileBodyPart.setFileName((String) fileName.elementAt(i));
                        multipart.addBodyPart(fileBodyPart);

                    }

                    // Put parts in message
                    message.setContent(multipart);
                }
            }

            //System.out.println("mail sent");
            // Should try 4 times to send a mail with a gap of 20 mins

            boolean sendSuccessful = false;
            int tries = 0;

            do {
                try {
                    Transport.send(message);
                     sendSuccessful = true;
                    //System.out.println("sendSuccessful = " + sendSuccessful);
                } catch (MessagingException me) {
                    ++tries;
                    System.err.println("Email::run : " + me);
                    System.err.println("Email::run : Failed to send mail. Try " + tries + " of 3.");
                    try {
                        Thread.sleep(1000 * 60 * 20);
                    } catch (InterruptedException e) {
                    }
                }
            } while (!sendSuccessful && tries < 4);

        } catch (Exception ex) {
            System.err.println("Email::run :1 " + ex.toString());

            //ex.printStackTrace();

        } finally {
            if (deleteFile && (fileName != null)) {
                // sleep for some time to allow other thread to send the file
                // before deleting the file
                try {
                    Thread.sleep(1000 * 60 * 10);
                } catch (InterruptedException e) {
                }

                for (int i = 0; i < fileName.size(); i++) {
                    File file = new File((String) fileName.elementAt(i)) ;
                            //(filePath.elementAt(i) + "/" + fileName.elementAt(i));
                    file.delete();
                }
            }
        }
    }
}
