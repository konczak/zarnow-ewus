package pl.konczak.nzoz.ewus.client.ewus.auth;

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
public class AuthClient {

    private final URL endpoint;

    AuthClient(@Value("${ewus.auth.url}") String url) throws MalformedURLException {
        this.endpoint = new URL(url);
    }

    public SOAPMessage send(SOAPMessage request) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("sending request to Auth WS");
            log.debug(SOAPFormatterUtil.format(request));
        }
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();

        SOAPMessage response = connection.call(request, endpoint);

        connection.close();

        if (log.isDebugEnabled()) {
            log.debug("request to Auth WS completed");
            log.debug(SOAPFormatterUtil.format(request));
        }
        return response;
    }
}
