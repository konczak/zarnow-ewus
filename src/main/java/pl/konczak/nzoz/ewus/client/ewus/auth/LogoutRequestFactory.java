package pl.konczak.nzoz.ewus.client.ewus.auth;

import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.client.ewus.namespace.AuthNamespaceUtil;
import pl.konczak.nzoz.ewus.client.ewus.namespace.CommonNamespaceUtil;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@Component
public class LogoutRequestFactory {

    public SOAPMessage create(Credentials credentials) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("com", "http://xml.kamsoft.pl/ws/common");
        envelope.addNamespaceDeclaration("auth", "http://xml.kamsoft.pl/ws/kaas/login_types");

        SOAPHeader header = message.getSOAPHeader();

        addSession(header, credentials.getSession());
        addAuthToken(header, credentials.getAuthToken());

        SOAPBody body = message.getSOAPBody();

        body.addBodyElement(AuthNamespaceUtil._Logout_QNAME);

        return message;
    }

    private void addSession(SOAPHeader header, String session) throws SOAPException {
        SOAPElement sessionElement = header.addChildElement(CommonNamespaceUtil._Session_QNAME);

        sessionElement.addAttribute(new QName("id"), session);
    }

    private void addAuthToken(SOAPHeader header, String authToken) throws SOAPException {
        SOAPElement authTokenElement = header.addChildElement(CommonNamespaceUtil._AuthToken_QNAME);

        authTokenElement.addAttribute(new QName("id"), authToken);
    }
}
