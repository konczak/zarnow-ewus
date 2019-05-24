package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.konczak.nzoz.ewus.client.ewus.broker.BrokerClient;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import javax.xml.soap.SOAPMessage;

@Slf4j
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUStatusService {

    private final CheckCWURequestFactory checkCWURequestFactory;

    private final BrokerClient brokerClient;

    private final CheckCWUStatusResponsePersistence checkCWUStatusResponsePersistence;

    private final CheckCWUResponseFactory checkCWUResponseFactory;

    public CheckCWUResponse checkCWU(Credentials credentials, String pesel) throws Exception {
        log.info("going to checkCWU <{}>", pesel);

        SOAPMessage request = checkCWURequestFactory.create(credentials, pesel);

        SOAPMessage response = brokerClient.send(request);

        checkCWUStatusResponsePersistence.persist(pesel, response);

        if (response.getSOAPBody().hasFault()) {
            log.warn("Response for <{}> contains fault", pesel);
        }

        CheckCWUResponse checkCWUResponse = checkCWUResponseFactory.create(response);

        log.info("checkCWU completed");

        return checkCWUResponse;
    }
}
