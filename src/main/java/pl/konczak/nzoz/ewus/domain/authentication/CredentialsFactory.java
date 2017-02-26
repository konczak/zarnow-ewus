package pl.konczak.nzoz.ewus.domain.authentication;

import pl.konczak.nzoz.ewus.client.ewus.namespace.AuthResponseNamespaceUtil;

import java.util.Iterator;

import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

@Component
public class CredentialsFactory {

    Credentials parse(SOAPMessage message) throws SOAPException {
        SOAPHeader sOAPHeader = message.getSOAPHeader();

        Iterator headerElements = sOAPHeader.getChildElements(AuthResponseNamespaceUtil._Session_QNAME);
        SOAPHeaderElement sessionElement = (SOAPHeaderElement) headerElements.next();
        String sessionId = sessionElement.getAttribute("id");

        headerElements = sOAPHeader.getChildElements(AuthResponseNamespaceUtil._AuthToken_QNAME);

        SOAPHeaderElement authTokenElement = (SOAPHeaderElement) headerElements.next();
        String authTokenId = authTokenElement.getAttribute("id");

        SOAPBody sOAPBody = message.getSOAPBody();
        Iterator bodyElements = sOAPBody.getChildElements(AuthResponseNamespaceUtil._LoginReturn_QNAME);
        SOAPBodyElement loginReturnElement = (SOAPBodyElement) bodyElements.next();
        String response = loginReturnElement.getTextContent();

        return new Credentials(sessionId, authTokenId, response);
    }
}
