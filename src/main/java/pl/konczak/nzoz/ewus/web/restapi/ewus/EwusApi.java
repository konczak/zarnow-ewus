package pl.konczak.nzoz.ewus.web.restapi.ewus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.authentication.LoginService;
import pl.konczak.nzoz.ewus.domain.authentication.LogoutService;
import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

@Slf4j
@RestController
@RequestMapping("/ewus")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EwusApi {

    private final LoginService loginService;

    private final LogoutService logoutService;

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final CheckStatusResponseFactory checkStatusResponseFactory;

    @RequestMapping(value = "/login",
            method = RequestMethod.GET)
    public HttpEntity<LoginResponse> loginSecond() throws Exception {
        log.info("/ewus/login");

        Credentials credentials = loginService.login();

        logoutService.logout(credentials);

        return new ResponseEntity<>(new LoginResponse(credentials), HttpStatus.OK);
    }

    @RequestMapping(value = "/check",
            method = RequestMethod.GET,
            params = {"pesel"})
    public HttpEntity<CheckStatusResponse> check(@RequestParam("pesel") String pesel) throws Exception {
        log.info("/ewus/check?pesel={}", pesel);

        CheckCWUResponse checkCWUResponse = checkCWUStatusFacade.checkCWU(pesel);

        CheckStatusResponse checkStatusResponse = checkStatusResponseFactory.create(checkCWUResponse);

        return new ResponseEntity<>(checkStatusResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/check/all",
            method = RequestMethod.GET)
    public HttpEntity<Void> checkAll() throws Exception {
        log.info("/ewus/check/all");
        checkCWUStatusFacade.checkCWUForAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check/listed",
            method = RequestMethod.GET)
    public HttpEntity<Void> checkListed() throws Exception {
        log.info("/ewus/check/listed");
        checkCWUStatusFacade.checkCWUForListed();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
