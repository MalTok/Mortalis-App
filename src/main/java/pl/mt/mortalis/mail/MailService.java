package pl.mt.mortalis.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public static final String SERVICE_EMAIL_ADDRESS = "mortalis.necrology@gmail.com";
    public static final String ENCODING = "utf-8";

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private void sendHtmlMessage(String to, String from, String replyTo, String subject, String messageText) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, ENCODING);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setReplyTo(replyTo);
        helper.setSubject(subject);
        helper.setText(messageText, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendActivationEmail(Message message) throws MessagingException {
        sendHtmlMessage(
                message.getRecipientEmail(),
                SERVICE_EMAIL_ADDRESS,
                SERVICE_EMAIL_ADDRESS,
                message.getSubject(),
                message.getText()
        );
    }
}
