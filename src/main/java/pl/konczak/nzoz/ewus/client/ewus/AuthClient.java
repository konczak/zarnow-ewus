package pl.konczak.nzoz.ewus.client.ewus;

import java.net.URL;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.client.old.LoginResponse;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

@Component
public class AuthClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);

    private final AuthLoginRequestFactory authLoginRequestFactory;

    public AuthClient(AuthLoginRequestFactory authLoginRequestFactory) {
        this.authLoginRequestFactory = authLoginRequestFactory;
    }

    public LoginResponse login() throws Exception {
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

        SOAPHeader sOAPHeader = response.getSOAPHeader();

        Iterator headerElements = sOAPHeader.getChildElements(AuthResponseNamespaceUtil._Session_QNAME);
        SOAPHeaderElement sessionElement = (SOAPHeaderElement) headerElements.next();
        String sessionId = sessionElement.getAttribute("id");

        headerElements = sOAPHeader.getChildElements(AuthResponseNamespaceUtil._AuthToken_QNAME);

        SOAPHeaderElement authTokenElement = (SOAPHeaderElement) headerElements.next();
        String authTokenId = authTokenElement.getAttribute("id");

        SOAPBody sOAPBody = response.getSOAPBody();
        Iterator bodyElements = sOAPBody.getChildElements(AuthResponseNamespaceUtil._LoginReturn_QNAME);
        SOAPBodyElement loginReturnElement = (SOAPBodyElement) bodyElements.next();
        String loginReturn = loginReturnElement.getTextContent();

        return new LoginResponse(sessionId, authTokenId, loginReturn);
    }
}
