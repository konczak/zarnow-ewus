package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Pacjent {

    @XmlElement(name = "data_waznosci_potwierdzenia",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String dataWaznosciPotwierdzenia;

    @XmlElement(name = "status_ubezp",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private StatusUbezp statusUbezp;

    @XmlElement(name = "imie",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String imie;

    @XmlElement(name = "nazwisko",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String nazwisko;

    @XmlElement(
            name = "informacja",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5",
            type = InformacjaDodatkowa.class
    )
    @XmlElementWrapper(name = "informacje_dodatkowe",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private List<InformacjaDodatkowa> informacjeDodatkowe;

}
