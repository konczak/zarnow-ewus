package pl.konczak.nzoz.ewus.web.restapi.ewus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdditionalInfo {
    private final String kod;
    private final String poziom;
    private final String wartosc;
}
