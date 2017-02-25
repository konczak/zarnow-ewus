package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;
import pl.konczak.nzoz.ewus.client.ewus.auth.LoginService;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.CheckCWUStatusFacade;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;
import pl.konczak.nzoz.ewus.db.PacjentPagableRepository;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private final LoginService loginService;

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final CheckStatusResponseFactory checkStatusResponseFactory;

    private final PacjentPagableRepository pacjentPagableRepository;

    public EwusApi(LoginService loginService,
            CheckCWUStatusFacade checkCWUStatusFacade,
            CheckStatusResponseFactory checkStatusResponseFactory,
            PacjentPagableRepository pacjentPagableRepository) {
        this.loginService = loginService;
        this.checkCWUStatusFacade = checkCWUStatusFacade;
        this.checkStatusResponseFactory = checkStatusResponseFactory;
        this.pacjentPagableRepository = pacjentPagableRepository;
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

    @RequestMapping(value = "/check/all",
                    method = RequestMethod.GET)
    public HttpEntity<Void> checkAll() throws Exception {
        checkCWUStatusFacade.checkCWUForAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/load/pesel",
                    method = RequestMethod.GET)
    public HttpEntity<Page<String>> loadPeselList(Pageable pageable) {
        Page<String> pageOfPesel = pacjentPagableRepository.findPage(pageable.getPageNumber(), pageable.getPageSize());

        return new ResponseEntity<>(pageOfPesel, HttpStatus.OK);
    }
}
