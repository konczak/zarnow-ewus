package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Textload {

    @XmlElement(name = "status_cwu_odp",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private StatusCwuOdp statusCwuOdp;

}
