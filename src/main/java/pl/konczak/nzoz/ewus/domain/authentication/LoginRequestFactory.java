package pl.konczak.nzoz.ewus.domain.authentication;

import pl.konczak.nzoz.ewus.client.ewus.namespace.AuthNamespaceUtil;

import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.config.EwusCredentialsConfiguration;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@Component
public class LoginRequestFactory {

    private final EwusCredentialsConfiguration ewusCredentialsConfiguration;

    public LoginRequestFactory(EwusCredentialsConfiguration ewusCredentialsConfiguration) {
        this.ewusCredentialsConfiguration = ewusCredentialsConfiguration;
    }

    public SOAPMessage create() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("auth", "http://xml.kamsoft.pl/ws/kaas/login_types");

        SOAPBody body = message.getSOAPBody();

        SOAPBodyElement login = body.addBodyElement(AuthNamespaceUtil._Login_QNAME);
        SOAPElement credentials = login.addChildElement(AuthNamespaceUtil._Credentials_QNAME);

        itemParam(credentials, "domain", ewusCredentialsConfiguration.getOw());
        itemParam(credentials, "type", ewusCredentialsConfiguration.getUserType());
        itemParam(credentials, "idntSwd", ewusCredentialsConfiguration.getIdentyfikatorSwiadczeniodawcy());
        itemParam(credentials, "login", ewusCredentialsConfiguration.getLogin());

        SOAPElement password = login.addChildElement(AuthNamespaceUtil._Password_QNAME);
        password.addTextNode(ewusCredentialsConfiguration.getPassword());

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
