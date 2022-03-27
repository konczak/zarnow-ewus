package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckCWUForManyPeselsReport {

    private final String name;

    private final long countOfCheckedPesel;

    private final long countOfAllPesel;

    private final long processTimeInSeconds;

    private final Set<String> failedPesels;

    private final Set<String> peseleBezUbezpieczenia;

    public Set<String> getFailedPesels() {
        return Collections.unmodifiableSet(failedPesels);
    }

    public int getCountOfFailedPesels() {
        return this.failedPesels.size();
    }

    public Set<String> getPeseleBezUbezpieczenia() {
        return Collections.unmodifiableSet(peseleBezUbezpieczenia);
    }

    public int getCountOfPeseleBezUbezpieczenia() {
        return this.peseleBezUbezpieczenia.size();
    }

    public static CheckCWUForManyPeselsBuilder builder() {
        return new CheckCWUForManyPeselsBuilder();
    }

    public static class CheckCWUForManyPeselsBuilder {
        private final String REPORT_NAME_CHECK_ALL = "CheckCWUForAllReport";
        private final String REPORT_NAME_CHECK_LISTED = "CheckCWUForListedPeselsReport";

        private String name;

        private long countOfAllPesel;

        private long countOfCheckedPesel;

        private long startProcess;

        private long endProcess;

        private final Set<String> failedPesels;

        private final Set<String> peseleBezUbezpieczenia;

        CheckCWUForManyPeselsBuilder() {
            this.name = null;
            this.countOfAllPesel = 0;
            this.countOfCheckedPesel = 0;
            this.startProcess = 0;
            this.endProcess = 0;
            this.failedPesels = new HashSet<>();
            this.peseleBezUbezpieczenia = new HashSet<>();
        }

        public CheckCWUForManyPeselsBuilder withNameForAll() {
            this.name = REPORT_NAME_CHECK_ALL;
            return this;
        }

        public CheckCWUForManyPeselsBuilder withNameForListed() {
            this.name = REPORT_NAME_CHECK_LISTED;
            return this;
        }

        public CheckCWUForManyPeselsBuilder withCountOfAllPesel(long countOfAllPesel) {
            this.countOfAllPesel = countOfAllPesel;
            return this;
        }

        public CheckCWUForManyPeselsBuilder incrementCountOfCheckedPesel() {
            this.countOfCheckedPesel++;
            return this;
        }

        public CheckCWUForManyPeselsBuilder registerStart() {
            this.startProcess = System.currentTimeMillis();
            return this;
        }

        public CheckCWUForManyPeselsBuilder registerEnd() {
            this.endProcess = System.currentTimeMillis();
            return this;
        }

        public CheckCWUForManyPeselsBuilder addFailedPesel(String pesel) {
            this.failedPesels.add(pesel);
            return this;
        }

        public CheckCWUForManyPeselsBuilder addPeselBezUbezpieczenia(String pesel) {
            this.peseleBezUbezpieczenia.add(pesel);
            return this;
        }

        public CheckCWUForManyPeselsReport build() {
            if (name == null
                    || name.trim().isEmpty()) {
                throw new IllegalStateException("report name has to be specified");
            }
            if (startProcess == 0
                    || endProcess == 0) {
                throw new IllegalStateException("start and end of process has to be registered");
            }
            if (countOfAllPesel == 0
                    && (countOfCheckedPesel != 0 || !failedPesels.isEmpty())) {
                throw new IllegalStateException("total count of all pesel is not set");
            }

            return new CheckCWUForManyPeselsReport(
                    name,
                    countOfCheckedPesel,
                    countOfAllPesel,
                    (endProcess - startProcess) / 1000,
                    new HashSet<>(failedPesels),
                    new HashSet<>(peseleBezUbezpieczenia)
            );
        }

    }

}
