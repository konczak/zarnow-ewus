package pl.konczak.nzoz.ewus.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PatientStatus {

    DEAD("Z"),
    COMPLETE("K"),
    UNDECLARED("N"),
    CHARGABLE("P"),
    UNKNOWN("-");

    private final String flag;

    public static PatientStatus mapByFlag(String flag) {
        for (PatientStatus patientStatus : PatientStatus.values()) {
            if (patientStatus.getFlag().equals(flag)) {
                return patientStatus;
            }
        }
        return UNKNOWN;
    }

}
