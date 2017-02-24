package pl.konczak.nzoz.ewus.web.restapi;

public class CheckStatusResponse {

    private final PersonSearchStatus status;

    private final String pesel;

    private final String imie;

    private final String nazwisko;

    private final boolean ubezpieczony;

    private final String oznaczenieRecept;

    public CheckStatusResponse(PersonSearchStatus personSearchStatus,
            String pesel,
            String imie, String nazwisko,
            boolean ubezpieczony,
            String oznaczenieRecept) {
        this.status = personSearchStatus;
        this.pesel = pesel;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.oznaczenieRecept = oznaczenieRecept;
        this.ubezpieczony = ubezpieczony;
    }

    public PersonSearchStatus getStatus() {
        return status;
    }

    public String getPesel() {
        return pesel;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public boolean isUbezpieczony() {
        return ubezpieczony;
    }

    public String getOznaczenieRecept() {
        return oznaczenieRecept;
    }

}
