package pl.konczak.nzoz.ewus.client.ewus.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.util.SOAPFormatterUtil;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Component
public class BrokerClient {

    private final URL endpoint;

    BrokerClient(@Value("${ewus.broker.url}") String url) throws MalformedURLException {
        this.endpoint = new URL(url);
    }

    public SOAPMessage send(SOAPMessage request) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("sending request to Broker WS");
            log.debug(SOAPFormatterUtil.format(request));
        }
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        SOAPMessage response = connection.call(request, endpoint);

        connection.close();

        if (log.isDebugEnabled()) {
            log.debug("request to Broker WS completed");
            log.debug(SOAPFormatterUtil.format(request));
        }

        return response;
    }

}
