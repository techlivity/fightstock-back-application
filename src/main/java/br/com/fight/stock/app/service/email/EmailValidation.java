package br.com.fight.stock.app.service.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EmailValidation {

    private Environment environment;
    private static final String NAME_DYNAMIC_DATA = "nome";
    private static final String CODE_DYNAMIC_DATA = "codigo";
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 5;
    private static final SecureRandom random = new SecureRandom();

    public void sendEmail(String to, String name) throws IOException {

        Email from = new Email(System.getProperty("MAIN_ORGANIZATION_MAIL"));
        String subject = "Verifique seu e-mail";
        Email toEmail = new Email(to);
        Content content = new Content("text/html", "descricao");
        Mail mail = new Mail(from, subject, toEmail, content);


        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);

        Map<String, String> dynamicData = new HashMap<>();
        dynamicData.put(NAME_DYNAMIC_DATA, name);
        dynamicData.put(CODE_DYNAMIC_DATA, generateCode());

        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        mail.addPersonalization(personalization);
        mail.setTemplateId(System.getProperty("SEND_GRID_TEMPLATE_ID"));
        SendGrid sg = new SendGrid(System.getProperty("SEND_GRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response api = sg.api(request);
        log.info("status code send grid api: {}", api.getStatusCode());
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
