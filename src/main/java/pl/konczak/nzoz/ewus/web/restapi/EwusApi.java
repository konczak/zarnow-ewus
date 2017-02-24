package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.db.AccessDatabse;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.CheckCWUStatusFacade;
import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;
import pl.konczak.nzoz.ewus.client.ewus.auth.LoginService;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private final LoginService loginService;

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final CheckStatusResponseFactory checkStatusResponseFactory;

    public EwusApi(LoginService loginService,
            CheckCWUStatusFacade checkCWUStatusFacade,
            CheckStatusResponseFactory checkStatusResponseFactory) {
        this.loginService = loginService;
        this.checkCWUStatusFacade = checkCWUStatusFacade;
        this.checkStatusResponseFactory = checkStatusResponseFactory;
    }

    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public HttpEntity<LoginResponse> loginSecond() throws Exception {
        Credentials credentials = loginService.login();

        return new ResponseEntity<>(new LoginResponse(credentials), HttpStatus.OK);
    }

    @RequestMapping(value = "/check",
                    method = RequestMethod.GET,
                    params = {"pesel"})
    public HttpEntity<CheckStatusResponse> check(@RequestParam("pesel") String pesel) throws Exception {
        CheckCWUResponse checkCWUResponse = checkCWUStatusFacade.checkCWU(pesel);

        CheckStatusResponse checkStatusResponse = checkStatusResponseFactory.create(checkCWUResponse);

        return new ResponseEntity<>(checkStatusResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/db",
                    method = RequestMethod.GET)
    public HttpEntity<Void> db() {
        AccessDatabse accessDatabse = new AccessDatabse();

        accessDatabse.connect();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
