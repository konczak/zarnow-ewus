package pl.konczak.nzoz.ewus.client.ewus;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import pl.konczak.nzoz.ewus.client.ewus.response.CheckCWUResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

@Component
public class BrokerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerClient.class);

    private final CheckCWURequestFactory checkCWURequestFactory;

    public BrokerClient(CheckCWURequestFactory checkCWURequestFactory) {
        this.checkCWURequestFactory = checkCWURequestFactory;
    }

    public void checkCWU(LoginResponse loginResponse) throws Exception {
        LOGGER.debug("checkCWU");

        SOAPMessage message = checkCWURequestFactory.create(loginResponse.getSession(), loginResponse.getAuthToken(), "79060804378");

        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        String formattedRequest = SOAPFormatter.format(message);

        URL endpoint = new URL("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/ServiceBroker");
        SOAPMessage response = connection.call(message, endpoint);

        String formattedResponse = SOAPFormatter.format(response);

        LOGGER.info(formattedRequest);
        LOGGER.info(formattedResponse);

        connection.close();

        LOGGER.debug("checkCWU ends");

        Document document = response.getSOAPBody().extractContentAsDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(CheckCWUResponse.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        CheckCWUResponse checkCWUResponse = (CheckCWUResponse) jaxbUnmarshaller.unmarshal(document);

        LOGGER.info("Hello I did it! status <{}> pesel <{}> fullname <{}>",
                readSatus(checkCWUResponse),
                readPesel(checkCWUResponse),
                readFullname(checkCWUResponse));
    }

    private String readSatus(CheckCWUResponse checkCWUResponse) {
        return String.valueOf(checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getStatusCwu());
    }

    private String readPesel(CheckCWUResponse checkCWUResponse) {
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPesel();
    }

    private String readFullname(CheckCWUResponse checkCWUResponse) {
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getNazwisko()
                + " "
                + checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getImie();
    }

}
