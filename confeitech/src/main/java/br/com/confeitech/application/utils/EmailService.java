package br.com.confeitech.application.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        @Autowired
        private JavaMailSender mailSender;

        public void enviarEmail() {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo("antonio.sousa@sptech.school");
            email.setSubject("teste");
            email.setText("testando a api muito legal");
            email.setFrom("scortuzzi@gmail.com");

            mailSender.send(email);
        }
    }

