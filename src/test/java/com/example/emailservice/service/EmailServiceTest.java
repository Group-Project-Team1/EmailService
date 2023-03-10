package com.example.emailservice.service;

import com.example.emailservice.domain.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmailServiceTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void test_sendSimpleMail(){
        EmailDetails emailDetails = new EmailDetails("tz2179@gmail.com", "0", "0", "");
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        mailMessage.setFrom("tengzhang205968@gmail.com");
        mailMessage.setTo(emailDetails.getRecipient());
        mailMessage.setText(emailDetails.getMsgBody());
        mailMessage.setSubject(emailDetails.getSubject());
        emailService.sendSimpleMail(emailDetails);
        doNothing().when(javaMailSender).send(mailMessage);
        verify(javaMailSender, times(1)).send(mailMessage);
    }

}
