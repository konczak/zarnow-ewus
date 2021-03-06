package pl.konczak.nzoz.ewus.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Patient
        implements Comparable<Patient> {

    private final String pesel;

    private final PatientStatus patientStatus;

    public boolean isDead() {
        return PatientStatus.DEAD == patientStatus;
    }

    @Override
    public int compareTo(Patient o) {
        if (pesel == null) {
            return 1;
        }
        return pesel.compareTo(o.pesel);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.pesel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        return Objects.equals(this.pesel, other.pesel);
    }

}
