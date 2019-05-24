package pl.konczak.nzoz.ewus.web.restapi.ewus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.config.EwusCheckCWUHealthIndicator;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.authentication.LoginService;
import pl.konczak.nzoz.ewus.domain.authentication.LogoutService;
import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(EwusApi.class);

    private final LoginService loginService;

    private final LogoutService logoutService;

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final CheckStatusResponseFactory checkStatusResponseFactory;

    public EwusApi(LoginService loginService,
                   LogoutService logoutService,
                   CheckCWUStatusFacade checkCWUStatusFacade,
                   CheckStatusResponseFactory checkStatusResponseFactory) {
        this.loginService = loginService;
        this.logoutService = logoutService;
        this.checkCWUStatusFacade = checkCWUStatusFacade;
        this.checkStatusResponseFactory = checkStatusResponseFactory;
    }

    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public HttpEntity<LoginResponse> loginSecond() throws Exception {
        LOGGER.info("/ewus/login");

        Credentials credentials = loginService.login();

        logoutService.logout(credentials);

        return new ResponseEntity<>(new LoginResponse(credentials), HttpStatus.OK);
    }

    @RequestMapping(value = "/check",
                    method = RequestMethod.GET,
                    params = {"pesel"})
    public HttpEntity<CheckStatusResponse> check(@RequestParam("pesel") String pesel) throws Exception {
        LOGGER.info("/ewus/check?pesel={}", pesel);

        CheckCWUResponse checkCWUResponse = checkCWUStatusFacade.checkCWU(pesel);

        CheckStatusResponse checkStatusResponse = checkStatusResponseFactory.create(checkCWUResponse);

        return new ResponseEntity<>(checkStatusResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/check/all",
                    method = RequestMethod.GET)
    public HttpEntity<Void> checkAll() throws Exception {
        LOGGER.info("/ewus/check/all");
        checkCWUStatusFacade.checkCWUForAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
