package pl.konczak.nzoz.ewus.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.db.PacjentRepository;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BazapHealthIndicator
        implements HealthIndicator {

    private final PacjentRepository pacjentRepository;

    @Override
    public Health health() {
        log.info("BazapHealthIndicator starts");

        try {
            pacjentRepository.testAccess();
        } catch (Exception e) {
            return Health.down(e)
                    .build();
        }

        return Health.up()
                .build();
    }

}
