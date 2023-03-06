package com.example.emailservice.service;

import com.example.emailservice.domain.EmailDetails;
import com.example.emailservice.service.EmailService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitListener implements MessageListener {

    private EmailService emailService;

    @Autowired
    public RabbitListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("New message received from "
                + message.getMessageProperties().getConsumerQueue()
                + ": "
                + new String(message.getBody()));

        EmailDetails emailDetails = new EmailDetails((String)message.getMessageProperties().getHeaders().get("recipient"),
                new String(message.getBody()),
                (String)message.getMessageProperties().getHeaders().get("subject"),
                "attachment");
        emailService.sendSimpleMail(emailDetails);

        System.out.println("email sent.");
    }
}
