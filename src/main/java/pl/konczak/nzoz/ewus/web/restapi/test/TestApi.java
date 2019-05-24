package pl.konczak.nzoz.ewus.web.restapi.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.konczak.nzoz.ewus.db.PacjentPagableRepository;
import pl.konczak.nzoz.ewus.db.Patient;

@Slf4j
@RestController
@RequestMapping("/test")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TestApi {

    private final PacjentPagableRepository pacjentPagableRepository;

    @RequestMapping(value = "/load/pesel",
            method = RequestMethod.GET)
    public HttpEntity<Page<Patient>> loadPeselList(Pageable pageable) {
        log.info("/test/load/pesel");
        Page<Patient> pageOfPesel = pacjentPagableRepository.findPage(pageable.getPageNumber(), pageable.getPageSize());

        return new ResponseEntity<>(pageOfPesel, HttpStatus.OK);
    }
}
