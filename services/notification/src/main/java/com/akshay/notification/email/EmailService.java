package com.akshay.notification.email;

import com.akshay.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessMail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("work.akshaybaviskar@gmail.com");

        final String templateName = EnumTemplate.PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(EnumTemplate.PAYMENT_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info(String.format("INFO - Email successfully sent to %s with template %s,", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARN - Cannot send email to {}", destinationEmail);
        }
    }

    @Async
    public void orderConfirmationMail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED,StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("work.akshaybaviskar@gmail.com");

        final String templateName = EnumTemplate.ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(EnumTemplate.ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info(String.format("INFO - Email successfully sent to %s with template %s,", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARN - Cannot send email to {}", destinationEmail);
        }
    }
}
