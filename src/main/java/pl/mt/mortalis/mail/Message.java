package pl.mt.mortalis.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String recipientEmail;

    private String subject;
    
    private String text;
}
