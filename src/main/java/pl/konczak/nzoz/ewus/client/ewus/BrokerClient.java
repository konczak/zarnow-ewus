package pl.konczak.nzoz.ewus.client.ewus;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    public void checkCWU() throws Exception {
        LOGGER.debug("checkCWU");

        SOAPMessage message = checkCWURequestFactory.create("C0A608E55701A657867570C2356A89D4", "BSe360M9YpZpjQ+q_uJfdk", "79060804378");

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
    }
}
