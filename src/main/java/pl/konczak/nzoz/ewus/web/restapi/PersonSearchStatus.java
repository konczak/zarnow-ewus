package pl.konczak.nzoz.ewus.web.restapi;

public enum PersonSearchStatus {

    PERSON_WITH_PESEL_DOES_NOT_EXISTS(0),
    PERSON_WITH_PESEL_EXISTS(1),
    PERSON_EXISTS_BUT_PESEL_IS_CANCELED(-1);

    private final int status;

    private PersonSearchStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static PersonSearchStatus convert(int status) {
        for (PersonSearchStatus searchStatus : PersonSearchStatus.values()) {
            if (searchStatus.getStatus() == status) {
                return searchStatus;
            }
        }
        throw new IllegalArgumentException("Person status for value <" + status + "> is not supported");
    }

}
