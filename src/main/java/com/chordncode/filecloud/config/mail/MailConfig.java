package com.chordncode.filecloud.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(
    value="file:${user.home}/Desktop/dev/web/properties/mail.properties"
)
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean enable;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean required;
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectiontimeout;
    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;
    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writetimeout;
}
