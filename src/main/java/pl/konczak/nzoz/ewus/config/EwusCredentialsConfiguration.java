package pl.konczak.nzoz.ewus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ewus.credentials")
public class EwusCredentialsConfiguration {

    private String ow;

    private String userType;

    private String identyfikatorSwiadczeniodawcy;

    private String login;

    private String password;

}
