package pl.konczak.nzoz.ewus.domain.checkcwu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import javax.xml.soap.SOAPMessage;

@Component
public class CheckCWUStatusResponsePersistenceSuffixFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusResponsePersistenceSuffixFactory.class);

    private static final String FAIL = "FAIL";

    private static final String UBEZPIECZONY = "UBEZPIECZONY";

    private static final String NIE_UBEZPIECZONY = "NIE_UBEZPIECZONY";

    private final CheckCWUResponseFactory checkCWUResponseFactory;

    public CheckCWUStatusResponsePersistenceSuffixFactory(CheckCWUResponseFactory checkCWUResponseFactory) {
        this.checkCWUResponseFactory = checkCWUResponseFactory;
    }

    public String create(SOAPMessage sOAPMessage) {
        String suffix;
        try {
            suffix = createUnsafe(sOAPMessage);
        } catch (Exception exception) {
            LOGGER.error("Failed to create suffix for CheckCWUStatusResponse", exception);
            suffix = FAIL;
        }
        return suffix;
    }

    private String createUnsafe(SOAPMessage sOAPMessage) throws Exception {
        if (sOAPMessage.getSOAPBody().hasFault()) {
            return FAIL;
        }

        CheckCWUResponse checkCWUResponse = checkCWUResponseFactory.create(sOAPMessage);

        return CheckCWUResponseAnalyzeUtil.isUbezpieczony(checkCWUResponse) ? UBEZPIECZONY : NIE_UBEZPIECZONY;
    }
}
