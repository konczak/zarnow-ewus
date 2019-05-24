package pl.konczak.nzoz.ewus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.db.PacjentRepository;

@Component
public class BazapHealthIndicator
        implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BazapHealthIndicator.class);

    private final PacjentRepository pacjentRepository;

    public BazapHealthIndicator(PacjentRepository pacjentRepository) {
        this.pacjentRepository = pacjentRepository;
    }

    @Override
    public Health health() {
        LOGGER.info("BazapHealthIndicator starts");

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
