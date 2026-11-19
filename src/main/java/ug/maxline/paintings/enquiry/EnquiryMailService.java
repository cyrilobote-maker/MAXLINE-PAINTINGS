
package ug.maxline.paintings.enquiry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnquiryMailService {

    private final RestClient restClient;

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    @Value("${maxline.enquiry.to}")
    private String recipient;

    @Value("${RESEND_FROM:onboarding@resend.dev}")
    private String from;

    public EnquiryMailService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.resend.com")
                .build();
    }

    @SuppressWarnings("null")
    public void send(EnquiryRequest r) {

        Map<String, Object> email = new HashMap<>();

        email.put("from", from);
        email.put("to", List.of(recipient));
        email.put(
            "subject",
            "MAXLINE PAINTINGS website enquiry: "
                + clean(r.getService())
        );

        email.put(
            "text",
            "New website enquiry\n\n" +
            "Name: " + clean(r.getName()) + "\n" +
            "Phone: " + clean(r.getPhone()) + "\n" +
            "Email: " + clean(r.getEmail()) + "\n" +
            "Service: " + clean(r.getService()) + "\n\n" +
            "Project details:\n" + clean(r.getMessage())
        );

        if (r.getEmail() != null && !r.getEmail().isBlank()) {
            email.put("reply_to", r.getEmail().trim());
        }

        try {
            restClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(email)
                .retrieve()
                .toBodilessEntity();

            System.out.println(
                "EMAIL SENT SUCCESSFULLY TO: " + recipient
            );

        } catch (RestClientResponseException ex) {
            System.err.println("RESEND API ERROR:");
            System.err.println(ex.getStatusCode());
            System.err.println(ex.getResponseBodyAsString());

            throw new EnquiryDeliveryException();

        } catch (RestClientException ex) {
            System.err.println("RESEND CONNECTION FAILED:");
            ex.printStackTrace();

            throw new EnquiryDeliveryException();
        }
    }

    private String clean(String s) {
        return s == null ? "Not provided" : s.trim();
    }
}