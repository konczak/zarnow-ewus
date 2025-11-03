package pl.konczak.nzoz.ewus.domain.authentication;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.client.ewus.namespace.AuthNamespaceUtil;
import pl.konczak.nzoz.ewus.config.EwusCredentialsConfiguration;
import pl.konczak.nzoz.ewus.util.SecurityTOTPUtil;

import javax.xml.soap.*;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginRequestFactory {

    private final EwusCredentialsConfiguration ewusCredentialsConfiguration;

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
        itemParam(credentials, "mfaTotp", generateTotp());

        SOAPElement password = login.addChildElement(AuthNamespaceUtil._Password_QNAME);
        password.addTextNode(ewusCredentialsConfiguration.getPassword());

        return message;
    }

    private String generateTotp() {
        if (log.isDebugEnabled()) {
            log.debug("Generating Totp Token using {}", ewusCredentialsConfiguration.getTotp());
        }

        return SecurityTOTPUtil.generateTOTP(
                ewusCredentialsConfiguration.getTotp().getSecret(),
                ewusCredentialsConfiguration.getTotp().getAlgorithm(),
                ewusCredentialsConfiguration.getTotp().getDigits(),
                ewusCredentialsConfiguration.getTotp().getPeriod()
        );
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
