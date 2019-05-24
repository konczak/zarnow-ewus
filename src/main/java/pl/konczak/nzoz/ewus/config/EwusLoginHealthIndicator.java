package pl.konczak.nzoz.ewus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.authentication.LoginService;

@Component
public class EwusLoginHealthIndicator
        implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EwusLoginHealthIndicator.class);

    private final LoginService loginService;

    public EwusLoginHealthIndicator(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Health health() {
        LOGGER.info("EwusLoginHealthIndicator starts");

        Credentials credentials;
        try {
            loginService.clearCachedCredentials();
            credentials = loginService.login();
        } catch (Exception e) {
            return Health.down(e)
                    .build();
        } finally {
            loginService.clearCachedCredentials();
        }
        if (credentials == null) {
            return Health.down()
                    .withDetail("EwusLogin", "Failed to get Credentials of EwusLogin")
                    .build();
        }

        return Health.up()
                .build();
    }

}
