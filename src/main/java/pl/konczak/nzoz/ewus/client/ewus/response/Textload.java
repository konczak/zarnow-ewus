package pl.konczak.nzoz.ewus.client.ewus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Textload {

    @XmlElement(name = "status_cwu_odp",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private StatusCwuOdp statusCwuOdp;

    public StatusCwuOdp getStatusCwuOdp() {
        return statusCwuOdp;
    }

    public void setStatusCwuOdp(StatusCwuOdp statusCwuOdp) {
        this.statusCwuOdp = statusCwuOdp;
    }

}
