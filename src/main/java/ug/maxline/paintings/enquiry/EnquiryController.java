


package ug.maxline.paintings.enquiry;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    private final EnquiryMailService mail;

    public EnquiryController(EnquiryMailService mail) {
        this.mail = mail;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> submit(
            @Valid @RequestBody EnquiryRequest r) {

        System.out.println("ENQUIRY RECEIVED FROM: " + r.getName());

        if (r.getWebsite() != null && !r.getWebsite().isBlank()) {
            System.out.println("SPAM CHECK: Hidden website field is not empty.");
            return ResponseEntity.ok(
                Map.of("message", "Thank you. Your enquiry has been received.")
            );
        }

        System.out.println("ATTEMPTING TO SEND ENQUIRY EMAIL...");

        mail.send(r);

        System.out.println("EMAIL SERVICE COMPLETED.");

        return ResponseEntity.ok(
            Map.of("message", "Enquiry sent by email.")
        );
    }

    @ExceptionHandler(EnquiryDeliveryException.class)
    public ResponseEntity<Map<String, String>> mailError() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of("message",
                "Email could not be sent right now. Please try again or contact us by phone."));
    }
}