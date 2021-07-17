package pl.konczak.nzoz.ewus.domain.checkcwu;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.client.ewus.namespace.BrokerNamespaceUtil;
import pl.konczak.nzoz.ewus.client.ewus.namespace.CommonNamespaceUtil;
import pl.konczak.nzoz.ewus.client.ewus.namespace.EwusNamespaceUtil;
import pl.konczak.nzoz.ewus.config.EwusClientConfiguration;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWURequestFactory {

    private final EwusClientConfiguration ewusClientConfiguration;

    SOAPMessage create(Credentials credentials, String pesel) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("brok", "http://xml.kamsoft.pl/ws/broker");
        envelope.addNamespaceDeclaration("com", "http://xml.kamsoft.pl/ws/common");

        SOAPHeader header = message.getSOAPHeader();

        addSession(header, credentials.getSession());
        addAuthToken(header, credentials.getAuthToken());

        SOAPBody body = message.getSOAPBody();

        SOAPBodyElement executeService = body.addBodyElement(BrokerNamespaceUtil._ExecuteService_QNAME);
        addLocation(executeService);
        addDate(executeService);

        SOAPElement payload = executeService.addChildElement(BrokerNamespaceUtil._Payload_QNAME);
        SOAPElement textload = payload.addChildElement(BrokerNamespaceUtil._Textload_QNAME);

        addStatusCWUpyt(textload, pesel);

        return message;
    }

    private void addSession(SOAPHeader header, String session) throws SOAPException {
        SOAPElement sessionElement = header.addChildElement(CommonNamespaceUtil._Session_QNAME);

        sessionElement.addAttribute(new QName("id"), session);
        sessionElement.addAttribute(new QName("", "ns1", "xmlns"), "http://xml.kamsoft.pl/ws/common");
    }

    private void addAuthToken(SOAPHeader header, String authToken) throws SOAPException {
        SOAPElement authTokenElement = header.addChildElement(CommonNamespaceUtil._AuthToken_QNAME);

        authTokenElement.addAttribute(new QName("id"), authToken);
        authTokenElement.addAttribute(new QName("", "ns1", "xmlns"), "http://xml.kamsoft.pl/ws/common");
    }

    private SOAPElement addLocation(SOAPBodyElement executeService) throws SOAPException {
        SOAPElement location = executeService.addChildElement(CommonNamespaceUtil._Location_QNAME);
        SOAPElement namespace = location.addChildElement(CommonNamespaceUtil._Namespace_QNAME);
        namespace.addTextNode("nfz.gov.pl/ws/broker/cwu");
        SOAPElement localname = location.addChildElement(CommonNamespaceUtil._Localname_QNAME);
        localname.addTextNode("checkCWU");
        SOAPElement version = location.addChildElement(CommonNamespaceUtil._Version_QNAME);
        version.addTextNode("5.0");

        return location;
    }

    private SOAPElement addDate(SOAPBodyElement executeService) throws SOAPException {
        ZonedDateTime zdt = ZonedDateTime.now();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zdt);
        XMLGregorianCalendar xMLGregorianCalendar = new XMLGregorianCalendarImpl(gregorianCalendar);

        SOAPElement date = executeService.addChildElement(BrokerNamespaceUtil._Date_QNAME);
        date.addTextNode(xMLGregorianCalendar.toXMLFormat());

        return date;
    }

    private void addStatusCWUpyt(SOAPElement textload, String pesel) throws SOAPException {
        SOAPElement statusCWUpyt = textload.addChildElement(EwusNamespaceUtil._Status_cwu_pyt_QNAME);
        statusCWUpyt.addNamespaceDeclaration("ewus", "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5");
        SOAPElement numerPesel = statusCWUpyt.addChildElement(EwusNamespaceUtil._Numer_pesel_QNAME);
        numerPesel.addTextNode(pesel);
        SOAPElement systemSwiad = statusCWUpyt.addChildElement(EwusNamespaceUtil._System_swiad_QNAME);
        systemSwiad.addAttribute(new QName("nazwa"), ewusClientConfiguration.getName());
        systemSwiad.addAttribute(new QName("wersja"), ewusClientConfiguration.getVersion());
    }

}
