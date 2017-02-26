package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatusCwuOdp {

    @XmlAttribute(name = "id_operacji")
    private String idOperacji;

    @XmlAttribute(name = "data_czas_operacji")
    private String dataCzasOperacji;

    @XmlElement(name = "status_cwu",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private int statusCwu;

    @XmlElement(name = "numer_pesel",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String pesel;

    @XmlElement(name = "system_nfz",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private SystemNfz systemNfz;

    @XmlElement(name = "swiad",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private Swiad swiad;

    @XmlElement(name = "pacjent",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private Pacjent pacjent;

    @XmlElement(name = "signature",
                namespace = "http://www.w3.org/2000/09/xmldsig#")
    private Signature signature;

    public String getIdOperacji() {
        return idOperacji;
    }

    public void setIdOperacji(String idOperacji) {
        this.idOperacji = idOperacji;
    }

    public String getDataCzasOperacji() {
        return dataCzasOperacji;
    }

    public void setDataCzasOperacji(String dataCzasOperacji) {
        this.dataCzasOperacji = dataCzasOperacji;
    }

    public int getStatusCwu() {
        return statusCwu;
    }

    public void setStatusCwu(int statusCwu) {
        this.statusCwu = statusCwu;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public SystemNfz getSystemNfz() {
        return systemNfz;
    }

    public void setSystemNfz(SystemNfz systemNfz) {
        this.systemNfz = systemNfz;
    }

    public Swiad getSwiad() {
        return swiad;
    }

    public void setSwiad(Swiad swiad) {
        this.swiad = swiad;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

}
