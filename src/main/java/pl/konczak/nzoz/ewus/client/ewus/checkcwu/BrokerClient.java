package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

@Component
public class BrokerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerClient.class);

    private final URL endpoint;

    public BrokerClient(@Value("${ewus.broker.url}") String url) throws MalformedURLException {
        this.endpoint = new URL(url);
    }

    public SOAPMessage send(SOAPMessage request) throws Exception {
        LOGGER.debug("sending request to Broker WS");
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        SOAPMessage response = connection.call(request, endpoint);

        connection.close();

        LOGGER.debug("request to Broker WS completed");

        return response;
    }

}
