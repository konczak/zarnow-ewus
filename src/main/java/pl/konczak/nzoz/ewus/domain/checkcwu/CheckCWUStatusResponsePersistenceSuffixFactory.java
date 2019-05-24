package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import javax.xml.soap.SOAPMessage;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUStatusResponsePersistenceSuffixFactory {

    private static final String FAIL = "FAIL";

    private static final String UBEZPIECZONY = "UBEZPIECZONY";

    private static final String NIE_UBEZPIECZONY = "NIE_UBEZPIECZONY";

    private final CheckCWUResponseFactory checkCWUResponseFactory;

    public String create(SOAPMessage sOAPMessage) {
        String suffix;
        try {
            suffix = createUnsafe(sOAPMessage);
        } catch (Exception exception) {
            log.error("Failed to create suffix for CheckCWUStatusResponse", exception);
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
