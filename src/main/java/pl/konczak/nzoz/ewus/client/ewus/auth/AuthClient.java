package pl.konczak.nzoz.ewus.client.ewus.auth;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.util.SOAPFormatterUtil;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

@Component
public class AuthClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);

    private final URL endpoint;

    public AuthClient(@Value("${ewus.auth.url}") String url) throws MalformedURLException {
        this.endpoint = new URL(url);
    }

    public SOAPMessage send(SOAPMessage request) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("sending request to Auth WS");
            LOGGER.debug(SOAPFormatterUtil.format(request));
        }
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        SOAPMessage response = connection.call(request, endpoint);

        connection.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("request to Auth WS completed");
            LOGGER.debug(SOAPFormatterUtil.format(request));
        }
        return response;
    }
}
