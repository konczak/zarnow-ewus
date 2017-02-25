package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;
import pl.konczak.nzoz.ewus.client.ewus.auth.LoginService;
import pl.konczak.nzoz.ewus.client.ewus.auth.LogoutService;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;


@Service
public class CheckCWUStatusFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusFacade.class);

    private final LoginService loginService;

    private final CheckCWUStatusService checkCWUStatusService;

    private final LogoutService logoutService;

    public CheckCWUStatusFacade(LoginService loginService, CheckCWUStatusService checkCWUStatusService, LogoutService logoutService) {
        this.loginService = loginService;
        this.checkCWUStatusService = checkCWUStatusService;
        this.logoutService = logoutService;
    }

    public CheckCWUResponse checkCWU(String pesel) throws Exception {
        LOGGER.debug("start checkCWU proces");

        Credentials credentials = loginService.login();

        CheckCWUResponse checkCWUResponse = checkCWUStatusService.checkCWU(credentials, pesel);

        logoutService.logout(credentials);

        LOGGER.debug("checkCWU proces completed");

        return checkCWUResponse;
    }
}
