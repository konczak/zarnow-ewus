package pl.konczak.nzoz.ewus.domain.authentication;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.konczak.nzoz.ewus.client.ewus.auth.AuthClient;

import javax.xml.soap.SOAPMessage;

@Slf4j
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LogoutService {

    private final LoginService loginService;

    private final AuthClient authClient;

    private final LogoutRequestFactory logoutRequestFactory;

    public void logout(Credentials credentials) throws Exception {
        log.info("logout");
        SOAPMessage request = logoutRequestFactory.create(credentials);

        authClient.send(request);

        loginService.clearCachedCredentials();

        log.info("logout completed");
    }

}
