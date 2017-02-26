package pl.konczak.nzoz.ewus.domain.checkcwu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import javax.xml.soap.SOAPMessage;

import pl.konczak.nzoz.ewus.client.ewus.broker.BrokerClient;

@Service
public class CheckCWUStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusService.class);

    private final CheckCWURequestFactory checkCWURequestFactory;

    private final BrokerClient brokerClient;

    private final CheckCWUStatusResponsePersistence checkCWUStatusResponsePersistence;

    private final CheckCWUResponseFactory checkCWUResponseFactory;

    public CheckCWUStatusService(CheckCWURequestFactory checkCWURequestFactory,
            BrokerClient brokerClient,
            CheckCWUStatusResponsePersistence checkCWUStatusResponsePersistence,
            CheckCWUResponseFactory checkCWUResponseFactory) {
        this.checkCWURequestFactory = checkCWURequestFactory;
        this.brokerClient = brokerClient;
        this.checkCWUStatusResponsePersistence = checkCWUStatusResponsePersistence;
        this.checkCWUResponseFactory = checkCWUResponseFactory;
    }

    public CheckCWUResponse checkCWU(Credentials credentials, String pesel) throws Exception {
        LOGGER.info("going to checkCWU <{}>", pesel);

        SOAPMessage request = checkCWURequestFactory.create(credentials, pesel);

        SOAPMessage response = brokerClient.send(request);

        checkCWUStatusResponsePersistence.persist(pesel, response);

        if (response.getSOAPBody().hasFault()) {
            LOGGER.warn("Response for <{}> cotains fault", pesel);
        }

        CheckCWUResponse checkCWUResponse = checkCWUResponseFactory.create(response);

        LOGGER.info("checkCWU completed");

        return checkCWUResponse;
    }
}
