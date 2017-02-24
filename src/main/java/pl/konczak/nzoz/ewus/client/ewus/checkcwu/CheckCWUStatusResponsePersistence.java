package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.config.EwusPersistenceConfiguration;

import javax.xml.soap.SOAPMessage;

@Component
public class CheckCWUStatusResponsePersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusResponsePersistence.class);

    private final EwusPersistenceConfiguration ewusPersistenceConfiguration;

    public CheckCWUStatusResponsePersistence(EwusPersistenceConfiguration ewusPersistenceConfiguration) {
        this.ewusPersistenceConfiguration = ewusPersistenceConfiguration;
    }

    void persist(String pesel, SOAPMessage message) {
        if (ewusPersistenceConfiguration.isExecute()) {
            return;
        }
        //TODO save to file

        LOGGER.info("CheckCWUStatusResponse save to file");
    }
}
