package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;
import pl.konczak.nzoz.ewus.client.ewus.auth.LoginService;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;

import javax.xml.soap.SOAPMessage;

@Service
public class CheckCWUStatusFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusFacade.class);

    private final LoginService loginService;

    private final CheckCWURequestFactory checkCWURequestFactory;

    private final BrokerClient brokerClient;

    private final CheckCWUStatusResponsePersistence checkCWUStatusResponsePersistence;

    private final CheckCWUResponseFactory checkCWUResponseFactory;

    public CheckCWUStatusFacade(LoginService loginService,
            CheckCWURequestFactory checkCWURequestFactory,
            BrokerClient brokerClient,
            CheckCWUStatusResponsePersistence checkCWUStatusResponsePersistence,
            CheckCWUResponseFactory checkCWUResponseFactory) {
        this.loginService = loginService;
        this.checkCWURequestFactory = checkCWURequestFactory;
        this.brokerClient = brokerClient;
        this.checkCWUStatusResponsePersistence = checkCWUStatusResponsePersistence;
        this.checkCWUResponseFactory = checkCWUResponseFactory;
    }

    public CheckCWUResponse checkCWU(String pesel) throws Exception {
        LOGGER.info("going to checkCWU <{}>", pesel);

        Credentials credentials = loginService.login();

        SOAPMessage request = checkCWURequestFactory.create(credentials, pesel);

        SOAPMessage response = brokerClient.send(request);

        checkCWUStatusResponsePersistence.persist(pesel, response);

        CheckCWUResponse checkCWUResponse = checkCWUResponseFactory.create(response);
        
        LOGGER.info("checkCWU completed");

        return checkCWUResponse;
    }
}
