package ug.maxline.paintings.enquiry;
import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public class EnquiryRequest {
 @NotBlank @Size(max=100) private String name; @NotBlank @Size(max=30) private String phone; @Email @Size(max=150) private String email; @NotBlank @Size(max=80) private String service; @NotBlank @Size(max=2000) private String message; @Size(max=200) private String website;
 public String getName(){return name;} public void setName(String v){name=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getService(){return service;} public void setService(String v){service=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public String getWebsite(){return website;} public void setWebsite(String v){website=v;}
}
