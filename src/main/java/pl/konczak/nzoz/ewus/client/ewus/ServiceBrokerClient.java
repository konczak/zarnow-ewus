package pl.konczak.nzoz.ewus.client.ewus;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;

import pl.konczak.nzoz.ewus.client.broker.ObjectFactory;
import pl.konczak.nzoz.ewus.client.broker.Payload;
import pl.konczak.nzoz.ewus.client.broker.ServiceLocation;
import pl.konczak.nzoz.ewus.client.broker.ServiceRequest;
import pl.konczak.nzoz.ewus.client.broker.ServiceResponse;
import pl.konczak.nzoz.ewus.client.xsd.StatusCwuPyt;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

public class ServiceBrokerClient
        extends WebServiceGatewaySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBrokerClient.class);

    private final ObjectFactory brokerObjectFactory;

    private final pl.konczak.nzoz.ewus.client.xsd.ObjectFactory objectFactory;

    private final Jaxb2Marshaller marshaller;

    public ServiceBrokerClient(Jaxb2Marshaller marshaller) {
        this.brokerObjectFactory = new ObjectFactory();
        this.objectFactory = new pl.konczak.nzoz.ewus.client.xsd.ObjectFactory();
        this.marshaller = marshaller;
    }

    public void call(String pesel, LoginResponse loginResponse) {
        pl.konczak.nzoz.ewus.client.xsd.System system = objectFactory.createSystem();
        system.setNazwa("konczak-development");
        system.setWersja("0.0.1");

        StatusCwuPyt statusCwuPyt = objectFactory.createStatusCwuPyt();
        statusCwuPyt.setNumerPesel(pesel);
        statusCwuPyt.setSystemSwiad(system);

        Payload.Textload textload = brokerObjectFactory.createPayloadTextload();
        textload.setAny(statusCwuPyt);

        Payload payload = brokerObjectFactory.createPayload();
        payload.setTextload(textload);

        ServiceLocation serviceLocation = brokerObjectFactory.createServiceLocation();
        serviceLocation.setNamespace("nfz.gov.pl/ws/broker/cwu");
        serviceLocation.setLocalname("checkCWU");
        serviceLocation.setVersion("3.0");

        ZonedDateTime zdt = ZonedDateTime.now();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zdt);
        XMLGregorianCalendar xMLGregorianCalendar = new XMLGregorianCalendarImpl(gregorianCalendar);

        ServiceRequest serviceRequest = brokerObjectFactory.createServiceRequest();
        serviceRequest.setLocation(serviceLocation);
        serviceRequest.setDate(xMLGregorianCalendar);
        serviceRequest.setPayload(payload);

        JAXBElement<ServiceRequest> checkCWURequest = brokerObjectFactory.createExecuteService(serviceRequest);

        ServiceResponse response = (ServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(checkCWURequest, new WebServiceMessageCallback() {
                    public void doWithMessage(WebServiceMessage message) throws IOException {
                        SoapMessage soapMessage = (SoapMessage) message;
                        SoapHeader soapHeader = soapMessage.getSoapHeader();
//                        soapHeader.addAttribute(null, null);
//                        new QName("http://xml.kamsoft.pl/ws/common", "session", "ns1"

                        StringSource headerSource = new StringSource("<session id=\"" + loginResponse.getSession() + "\" xmlns:ns1=\"http://xml.kamsoft.pl/ws/common\"/>"
                                + "<authToken id=\"" + loginResponse.getAuthToken() + "\" xmlns:ns1=\"http://xml.kamsoft.pl/ws/common\"/>");
                        try {
                            Transformer transformer = TransformerFactory.newInstance().newTransformer();
                            transformer.transform(headerSource, soapHeader.getResult());
                        } catch (TransformerException ex) {
                            LOGGER.error("soap header transformation failed", ex);
                        }
                    }
                });
    }

}
