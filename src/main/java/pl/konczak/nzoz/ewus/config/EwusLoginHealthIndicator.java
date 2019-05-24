package pl.konczak.nzoz.ewus.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.authentication.LoginService;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EwusLoginHealthIndicator
        implements HealthIndicator {

    private final LoginService loginService;

    @Override
    public Health health() {
        log.info("EwusLoginHealthIndicator starts");

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
