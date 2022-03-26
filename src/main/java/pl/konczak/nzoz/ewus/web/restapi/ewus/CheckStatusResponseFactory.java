package pl.konczak.nzoz.ewus.web.restapi.ewus;

import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckStatusResponseFactory {

    CheckStatusResponse create(CheckCWUResponse checkCWUResponse) {
        int status = readSatus(checkCWUResponse);
        PersonSearchStatus personSearchStatus = PersonSearchStatus.convert(status);
        String pesel = readPesel(checkCWUResponse);
        String imie = readImie(checkCWUResponse);
        String nazwisko = readNazwisko(checkCWUResponse);
        String oznaczenieRecept = readOznaczenieRecept(checkCWUResponse);
        int statusUbezpieczenia = readStatusUbezpieczenia(checkCWUResponse);
        List<AdditionalInfo> informacjeDodatkowe = readInformacjeDodatkowe(checkCWUResponse);
        return new CheckStatusResponse(personSearchStatus, pesel, imie, nazwisko, statusUbezpieczenia == 1, oznaczenieRecept, informacjeDodatkowe);
    }

    private int readSatus(CheckCWUResponse checkCWUResponse) {
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getStatusCwu();
    }

    private String readPesel(CheckCWUResponse checkCWUResponse) {
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPesel();
    }

    private String readImie(CheckCWUResponse checkCWUResponse) {
        if (checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent() == null) {
            return null;
        }
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getImie();
    }

    private String readNazwisko(CheckCWUResponse checkCWUResponse) {
        if (checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent() == null) {
            return null;
        }
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getNazwisko();
    }

    private String readOznaczenieRecept(CheckCWUResponse checkCWUResponse) {
        if (checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent() == null) {
            return null;
        }
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getStatusUbezp().getOznRec();
    }

    private int readStatusUbezpieczenia(CheckCWUResponse checkCWUResponse) {
        if (checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent() == null) {
            return 0;
        }
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getStatusUbezp().getStatus();
    }

    private List<AdditionalInfo> readInformacjeDodatkowe(CheckCWUResponse checkCWUResponse) {
        if (checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent() == null
                || checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getInformacjeDodatkowe() == null) {
            return new ArrayList<>();
        }
        return checkCWUResponse.getPayload().getTextload().getStatusCwuOdp().getPacjent().getInformacjeDodatkowe()
                .stream()
                .map(informacjaDodatkowa -> new AdditionalInfo(informacjaDodatkowa.getKod(), informacjaDodatkowa.getPoziom(), informacjaDodatkowa.getWartosc()))
                .collect(Collectors.toList());
    }
}
