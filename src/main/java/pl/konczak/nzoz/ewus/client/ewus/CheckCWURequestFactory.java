package pl.konczak.nzoz.ewus.client.ewus;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@Component
public class CheckCWURequestFactory {

    public SOAPMessage create(String session, String authToken, String pesel) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("brok", "http://xml.kamsoft.pl/ws/broker");
        envelope.addNamespaceDeclaration("com", "http://xml.kamsoft.pl/ws/common");

        SOAPHeader header = message.getSOAPHeader();

        SOAPElement sessionElement = header.addChildElement(CommonNamespaceUtil._Session_QNAME);

        sessionElement.addAttribute(new QName("id"), session);
        sessionElement.addAttribute(new QName("", "ns1", "xmlns"), "http://xml.kamsoft.pl/ws/common");

        SOAPElement authTokenElement = header.addChildElement(CommonNamespaceUtil._AuthToken_QNAME);
        authTokenElement.addAttribute(new QName("id"), authToken);
        authTokenElement.addAttribute(new QName("", "ns1", "xmlns"), "http://xml.kamsoft.pl/ws/common");

        SOAPBody body = message.getSOAPBody();

        SOAPBodyElement executeService = body.addBodyElement(BrokerNamespaceUtil._ExecuteService_QNAME);
        location(executeService);
        date(executeService);

        SOAPElement payload = executeService.addChildElement(BrokerNamespaceUtil._Payload_QNAME);
        SOAPElement textload = payload.addChildElement(BrokerNamespaceUtil._Textload_QNAME);

        SOAPElement statusCWUpyt = textload.addChildElement(EwusNamespaceUtil._Status_cwu_pyt_QNAME);
        statusCWUpyt.addNamespaceDeclaration("ewus", "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3");
        SOAPElement numerPesel = statusCWUpyt.addChildElement(EwusNamespaceUtil._Numer_pesel_QNAME);
        numerPesel.addTextNode(pesel);
        SOAPElement systemSwiad = statusCWUpyt.addChildElement(EwusNamespaceUtil._System_swiad_QNAME);
        systemSwiad.addAttribute(new QName("nazwa"), "client-eWUS");
        systemSwiad.addAttribute(new QName("wersja"), "1.0.0");

        /*
         <ewus:status_cwu_pyt xmlns:ewus="https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3">
         <ewus:numer_pesel>79060804378</ewus:numer_pesel>
         <ewus:system_swiad nazwa="client-eWUŚ" wersja="1.0.0"/>
         </ewus:status_cwu_pyt>
         */
        return message;
    }

    private SOAPElement location(SOAPBodyElement executeService) throws SOAPException {
        SOAPElement location = executeService.addChildElement(CommonNamespaceUtil._Location_QNAME);
        SOAPElement namespace = location.addChildElement(CommonNamespaceUtil._Namespace_QNAME);
        namespace.addTextNode("nfz.gov.pl/ws/broker/cwu");
        SOAPElement localname = location.addChildElement(CommonNamespaceUtil._Localname_QNAME);
        localname.addTextNode("checkCWU");
        SOAPElement version = location.addChildElement(CommonNamespaceUtil._Version_QNAME);
        version.addTextNode("3.0");

        return location;
    }

    private SOAPElement date(SOAPBodyElement executeService) throws SOAPException {
        ZonedDateTime zdt = ZonedDateTime.now();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zdt);
        XMLGregorianCalendar xMLGregorianCalendar = new XMLGregorianCalendarImpl(gregorianCalendar);

        SOAPElement date = executeService.addChildElement(BrokerNamespaceUtil._Date_QNAME);
        date.addTextNode(xMLGregorianCalendar.toXMLFormat());

        return date;
    }
}
