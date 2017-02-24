package pl.konczak.nzoz.ewus.client.ewus.checkcwu.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatusUbezp {

    @XmlValue
    private int status;

    @XmlAttribute(name = "ozn_rec",
                  required = false)
    private String oznRec;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOznRec() {
        return oznRec;
    }

    public void setOznRec(String oznRec) {
        this.oznRec = oznRec;
    }

}
