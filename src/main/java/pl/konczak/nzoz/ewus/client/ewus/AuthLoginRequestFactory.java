package pl.konczak.nzoz.ewus.client.ewus;

import org.springframework.stereotype.Component;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@Component
public class AuthLoginRequestFactory {

    private static final String OW = "01";

    private static final String USER_TYPE = "SWD";

    private static final String IDENTYFIKATOR_SWIADCZENIODAWCY = "123456789";

    private static final String LOGIN = "TEST";

    private static final String PASSWORD = "qwerty!@#";

    public SOAPMessage create() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("auth", "http://xml.kamsoft.pl/ws/kaas/login_types");

        SOAPHeader header = message.getSOAPHeader();
        SOAPBody body = message.getSOAPBody();

        SOAPBodyElement login = body.addBodyElement(AuthNamespaceUtil._Login_QNAME);
        SOAPElement credentials = login.addChildElement(AuthNamespaceUtil._Credentials_QNAME);

        SOAPElement domainParam = itemParam(credentials, "domain", OW);
        SOAPElement typeParam = itemParam(credentials, "type", USER_TYPE);
        SOAPElement identyfikatorSwiadczeniodawcyParam = itemParam(credentials, "idntSwd", IDENTYFIKATOR_SWIADCZENIODAWCY);
        SOAPElement loginParam = itemParam(credentials, "login", LOGIN);

        //
        SOAPElement password = login.addChildElement(AuthNamespaceUtil._Password_QNAME);
        password.addTextNode(PASSWORD);

        return message;
    }

    private SOAPElement itemParam(SOAPElement credentials, String name, String value) throws Exception {
        SOAPElement itemParam = credentials.addChildElement(AuthNamespaceUtil._Item_QNAME);
        SOAPElement itemParamName = itemParam.addChildElement(AuthNamespaceUtil._Name_QNAME);
        itemParamName.addTextNode(name);
        SOAPElement itemParamValue = itemParam.addChildElement(AuthNamespaceUtil._Value_QNAME);
        SOAPElement itemParamStringValue = itemParamValue.addChildElement(AuthNamespaceUtil._StringValue_QNAME);
        itemParamStringValue.addTextNode(value);

        return itemParam;
    }
}
