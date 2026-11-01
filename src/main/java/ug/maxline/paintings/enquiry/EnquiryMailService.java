
package ug.maxline.paintings.enquiry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnquiryMailService {

    private final JavaMailSender sender;

    @Value("${maxline.enquiry.to}")
    private String recipient;

    @Value("${spring.mail.username}")
    private String from;

    public EnquiryMailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(EnquiryRequest r) {
        SimpleMailMessage m = new SimpleMailMessage();

        m.setFrom(from);
        m.setTo(recipient);

        m.setReplyTo(
            r.getEmail() == null || r.getEmail().isBlank()
                ? from
                : r.getEmail().trim()
        );

        m.setSubject(
            "MAXLINE PAINTINGS website enquiry: " + r.getService()
        );

        m.setText(
            "New website enquiry\n\n" +
            "Name: " + clean(r.getName()) + "\n" +
            "Phone: " + clean(r.getPhone()) + "\n" +
            "Email: " + clean(r.getEmail()) + "\n" +
            "Service: " + clean(r.getService()) + "\n\n" +
            "Project details:\n" + clean(r.getMessage())
        );

        try {
            sender.send(m);
            System.out.println(
                "EMAIL SENT SUCCESSFULLY TO: " + recipient
            );
        } catch (MailException ex) {
            System.err.println("EMAIL SENDING FAILED:");
            ex.printStackTrace();
            throw new EnquiryDeliveryException();
        }
    }

    private String clean(String s) {
        return s == null ? "Not provided" : s.trim();
    }
}
