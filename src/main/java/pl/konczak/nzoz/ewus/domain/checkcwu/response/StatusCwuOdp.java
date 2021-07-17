package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusCwuOdp {

    @XmlAttribute(name = "id_operacji")
    private String idOperacji;

    @XmlAttribute(name = "data_czas_operacji")
    private String dataCzasOperacji;

    @XmlElement(name = "status_cwu",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private int statusCwu;

    @XmlElement(name = "numer_pesel",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String pesel;

    @XmlElement(name = "system_nfz",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private SystemNfz systemNfz;

    @XmlElement(name = "swiad",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private Swiad swiad;

    @XmlElement(name = "pacjent",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private Pacjent pacjent;

    @XmlElement(name = "signature",
            namespace = "http://www.w3.org/2000/09/xmldsig#")
    private Signature signature;

}
