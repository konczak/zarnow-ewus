package pl.konczak.nzoz.ewus.client.ewus;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

@Component
public class AuthClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);

    private final AuthLoginRequestFactory authLoginRequestFactory;

    public AuthClient(AuthLoginRequestFactory authLoginRequestFactory) {
        this.authLoginRequestFactory = authLoginRequestFactory;
    }

    public void login() throws Exception {
        LOGGER.debug("login");
        SOAPMessage message = authLoginRequestFactory.create();

        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        String formattedRequest = SOAPFormatter.format(message);

        URL endpoint = new URL("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth");
        SOAPMessage response = connection.call(message, endpoint);

        String formattedResponse = SOAPFormatter.format(response);

        LOGGER.info(formattedRequest);
        LOGGER.info(formattedResponse);

        connection.close();
        LOGGER.info("login ends");
    }
}
