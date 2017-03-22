package pl.konczak.nzoz.ewus.db;

public enum PatientStatus {

    DEAD("Z"),
    COMPLETE("K"),
    UNDECLARED("N"),
    CHARGABLE("P"),
    UNKNOWN("-");

    private final String flag;

    private PatientStatus(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static PatientStatus mapByFlag(String flag) {
        for (PatientStatus patientStatus : PatientStatus.values()) {
            if (patientStatus.getFlag().equals(flag)) {
                return patientStatus;
            }
        }
        return UNKNOWN;
    }

}
