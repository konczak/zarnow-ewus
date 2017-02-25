package pl.konczak.nzoz.ewus.client.ewus.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.soap.SOAPMessage;

@Service
public class LogoutService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutService.class);

    private final LoginService loginService;

    private final AuthClient authClient;

    private final LogoutRequestFactory logoutRequestFactory;

    public LogoutService(LoginService loginService, AuthClient authClient, LogoutRequestFactory logoutRequestFactory) {
        this.loginService = loginService;
        this.authClient = authClient;
        this.logoutRequestFactory = logoutRequestFactory;
    }

    public void logout(Credentials credentials) throws Exception {
        LOGGER.info("logout");
        SOAPMessage request = logoutRequestFactory.create(credentials);

        authClient.send(request);

        loginService.clearCachedCredentials();

        LOGGER.info("logout completed");
    }

}
