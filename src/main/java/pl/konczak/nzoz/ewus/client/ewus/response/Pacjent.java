package pl.konczak.nzoz.ewus.client.ewus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Pacjent {

    @XmlElement(name = "data_waznosci_potwierdzenia",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String dataWaznosciPotwierdzenia;

    @XmlElement(name = "status_ubezp",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String statusUbezp;

    @XmlElement(name = "imie",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String imie;

    @XmlElement(name = "nazwisko",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String nazwisko;

    public String getDataWaznosciPotwierdzenia() {
        return dataWaznosciPotwierdzenia;
    }

    public void setDataWaznosciPotwierdzenia(String dataWaznosciPotwierdzenia) {
        this.dataWaznosciPotwierdzenia = dataWaznosciPotwierdzenia;
    }

    public String getStatusUbezp() {
        return statusUbezp;
    }

    public void setStatusUbezp(String statusUbezp) {
        this.statusUbezp = statusUbezp;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

}
