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

    private static final String INDETYFIKATOR_SWIADCZENIODAWCY = "123456789";

    private static final String LOGIN = "TEST";

    private static final String PASSWORD = "qwerty!@#";

    public SOAPMessage create() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
//        QName qName = new QName("http://xml.kamsoft.pl/ws/kaas/login_types", "auth", "xmlns");
//        envelope.addAttribute(qName, "http://xml.kamsoft.pl/ws/kaas/login_types");

        envelope.addNamespaceDeclaration("auth", "http://xml.kamsoft.pl/ws/kaas/login_types");

        SOAPHeader header = message.getSOAPHeader();
        SOAPBody body = message.getSOAPBody();

        SOAPBodyElement login = body.addBodyElement(AuthNamespaceUtil._Login_QNAME);
        SOAPElement credentials = login.addChildElement(AuthNamespaceUtil._Credentials_QNAME);

        SOAPElement domainParam = domainParam(credentials);

        SOAPElement typeParam = typeParam(credentials);

        SOAPElement identyfikatorSwiadczeniodawcyParam = identyfikatorSwiadczeniodawcyParam(credentials);

        SOAPElement loginParam = loginParam(credentials);

        //
        SOAPElement password = login.addChildElement(AuthNamespaceUtil._Password_QNAME);
        password.addTextNode(PASSWORD);

        return message;
    }

    private SOAPElement domainParam(SOAPElement credentials) throws Exception {
        SOAPElement domainParam = credentials.addChildElement(AuthNamespaceUtil._Item_QNAME);
        SOAPElement domainParamName = domainParam.addChildElement(AuthNamespaceUtil._Name_QNAME);
        domainParamName.addTextNode("domain");
        SOAPElement domainParamValue = domainParam.addChildElement(AuthNamespaceUtil._Value_QNAME);
        SOAPElement domainValueStringValue = domainParamValue.addChildElement(AuthNamespaceUtil._StringValue_QNAME);
        domainValueStringValue.addTextNode(OW);

        return domainParam;
    }

    private SOAPElement typeParam(SOAPElement credentials) throws Exception {
        SOAPElement typeParam = credentials.addChildElement(AuthNamespaceUtil._Item_QNAME);
        SOAPElement typeParamName = typeParam.addChildElement(AuthNamespaceUtil._Name_QNAME);
        typeParamName.addTextNode("type");
        SOAPElement typeParamValue = typeParam.addChildElement(AuthNamespaceUtil._Value_QNAME);
        SOAPElement typeParamValueStringValue = typeParamValue.addChildElement(AuthNamespaceUtil._StringValue_QNAME);
        typeParamValueStringValue.addTextNode(USER_TYPE);

        return typeParam;
    }

    private SOAPElement identyfikatorSwiadczeniodawcyParam(SOAPElement credentials) throws Exception {
        SOAPElement identyfikatorSwiadczeniodawcyParam = credentials.addChildElement(AuthNamespaceUtil._Item_QNAME);
        SOAPElement identyfikatorSwiadczeniodawcyParamName = identyfikatorSwiadczeniodawcyParam.addChildElement(AuthNamespaceUtil._Name_QNAME);
        identyfikatorSwiadczeniodawcyParamName.addTextNode("idntSwd");
        SOAPElement typeValue = identyfikatorSwiadczeniodawcyParam.addChildElement(AuthNamespaceUtil._Value_QNAME);
        SOAPElement typeStringValue = typeValue.addChildElement(AuthNamespaceUtil._StringValue_QNAME);
        typeStringValue.addTextNode(INDETYFIKATOR_SWIADCZENIODAWCY);

        return identyfikatorSwiadczeniodawcyParam;
    }

    private SOAPElement loginParam(SOAPElement credentials) throws Exception {
        SOAPElement loginParam = credentials.addChildElement(AuthNamespaceUtil._Item_QNAME);
        SOAPElement loginParamName = loginParam.addChildElement(AuthNamespaceUtil._Name_QNAME);
        loginParamName.addTextNode("login");
        SOAPElement loginParamValue = loginParam.addChildElement(AuthNamespaceUtil._Value_QNAME);
        SOAPElement loginParamValueStringValue = loginParamValue.addChildElement(AuthNamespaceUtil._StringValue_QNAME);
        loginParamValueStringValue.addTextNode(LOGIN);

        return loginParam;
    }
}
