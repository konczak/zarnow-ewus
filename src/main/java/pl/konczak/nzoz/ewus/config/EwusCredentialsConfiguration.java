package pl.konczak.nzoz.ewus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ewus.credentials")
public class EwusCredentialsConfiguration {

    private String ow;

    private String userType;

    private String identyfikatorSwiadczeniodawcy;

    private String login;

    private String password;

    public String getOw() {
        return ow;
    }

    public void setOw(String ow) {
        this.ow = ow;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIdentyfikatorSwiadczeniodawcy() {
        return identyfikatorSwiadczeniodawcy;
    }

    public void setIdentyfikatorSwiadczeniodawcy(String identyfikatorSwiadczeniodawcy) {
        this.identyfikatorSwiadczeniodawcy = identyfikatorSwiadczeniodawcy;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
