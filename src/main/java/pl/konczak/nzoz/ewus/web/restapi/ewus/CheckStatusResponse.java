package pl.konczak.nzoz.ewus.web.restapi.ewus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckStatusResponse {

    private final PersonSearchStatus status;

    private final String pesel;

    private final String imie;

    private final String nazwisko;

    private final boolean ubezpieczony;

    private final String oznaczenieRecept;

}
