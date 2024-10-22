package com.quantus.backend.controllers.system;

import com.quantus.backend.utils.MailSenderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-22
 *
 * QLMS Test Controller for various experimental features
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/test")
public class TestController {

    private final MailSenderHelper emailSenderHelper;

    @GetMapping("")
    public ResponseEntity<Object>  getCasTest() {
        RestTemplate restTemplate = new RestTemplate();
        String casNumber = "1336-21-6";  // Example CAS number
        String url = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/" + casNumber + "/JSON";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        return ResponseEntity.ok("");
    }

    @GetMapping("/mail")
    public String sendEmail() {
        emailSenderHelper.sendSimpleEmail(
                "dakota.soares@execulink.com",
                "Test Subject",
                "This is a test email."
        );
        return "Email sent successfully";
    }
}
