package pl.konczak.nzoz.ewus.client.ewus.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.soap.SOAPMessage;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final AuthLoginRequestFactory authLoginRequestFactory;

    private final AuthClient authClient;

    private final CredentialsFactory credentialsFactory;

    public LoginService(AuthLoginRequestFactory authLoginRequestFactory, AuthClient authClient, CredentialsFactory credentialsFactory) {
        this.authLoginRequestFactory = authLoginRequestFactory;
        this.authClient = authClient;
        this.credentialsFactory = credentialsFactory;
    }

    @Cacheable(value = "ewus-credentials")
    public Credentials login() throws Exception {
        LOGGER.info("login");
        SOAPMessage authLoginRequest = authLoginRequestFactory.create();
        SOAPMessage response = authClient.login(authLoginRequest);
        Credentials credentials = credentialsFactory.parse(response);

        LOGGER.info("logged with session <{}> authToken <{}> response <{}>",
                credentials.getSession(),
                credentials.getAuthToken(),
                credentials.getResponse());

        return credentials;
    }
}
