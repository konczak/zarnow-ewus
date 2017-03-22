package pl.konczak.nzoz.ewus.web.restapi.test;

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

@RestController
@RequestMapping("/test")
public class TestApi {

    private final PacjentPagableRepository pacjentPagableRepository;

    public TestApi(PacjentPagableRepository pacjentPagableRepository) {
        this.pacjentPagableRepository = pacjentPagableRepository;
    }

    @RequestMapping(value = "/load/pesel",
                    method = RequestMethod.GET)
    public HttpEntity<Page<Patient>> loadPeselList(Pageable pageable) {
        Page<Patient> pageOfPesel = pacjentPagableRepository.findPage(pageable.getPageNumber(), pageable.getPageSize());

        return new ResponseEntity<>(pageOfPesel, HttpStatus.OK);
    }
}
