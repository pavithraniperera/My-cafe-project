package lk.ijse.freshBite.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SendEmail {
  public static void outMail(String message,String to,String subject,List<File> attachedFiles){
      String from = "pererapavithrani02@gmail.com";
      Properties properties = new Properties();
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      Session session = Session.getInstance(properties, new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication("pvlo98715@gmail.com", "uerw dtun oxap lmxf");
          }
      });
      try {
         Message message1 = new MimeMessage(session);
         message1.setFrom(new InternetAddress("pvlo98715@gmail.com"));
         message1.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
         message1.setSubject(subject);
          // Create the message body part
          BodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setText(message);
          // Create a multipart message
          Multipart multipart = new MimeMultipart();
          multipart.addBodyPart(messageBodyPart);
          // Add attachments
          if (attachedFiles != null && !attachedFiles.isEmpty()) {
              for (File attachedFile : attachedFiles) {
                  MimeBodyPart attachmentPart = new MimeBodyPart();
                  attachmentPart.attachFile(attachedFile);
                  multipart.addBodyPart(attachmentPart);
              }
          }
          // Set the multipart message as the email content
          message1.setContent(multipart);
          Transport.send(message1);
          System.out.println("Email sent successfully!");
         // new Alert(Alert.AlertType.INFORMATION,"Emails sent SuccessFully").show();

      } catch (MessagingException e) {
          System.out.println(e.getMessage());
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
    public static void outMail(String msg, List<String> to, String subject, List<File> attachedFiles) throws MessagingException {
      //  System.out.println(to);
        for (String ele : to) {
            outMail(msg, ele, subject,attachedFiles);
        }
    }

}
