package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;

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
        return new CheckStatusResponse(personSearchStatus, pesel, imie, nazwisko, statusUbezpieczenia == 1, oznaczenieRecept);
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
}
