package pl.konczak.nzoz.ewus.domain.authentication;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.konczak.nzoz.ewus.client.ewus.auth.AuthClient;

import javax.xml.soap.SOAPMessage;

@Slf4j
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginService {

    private final LoginRequestFactory loginRequestFactory;

    private final AuthClient authClient;

    private final CredentialsFactory credentialsFactory;

    @Cacheable(value = "ewus-credentials")
    public Credentials login() throws Exception {
        log.info("login");
        SOAPMessage authLoginRequest = loginRequestFactory.create();
        SOAPMessage response = authClient.send(authLoginRequest);
        Credentials credentials = credentialsFactory.parse(response);

        log.info("logged with session <{}> authToken <{}> response <{}>",
                credentials.getSession(),
                credentials.getAuthToken(),
                credentials.getResponse());

        return credentials;
    }

    @CacheEvict(cacheNames = "ewus-credentials")
    public void clearCachedCredentials() {
        //do nothing
        log.info("ewus-credentials cache cleared");
    }
}
