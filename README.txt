MAXLINE PAINTINGS — JAVA EMAIL ENQUIRY WEBSITE
==============================================

This project serves the five responsive website pages and uses Java Spring Boot to send contact form enquiries by email to maxlinepaintings@gmail.com. Customers do not need WhatsApp to submit the form. The contact page also provides optional WhatsApp links for +256 787 385413 and +256 708 447998.

IMPORTANT: WhatsApp links do not automatically send the form contents. Automatic WhatsApp delivery requires approved WhatsApp Business Platform/API setup. Email delivery works independently.

REQUIREMENTS
- Java JDK 17 or newer
- Maven 3.6+
- Internet for Maven dependencies and Gmail SMTP

GMAIL SETUP
1. Sign in to the Gmail account that will SEND email. It can be maxlinepaintings@gmail.com if you control it.
2. Enable 2-Step Verification in Google Account security.
3. Create a Google App Password in Google Account > Security > App passwords.
4. Use the App Password, not your normal Gmail password. Never publish it in HTML, JavaScript, GitHub or screenshots.

RUN ON WINDOWS (PowerShell)
Open PowerShell in the project root folder and enter these commands, replacing the sample with your generated App Password:
$env:MAIL_USERNAME="maxlinepaintings@gmail.com"
$env:MAIL_APP_PASSWORD="YOUR_GOOGLE_APP_PASSWORD"
$env:ENQUIRY_TO="maxlinepaintings@gmail.com"
mvn spring-boot:run

Keep that PowerShell window open. Then visit http://localhost:8080. Do not double-click index.html; the form needs the Java server. Submit a test enquiry and check Inbox and Spam/Junk.

HOW IT WORKS
Browser -> POST /api/enquiries -> Java validation -> Gmail SMTP -> business inbox.
The page shows success only when the SMTP service accepts the email. This cannot guarantee inbox placement, so check Spam/Junk as well.

BEFORE PUBLIC DEPLOYMENT
- Set MAIL_USERNAME, MAIL_APP_PASSWORD and ENQUIRY_TO as private environment variables in your host.
- Use HTTPS.
- Add production-grade rate limiting/spam protection.
- This package does not yet include database storage, user login/registration or online payments.


