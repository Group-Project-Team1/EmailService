package com.example.emailservice.service;

import com.example.emailservice.domain.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RabbitListenerTest {
    @InjectMocks
    RabbitListener rabbitListener;

    @Mock
    private EmailService emailService;

    @Test
    void test_onMessage(){
        byte[] body = "Test message body".getBytes();
        String recipient = "test@example.com";
        String subject = "Test email subject";
        Map<String, Object> headers = new HashMap<>();
        headers.put("recipient", recipient);
        headers.put("subject", subject);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeaders(headers);
        Message message = new Message(body, messageProperties);

        EmailDetails expectedEmailDetails = new EmailDetails(recipient, new String(body), subject, "attachment");

        // Act
        rabbitListener.onMessage(message);

        // Assert
        verify(emailService).sendSimpleMail(expectedEmailDetails);
    }
}
