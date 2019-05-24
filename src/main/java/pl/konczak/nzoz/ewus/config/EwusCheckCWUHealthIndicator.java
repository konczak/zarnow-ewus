package pl.konczak.nzoz.ewus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

@Slf4j
@Component
public class EwusCheckCWUHealthIndicator
        implements HealthIndicator {

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final String pesel;

    EwusCheckCWUHealthIndicator(CheckCWUStatusFacade checkCWUStatusFacade,
                                @Value("${health.ewus.checkcwu.pesel}") String pesel) {
        this.checkCWUStatusFacade = checkCWUStatusFacade;
        this.pesel = pesel;
    }

    @Override
    public Health health() {
        log.info("EwusCheckCWUHealthIndicator starts");

        CheckCWUResponse checkCWUResponse;
        try {
            checkCWUResponse = checkCWUStatusFacade.checkCWU(pesel);
        } catch (Exception e) {
            return Health.down(e)
                    .build();
        }
        if (checkCWUResponse == null) {
            return Health.down()
                    .withDetail("EwusCheckCWU", "Failed to execute EwusCheckCWU for " + pesel)
                    .build();
        }

        return Health.up()
                .build();
    }

}
